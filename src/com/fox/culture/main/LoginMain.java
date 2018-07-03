package com.fox.culture.main;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;

public class LoginMain extends Frame {
	public Label lid, lpass, empty;
	public TextField tf_id, tf_pass;
	public JButton newID, ok;

	public static void main(String[] args) {
		LoginMain lg = new LoginMain();
	}

	public LoginMain() {
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		setFont(new Font("고딕체", Font.BOLD, 15));
		setLayout(new FlowLayout());
		setBackground(Color.yellow);
		setBounds(400, 300, 350, 130);
		setResizable(false);
		lid = new Label("I  D  :      ");
		lpass = new Label("password : ");
		empty = new Label(" ");
		tf_id = new TextField(10);
		tf_pass = new TextField(10);
		newID = new JButton("신규가입");
		ok = new JButton(" 로 그 인");
		add(lid);
		add(tf_id);
		add(newID);
		add(lpass);
		add(tf_pass);
		add(empty);
		tf_pass.setEchoChar('*');
		add(ok);
		setVisible(true);

	}

}
