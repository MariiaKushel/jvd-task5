package by.javacourse.task5.entity;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogisticsCenter {

	static Logger logger = LogManager.getLogger();

	private static LogisticsCenter instance;
	private static AtomicBoolean instanceIsExist = new AtomicBoolean(false);
	private static Lock instanceLocker = new ReentrantLock();

	public static final int NUMBER_OF_TERMINALS = 20;
	public static final int LOGISTICS_CENTER_CAPAСITY = 5000;
	public static final int HALF_LOGISTICS_CENTER_CAPAСITY = LogisticsCenter.LOGISTICS_CENTER_CAPAСITY / 2;
	public static final int LOW_LIMIT = (int) (0.25 * LogisticsCenter.LOGISTICS_CENTER_CAPAСITY);
	public static final int UPPER_LIMIT = (int) (0.75 * LogisticsCenter.LOGISTICS_CENTER_CAPAСITY);

	private AtomicInteger currentGoodsQuantity = new AtomicInteger();
	private Deque<Terminal> terminals = new ArrayDeque<Terminal>();
	private Lock locker = new ReentrantLock();

	private Semaphore prioretySemaphore = new Semaphore(NUMBER_OF_TERMINALS, true);
	private Semaphore nonPrioretySemaphore = new Semaphore(1, true);

	private LogisticsCenter() {
		for (int i = 0; i < NUMBER_OF_TERMINALS; i++) {
			terminals.add(new Terminal());
		}
		currentGoodsQuantity.set(HALF_LOGISTICS_CENTER_CAPAСITY);
	}

	public static LogisticsCenter getInstance() {

		if (!instanceIsExist.get()) {
			instanceLocker.lock();
			try {
				if (!instanceIsExist.get()) {
					instance = new LogisticsCenter();
					instanceIsExist.set(true);
				}
			} finally {
				instanceLocker.unlock();
			}
		}

		return instance;
	}

	public Terminal getTerminal(boolean perishableGoods) {

		try {
			if (!perishableGoods) {
				nonPrioretySemaphore.acquire();
				while (prioretySemaphore.availablePermits() < 1) {
					TimeUnit.MILLISECONDS.sleep(Terminal.MAX_LOAD_UNLOAD_TIME);
				}
				nonPrioretySemaphore.release();
			}
			prioretySemaphore.acquire();

		} catch (InterruptedException e) {
			logger.error(Thread.currentThread().getName() + "was interrupted.");
			Thread.currentThread().interrupt();
		}

		return getFirstAvailableTerminal(perishableGoods);

	}

	public void releaseTerminal(Terminal terminal) {
		releaseUsingTerminal(terminal);
		prioretySemaphore.release();
	}

	private Terminal getFirstAvailableTerminal(boolean perishableGoods) {
		locker.lock();
		Terminal freeTerminal = null;
		try {
			freeTerminal = terminals.poll();
			logger.info(Thread.currentThread().getName() + " got terminal " + freeTerminal.getTerminalId() + " "
					+ perishableGoods);
		} finally {
			locker.unlock();
		}
		return freeTerminal;
	}

	private void releaseUsingTerminal(Terminal terminal) {
		locker.lock();
		try {
			terminals.add(terminal);
			logger.info(Thread.currentThread().getName() + " release " + terminal.getTerminalId());
		} finally {
			locker.unlock();
		}
	}

	public void addGoods(int quantity) {
		currentGoodsQuantity.getAndAdd(quantity);
	}

	public void removeGoods(int quantity) {
		currentGoodsQuantity.getAndAdd(-1 * quantity);
	}

	public void refreshGoodsQuantity() {
		locker.lock();
		logger.info(Thread.currentThread().getName() + " revisor check goods quantity ====== " + currentGoodsQuantity);
		try {
			int current = currentGoodsQuantity.getAcquire();
			if (current <= LOW_LIMIT || current >= UPPER_LIMIT) {
				currentGoodsQuantity.set(HALF_LOGISTICS_CENTER_CAPAСITY);
				logger.info(Thread.currentThread().getName() + " refreshGoodsQuantity ============== " + currentGoodsQuantity);
			}
		} finally {
			locker.unlock();
		}
	}

}
