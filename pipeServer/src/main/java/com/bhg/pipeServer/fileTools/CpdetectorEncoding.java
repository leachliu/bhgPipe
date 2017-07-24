package com.bhg.pipeServer.fileTools;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.stream.Stream;

import org.apache.log4j.Logger;

import com.bhg.pipeServer.config.ErrorCode;
import com.bhg.pipeServer.exception.PipeException;

import info.monitorenter.cpdetector.io.ASCIIDetector;
import info.monitorenter.cpdetector.io.ByteOrderMarkDetector;
import info.monitorenter.cpdetector.io.CodepageDetectorProxy;
import info.monitorenter.cpdetector.io.JChardetFacade;
import info.monitorenter.cpdetector.io.ParsingDetector;
import info.monitorenter.cpdetector.io.UnicodeDetector;

/**
 * <p>
 * 获取流编码,不保证完全正确，设置检测策略 isFast为true为快速检测策略，false为正常检测 InputStream
 * 支持mark,则会在检测后调用reset，外部可重新使用。 InputStream 流没有关闭。
 * </p>
 * 
 * <p>
 * 如果采用快速检测编码方式,最多会扫描8个字节，依次采用的{@link UnicodeDetector}，
 * {@link byteOrderMarkDetector}， {@link JChardetFacade}， {@link ASCIIDetector}
 * 检测。对于一些标准的unicode编码，适合这个方式或者对耗时敏感的。
 * </p>
 * 
 * <p>
 * 采用正常检测，读取指定字节数，如果没有指定，默认读取全部字节检测，依次采用的{@link byteOrderMarkDetector}，
 * {@link parsingDetector}，{@link JChardetFacade}， {@link ASCIIDetector}检测。
 * 字节越多检测时间越长，正确率较高。
 * </p>
 * 
 * @author WuKong
 *
 */
public class CpdetectorEncoding {

	public static final String DEFAULT = "UTF-8";

	private static final Logger logger = Logger.getLogger(CpdetectorEncoding.class);

	private static final int CHECK_DATA_LENGTH = 1024;

	/**
	 * <p>
	 * 获取流编码,不保证完全正确，设置检测策略 isFast为true为快速检测策略，false为正常检测 InputStream
	 * 支持mark,则会在检测后调用reset，外部可重新使用。 InputStream 流没有关闭。
	 * </p>
	 * 
	 * <p>
	 * 如果采用快速检测编码方式,最多会扫描8个字节，依次采用的{@link UnicodeDetector}，
	 * {@link byteOrderMarkDetector}， {@link JChardetFacade}，
	 * {@link ASCIIDetector}检测。对于一些标准的unicode编码，适合这个方式或者对耗时敏感的。
	 * </p>
	 * 
	 * <p>
	 * 采用正常检测，读取指定字节数，如果没有指定，默认读取全部字节检测，依次采用的{@link byteOrderMarkDetector}，
	 * {@link parsingDetector}，{@link JChardetFacade}， {@link ASCIIDetector}检测。
	 * 字节越多检测时间越长，正确率较高。
	 * </p>
	 *
	 * @param in
	 *            输入流 isFast 是否采用快速检测编码方式
	 * @return Charset The character are now - hopefully -
	 *         correct。如果为null，没有检测出来。
	 * @throws IOException
	 */
	public Charset getEncoding(InputStream buffIn, boolean isFast) throws IOException {

		return getEncoding(buffIn, buffIn.available(), isFast);
	}

	public Charset getFastEncoding(InputStream buffIn) throws IOException {
		return getEncoding(buffIn, MAX_READBYTE_FAST, DEFALUT_DETECT_STRATEGY);
	}

	public Charset getEncoding(InputStream in, int size, boolean isFast) throws IOException {

		try {

			java.nio.charset.Charset charset = null;

			int tmpSize = in.available();
			size = size > tmpSize ? tmpSize : size;
			// if in support mark method,
			if (in.markSupported()) {

				if (isFast) {

					size = size > MAX_READBYTE_FAST ? MAX_READBYTE_FAST : size;
					in.mark(size++);
					charset = getFastDetector().detectCodepage(in, size);
				} else {

					in.mark(size++);
					charset = getDetector().detectCodepage(in, size);
				}
				in.reset();

			} else {

				if (isFast) {

					size = size > MAX_READBYTE_FAST ? MAX_READBYTE_FAST : size;
					charset = getFastDetector().detectCodepage(in, size);
				} else {
					charset = getDetector().detectCodepage(in, size);
				}
			}

			return charset;
		} catch (IllegalArgumentException e) {

			logger.error(e.getMessage(), e);
			throw e;
		} catch (IOException e) {

			logger.error(e.getMessage(), e);
			throw e;
		}

	}

	public Charset getEncoding(byte[] byteArr, boolean isFast) throws IOException {

		return getEncoding(byteArr, byteArr.length, isFast);
	}

	public Charset getFastEncoding(byte[] byteArr) throws IOException {

		return getEncoding(byteArr, MAX_READBYTE_FAST, DEFALUT_DETECT_STRATEGY);
	}

	public static Charset getEncoding(byte[] byteArr, int size, boolean isFast) throws IOException {

		size = byteArr.length > size ? size : byteArr.length;
		if (isFast) {
			size = size > MAX_READBYTE_FAST ? MAX_READBYTE_FAST : size;
		}

		ByteArrayInputStream byteArrIn = new ByteArrayInputStream(byteArr, 0, size);
		BufferedInputStream in = new BufferedInputStream(byteArrIn);

		try {

			Charset charset = null;
			if (isFast) {
				charset = getFastDetector().detectCodepage(in, size);
			} else {
				charset = getDetector().detectCodepage(in, size);
			}
			return charset;
		} catch (IllegalArgumentException e) {

			logger.error(e.getMessage(), e);
			throw e;
		} catch (IOException e) {

			logger.error(e.getMessage(), e);
			throw e;
		}

	}

	private static CodepageDetectorProxy detector = null;
	private static CodepageDetectorProxy fastDtector = null;
	private static ParsingDetector parsingDetector = new ParsingDetector(false);
	private static ByteOrderMarkDetector byteOrderMarkDetector = new ByteOrderMarkDetector();

	// default strategy use fastDtector
	private static final boolean DEFALUT_DETECT_STRATEGY = true;

	private static final int MAX_READBYTE_FAST = 8;

	private static CodepageDetectorProxy getDetector() {

		synchronized (CpdetectorEncoding.class) {
			if (detector == null) {

				detector = CodepageDetectorProxy.getInstance();
				// Add the implementations of
				// info.monitorenter.cpdetector.io.ICodepageDetector:
				// This one is quick if we deal with unicode codepages:
				detector.add(byteOrderMarkDetector);

				// The first instance delegated to tries to detect the meta
				// charset
				// attribut in html pages.
				detector.add(parsingDetector);
				// This one does the tricks of exclusion and frequency
				// detection, if
				// first implementation is
				// unsuccessful:
				detector.add(JChardetFacade.getInstance());
				detector.add(ASCIIDetector.getInstance());
			}
		}

		return detector;
	}

	private static CodepageDetectorProxy getFastDetector() {

		if (fastDtector == null) {

			fastDtector = CodepageDetectorProxy.getInstance();
			fastDtector.add(UnicodeDetector.getInstance());
			fastDtector.add(byteOrderMarkDetector);
			fastDtector.add(JChardetFacade.getInstance());
			fastDtector.add(ASCIIDetector.getInstance());
		}

		return fastDtector;
	}

	public static String getFileEncoding(String fileName) {
		try {
			BufferedInputStream stream = new BufferedInputStream(new FileInputStream(fileName));
			int fileLength = stream.available();
			int checkLen = (fileLength > CHECK_DATA_LENGTH || fileLength < 0) ? CHECK_DATA_LENGTH : fileLength;
			final byte[] bytes = new byte[checkLen];
			stream.read(bytes, 0, checkLen);
			stream.close();
			return CpdetectorEncoding.getEncoding(bytes, checkLen, false).toString();
		} catch (FileNotFoundException e) {
			throw new PipeException(ErrorCode.FILE_LOAD_FAILED);
		} catch (IOException e) {
			throw new PipeException(ErrorCode.FILE_LOAD_FAILED);
		}
	}

	public static String getStreamEncoding(InputStream inputStream) {
		try {
			BufferedInputStream stream = new BufferedInputStream(inputStream);
			int fileLength = stream.available();
			int checkLen = (fileLength > CHECK_DATA_LENGTH || fileLength < 0) ? CHECK_DATA_LENGTH : fileLength;
			final byte[] bytes = new byte[checkLen];
			stream.mark(checkLen + 1);
			stream.read(bytes, 0, checkLen);
			stream.reset();
			return CpdetectorEncoding.getEncoding(bytes, checkLen, false).toString();
		} catch (FileNotFoundException e) {
			throw new PipeException(ErrorCode.FILE_LOAD_FAILED);
		} catch (IOException e) {
			throw new PipeException(ErrorCode.FILE_LOAD_FAILED);
		}
	}

	public static void main(String[] args) {
		for (int i = 0; i < 200; i++) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					System.out.println("start...");
					System.out.println(CpdetectorEncoding.getFileEncoding(
							"/home/llq/program/test/json/kf5_bd0259cf9e313b48324f47e2e78a9903_closed"));
					System.out.println("end...");

				}
			}).start();
		}
	}
}