package by.javacourse.task5.entity;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.javacourse.task5.util.GeneratorTerminalId;

public class Terminal {

	static Logger logger = LogManager.getLogger();

	public static final int MAX_LOAD_UNLOAD_TIME = 100;

	private long terminalId;

	public Terminal() {
		terminalId = GeneratorTerminalId.generete();
	}

	public void loadTruck() {

		LogisticsCenter center = LogisticsCenter.getInstance();
		center.removeGoods(Truck.TRUCK_CAPACITY);

		try {
			logger.info(Thread.currentThread().getName() + " loading...");
			TimeUnit.MILLISECONDS.sleep(new Random().nextInt(MAX_LOAD_UNLOAD_TIME));
		} catch (InterruptedException e) {
			logger.error(Thread.currentThread().getName() + "was interrupted.");
			Thread.currentThread().interrupt();
		}
	}

	public void unloadTruck() {

		LogisticsCenter center = LogisticsCenter.getInstance();
		center.addGoods(Truck.TRUCK_CAPACITY);

		try {
			logger.info(Thread.currentThread().getName() + " unloading...");
			TimeUnit.MILLISECONDS.sleep(new Random().nextInt(MAX_LOAD_UNLOAD_TIME));
		} catch (InterruptedException e) {
			logger.error(Thread.currentThread().getName() + "was interrupted.");
			Thread.currentThread().interrupt();
		}
	}

	public long getTerminalId() {
		return terminalId;
	}

}
