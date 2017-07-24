package com.bhg.posServer.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPOutputStream;

public class FileUtil {
	
	public static List<String> readLines(File file) throws IOException {
		String line = null;
		List<String> lines = new ArrayList<String>();
		BufferedReader reader = new BufferedReader(new FileReader(file));
		try {
			while ((line = reader.readLine()) != null) {
				lines.add(line);
			}
		} finally {
			reader.close();
		}
		return lines;
	}
	
	/**
	 * 获取文件夹下所有制定后缀名的文件
	 * @param rootDir
	 * @param extentions	后缀名列表
	 * @return
	 */
	public static List<File> listFilesDeep(String rootDir, String[] extentions) {
		List<File> list = new ArrayList<File>();
		doListFiles(new File(rootDir), extentions, list);
		return list;
	}

	private static void doListFiles(File currentDir, String[] extentions, List<File> list) {
		if (currentDir.isDirectory()) {
			File[] files = currentDir.listFiles();
			for (File currentFile : files) {
				if (currentFile.isDirectory()) {
					doListFiles(currentFile, extentions, list);
				} else {
					String name = currentFile.getName();
					for (String extention : extentions) {
						if (name.endsWith(extention)) {
							list.add(currentFile);
						}
					}
				}
			}
		}
	}
	
	public static void gzip(File file) {
		FileInputStream fileInputStream = null;
		GZIPOutputStream gzipOutputStream = null;
		try {
			fileInputStream = new FileInputStream(file);
			String gzipFile = file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf(".")) + ".gz";
			gzipOutputStream = new GZIPOutputStream(new FileOutputStream(gzipFile));
			byte[] buffer = new byte[1024];
			while (fileInputStream.read(buffer) != -1) {
				gzipOutputStream.write(buffer);
			}
			gzipOutputStream.flush();
			file.deleteOnExit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(fileInputStream != null) {
					fileInputStream.close();
				}
				if(gzipOutputStream != null) {
					gzipOutputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
