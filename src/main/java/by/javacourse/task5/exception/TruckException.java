package by.javacourse.task5.exception;

public class TruckException extends Exception {

	public TruckException() {
		super();
	}

	public TruckException(String message) {
		super(message);
	}

	public TruckException(Exception e) {
		super(e);
	}

	public TruckException(String message, Exception e) {
		super(message, e);
	}
}
