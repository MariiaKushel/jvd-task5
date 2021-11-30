package by.javacourse.task5.entity;

import java.util.TimerTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RevisorTimerTask extends TimerTask {

	static Logger logger = LogManager.getLogger();

	private static final int LOW_LIMIT = (int) (0.25 * LogisticsCenter.LOGISTICS_CENTER_CAPASITY);
	private static final int UPPER_LIMIT = (int) (0.75 * LogisticsCenter.LOGISTICS_CENTER_CAPASITY);
	private static final int HALF_LOGISTICS_CENTER_CAPASITY = LogisticsCenter.LOGISTICS_CENTER_CAPASITY / 2;

	@Override
	public void run() {

		LogisticsCenter center = LogisticsCenter.getInstance();
		int currentGoodsQuantity = center.getCurrentGoodsQuantity().get();
			logger.info("Current goods quantity ======================" + currentGoodsQuantity);

		if (currentGoodsQuantity <= LOW_LIMIT) {
			int delta = HALF_LOGISTICS_CENTER_CAPASITY - currentGoodsQuantity;
			center.addGoods(delta);
			logger.info("++++++++++ Logistics center replenished ++++++++++");
			return;
		}
		if (currentGoodsQuantity >= UPPER_LIMIT) {
			int delta = currentGoodsQuantity - HALF_LOGISTICS_CENTER_CAPASITY;
			center.removeGoods(delta);
			logger.info("---------- Logistics center devastated ----------");
			return;
		}
	}

}
