package by.javacourse.task5.util;

public class GeneratorTerminalId {

	private static long counter;

	public static long generete() {
		return counter++;
	}

}
