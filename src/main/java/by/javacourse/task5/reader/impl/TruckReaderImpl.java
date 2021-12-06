package by.javacourse.task5.reader.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.javacourse.task5.exception.TruckException;
import by.javacourse.task5.reader.TruckReader;

public class TruckReaderImpl implements TruckReader {

	static Logger logger = LogManager.getLogger();

	@Override
	public List<String> read(String pathToFile) throws TruckException {

		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(pathToFile);
		if (inputStream == null) {
			logger.error("File " + pathToFile + " does not exsist or is not available.");
			throw new TruckException("File " + pathToFile + " does not exsist or is not available.");
		}
		
		List<String> lines = new ArrayList<String>();
		try {
			String fileContent = new String(inputStream.readAllBytes());
			lines = Stream.of(fileContent.split(System.lineSeparator())).toList();

		} catch (IOException e) {
			logger.error("IO Exception while working on the file " + pathToFile + ".");
			throw new TruckException(
					"Failed or interrupted I/O operations while working on the file " + pathToFile + ".", e);
		}
		return lines;
	}

}
