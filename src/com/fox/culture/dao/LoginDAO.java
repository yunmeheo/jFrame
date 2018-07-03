package com.fox.culture.dao;
import java.awt.BorderLayout;


import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.fox.culture.main.DBMain;
import com.fox.culture.main.LoginMain;


class LoginDAO extends Frame implements ActionListener {
	Connection conn;		
	Statement stmt;
	ResultSet rs;			
	String sql;
	String id;				
	String pass;
	int row;					
	boolean flag = false;
	LoginMain login;			
	DBMain submit;
	String data[][] = new String[0][7];
	// 앞에 0은 addRow할 때 시작점. 7은 column 갯수
	
	String title[] = { "ID", "이름", "나이", "성", "전화번호", "주소", "직업" };
	DefaultTableModel model = new DefaultTableModel(data, title) {

		private static final long serialVersionUID = 1L;

		public boolean isCellEditable(int row, int col) {
			return false;
		}
	};
	JTable table = new JTable(model);
	JScrollPane pan = new JScrollPane(table);
	Panel south = new Panel();
	JButton show = new JButton("보기");
	JButton del = new JButton("삭제");
	JButton update = new JButton("수정");
	JButton select = new JButton("검색");
	MenuBar mb = new MenuBar();
	Menu menu = new Menu("관리자");
	MenuItem admin = new MenuItem("관리자인증");
	MenuItem exit = new MenuItem("종료");
	LoginDAO() {
		setLayout(new BorderLayout());
		south.setLayout(new GridLayout(1, 4, 10, 10));
		south.add(show);			south.add(del);
		south.add(update);		south.add(select);
		add("Center", pan);		add("South", south);
		setSize(650, 300);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		del.setEnabled(false); // 관리자만 누를수 있도록
		menu.add(admin);		menu.add(exit);
		mb.add(menu);			setMenuBar(mb);
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:orcl", "lion", "1234");
			stmt = conn.createStatement();
			System.out.println("DB 연결 성공");
		} catch (Exception e) { System.out.println("DB 연결 실패" + e); }
		
		login = new LoginMain();		
		submit = new DBMain();
		eventUp();
	}
	public void loginCheck() {
		id = login.tf_id.getText().trim();
		pass = login.tf_pass.getText().trim();
		if (id.length() == 0) {
			JOptionPane.showMessageDialog(login, "ID를 입력");
			login.tf_pass.setText(null);
			login.tf_id.requestFocus(); // Focus 맞춰주기.
			return;
		} // id에 입력값이 없는 경우
		else if (pass.length() == 0) {
			JOptionPane.showMessageDialog(login, "비밀번호를 입력");
			login.tf_pass.requestFocus();
			return;
		} // pass 에 입력값이 없는 경우
		sql = "select pass from datadb where id='" + id + "'";
		try {
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				if (pass.equals(rs.getString("pass"))) {
					JOptionPane.showMessageDialog(login, "환영합니다.");
					login.setVisible(false);
					setVisible(true);			rs.close();
					return;
				} else {
					JOptionPane.showMessageDialog(login,
							"비밀번호가 일치하지 않습니다.");
					login.tf_pass.setText("");
					login.tf_pass.requestFocus();
				}
			} else {
				JOptionPane.showMessageDialog(login,
						"존재하지 않는 아이디입니다.");
				login.tf_id.setText("");
				login.tf_pass.setText("");
				login.tf_id.requestFocus();
			}
		} catch (Exception e) {
			System.out.println("ID, pass 검색 실패" + e);
		}
	}
	public void insertProcess() {
		Data d = new Data();
		d.id = submit.tf_id.getText().trim();
		d.name = submit.tf_name.getText().trim();
		d.pass = submit.tf_pass1.getText().trim();
		d.jumin = submit.tf_jumin1.getText().trim()
				+ "-" + submit.tf_jumin2.getText().trim();
		d.tel = submit.tf_tel1.getText().trim() + "-"
				+ submit.tf_tel2.getText().trim() + "-"
				+ submit.tf_tel3.getText().trim();
		d.addr = submit.tf_addr.getText().trim();
		d.job = submit.job.getSelectedItem();
		if (!d.isFull()) {
			JOptionPane.showMessageDialog(submit, "기재사항 확인");
			return;
		}
		if (!d.pass.equals(submit.tf_pass2.getText().trim())) {
			JOptionPane.showMessageDialog(submit, "PassWord 다시 확인");
			return;
		}
		try {
			sql = "insert into datadb values (" + d + ")";
			System.out.println("sql" + sql);
			stmt.executeUpdate(sql);
			JOptionPane.showMessageDialog(submit, "새로 가입되었습니다.");
			submit.setVisible(false);
			login.setVisible(true);
		} catch (Exception e) {
			System.out.println("DB추가 실패" + e);
		}
	}
	public void showProcess() {
		try {
			sql = "select * from datadb";
			rs = stmt.executeQuery(sql);
			String temp[] = new String[7];
			String jumin;		int gender, age, year;
			model.setRowCount(0); // 보기의 초기화
			while (rs.next()) {
				temp[0] = rs.getString("id");
				temp[1] = rs.getString("name");
				jumin = rs.getString("jumin");
				year = Integer.parseInt(jumin.substring(0, 2));
				gender = jumin.charAt(7) - 48;
				age = gender >= 3 ? 2000 + year : 1900 + year;
				Calendar cal = new GregorianCalendar();
				age = cal.get(Calendar.YEAR) - age + 1;
				temp[2] = age + "";
				temp[3] = (gender % 2 == 1) ? "남자" : "여자";
				temp[4] = rs.getString("tel");
				temp[5] = rs.getString("addr");
				temp[6] = rs.getString("job");
				model.addRow(temp);
			}
		} catch (Exception e) {
			System.out.println("DB검색 실패");
		}
	}
	void deleteProcess() {
		String str = JOptionPane.showInputDialog(this, "삭제할 ID 입력 : ");
		if (str == null)
			return;
		if (id.equals(str)) { // 로그인된 ID는 삭제할 수 없게 한다
			JOptionPane.showMessageDialog(this, "삭제할 수 없는 ID입니다.");
			return;
		}
		if (checkID(str)) {
			JOptionPane.showMessageDialog(this, "존재하지 않는 ID입니다.");
			return;
		} else {
			int t = JOptionPane.showConfirmDialog(this,
					"정말로 삭제하시겠습니까?", "삭제 경고",
					JOptionPane.YES_NO_OPTION);
			if (t == 1 || str.equals(""))
				return;
			sql = "delete from dataDB where id = '" + str + "'";
			try {
				stmt.executeUpdate(sql);
				for (int i = 0; i < model.getRowCount(); i++) {
					String temp = (String) model.getValueAt(i, 0);
					if (temp.equals(str)) {
						model.removeRow(i);
					}
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "삭제 실패");
				return;
			}
			JOptionPane.showMessageDialog(this, "삭제 성공");
		}
	}
	public void eventUp() {
		login.newID.addActionListener(this);
		submit.submit.addActionListener(this);
		show.addActionListener(this);
		del.addActionListener(this);
		login.ok.addActionListener(this);
		submit.idCheck.addActionListener(this);
		update.addActionListener(this);
		admin.addActionListener(this);
		exit.addActionListener(this);
	}
	public boolean checkID(String id) {
		this.id = id;
		sql = "select id from datadb where id = '" + id + "'";
		try {
			rs = stmt.executeQuery(sql);
			if (rs.next())  return false;
		} catch (SQLException e) {
			System.out.println("ID검색실패!" + e);
		}
		return true;
	}
	public void updateShow() {
		String id = "";
		if (flag) { // 관리자
			int row = table.getSelectedRow();
			if (row < 0) {
				JOptionPane.showMessageDialog(this,
						"수정할 데이터를 선택하세요.");
				return;
			}
			id = (String) (model.getValueAt(row, 0));
		} else { // 일반 사용자
			id = login.tf_id.getText().trim();
		}
		sql = "select * from dataDB where id='" + id + "'";
		try {
			rs = stmt.executeQuery(sql);
			if (!rs.next()) {
				JOptionPane.showMessageDialog(this,
						"데이터가 존재하지 않습니다.");
				return;
			}
			submit.tf_id.setText(rs.getString("id"));
			String pass = rs.getString("pass");
			submit.tf_pass1.setText(pass);
			submit.tf_pass2.setText(pass);
			submit.tf_name.setText(rs.getString("name"));
			String jumin = rs.getString("jumin");
			submit.tf_jumin1.setText(jumin.substring(0, 6));
			submit.tf_jumin2.setText(jumin.substring(7));
			String tel = rs.getString("tel");
			int index = tel.indexOf('-');
			if (index == -1) {
				submit.tf_tel1.setText(tel);
				submit.tf_tel2.setText(null);
				submit.tf_tel3.setText(null);
			} else {
				submit.tf_tel1.setText(tel.substring(0, index));
				tel = tel.substring(index + 1);
				index = tel.indexOf('-');
				if (index == -1) {
					submit.tf_tel2.setText(tel);
					submit.tf_tel3.setText(null);
				} else {
					submit.tf_tel2.setText(tel.substring(0, index));
					submit.tf_tel3.setText(tel.substring(index + 1));
				}
				submit.tf_addr.setText(rs.getString("addr"));
				submit.job.select(rs.getString("job"));
			}
		} catch (Exception e) {
			System.out.println("검색창 생성 실패" + e);
		}
		submit.submit.setText("수정");
		submit.setVisible(true);
		submit.tf_id.setEnabled(false); // 체크를 못하게 막음
		submit.tf_name.setEnabled(false);
		submit.tf_jumin1.setEnabled(false);
		submit.tf_jumin2.setEnabled(false);
		submit.idCheck.setEnabled(false);
	}
	public void updateProcess() {
		Data d = new Data();
		d.id = submit.tf_id.getText().trim();
		d.pass = submit.tf_pass1.getText().trim();
		d.tel = submit.tf_tel1.getText().trim() + "-"
				+ submit.tf_tel2.getText().trim() + "-"
				+ submit.tf_tel3.getText().trim();
		d.addr = submit.tf_addr.getText().trim();
		d.job = submit.job.getSelectedItem();
		if (!d.isFull2()) {
			JOptionPane.showMessageDialog(submit, "기재사항 확인");
			return;
		}
		if (!d.pass.equals(submit.tf_pass2.getText().trim())) {
			JOptionPane.showMessageDialog(submit, "PassWord를 다시 확인.");
			return;
		}
		try {
			sql = "update datadb set pass = '" + d.pass + "', tel='"
					+ d.tel + "', addr='" + d.addr + "', job='" + d.job
					+ "' where id='" + d.id + "'";
			System.out.println("sql" + sql);
			stmt.executeUpdate(sql);
			submit.setVisible(false);
			JOptionPane.showMessageDialog(this, "정보가 수정되었습니다.");
		} catch (java.sql.SQLException ee) {
			System.out.println("내용 갱신 실패 : " + ee);
			ee.printStackTrace();
		}
		showProcess();
	}
	public void actionPerformed(ActionEvent e) {
		Object ob = e.getSource();
		if (ob == login.newID) {
			login.setVisible(false);
			submit.setVisible(true);
		} else if (ob == submit.submit) {
			String la = submit.submit.getText();
			if (la.equals("  등     록  ")) {
				if (!checkID(id)) {
					JOptionPane.showMessageDialog(submit,
							"이미 등록된 ID입니다.");
					return;
				}
				insertProcess();
				submit.tf_id.setText("");
				submit.tf_pass1.setText("");
				submit.tf_pass2.setText("");
				submit.tf_name.setText("");
				submit.tf_jumin1.setText("");
				submit.tf_jumin2.setText("");
				submit.tf_tel1.setText("");
				submit.tf_tel2.setText("");
				submit.tf_tel3.setText("");
				submit.tf_addr.setText("");
			} else {
				updateProcess();
			}
		} else if (ob == show) {
			showProcess();
		} else if (ob == del) {
			deleteProcess();
		} else if (ob == login.ok) {
			loginCheck();
		} else if (ob == submit.idCheck) {
			id = submit.tf_id.getText().trim();
			if (id.equals("")) {
				JOptionPane.showMessageDialog(submit, "아이디 입력");
				submit.tf_id.requestFocus();
				return;
			}
			if (checkID(id)) {
				JOptionPane.showMessageDialog(submit, "사용가능한 ID입니다.");
				submit.tf_pass1.requestFocus();
			} else {
				JOptionPane.showMessageDialog(submit,
						"등록된 ID입니다. 다시 입력하세요.");
				submit.tf_id.setText(null);
				submit.tf_id.requestFocus();
				return;
			}
		} else if (ob == update) {
			updateShow();
		} else if (ob == exit) {
			System.exit(0);
		} else if (ob == admin) {
			if (!id.equals("admin") || !pass.equals("admin")) {
				JOptionPane.showMessageDialog(submit,
						"관리자 인증이 필요합니다.\n관리자로 로그인 하세요.");
				return;
			} else {
				JOptionPane.showMessageDialog(submit, 
						"관리자 로그인 되었습니다.");
				del.setEnabled(true);
				flag = true; // 수정 버튼 관련
			}
		}
	}
//	public static void main(String[] args) {
//		DBMain dbm = new DBMain();
//	}
	
	
	public class Data {
		String id, name, pass, jumin, tel, addr, job;
		boolean isFull() {
			if (id=="" || name==""|| pass=="" || jumin.length() != 14 || tel=="" ||addr=="")
				return false;
			return true;
		}
		boolean isFull2() {
			if (id=="" || pass==""|| tel=="" || addr=="")
				return false;
			return true;
		}
		public String toString() {
			return "'"+id+"', "+pass+"', '"+name+"', '"+jumin+"', '"+tel+"', '"
					+addr+"', '"+job+"'";
	  }
	}

} 