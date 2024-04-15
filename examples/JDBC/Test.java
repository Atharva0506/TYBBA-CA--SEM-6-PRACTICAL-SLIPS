import java.sql.*;  
class Test{  
public static void main(String[] ar){  
 try{  
   String url="jdbc:odbc:Test";  
   Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");  
   Connection c=DriverManager.getConnection(url,"","");  
   Statement st=c.createStatement();  
   ResultSet rs=st.executeQuery("select * from Test");  
    
   while(rs.next()){  
    System.out.println(rs.getString(1));  
   }  
  
}catch(Exception ee){System.out.println(ee);}  
  
}}  