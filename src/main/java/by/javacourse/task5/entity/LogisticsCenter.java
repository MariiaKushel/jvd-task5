package by.javacourse.task5.entity;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
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
	public static final int LOGISTICS_CENTER_CAPASITY = 5000;

	private AtomicInteger currentGoodsQuantity = new AtomicInteger();
	private Deque<Terminal> terminals = new ArrayDeque<Terminal>();
	private Lock locker = new ReentrantLock();
	private Semaphore semaphore = new Semaphore(NUMBER_OF_TERMINALS, true);

	private LogisticsCenter() {
		for (int i = 0; i < NUMBER_OF_TERMINALS; i++) {
			terminals.add(new Terminal());
		}
		currentGoodsQuantity.set(new Random().nextInt(3000));
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
			semaphore.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return getFirstAvailableTerminal();

	}

	public void releaseTerminal(Terminal terminal) {
		releaseUsingTerminal(terminal);
		semaphore.release();
	}

	public Terminal getFirstAvailableTerminal() {
		locker.lock();
		Terminal freeTerminal = null;
		try {
			freeTerminal = terminals.poll();
			logger.info(Thread.currentThread().getName() + " got terminal " + freeTerminal.getTerminalId());
		} finally {
			locker.unlock();
		}
		return freeTerminal;
	}

	public void releaseUsingTerminal(Terminal terminal) {

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
		// FIXME обработать если больше capasity
	}

	public void removeGoods(int quantity) {

		currentGoodsQuantity.getAndAdd(-1 * quantity);
		// FIXME обработать если меньше 0
	}

}
