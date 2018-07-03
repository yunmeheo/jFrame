package com.fox.culture.dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.fox.culture.dto.RegisterDTO;


public class RegisterDAO {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:orcl";
	String userid = "fox";  String passwd = "1234";
	
	Connection con = null;  PreparedStatement psmt = null;
	ResultSet rs = null;  
	
	public RegisterDAO() {
		try {Class.forName(driver);
		con = DriverManager.getConnection(url, userid, passwd);
		System.out.println("데이타베이스 연결 성공");
		}catch(Exception e) {
			System.out.println("데이타베이스 연결 실패:"+e.getMessage());
		}
	}
	// 자원반납
			public void dbClose() {
				try {
					if (rs != null)
						rs.close();
					if (psmt != null)
						psmt.close();
					if (con != null)
						con.close();
				} catch (Exception e) {
				}
			}
			public int insert(RegisterDTO dto) {
				int affectedRow = 0;

				String sql = "INSERT INTO register(rid, rcode, rclass, rst, rcost, rmax, rdate, rname) "
						+ "VALUES(?,?,?,?,?,?,?,?)";
				try {
					psmt = con.prepareStatement(sql);

					psmt.setString(1, dto.getRid());
					psmt.setInt(2, dto.getRcode());
					psmt.setString(3, dto.getRclass());
					psmt.setString(4, dto.getRst());
					psmt.setString(5, dto.getRcost());
					psmt.setInt(6, dto.getRmax());
					psmt.setString(7, dto.getRdate());
					psmt.setString(8, dto.getRname());
							

					affectedRow = psmt.executeUpdate();

				} catch (Exception e) {
					System.out.println("입력시 오류:" + e.getMessage());
				}
				return affectedRow;
			}
			
			// 모든 레코드 반환처리
			public List<RegisterDTO> getRecordAll() {
				List<RegisterDTO> list = null;

				try {
					list = new Vector<RegisterDTO>();

					String sql = "SELECT * FROM register order by rCode";
					psmt = con.prepareStatement(sql);
					rs = psmt.executeQuery();

					while (rs.next()) {
						/*
						 * 1. 기본 생성자로 MemberDTO dto = new MemberDTO(); 1]ResultSet에서 레코드를 하나씩 꺼내와서
						 * MemberDTO에 저장 dto.setId(rs.getString(1)); dto.setPwd(rs.getString(2));
						 * dto.setName(rs.getString(3)); dto.setAge(rs.getInt(4));
						 * dto.setRegdate(rs.getDate(5)); 2]MemberDTO를 List컬렉션에 저장 list.add(dto);
						 */
						// 2. 인자 생성자로
						RegisterDTO dto = new RegisterDTO(rs.getString(1), rs.getInt(2), 
								rs.getString(3), rs.getString(4), rs.getString(5), 
								rs.getInt(6), rs.getString(7), rs.getString(8));
								

						list.add(dto);
					}
				} catch (Exception e) {
					System.out.println("전체 레코드 반환시 오류:" + e.getMessage());
				}
				return list;
			}
			public List<RegisterDTO> getSearchRecord(String trim) {
				// TODO Auto-generated method stub
				return null;
			}
}
		
		

	
		