package by.javacourse.task5.entity;

import java.util.TimerTask;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RevisorTimerTask extends TimerTask {

	static Logger logger = LogManager.getLogger();

	@Override
	public void run() {
		LogisticsCenter center = LogisticsCenter.getInstance();
		center.refreshGoodsQuantity();
	}

}
