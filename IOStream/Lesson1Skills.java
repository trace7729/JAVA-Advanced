import java.io.DataInput;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class Lesson1Skills {
	private static String name;
	private static int month;
	private static int day;
	private static double salary;
	private static int year;
	private static int index;
	public static void main(String[] args) throws IOException, ClassNotFoundException{
		if (args[0].equals("-help")){
		//case "--help":
			System.out.println("-text (writes/reads as text file and displays results on console)");
			System.out.println("-binary (writes/reads as binary file and displays results on console)");
			System.out.println("-object (writes/reads as object file and displays results on console)");
			System.out.println("-crc  (writes/reads 1 million employees and compute the checksum)");
			System.out.println("Input the first name and last name of Employee,");
			System.out.println("follow by the yearly salary and the year, month, day of hire date, separated by | and -");
			System.out.println("Such as John Doe|456|1980-11-12");}
		else if(args[0].equals("-text")){
		//case "--text":
			System.out.println("-text (writes/reads as text file and displays results on console)");
			System.out.println("Enter Employee name, yearly salary, and year, month and day of hire date");
			Scanner scannerT=new Scanner(System.in);
			String inputT=scannerT.nextLine();
			String[]textArray=inputT.split("\\||-");
			textUserInput(textArray);}
		else if (args[0].equals("-binary")){
		//case "--binary":
			System.out.println("-binary (writes/reads as binary file and displays results on console)");
			System.out.println("Enter Employee name, yearly salary, and year, month and day of hire date");
			Scanner scannerB=new Scanner(System.in);
			String inputB=scannerB.nextLine();
			String[]binaryArray=inputB.split("\\||-");
			binaryUserInput(binaryArray);}
		else if (args[0].equals("-object")){
		//case "--object":
			System.out.println("-object (writes/reads as object file and displays results on console)");
			System.out.println("Enter Employee name, yearly salary, and year, month and day of hire date");
			Scanner scannerO=new Scanner(System.in);
			String inputO=scannerO.nextLine();
			String[]objectArray=inputO.split("\\||-");
			objectUserInput(objectArray);}
		else if (args[0].equals("-crc")){
		//case "--crc":
			System.out.println("-crc  (writes/reads 1 million employees and compute the checksum)");
			System.out.println("Enter Employee name, yearly salary, and year, month and day of hire date");
			Scanner scannerC=new Scanner(System.in);
			String inputC=scannerC.nextLine();
			String[]crcArray=inputC.split("\\||-");
			crcUserInput(crcArray);
		}
	}
	public static void textUserInput (String[] textArray) throws IOException, ClassNotFoundException{
		Employee[] staff=new Employee[(int)(textArray.length/5)];
		for(int i=0; i<textArray.length;i+=5){
			name=textArray[i].replaceAll("[\\[\\]\\(\\)]", "");
			salary=Double.parseDouble(textArray[i+1].replaceAll("[\\[\\]\\(\\)]", ""));
			year=Integer.parseInt(textArray[i+2].replaceAll("[\\[\\]\\(\\)]", ""));
			month=Integer.parseInt(textArray[i+3].replaceAll("[\\[\\]\\(\\)]", ""));
			day=Integer.parseInt(textArray[i+4].replaceAll("[\\[\\]\\(\\)]", ""));
			
				if(i==0){
					index=0; 
					staff[index]=new Employee(name,salary,year,month,day);
				}else{
					index=(int)(i/5); 
					staff[index]=new Employee(name,salary,year,month,day);
				}
		}try (PrintWriter out = new PrintWriter("employee.dat", "UTF-8"))
	      {         
	         writeData(staff, out);
	      }
	      
	      // retrieve all records into a new array
	      try (Scanner in = new Scanner(
	            new FileInputStream("employee.dat"), "UTF-8"))
	      {
	         Employee[] newStaff = readData(in);

	         // print the newly read employee records
	         for (Employee e : newStaff)
	            System.out.printf("%s \n",e);
	      }
	      }
	public static void binaryUserInput (String[] binaryArray) throws IOException{
		Employee[] staff=new Employee[(int)(binaryArray.length/5)];
		for(int i=0; i<binaryArray.length;i+=5){
			name=binaryArray[i].replaceAll("[\\[\\]\\(\\)]", "");
			salary=Double.parseDouble(binaryArray[i+1].replaceAll("[\\[\\]\\(\\)]", ""));
			year=Integer.parseInt(binaryArray[i+2].replaceAll("[\\[\\]\\(\\)]", ""));
			month=Integer.parseInt(binaryArray[i+3].replaceAll("[\\[\\]\\(\\)]", ""));
			day=Integer.parseInt(binaryArray[i+4].replaceAll("[\\[\\]\\(\\)]", ""));
			
				if(i==0){
					index=0; 
					staff[index]=new Employee(name,salary,year,month,day);
				}else{
					index=(int)(i/5); 
					staff[index]=new Employee(name,salary,year,month,day);
				}
		}try (DataOutputStream out = new DataOutputStream(new FileOutputStream("employee.dat")))
	      {  
	         // save all employee records to the file employee.dat
	         for (Employee e : staff)
	            writeData(out, e);
	      }
	         
	      try (RandomAccessFile in = new RandomAccessFile("employee.dat", "r"))
	      {
	         // retrieve all records into a new array
	            
	         // compute the array size
	         int n = (int)(in.length() / Employee.RECORD_SIZE);
	         Employee[] newStaff = new Employee[n];

	         // read employees in reverse order
	         for (int i = n - 1; i >= 0; i--)
	         {  
	            newStaff[i] = new Employee();
	            in.seek(i * Employee.RECORD_SIZE);
	            newStaff[i] = readData(in);
	         }
	         
	         // print the newly read employee records
	         for (Employee e : newStaff) 
	            System.out.printf("%s \n",e);
	      }
	}
	public static void objectUserInput (String[] objectArray) throws IOException, ClassNotFoundException{
		Employee[] staff=new Employee[(int)(objectArray.length/5)];
		for(int i=0; i<objectArray.length;i+=5){
			name=objectArray[i].replaceAll("[\\[\\]\\(\\)]", "");
			salary=Double.parseDouble(objectArray[i+1].replaceAll("[\\[\\]\\(\\)]", ""));
			year=Integer.parseInt(objectArray[i+2].replaceAll("[\\[\\]\\(\\)]", ""));
			month=Integer.parseInt(objectArray[i+3].replaceAll("[\\[\\]\\(\\)]", ""));
			day=Integer.parseInt(objectArray[i+4].replaceAll("[\\[\\]\\(\\)]", ""));
			
				if(i==0){
					index=0; 
					staff[index]=new Employee(name,salary,year,month,day);
				}else{
					index=(int)(i/5); 
					staff[index]=new Employee(name,salary,year,month,day);
				}
		}// save all employee records to the file employee.dat         
	      try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("employee.dat"))) 
	      {
	         out.writeObject(staff);
	      }

	      try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("employee.dat")))
	      {
	         // retrieve all records into a new array
	         
	         Employee[] newStaff = (Employee[]) in.readObject();

	         // print the newly read employee records
	         for (Employee e : newStaff)
	            System.out.println(e);
	      }
	}
	public static void crcUserInput (String[] crcArray) throws IOException{
			name=crcArray[0].replaceAll("[\\[\\]\\(\\)]", "");
			salary=Double.parseDouble(crcArray[1].replaceAll("[\\[\\]\\(\\)]", ""));
			year=Integer.parseInt(crcArray[2].replaceAll("[\\[\\]\\(\\)]", ""));
			month=Integer.parseInt(crcArray[3].replaceAll("[\\[\\]\\(\\)]", ""));
			day=Integer.parseInt(crcArray[4].replaceAll("[\\[\\]\\(\\)]", ""));
		//String[] crcArray=ns.getString(dest) 
		Employee[] staff = new Employee[1000000];
	      
	      for (int i=0; i<staff.length; i++){
	      staff[i] = new Employee(name,salary,year,month,day);
	      }

	      // save all employee records to the file employee.dat
	      try (PrintWriter out = new PrintWriter("employee.dat", "UTF-8"))
	      {         
	         writeData(staff, out);
	      }
	      
	      // retrieve all records into a new array
	      try (Scanner in = new Scanner(
	            new FileInputStream("employee.dat"), "UTF-8"))
	      {
	         Employee[] newStaff = readData(in);

	      }
	      String[] file=new String[1];file[0]="employee.dat";
	      MemoryMapTest.main(file);
	}
	
	private static void writeData(Employee[] employees, PrintWriter out) throws IOException
	   {
	      // write number of employees
	      out.println(employees.length);

	      for (Employee e : employees)
	         writeEmployee(out, e);
	   }
	private static Employee[] readData(Scanner in)
	   {
	      // retrieve the array size
	      int n = in.nextInt();
	      in.nextLine(); // consume newline

	      Employee[] employees = new Employee[n];
	      for (int i = 0; i < n; i++)
	      {
	         employees[i] = readEmployee(in);
	      }
	      return employees;
	   }
	public static void writeEmployee(PrintWriter out, Employee e)
	   {
		      GregorianCalendar calendar = new GregorianCalendar();
		      calendar.setTime(e.getHireDay());
		      out.println(e.getName() + "|" + e.getSalary() + "|" + calendar.get(Calendar.YEAR) + "-"
		            + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH));
		   }
	   public static Employee readEmployee(Scanner in)
	   {
	      String line = in.nextLine();
	      String[] tokens = line.split("\\||-");
	      String name = tokens[0];
	      double salary = Double.parseDouble(tokens[1]);
	      int year = Integer.parseInt(tokens[2]);
	      int month = Integer.parseInt(tokens[3]);
	      int day = Integer.parseInt(tokens[4]);
	      return new Employee(name, salary, year, month, day);
	   }
	   public static void writeData(DataOutput out, Employee e) throws IOException
	   {
	      DataIO.writeFixedString(e.getName(), Employee.NAME_SIZE, out);
	      out.writeDouble(e.getSalary());

	      GregorianCalendar calendar = new GregorianCalendar();
	      calendar.setTime(e.getHireDay());
	      out.writeInt(calendar.get(Calendar.YEAR));
	      out.writeInt(calendar.get(Calendar.MONTH) + 1);
	      out.writeInt(calendar.get(Calendar.DAY_OF_MONTH));
	   }
	   public static Employee readData(DataInput in) throws IOException
	   {      
	      String name = DataIO.readFixedString(Employee.NAME_SIZE, in);
	      double salary = in.readDouble();
	      int y = in.readInt();
	      int m = in.readInt();
	      int d = in.readInt();
	      return new Employee(name, salary, y, m - 1, d);
	   }  
}
