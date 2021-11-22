package by.javacourse.task5.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Truck implements Runnable {

	static Logger logger = LogManager.getLogger();

	public static final int TRUCK_CAPACITY = 60;
	private String licensePlate; // analogue id
	private boolean perishableGoods;
	private TruckState state;
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
		this.state = TruckState.NEW;
	}

	@Override
	public void run() {

		this.state = TruckState.IN_PROCESS;

		LogisticsCenter center = LogisticsCenter.getInstance();
		Terminal terminal = center.getTerminal(licensePlate);
		
		switch (task) {
		case LOADING -> terminal.loadTruck(licensePlate);
		case UNLOADING -> terminal.unloadTruck(licensePlate);
		}

		center.releaseTerminal(licensePlate, terminal);

		this.state = TruckState.COMPLITED;

	}

	public boolean isPerishableGoods() {
		return perishableGoods;
	}

	public TruckState getState() {
		return state;
	}

	public Task getTask() {
		return task;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

}
