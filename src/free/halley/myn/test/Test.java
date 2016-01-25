package free.halley.myn.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Test {
	
	
	
	public static void main(String[] args){
			        try{
			        //连接SQLite的JDBC
			          Class.forName("org.sqlite.JDBC");         
			          //建立一个数据库名zieckey.db的连接，如果不存在就在当前目录下创建之
			          Connection conn = DriverManager.getConnection("jdbc:sqlite:halley.db");         
			          Statement stat = conn.createStatement();
			 
			          stat.executeUpdate( "create table tbl1(name varchar(20), salary int);" );//创建一个表，两列
			          stat.executeUpdate( "insert into tbl1 values('ZhangSan',8000);" ); //插入数据
			          stat.executeUpdate( "insert into tbl1 values('LiSi',7800);" );
			          stat.executeUpdate( "insert into tbl1 values('WangWu',5800);" );
			          stat.executeUpdate( "insert into tbl1 values('ZhaoLiu',9100);" ); 
			  
			          ResultSet rs = stat.executeQuery("select * from tbl1;"); //查询数据 
			 
			          while (rs.next()) { //将查询到的数据打印出来
			              System.out.print("name = " + rs.getString("name") + " "); //列属性一
			              System.out.println("salary = " + rs.getString("salary")); //列属性二
			          }
			          rs.close();
			          conn.close(); //结束数据库的连接 
			        }
			        catch( Exception e )
			        {
			        	
			        }
 

	}

	private void test() {
		        Connection conn = null;
		        Statement stmt = null;
		        ResultSet rset = null;
		        System.out.println(new java.util.Date());
		        try {  
		        Class.forName("org.sqlite.JDBC");
		        conn = DriverManager.getConnection("jdbc:sqlite://d:/test.db");
		        conn.setAutoCommit(false);
		        stmt = conn.createStatement();
		        stmt.executeUpdate("create table hehe(id number, name varchar(32))");
		        System.out.println("建表hehe成功!");
		        for (int i=0; i<10000; i++) {
		            System.out.print("插入条目i/n");
		            System.out.println(stmt.executeUpdate("INSERT INTO hehe VALUES(" + i + ", '我爱中国" + i + "')"));
		        }
		        conn.commit();


		        System.out.println("不建索引查询:");
		        System.out.println(new java.util.Date());
		        rset = stmt.executeQuery("SELECT id, name FROM hehe where id>5");
		        while (rset.next()){
		            System.out.println(rset.getInt("id"));
		            System.out.println(rset.getString("name"));
		        }
		        if (rset!=null){
		            rset.close(); rset = null;
		        }
		        System.out.println(new java.util.Date());




		        System.out.println("建索引:");
		        System.out.println(new java.util.Date());
		        stmt.executeUpdate("CREATE INDEX hehe_idx on hehe(id)");
		        stmt.executeUpdate("CREATE INDEX hehe_idx2 on hehe(name)");
		        conn.commit();
		        System.out.println(new java.util.Date());
		        System.out.println("建索引后的查询:");
		        System.out.println(new java.util.Date());
		        rset = stmt.executeQuery("SELECT id, name FROM hehe where id > 5 ");
		        while (rset.next()){
		            System.out.println(rset.getInt("id"));
		            System.out.println(rset.getString("name"));
		        }
		        System.out.println(new java.util.Date());
		        stmt.executeUpdate("drop table hehe");
		        System.out.println("删除表hehe成功!");
		        conn.commit();
		        System.out.println(new java.util.Date());
		        } catch(ClassNotFoundException cnfe)
		        {
		            System.out.println("Can´t find class for driver: " + cnfe.getMessage());
		            System.exit(-1);
		        } catch (SQLException e){
		            System.out.println("SQLException :" + e.getMessage());
		            System.exit(-1); }
		        finally {
		            try {
		                if (rset!=null) rset.close();
		                stmt.close();
		                conn.close();
		            } catch (SQLException e) { System.out.println("SQLException in finally :" + e.getMessage());
		            System.exit(-1);} } 

		
		
	}
}
