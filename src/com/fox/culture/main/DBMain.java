package com.fox.culture.main;

import java.awt.Choice;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JButton;

public class DBMain extends Frame{ 
	private static final long serialVersionUID = 1L;
	public Label lid, lpass1, lpass2, lname, ljumin, ltel, laddr, ljob, lbar1, lbar2, lbar3, empty1, empty2, empty3, empty4;
	public TextField tf_id, tf_pass1, tf_pass2, tf_name, tf_jumin1, tf_jumin2, tf_tel1, tf_tel2, tf_tel3, tf_addr;
	public JButton idCheck, submit;
	public Choice job;
	public String name;
	Connection conn;
	Statement stmt;
	ResultSet rs;
//	public static void main(String[] args) {
//		DBMain dbf = new DBMain();
//	}
	public DBMain() {
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		setResizable(false);
		setFont(new Font("고딕체", Font.BOLD, 15));
		setLayout (new FlowLayout());
		setBackground(Color.yellow);
		setBounds(250, 230, 350, 320);
		lid = new Label(" 아 이 디 : ");
		lpass1 = new Label("비밀번호 : " );
		lpass2 = new Label("비번확인 : " );
		lname = new Label("이   름 : " );
		ljumin = new Label("주민번호 : " );
		ltel = new Label("전화번호 : " );
		laddr = new Label("주    소 : " );
		ljob = new Label("직    업 : " );
		lbar1 = new Label("-");
		lbar2 = new Label("-");
		lbar3 = new Label("-");
		empty1 = new Label("          ");
		empty2 = new Label("             ");
		empty3 = new Label("             ");
		empty4 = new Label("             ");
		tf_id = new TextField(10); 
		tf_pass1 = new TextField(10);
		tf_pass2 = new TextField(10);
		tf_name = new TextField(10);
		tf_jumin1 = new TextField(9);
		tf_jumin2 = new TextField(9);
		tf_tel1 = new TextField(3);
		tf_tel2 = new TextField(4);
		tf_tel3 = new TextField(4);
		tf_addr = new TextField(25);
		tf_pass1.setEchoChar('*');
		tf_pass2.setEchoChar('*');
		tf_jumin2.setEchoChar('*');
		idCheck = new JButton(" 중복 확인 ");
		submit = new JButton("등  록");
		job = new Choice();
		add(lid);  add(tf_id);  add(idCheck);
		add(lpass1);  add(tf_pass1);  add(empty1);
		add(lpass2);  add(tf_pass2);  add(empty2);
		add(lname);  add(tf_name);  add(empty3);
		add(ljumin);  add(tf_jumin1);  add(lbar1);
		add(tf_jumin2);  add(ltel);  add(tf_tel1);
		add(lbar2);  add(tf_tel2);  add(lbar3);
		add(tf_tel3);  add(laddr);  add(tf_addr);
		add(ljob);  add(job);  
		job.add("학생"); job.add("직장인");
		job.add("백수"); job.add("주부");
		job.add("은행원"); job.add("그 외에 기타");
		add(empty4); add(submit);
	}
}
