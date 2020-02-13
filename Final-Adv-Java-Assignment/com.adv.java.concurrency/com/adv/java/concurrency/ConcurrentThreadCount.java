package com.adv.java.concurrency;
import java.io.File;

import com.adv.java.concurrency.internal.*;
/*
* Counts the number of characters a string has.
* There are three options for synchronization: no locking, 
* reentrant locking and atomic long. 
*/
public class ConcurrentThreadCount {
	public static String withoutLockResult(int num, String string) throws InterruptedException {
		WithoutLock[] counts = new WithoutLock[num];
		for(int i=0; i<num; i++){
			counts[i]=new WithoutLock(string);
		}
		for(int i=0; i<num; i++){
			counts[i].start();
		}
		for(int i = 0; i < num; i++){
	            counts[i].join();
	    }
	    return Long.toString( WithoutLock.count);
	}
	
	public static String reentrant(int num, String string) throws InterruptedException{
		ReentrantLockCounter[] counts=new ReentrantLockCounter[num];
		for(int i = 0; i < num; i++){
            counts[i] = new ReentrantLockCounter(string);
        }
        for(int i = 0; i < num; i++){
            counts[i].start();
        }
        for(int i = 0; i < num; i++){
            counts[i].join();
        }
        return Long.toString(ReentrantLockCounter.count);
	}
	
	public static String atomicLong(int num, String string) throws InterruptedException{
		AtomicLongCounter[] counts=new AtomicLongCounter[num];
		 for(int i = 0; i < num; i++){
	            counts[i] = new AtomicLongCounter(string);
	        }
	        for(int i = 0; i < num; i++){
	            counts[i].start();
	        }
	        for(int i = 0; i < num; i++){
	            counts[i].join();
	        }
	        Long atomicCounter=AtomicLongCounter.count.longValue();
	       return atomicCounter.toString();
	}
}