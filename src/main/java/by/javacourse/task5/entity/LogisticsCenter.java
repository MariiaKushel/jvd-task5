package by.javacourse.task5.entity;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;
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

	public static final int NUMBER_OF_TERMINALS = 3;
	public static final int LOGISTICS_CENTER_CAPASITY = 5000;

	private AtomicInteger currentGoodsQuantity = new AtomicInteger();
	private Deque<Terminal> terminals = new ArrayDeque<Terminal>();
	private Deque<Lock> truckLockers = new ArrayDeque<Lock>();
	private Deque<Condition> truckConditions = new ArrayDeque<Condition>();

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

	public Terminal getTerminal(String licensePlate) {

		Lock truckLocker = new ReentrantLock();
		Condition truckCondition = truckLocker.newCondition();

		truckLocker.lock();

		truckLockers.add(truckLocker);
		truckConditions.add(truckCondition);

		Terminal freeTerminal = null;
		try {
			while (terminals.isEmpty()) {
				logger.info(licensePlate + " wait");
				truckCondition.await();
			}
			freeTerminal = terminals.poll();
			truckLockers.remove();
			truckConditions.remove();
			logger.info(licensePlate + " got terminal " + freeTerminal.getTerminalId());

		} catch (InterruptedException e) {
			logger.error("InterruptedException");
		} finally {
			truckLocker.unlock();
		}

		return freeTerminal;
	}

	public void releaseTerminal(String licensePlate, Terminal terminal) {

		if (truckLockers.peekFirst() == null) {
			logger.info(licensePlate + " release terminal " + terminal.getTerminalId());
			return;
		}
		
		Lock truckLocker = truckLockers.getFirst();
		Condition truckCondition = truckConditions.getFirst();

		truckLocker.lock();
		try {
			terminals.add(terminal);
			truckCondition.signal();
			logger.info(licensePlate + " release terminal " + terminal.getTerminalId());
		} finally {
			truckLocker.unlock();
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
