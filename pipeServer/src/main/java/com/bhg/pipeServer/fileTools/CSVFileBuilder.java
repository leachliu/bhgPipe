package com.bhg.pipeServer.fileTools;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

public class CSVFileBuilder {

	private static void buildFile() {
		OutputStreamWriter fileWriter;
		CSVPrinter csvFilePrinter;
		try {
			fileWriter = FileStreamFactory.createWriter("/home/llq/program/test/csv/1qw.csv");
			csvFilePrinter = new CSVPrinter(fileWriter,
					CSVFormat.DEFAULT.withRecordSeparator("\n").withIgnoreEmptyLines(true));
			for (int line = 0; line < 10000000; line++) {
				List<String> record = new ArrayList<String>();
				record.add(line + "");
				record.add("String_" + line);
				csvFilePrinter.printRecord(record);
			}
			csvFilePrinter.flush();
			csvFilePrinter.close();

		} catch (FileNotFoundException e) {
		} catch (IOException e1) {
		}
	}

	public static void main(String[] args) {
		buildFile();
	}
}
