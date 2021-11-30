package by.javacourse.task5.reader;

import java.util.List;

import by.javacourse.task5.exception.TruckException;

public interface TruckReader {

	List<String> read(String pathToFile) throws TruckException;

}
