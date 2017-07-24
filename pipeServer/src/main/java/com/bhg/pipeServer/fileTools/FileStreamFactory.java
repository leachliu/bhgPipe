package com.bhg.pipeServer.fileTools;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

/**
 * 统一的文件流创建工厂，所有的文件操作均采用此工厂类进行创建
 * 
 * @author llq
 *
 */
public class FileStreamFactory {
	public static OutputStreamWriter createWriter(String fileName)
			throws UnsupportedEncodingException, FileNotFoundException {
		return new OutputStreamWriter(new FileOutputStream(fileName), CpdetectorEncoding.DEFAULT);
	}

	public static PrintStream createPrintStream(String fileName)
			throws FileNotFoundException, UnsupportedEncodingException {
		return new PrintStream(fileName, CpdetectorEncoding.DEFAULT);
	}

	public static InputStreamReader createReader(String fileName)
			throws UnsupportedEncodingException, FileNotFoundException {
		String encoding = CpdetectorEncoding.getFileEncoding(fileName);
		if ("windows-1252".equals(encoding)) {
			return new InputStreamReader(new FileInputStream(fileName), "GBK");
		}
		return new InputStreamReader(new FileInputStream(fileName), encoding);
	}

	public static InputStreamReader createReaderDefault(InputStream stream)
			throws UnsupportedEncodingException, FileNotFoundException {
		return new InputStreamReader(stream, getDefaultEncoding());
	}

	public static String getDefaultEncoding() {
		return CpdetectorEncoding.DEFAULT;
	}
}
