
class Counter implements Runnable {

    private int counter = 0;
    private final int rounds = 100000;

    public void run () {
	try {
	    int id;
	    String name = Thread.currentThread().getName();
	    if (name.equals("thread1")) id = 1;
	    else id = 2;
	    for(int i = 0; i < rounds; i++) {
		// Thread 1, wait for thread 2 to write 1
		if(id == 1 && i == rounds -1) Thread.sleep(100);
		int tmp = counter;
		// Wait for both to read 0
		if(i == 0) Thread.sleep(10);
		// Thread 1, wait for thread 2 to finish
		if(id == 1 && i == rounds -1) Thread.sleep(100);
		// Thread 2, wait for thread 1 to get to round -1
		if(id == 2 && i == 0) Thread.sleep(50);
		// Thread 2, wait for thread 1 to read the stored 1.
		if(id == 2 && i == 1) Thread.sleep(100);
		counter = tmp + 1;
	    }
	} catch (InterruptedException e) {
	    System.out.println ("Interrupted!");
	}
    }

    public static void main (String[] args) {
	try {
	    Counter c = new Counter ();

	    // Create two threads that run our run () method.
	    Thread t1 = new Thread (c, "thread1");
	    Thread t2 = new Thread (c, "thread2");

	    t1.start (); t2.start ();

	    // Wait for the threads to finish.
	    t1.join (); t2.join ();

	    // Print the counter
	    System.out.println(c.counter);

	} catch (InterruptedException e) {
	    System.out.println ("Interrupted!");
	}
    }

}
