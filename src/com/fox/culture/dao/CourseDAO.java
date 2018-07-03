package com.fox.culture.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Vector;
import com.fox.culture.dto.CourseDTO;
import com.fox.culture.module.MyOracleConnection;

public class CourseDAO {
	Connection con;
	PreparedStatement psmt;
	ResultSet rs;


	public CourseDAO() {
		con = MyOracleConnection.getConnection();
	}

	// 자원반납
	public void dbClose() {
		MyOracleConnection.close(con,psmt,rs);
	}

	// 입력처리
	public int insert(CourseDTO dto) {
		int affectedRow = 0;

		String sql = "INSERT INTO coursebk(seq_csCode.nextval, csClass, csTea, csField, csTarget, csPeriod, csOpen, csCost, csMax, csRoom) "
				+ "VALUES(seq_ccode.nextval,?,?,?,?,?,?,?,?,?)";
		try {
			psmt = con.prepareStatement(sql);

			psmt.setString(1, dto.getCsClass());
			psmt.setString(2, dto.getCsTea());
			psmt.setString(3, dto.getCsField());
			psmt.setString(4, dto.getCsTarget());
			psmt.setString(5, dto.getCsPeriod());
			psmt.setString(6, dto.getCsOpen());
			psmt.setInt(7, dto.getCsCost());
			psmt.setInt(8, dto.getCsMax());
			psmt.setString(9, dto.getCsRoom());				

			affectedRow = psmt.executeUpdate();

		} catch (Exception e) {
			System.out.println("입력시 오류:" + e.getMessage());
		}
		return affectedRow;
	}

	// 모든 레코드 반환처리
	public List<CourseDTO> getRecordAll() {
		List<CourseDTO> list = null;

		try {
			list = new Vector<CourseDTO>();

			String sql = "SELECT * FROM coursebk order by csCode";
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
				CourseDTO dto = new CourseDTO(rs.getInt(1), rs.getString(2), 
						rs.getString(3), rs.getString(4), rs.getString(5), 
						rs.getString(6), rs.getString(7), rs.getInt(8), 
						rs.getInt(9), rs.getString(10));

				list.add(dto);
			}
		} catch (Exception e) {
			System.out.println("전체 레코드 반환시 오류:" + e.getMessage());
		}
		return list;
	}

	public int update(CourseDTO dto) {
		int affectedRow = 0;

		String sql = "UPDATE coursebk SET csClass=?, csTea=?, csField=?, csTarget=?, csPeriod=?, csOpen=?, csCost=?, csMax=?, csRoom=? WHERE csCode=?";
		try {
			psmt = con.prepareStatement(sql);

			psmt.setInt(10, dto.getCsCode());

			psmt.setString(1, dto.getCsClass());
			psmt.setString(2, dto.getCsTea());
			psmt.setString(3, dto.getCsField());
			psmt.setString(4, dto.getCsTarget());
			psmt.setString(5, dto.getCsPeriod());
			psmt.setString(6, dto.getCsOpen());
			psmt.setInt(7, dto.getCsCost());
			psmt.setInt(8, dto.getCsMax());
			psmt.setString(9, dto.getCsRoom());	

			affectedRow = psmt.executeUpdate();

		} catch (Exception e) {
			System.out.println("수정시 오류:" + e.getMessage());
		}
		return affectedRow;
	}

	// 삭제처리
	public int delete(int cscode) {
		int affectedRow = 0;
		String sql = "DELETE coursebk WHERE cscode=?";
		try {
			psmt = con.prepareStatement(sql);
			psmt.setInt(1, cscode);
			affectedRow = psmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("삭제시 오류:" + e.getMessage());
		}
		return affectedRow;
	}
	public List<CourseDTO> getSearchRecord(String csClass) {
		List<CourseDTO> list = null;
		try {
			list = new Vector<CourseDTO>();

			String sql = "SELECT * FROM coursebk WHERE ";
			String whereSql = " csField LIKE '%' || ? || '%'";
			psmt.setString(1, csClass);
			/*
				if (!csClass.equals("") && !csField.equals("")) {
					whereSql = " csClass LIKE '%' || ? || '%' OR csField LIKE '%' || ? || '%'";
				} else if (csClass.equals("") && !csField.equals(""))
					whereSql = " csField LIKE '%' || ? || '%'";
				else
					whereSql = " csClass LIKE '%' || ? || '%'";
			 */
			String orderSql = " ORDER BY csCode DESC";

			psmt = con.prepareStatement(sql + whereSql + orderSql);
			/*
				if (!csClass.equals("") && !csField.equals("")) {
					psmt.setString(1, csClass);
					psmt.setString(2, csField);
				} else if (csClass.equals("") && !csField.equals("")) {
					psmt.setString(1, csField);
				} else{
					psmt.setString(1, csClass);}
			 */					

			rs = psmt.executeQuery();

			while (rs.next()) {
				CourseDTO dto = new CourseDTO(rs.getInt(1), rs.getString(2), 
						rs.getString(3), rs.getString(4), rs.getString(5), 
						rs.getString(6), rs.getString(7), rs.getInt(8), 
						rs.getInt(9), rs.getString(10));

				list.add(dto);
			}

		} catch (Exception e) {
			System.out.println("검색시 오류:" + e.getMessage());
		}
		return list;
	}		

}
