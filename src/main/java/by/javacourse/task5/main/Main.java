package by.javacourse.task5.main;

import by.javacourse.task5.entity.Truck;
import by.javacourse.task5.entity.Truck.Task;

public class Main {

	public static void main(String[] args) {

		Thread t1 = new Thread(new Truck("AA111A", false, Task.LOADING));
		Thread t2 = new Thread(new Truck("BB222B", false, Task.UNLOADING));
		Thread t3 = new Thread(new Truck("CC333C", true, Task.LOADING));
		Thread t4 = new Thread(new Truck("DD444D", false, Task.LOADING));
		Thread t5 = new Thread(new Truck("EE555E", false, Task.LOADING));
		Thread t6 = new Thread(new Truck("FF666F", true, Task.UNLOADING));
		Thread t7 = new Thread(new Truck("GG777G", true, Task.LOADING));
		Thread t8 = new Thread(new Truck("II888I", false, Task.LOADING));
		Thread t9 = new Thread(new Truck("KK999K", false, Task.UNLOADING));
		Thread t10 = new Thread(new Truck("LL000L", false, Task.UNLOADING));

		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();
		t6.start();
		t7.start();
		t8.start();
		t9.start();
		t10.start();

	}

}
