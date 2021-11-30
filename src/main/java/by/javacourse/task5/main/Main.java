package by.javacourse.task5.main;

import java.util.List;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.javacourse.task5.entity.RevisorTimerTask;
import by.javacourse.task5.entity.Terminal;
import by.javacourse.task5.entity.Truck;
import by.javacourse.task5.exception.TruckException;
import by.javacourse.task5.parser.impl.TruckParserImpl;
import by.javacourse.task5.reader.TruckReader;
import by.javacourse.task5.reader.impl.TruckReaderImpl;

public class Main {

	static Logger logger = LogManager.getLogger();

	public static void main(String[] args) {

		Timer timer = new Timer();
		timer.schedule(new RevisorTimerTask(), Terminal.MAX_LOAD_UNLOAD_TIME, Terminal.MAX_LOAD_UNLOAD_TIME * 2);

		TruckReader reader = new TruckReaderImpl();
		TruckParserImpl parser = new TruckParserImpl();

		try {
			List<String> lines = reader.read("src\\main\\resources\\data\\trucks.txt");
			List<Truck> trucks = parser.parse(lines);

			ExecutorService executor = Executors.newFixedThreadPool(trucks.size());

			trucks.forEach(t -> executor.execute(t));

			TimeUnit.SECONDS.sleep(2);
			executor.shutdown();
			timer.cancel();

		} catch (TruckException | InterruptedException e) {
			logger.error(e.getMessage());
		}

	}

}
