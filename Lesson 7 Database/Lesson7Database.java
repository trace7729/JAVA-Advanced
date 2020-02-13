/*
 * Assignment 7. Lesson7Database connects to the database
 * at local host 1527. Populate the database with Lessons.sql.
 */

import java.sql.*;
import java.util.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Lesson7Database {
   public static void main(String[] args) {
      try {
         Class.forName("org.apache.derby.jdbc.ClientDriver");
         Connection conn=getConnection();
         System.out.println("Connecting to the database: "+checkConnection(conn)+".");
         Statement st=resetData(conn,args[0]);
         System.out.println("Populating the database: "+checkDatabase(st,conn)+".");
         String name=getTableName(conn);
    	 System.out.println("Query of table "+name+" results:");
         getResult(st, conn);
      } catch(Exception e) {
         System.out.println(e);
      }
   }
   /*
    * Populate the database table with given sql file
    * Return Statement st
    */
   public static Statement resetData (Connection conn, String filename) throws SQLException, IOException{
	   String s=new String();
	   FileReader fr = new FileReader(new File(filename));
	   StringBuffer sb=new StringBuffer();
		   BufferedReader br=new BufferedReader(fr);
		   while((s=br.readLine())!=null){
	    		  sb.append(s);
	    	  }
	    	  br.close();
	    	  Statement st = conn.createStatement();
	    	  String[] inst=sb.toString().split(";");
	    	  /*
	    	   * Compare the first word in each line,
	    	   * whether that be CREATE or INSERT
	    	   * Insert the missing word VALUES in front of the opening parentheses (
	    	   */
	    	  for(int i=0; i<inst.length;i++){
	    		  if(!inst[i].trim().equals("")){
	    			  String create="CREATE";
	    			  String insert="INSERT";
	    			  String values ="VALUES";
	    			  if(inst[i].toUpperCase().contains(create.toUpperCase())){
	    				  st.executeUpdate(inst[i]);
	    			  }
	    			  else if (inst[i].toUpperCase().contains(insert.toUpperCase())){
	    				  if(!(inst[i].toUpperCase().contains(values.toUpperCase()))){
	    					  String input = inst[i];
	    					  int pre=input.indexOf('(');
	    					  String newInput=input.substring(0, pre)+values+input.substring(pre);
	    					  st.executeUpdate(newInput);
	    				  }
	    			  }
	    			 else
	    				 st.executeUpdate(inst[i]);
	    		  }
	    	  }
	    	  return st;   	  
   }
   /*
    * Print out the contents of database table
    */
   public static void getResult(Statement st, Connection conn){
	   try{
	  ResultSet rs = st.executeQuery("SELECT * FROM "+getTableName(conn));
 	  ResultSetMetaData md = rs.getMetaData(); 
 	  int colCount = md.getColumnCount(); 
 	 List<String> col_name = new ArrayList<String>();
 	 for (int i = 1; i <= colCount; i++ ) {
			col_name.add(md.getColumnName(i));
			}
 	for (int ii = 0; ii < col_name.size(); ii++) {
		System.out.print(" "+col_name.get(ii)+"	");
	}
 	  for (int iii = 1; iii <= colCount ; iii++){  
 		  ResultSetMetaData metaData = rs.getMetaData();
 		  int columnCount = metaData.getColumnCount();
 	  while(rs.next()) {
 		 System.out.printf("%n");
 	      for(int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
 	          Object object = rs.getObject(columnIndex);
 	          System.out.printf("    %s   ", object == null ? "NULL" : object.toString());
 	      }    
 	  	}
 	  }
   }catch(Exception e){
	   e.printStackTrace();
   }
   }
   /*
    * Get Connection to database
    * Return Connection connection
    */
   public static Connection getConnection() throws SQLException,IOException {
	   Properties props = new Properties();
	   try (InputStream in =
		Files.newInputStream(Paths.get("ij.properties"))) {
		   props.load(in);
	   }
	   String drivers = props.getProperty("jdbc.drivers");
	   if (drivers != null) System.setProperty("jdbc.drivers", drivers);
	   String url = props.getProperty("ij.url");
	   String username = props.getProperty("ij.username");
	   String password = props.getProperty("ij.password");
	   Connection connection=DriverManager.getConnection(url,username,password);
	   System.out.println("Database Connection info: \n"
			   + "	Database Product Name: "+connection.getMetaData().getDatabaseProductName()+"\n"
               + "	Database User Name: "+connection.getMetaData().getUserName()+"\n"
               + "	Database URL: "+connection.getMetaData().getURL());
	   return connection;
	}
   /*
    * Get Database table name
    * Return String name
    */
   public static String getTableName(Connection conn) throws IOException
   {	
	   String name="";
       try {
           DatabaseMetaData dbmd = conn.getMetaData();
           String[] types = {"TABLE"};
           ResultSet rs = dbmd.getTables(null, null, "%", types);
           while (rs.next()) {
        	   name=rs.getString("TABLE_NAME");
           }
       } 
           catch (SQLException e) {
           e.printStackTrace();
       }
	return name;
   }
   /*
    * Check database connection
    * Return String checkDatabase
    */
   public static String checkDatabase(Statement st, Connection conn) throws SQLException, IOException{
	   String checkDatabase="";
	   ResultSet rs = st.executeQuery("SELECT * FROM "+getTableName(conn));
	   if (rs.next())
	       checkDatabase = "Done";
	   else
	       checkDatabase = "Failed";
	   return checkDatabase;
   }
   /*
    * Check connection 
    * Return String checkConnection
    */
   public static String checkConnection(Connection conn){
	   String checkConnection="";
	   if (conn!=null)
		   checkConnection="Done";
	   else
		   checkConnection="Failed";
	   return checkConnection;
   }
}