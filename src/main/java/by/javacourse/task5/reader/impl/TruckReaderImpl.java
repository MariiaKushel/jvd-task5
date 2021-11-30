package by.javacourse.task5.reader.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

		Path path = Paths.get(pathToFile);
		if (!Files.exists(path)) {
			logger.error("File " + pathToFile + " does not exsist or is not available.");
			throw new TruckException("File " + pathToFile + " does not exsist or is not available.");
		}

		List<String> lines = new ArrayList<String>();
		try {
			lines = Files.readAllLines(path);
		} catch (IOException e) {
			logger.error("IO Exception while working on the file " + pathToFile + ".");
			throw new TruckException(
					"Failed or interrupted I/O operations while working on the file " + pathToFile + ".", e);
		}
		return lines;
	}

}
