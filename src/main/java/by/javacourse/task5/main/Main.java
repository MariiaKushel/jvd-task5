package by.javacourse.task5.main;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import by.javacourse.task5.entity.Truck;
import by.javacourse.task5.entity.Truck.Task;

public class Main {

	public static void main(String[] args) {

		ExecutorService executor = Executors.newFixedThreadPool(100);

		for (int i = 0; i < 60; i++) {

			String licensePlate = "TRUCK-" + i;

			boolean perishableGoods;
			if (new Random().nextInt(2) == 1) {
				perishableGoods = false;
			} else {
				perishableGoods = true;
			}

			Task task;
			if (new Random().nextInt(2) == 1) {
				task = Task.LOADING;
			} else {
				task = Task.UNLOADING;
			}

			executor.execute(new Truck(licensePlate, perishableGoods, task));
		}

		try {
			TimeUnit.SECONDS.sleep(10);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		executor.shutdown();

	}

}
