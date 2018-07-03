package com.fox.culture.module;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MyOracleConnection {
	
	Connection con;
	//static PreparedStatement psmt;
	//static ResultSet rs;
	
	public static Connection getConnection() {
		Connection con = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "fox", "1234");
			System.out.println("데이타베이스 연결 성공");
			
		} catch (Exception e) {
			System.out.println("데이타 베이스 연결 실패:" + e.getMessage());
		}
		
		return con;
	}
	
	public static void close(Connection con, PreparedStatement psmt, ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
			if (psmt != null)
				psmt.close();
			if (con != null)
				con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
