package com.fox.culture.dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Vector;

import com.fox.culture.dto.MemberDTO;

public class MemberDAO {
	Connection con;
	PreparedStatement psmt;
	ResultSet rs;

	public MemberDAO() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "fox", "1234");

			System.out.println("데이타베이스 연결 성공");
		} catch (Exception e) {
			System.out.println("데이타 베이스 연결 실패:" + e.getMessage());
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

	// 입력처리
	public int insert(MemberDTO dto) {
		int affectedRow = 0;

		String sql = "INSERT INTO member(mid,mpwd,mname,mbirth,maddr,mhp,mdate) VALUES(?,?,?,?,?,?,SYSDATE)";
		try {
			psmt = con.prepareStatement(sql);

			psmt.setString(1, dto.getMid());
			psmt.setString(2, dto.getMpw());
			psmt.setString(3, dto.getMname());
			psmt.setString(4, dto.getMbirth());
			psmt.setString(5, dto.getMaddr());
			psmt.setString(6, dto.getMhp());

			affectedRow = psmt.executeUpdate();

		} catch (Exception e) {
			System.out.println("입력시 오류:" + e.getMessage());
		}
		return affectedRow;
	}

	// 모든 레코드 반환처리
	public List<MemberDTO> getRecordAll() {
		List<MemberDTO> list = null;

		try {
			list = new Vector<MemberDTO>();

			String sql = "SELECT * FROM member ORDER BY mdate DESC";
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
				MemberDTO dto = new MemberDTO();
				dto.setMid(rs.getString(1));
				dto.setMpw(rs.getString(2));
				dto.setMname(rs.getString(3));
				dto.setMbirth(rs.getString(4));
				dto.setMaddr(rs.getString(5));
				dto.setMhp(rs.getString(6));
				dto.setMdate(rs.getDate(7));

				list.add(dto);
			}
		} catch (Exception e) {
			System.out.println("전체 레코드 반환시 오류:" + e.getMessage());
		}
		return list;
	}

	public int update(MemberDTO dto) {
		int affectedRow = 0;

		String sql = "UPDATE member SET mpw=?, mname=?,mbirth=?,maddr=?,mhp=? WHERE mid=?";
		try {
			psmt = con.prepareStatement(sql);

			psmt.setString(6, dto.getMid());
			psmt.setString(1, dto.getMpw());
			psmt.setString(2, dto.getMname());
			psmt.setString(3, dto.getMbirth());
			psmt.setString(4, dto.getMaddr());
			psmt.setString(5, dto.getMhp());

			affectedRow = psmt.executeUpdate();

		} catch (Exception e) {
			System.out.println("수정시 오류:" + e.getMessage());
		}
		return affectedRow;
	}

	// 삭제처리
	public int delete(String id) {
		int affectedRow = 0;
		String sql = "DELETE member WHERE id=?";
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, id);
			affectedRow = psmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("삭제시 오류:" + e.getMessage());
		}
		return affectedRow;
	}

	public List<MemberDTO> getSearchRecord(String mname) {
		List<MemberDTO> list = null;
		try {
			list = new Vector<MemberDTO>();

			String sql = "SELECT * FROM member WHERE ";
			String whereSql = "";

			if (!mname.equals("")) {
				whereSql = " name LIKE '%' || ? || '%'";
			} else if (mname.length() != 0)
				whereSql = " name LIKE '%' || ? || '%'";

			String orderSql = " ORDER BY mdate DESC";

			psmt = con.prepareStatement(sql + whereSql + orderSql);

			if (!mname.equals("")) {

				psmt.setString(2, mname);
				;
			} else if (mname.length() != 0)
				psmt.setString(1, mname);

			rs = psmt.executeQuery();

			while (rs.next()) {
				MemberDTO dto = new MemberDTO();
				dto.setMid(rs.getString(1));
				dto.setMpw(rs.getString(2));
				dto.setMname(rs.getString(3));
				dto.setMbirth(rs.getString(4));
				dto.setMaddr(rs.getString(5));
				dto.setMhp(rs.getString(6));
				dto.setMdate(rs.getDate(7));

				list.add(dto);
			}

		} catch (Exception e) {
			System.out.println("검색시 오류:" + e.getMessage());
		}
		return list;
	}

	public boolean isDuplicate(String mid) {
		try {
			String sql = "SELECT COUNT(*) FROM member WHERE mid=?";
			psmt = con.prepareStatement(sql);
			psmt.setString(1, mid);

			rs = psmt.executeQuery();
			rs.next();
			int count = rs.getInt(1);

			if (count == 1)
				return true;

		} catch (Exception e) {
		}
		return false;
	}
}