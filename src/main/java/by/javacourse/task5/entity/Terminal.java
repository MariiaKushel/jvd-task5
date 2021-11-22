package by.javacourse.task5.entity;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.javacourse.task5.util.GeneratorTerminalId;

public class Terminal {

	static Logger logger = LogManager.getLogger();

	private long terminalId;

	public Terminal() {
		terminalId = GeneratorTerminalId.generete();
	}

	public void loadTruck(String licensePlate) {

		LogisticsCenter center = LogisticsCenter.getInstance();
		center.removeGoods(Truck.TRUCK_CAPACITY);

		try {
			logger.info(licensePlate + " loading...");
			TimeUnit.SECONDS.sleep(new Random().nextInt(30));
		} catch (InterruptedException e) {
			logger.error("InterruptedException");
		}
	}

	public void unloadTruck(String licensePlate) {
		
		LogisticsCenter center = LogisticsCenter.getInstance();
		center.addGoods(Truck.TRUCK_CAPACITY);
		
		try {
			logger.info(licensePlate + " unloading...");
			TimeUnit.SECONDS.sleep(new Random().nextInt(5));
		} catch (InterruptedException e) {
			logger.error("InterruptedException");
		}
	}

	public long getTerminalId() {
		return terminalId;
	}

}
