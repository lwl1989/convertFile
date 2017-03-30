/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package example.web.src.lib.sql;
import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
/**
 *
 * @author mystyle
 */
public class MySqlClass {
   
   private Connection conn=null;
   private Statement st=null;
   private ResultSet rs=null;
   
   public MySqlClass(String databaseName,String userName,String password){
       try{
           //写入驱动所在处，打开驱动
    		Class.forName("com.mysql.jdbc.Driver").newInstance();
			// 数据库，用户，密码，创建与具体数据库的连接
			conn = DriverManager
					.getConnection(
							"jdbc:mysql://localhost:3306/"
									+ databaseName
									+ "?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=round",
							userName, password);
			// 创建执行sql语句的对象
			st = conn.createStatement();
       }catch(Exception e){
    	   System.out.println("连接失败"+e.toString());
          
       }
       
   }
   
   public LinkedList<HashMap<String, String>> query(String sqlStatement){
       LinkedList<HashMap<String, String>> resultLinkedList=new LinkedList<HashMap<String,String>>();
      // int i=1;
       try{
           rs=st.executeQuery(sqlStatement);
           while(rs!=null && rs.next()){
        	   ResultSetMetaData data = rs.getMetaData();
        	   HashMap<String, String> row=new HashMap<String, String>(); 	   
        	   for (int j = 1; j <= data.getColumnCount();) {
        		   row.put(data.getColumnName(j), rs.getString(j));
        		   j++;
			   } 
        	   resultLinkedList.add(row);
           }
           rs.close();
           return resultLinkedList;
       }catch(Exception e){
    	   System.out.println("查询失败"+e.toString());
           
           return resultLinkedList;
       }
   }
   
   public int update(String sqlStatement){
       int row=0;
       try{
    	   row = st.executeUpdate(sqlStatement);
           ResultSet res = st.executeQuery("select LAST_INSERT_ID()");
           int ret_id = 0;
           if (res.next()) {
                   ret_id = res.getInt(1);
           }
           return ret_id;
       }catch(Exception e){
    	   System.out.println("执行sql语句失败"+e.toString());
           this.close();
           return row;
       }
   }
   
   public void close(){
      try{
          
          this.rs.close();
          this.st.close();
          this.conn.close();
          
      }catch(Exception e){
    	 // System.out.println("关闭数据库失败"+e.toString());
         // javax.swing.JOptionPane.showMessageDialog(null,"关闭数据库连接失败"+e.toString());
      }       
   }
}
