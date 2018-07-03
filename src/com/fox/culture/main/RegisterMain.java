package com.fox.culture.main;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import com.fox.culture.dao.RegisterDAO;
import com.fox.culture.dto.RegisterDTO;






public class RegisterMain extends JFrame{
	Container contentPane  = super.getContentPane();
	JPanel addLecture, addRegister, addMember;
	JTabbedPane tabbedPane;
	JScrollPane tableScroll1, tableScroll2, tableScroll3;
	
	JButton[] buttons;
	String[] btnTitle = {"검색","등록","수정","삭제"};
	
	JTextField tfSearch;
	Checkbox chkClass, chkField, chkTarget;
	
	JTable table;	
	DefaultTableModel model;
	
	//실제 데이타를 저장할 Object타입의 2차원 배열	
	Object[][] rowData;
	//컬럼명을 저장할 Object타입의 1차원배열
	Object[] columnNames = {"강좌코드", "강의명", "강사명", "분야", "대상", "수강기간", "개강일", "정원", "수강료", "강의실"};
	
	//데이타베이스에 접속해서 DML작업을 위한 DAO계열 클래스 변수 선언
	RegisterDAO dao;
	
	//선택한 행의 인덱스를 저장하기 위한 변수
	int selectedRow = -1;
	
	//전체 및 검색을 구분하기 위한 int형 상수선언
	public static final int ALL =1;
	public static final int SEARCH =2;
	
	// 원본 비밀번호를 저장할 컬렉션
		List<String> originalPassword = new Vector<String>();
	
	
	public RegisterMain() {
		super("강좌 신청 및 관리 프로그램");
		createComponent();
		addComponent();
		addListener();
		setSize(1000, 700);
		setLocation(200,200);
		setVisible(true);
		
	}
	
	public void createComponent(){
	dao = new RegisterDAO();
		
		buttons = new JButton[4];
		//버튼 생성
		for(int i=0; i < buttons.length;i++){			
			buttons[i] = new JButton(btnTitle[i]);
		}
		//텍스트 필드 생성
		tfSearch = new JTextField(10);
		//체크박스 생성
		chkClass = new Checkbox ("강좌명", true);
		chkField = new Checkbox ("분야");
		chkTarget = new Checkbox ("대상");
		
		//테이블 및 모델 객체 생성
		table = new JTable();
		
		JTableHeader header=table.getTableHeader();		
		header.setBackground(Color.WHITE);
		header.setForeground(Color.BLACK);		
		header.setPreferredSize(new Dimension(0,30));
		header.setReorderingAllowed(false);
		header.setResizingAllowed(false);

		model = new DefaultTableModel();
		
		//프로그램 최초 실행시 모든 레코드 출력
		setTableData(ALL);	
		
	}
private void setTableData(int flag) {
		
		//1]DAO에서 모든 레코드 데이타를 컬렉션으로 얻기		
		List<RegisterDTO> list=null;
		if(flag==ALL) list=dao.getRecordAll();		
		else list = dao.getSearchRecord(tfSearch.getText().trim());
		
		//2]컬렉션에 저장된 모든 레코드를 model에 저장
		rowData = new Object[list.size()][columnNames.length];
		
		for(int i=0; i < list.size();i++){
			
			RegisterDTO dto = list.get(i);
			
			rowData[i][0]= dto.getRid();
			rowData[i][1]= dto.getRcode();
			rowData[i][2]= dto.getRclass();
			rowData[i][3]= dto.getRst();
			rowData[i][4]= dto.getRcost();
			rowData[i][5]= dto.getRmax();
			rowData[i][6]= dto.getRdate();
			rowData[i][7]= dto.getRname();
		}
		model.setDataVector(rowData, columnNames);
		
		//3]model을 table에 연결:model저장된 데이타가 table에 뿌려짐
		table.setModel(model);
		
		//4]데이타 정렬
		
		TableColumnModel tcm=table.getColumnModel();		
		table.setRowHeight(25);
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(JLabel.CENTER);
				
		for(int i=2;i < tcm.getColumnCount() ;i++){
			tcm.getColumn(i).setCellRenderer(dtcr);
			if(i==3) tcm.getColumn(i).setPreferredWidth(15);
		}
	}
	
	public void addComponent() {
		
		JPanel pnlWest = new JPanel(new GridLayout(22,0,0,0));
		pnlWest.add(new JLabel(""));
		pnlWest.add(new JLabel(""));
		pnlWest.add(new JLabel(" 검 색"));
		pnlWest.add(tfSearch);
		pnlWest.add(buttons[0]);
		JPanel pnlChkBox = new JPanel(new FlowLayout());
		pnlChkBox.add(chkClass);
		pnlChkBox.add(chkField);
		pnlChkBox.add(chkTarget);
		pnlWest.add(pnlChkBox);
		add(pnlWest,"West");
						
		JPanel pnlCenter = new JPanel(new BorderLayout());
		pnlCenter.add(new JScrollPane(table));
		add(pnlCenter,"Center");
	    /*
		addLecture.add(tableScroll1);
		tabbedPane.addTab("강좌관리", addLecture);
		addRegister.add(tableScroll2);
		tabbedPane.addTab("수강내역", addRegister);
		addMember.add(tableScroll3);
		tabbedPane.addTab("회원관리", addMember);
		
		contentPane.add(tabbedPane, BorderLayout.CENTER);
	    */
		
		JPanel pnlESouth = new JPanel(new FlowLayout());
		pnlESouth.add(buttons[1]);
		pnlESouth.add(buttons[2]);
		pnlESouth.add(buttons[3]);
		add(pnlESouth,"South");
		
	}
	
	private void addListener() {
		
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				//프로그램 종료시 자원반납을 위한 WindowEvent 처리
				this.addWindowListener(new WindowAdapter() {
					
					@Override
					public void windowClosing(WindowEvent e) {
						//자원반납
						dao.dbClose();
						System.exit(0);				
					}
				});
				
				MyHandler handler = new MyHandler();
				//버튼에 리스너 부착
				for(int i=0;i<buttons.length;i++) {
					buttons[i].addActionListener(handler);
				}
				//테이블에 리스너 부착
				table.addMouseListener(handler);
	}
	class MyHandler extends MouseAdapter implements ActionListener{
		
		//유효성 체크를 위한 메소드
		public boolean checkInput(int flag){
			
			if(flag == ALL){
				if(tfSearch.getText().trim().equals("")){
					JOptionPane.showMessageDialog(
							RegisterMain.this, "검색어를 입력하세요!");
					
					tfSearch.requestFocus();
					return false;
				}
					
			}/*			
			else{				
				if(chkClass || chkField || chkTarget) {
					JOptionPane.showMessageDialog(
							CourseMain.this, "검색 조건을 체크하세요.");
										
					return false;
				}						
			}*/						
			return true;
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			//마우스로 클릭한 테이블의 행 인덱스 얻기
			selectedRow=table.getSelectedRow();
		}	
						
		@Override
		public void actionPerformed(ActionEvent e) {
			Object obj = e.getSource();
			if(obj == buttons[0]){//검색
				
				//유효성 체크
				if(!checkInput(ALL)) return;
				//입력처리
				String search= tfSearch.getText().trim();
				RegisterDTO dto = new RegisterDTO();
				dao.getSearchRecord(search);
				
				if (search!=""){
				JOptionPane.showMessageDialog(buttons[0],"검색이 완료되었습니다.");
				}
				else {
				JOptionPane.showMessageDialog(buttons[0],"검색이 실패되었습니다!");
				}
				
			}
			else if(obj == buttons[1]){//등록
				//CourseReg();
				
			}
			else if(obj == buttons[2]){//수정
				//CourseReg();
			
			}
			else if(obj == buttons[3]){//삭제
				if(selectedRow == -1){
					JOptionPane.showMessageDialog(buttons[3],
							"먼저 삭제할 레코드를 선택바람!");
					return;
				}
				
			}
		}
	}
	
	public static void main(String[] args) {
		RegisterMain rsMain = new RegisterMain();
		
	
			
		}
	}


