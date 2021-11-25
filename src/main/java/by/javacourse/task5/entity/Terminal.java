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

	public void loadTruck() {

		LogisticsCenter center = LogisticsCenter.getInstance();
		center.removeGoods(Truck.TRUCK_CAPACITY);

		try {
			logger.info(Thread.currentThread().getName()  + " loading...");
			TimeUnit.MILLISECONDS.sleep(new Random().nextInt(100));
		} catch (InterruptedException e) {
			logger.error("InterruptedException");
		}
	}

	public void unloadTruck() {
		
		LogisticsCenter center = LogisticsCenter.getInstance();
		center.addGoods(Truck.TRUCK_CAPACITY);
		
		try {
			logger.info(Thread.currentThread().getName() + " unloading...");
			TimeUnit.MILLISECONDS.sleep(new Random().nextInt(100));
		} catch (InterruptedException e) {
			logger.error("InterruptedException");
		}
	}

	public long getTerminalId() {
		return terminalId;
	}

}
