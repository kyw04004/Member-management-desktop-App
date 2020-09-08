import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;

class MyFrame extends JFrame {
	public MyFrame() {
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		JPanel panel4 = new JPanel();
		JPanel panel5 = new JPanel();
		JLabel name = new JLabel("이 름 :");
		JTextField namefield = new JTextField(10);
		JLabel date = new JLabel("생년월일 (8자리) :");
		JTextField datefield = new JTextField(20);
		JLabel number = new JLabel("전화번호('-'없이 입력) :");
		JTextField numberfield = new JTextField(20);
		JTextArea output = new JTextArea();
		setSize(600,400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("회원 정보 관리 데스크톱 애플리케이션");
		setLayout(new FlowLayout());
		JButton addbutt = new JButton("회원 추가");
		addbutt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = namefield.getText();
				String date = datefield.getText();
				String number = numberfield.getText();
				Connection conn = null;
				Statement stmt = null;
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					conn=DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/desktopdb?serverTimezone=UTC","root","1234567890");
					if(conn==null)
						throw new Exception("데이터베이스에 입력할 수 없습니다.");
					stmt = conn.createStatement();
					ResultSet rs = stmt.executeQuery("select * from userinfo where name='"+name+"';");
					output.setText("");
					if(rs.next()) output.append("존재하는 회원입니다.\n");
					else {
						String command = String.format("insert into userinfo"+
								" (name, date, number) values ('%s' , '%s','%s');", name, date, number);
						int rowNUM = stmt.executeUpdate(command);
						if(rowNUM < 1)
							throw new Exception("데이터를 DB에 입력할 수 없습니다.");
						output.append("회원 추가 완료\n");
					}
					namefield.setText("");
					datefield.setText("");
					numberfield.setText("");
					}
					catch (Exception e1) {
					}
				finally {
					try {
						stmt.close();
					}
					catch(Exception ignored){
					}
					try {
						conn.close();
					}
					catch(Exception ignored){
					}
				}
			}
		});
		JButton viewbutt = new JButton("회원 목록 조회");
		viewbutt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection conn = null;
				Statement stmt = null;
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					conn=DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/desktopdb?serverTimezone=UTC","root","1234567890");
					if(conn==null)
						throw new Exception("데이터베이스에 입력할 수 없습니다.");
					stmt = conn.createStatement();
					ResultSet rs = stmt.executeQuery("select * from userinfo");
					output.setText("");
					output.append("name\tdate\tnumber\n");
					while(true) {
						if(!rs.next()) break;
						String dbname = rs.getString("name");
						String dbdate = rs.getString("date");
						String dbnumber = rs.getString("number");
						output.append(dbname+"\t"+dbdate+"\t"+dbnumber+"\n");
					}
					namefield.setText("");
					datefield.setText("");
					numberfield.setText("");
					}
					catch (Exception e1) {
					}
				finally {
					try {
						stmt.close();
					}
					catch(Exception ignored){
					}
					try {
						conn.close();
					}
					catch(Exception ignored){
					}
				}
			}
		});
		JButton searchbutt = new JButton("회원 찾기");
		searchbutt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = namefield.getText();
				Connection conn = null;
				Statement stmt = null;
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					conn=DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/desktopdb?serverTimezone=UTC","root","1234567890");
					if(conn==null)
						throw new Exception("데이터베이스에 입력할 수 없습니다.");
					stmt = conn.createStatement();
					ResultSet rs = stmt.executeQuery("select * from userinfo where name like '%"+name+"%';");
					output.setText("");
					output.append("name\tdate\tnumber\n");
					while(true) {
						if(!rs.next()) break;
						String dbname = rs.getString("name");
						String dbdate = rs.getString("date");
						String dbnumber = rs.getString("number");
						output.append(dbname+"\t"+dbdate+"\t"+dbnumber+"\n");
					}
					namefield.setText("");
					datefield.setText("");
					numberfield.setText("");
					}
					catch (Exception e1) {
					}
				finally {
					try {
						stmt.close();
					}
					catch(Exception ignored){
					}
					try {
						conn.close();
					}
					catch(Exception ignored){
					}
				}
			}
		});
		JButton modifybutt = new JButton("회원 정보 수정");
		modifybutt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = namefield.getText();
				String number  = numberfield.getText();
				Connection conn = null;
				Statement stmt = null;
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					conn=DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/desktopdb?serverTimezone=UTC","root","1234567890");
					if(conn==null)
						throw new Exception("데이터베이스에 입력할 수 없습니다.");
					stmt = conn.createStatement();
					ResultSet rs = stmt.executeQuery("select * from userinfo where name='"+name+"';");
					output.setText("");
					if(rs.next()) {
						String command = String.format("update userinfo set "+
								"number := '"+number+"' where name = '"+name+"';");
						int rowNUM = stmt.executeUpdate(command);
						if(rowNUM < 1)
							throw new Exception("데이터를 DB에 입력할 수 없습니다.");
						output.append("회원 정보 수정 완료\n");
					}
					else {
						output.append("존재하지 않는 회원입니다.\n");
					}
					namefield.setText("");
					datefield.setText("");
					numberfield.setText("");
					}
					catch (Exception e1) {
					}
				finally {
					try {
						stmt.close();
					}
					catch(Exception ignored){
					}
					try {
						conn.close();
					}
					catch(Exception ignored){
					}
				}
			}
		});
		JButton deletebutt = new JButton("회원 삭제");
		deletebutt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = namefield.getText();
				Connection conn = null;
				Statement stmt = null;
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					conn=DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/desktopdb?serverTimezone=UTC","root","1234567890");
					if(conn==null)
						throw new Exception("데이터베이스에 입력할 수 없습니다.");
					stmt = conn.createStatement();
					ResultSet rs = stmt.executeQuery("select * from userinfo where name='"+name+"';");
					output.setText("");
					if(rs.next()) {
						String command = String.format("delete from userinfo where name='"+name+"';");
						int rowNUM = stmt.executeUpdate(command);
						if(rowNUM < 1)
							throw new Exception("데이터를 DB에 입력할 수 없습니다.");
						output.append("회원 삭제 완료\n");
					}
					else {
						output.append("존재하지 않는 회원입니다.\n");
					}
					namefield.setText("");
					datefield.setText("");
					numberfield.setText("");
					}
					catch (Exception e1) {
					}
				finally {
					try {
						stmt.close();
					}
					catch(Exception ignored){
					}
					try {
						conn.close();
					}
					catch(Exception ignored){
					}
				}
			}
		});
		panel1.add(name);
		panel1.add(namefield);
		panel2.add(date);
		panel2.add(datefield);
		panel3.add(number);
		panel3.add(numberfield);
		panel4.add(addbutt);
		panel4.add(viewbutt);
		panel4.add(searchbutt);
		panel4.add(modifybutt);
		panel4.add(deletebutt);
		panel5.add(output);
		this.add(panel1);
		this.add(panel2);
		this.add(panel3);
		this.add(panel4);
		this.add(panel5);
		setVisible(true);
	}
}

public class MemberManagement {
	public static void main(String[] args) {
		MyFrame f = new MyFrame();
	}
}
