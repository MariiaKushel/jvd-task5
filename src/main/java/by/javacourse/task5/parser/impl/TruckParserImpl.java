package by.javacourse.task5.parser.impl;

import java.util.List;
import java.util.stream.Stream;

import by.javacourse.task5.entity.Truck;
import by.javacourse.task5.entity.Truck.Task;
import by.javacourse.task5.parser.TruckParser;

public class TruckParserImpl implements TruckParser{

	private static final String LINES_SPLITTER = ";";
	
	@Override
	public List<Truck> parse(List<String> lines) {
		
		List<Truck> trucks = lines.stream()
		.map(l -> Stream.of(l.split(LINES_SPLITTER)).toList())
		.map(e -> new Truck (e.get(0), Boolean.parseBoolean(e.get(1)), Task.valueOf(e.get(2))))
		.toList();
		
		return trucks;
	}

}
