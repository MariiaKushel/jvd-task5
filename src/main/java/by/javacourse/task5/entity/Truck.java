package by.javacourse.task5.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Truck extends Thread {

	static Logger logger = LogManager.getLogger();

	public static final int TRUCK_CAPACITY = 60;
	private String licensePlate; // analog id
	private boolean perishableGoods;
	private TruckState truckState;
	private Task task;

	public enum TruckState {
		NEW, IN_PROCESS, COMPLITED
	}

	public enum Task {
		LOADING, UNLOADING
	}

	public Truck(String licensePlate, boolean perishableGoods, Task task) {
		this.licensePlate = licensePlate;
		this.perishableGoods = perishableGoods;
		this.task = task;
		this.truckState = TruckState.NEW;
	}

	@Override
	public void run() {

		this.truckState = TruckState.IN_PROCESS;

		LogisticsCenter center = LogisticsCenter.getInstance();
		Terminal terminal = center.getTerminal(perishableGoods);

		try {
			switch (task) {
			case LOADING -> terminal.loadTruck();
			case UNLOADING -> terminal.unloadTruck();
			}
		} finally {
			center.releaseTerminal(terminal);
		}

		this.truckState = TruckState.COMPLITED;

	}

	public boolean isPerishableGoods() {
		return perishableGoods;
	}

	public TruckState getTruckState() {
		return truckState;
	}

	public Task getTask() {
		return task;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

}
