
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

public class Lesson5Concurrent_modified {
	private static int NUMBER_OF_THREADS;
	public static void main(String[] args) throws InterruptedException {
		if(args[0].equals("-NoLocking")){
			System.out.print("Number of threads: ");
			Scanner scanner=new Scanner(System.in);
			NUMBER_OF_THREADS=scanner.nextInt();
			File file = new File("JobResult.dat");
			withoutLock(NUMBER_OF_THREADS,file);
		}else if(args[0].equals("-ReentrantLock")){
			System.out.print("Number of threads: ");
			Scanner scanner=new Scanner(System.in);
			NUMBER_OF_THREADS=scanner.nextInt();
			File file = new File("Lesson5Concurrent_modified.class");
			reentrant(NUMBER_OF_THREADS,file);
		}else if(args[0].equals("-AtomicLong")){
			System.out.print("Number of threads: ");
			Scanner scanner=new Scanner(System.in);
			NUMBER_OF_THREADS=scanner.nextInt();
			File file = new File("Lesson5Concurrent_modified.class");
			atomicLong(NUMBER_OF_THREADS,file);
		}
}
	private static void withoutLock(int num, File file) throws InterruptedException{
		WithoutLock[] counts = new WithoutLock[num];
		for(int i=0; i<num; i++){
			counts[i]=new WithoutLock(file);
		}
		for(int i=0; i<num; i++){
			counts[i].start();
		}
		for(int i = 0; i < num; i++){
	            counts[i].join();
	    }
	    System.out.println("WithoutLock Count : " + WithoutLock.count);
	}
	private static void reentrant(int num, File file) throws InterruptedException{
		ReentrantLockCounter[] counts=new ReentrantLockCounter[num];
		for(int i = 0; i < num; i++){
            counts[i] = new ReentrantLockCounter(file);
        }
        for(int i = 0; i < num; i++){
            counts[i].start();
        }
        for(int i = 0; i < num; i++){
            counts[i].join();
        }
        System.out.println("ReentrantLock Count : " + ReentrantLockCounter.count);
	}
	private static void atomicLong(int num, File file) throws InterruptedException{
		AtomicLongCounter[] counts=new AtomicLongCounter[num];
		 for(int i = 0; i < num; i++){
	            counts[i] = new AtomicLongCounter(file);
	        }
	        for(int i = 0; i < num; i++){
	            counts[i].start();
	        }
	        for(int i = 0; i < num; i++){
	            counts[i].join();
	        }
	        System.out.println("AtomicLong Count : " + AtomicLongCounter.count);
	}
}
/*
 * This class does not use any locking for synchronization purposes
 */
class WithoutLock extends Thread{
	public static long count=0;
	private File file;
	public WithoutLock(File file){
		this.file=file;
	}
	@Override
	public void run(){
		try{
			Scanner scan=new Scanner(file);
			while(scan.hasNext()){
				String line=scan.next();
				for(int i=0;i<line.length();i++){
					count+=1;
				}
			}
		}catch(FileNotFoundException ex){
			System.out.println(ex);
		}
	}
}
/*
 * This class uses ReetrantLock for synchronization purpose
 */
class ReentrantLockCounter extends Thread{
	public static long count=0;
	private static ReentrantLock rentLock=new ReentrantLock();
	private File file;
	public ReentrantLockCounter(File file){
		this.file=file;
	}
	@Override
	public void run(){
		try{
			rentLock.lock();
			Scanner scan=new Scanner(file);
			while(scan.hasNext()){
				String line=scan.next();
				for(int i=0; i<line.length();i++){
					count+=1;
				}
			}
			
		}catch(FileNotFoundException ex){
			System.out.println(ex);
		}finally{
			rentLock.unlock();
		}
	}
}
/*
 * This class uses AtomicLong for synchronization purpose
 */
class AtomicLongCounter extends Thread{
	public static AtomicLong count=new AtomicLong(0);
	private File file;
	public AtomicLongCounter(File file){
		this.file=file;
	}
	@Override
	public void run(){
		try{
			Scanner scan=new Scanner(file);
			while(scan.hasNext()){
				String line=scan.next();
				for(int i=0; i<line.length();i++){
					count.incrementAndGet();
				}
			}
			
		}catch (FileNotFoundException ex){
			System.out.println(ex);
		}
	}
}