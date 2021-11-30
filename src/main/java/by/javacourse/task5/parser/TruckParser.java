package by.javacourse.task5.parser;

import java.util.List;

import by.javacourse.task5.entity.Truck;

public interface TruckParser {

	List<Truck> parse(List<String> lines);

}
