package com.bhg.pipeServer.fileTools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.BitSet;
import java.util.Date;

import com.bhg.pipeServer.config.ErrorCode;
import com.bhg.pipeServer.exception.PipeException;

public class BloomFilter {
	private static final int DEFAULT_SIZE = 2 << 29;
	// private static final int[] seeds = new int[] {3, 5, 7, 11, 13, 31, 37,
	// 61, 67 };
	private static final int[] seeds = new int[] {2, 3, 7, 11, 13 };
	private BitSet bits = new BitSet(DEFAULT_SIZE);
	private SimpleHash[] func = new SimpleHash[seeds.length];

	public BloomFilter() {
		for (int i = 0; i < seeds.length; i++) {
			func[i] = new SimpleHash(DEFAULT_SIZE, seeds[i]);
		}
	}

	public void add(String value) {
		for (SimpleHash f : func) {
			bits.set(f.hash(value), true);
		}
	}

	public boolean contains(String value) {
		if (value == null) {
			return false;
		}
		boolean ret = true;
		for (SimpleHash f : func) {
			ret = ret && bits.get(f.hash(value));
		}
		return ret;
	}

	public void check() {
		int trues = 0;
		int falses = 0;
		for (int i = 0; i < bits.length(); i++) {
			if (bits.get(i)) {
				trues++;
			} else {
				falses++;
			}
		}
		System.out.println("length:" + bits.length() + ", trues:" + trues + ", falses:" + falses);
	}

	// 内部类，simpleHash
	public static class SimpleHash {
		private int cap;
		private int seed;

		public SimpleHash(int cap, int seed) {
			this.cap = cap;
			this.seed = seed;
		}

		public int hash(String value) {
			int result = 0;
			int len = value.length();
			for (int i = 0; i < len; i++) {
				result = seed * result + value.charAt(i);
			}
			return (cap - 1) & result;
		}
	}


	public static void main(String[] args) {
//		 if (generateFile())
//		 return;

		// 根据sourceId获取数据文件
		String csvFileName = "C:\\data\\dataimport\\import\\qqnumbers.txt";
		Date start = new Date();
		try {

			FileReader reader = new FileReader(csvFileName);
			BufferedReader br = new BufferedReader(reader);

			String str = null;
			BloomFilter bf = new BloomFilter();
			int line = 0;
			int falses = 0;
			while ((str = br.readLine()) != null) {
				line++;
				if (line % 10000 == 0) {
					// System.out.println(line + "");
					// System.out.println(str);
				}
				if (str.length() == 0)
					continue;
				boolean bl = bf.contains(str);

				if (bl) {
					falses++;
					System.out.println(line + "," + str);
				} else {
					bf.add(str);
				}
			}
			br.close();
			Date end = new Date();
			System.out.println("falses:" + falses + ", " + (end.getTime() - start.getTime()) + "ms");
			bf.check();
		} catch (IOException e) {

			throw new PipeException(ErrorCode.FILE_LOAD_FAILED);
		}
	}
}