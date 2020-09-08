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
		JLabel name = new JLabel("�� �� :");
		JTextField namefield = new JTextField(10);
		JLabel date = new JLabel("������� (8�ڸ�) :");
		JTextField datefield = new JTextField(20);
		JLabel number = new JLabel("��ȭ��ȣ('-'���� �Է�) :");
		JTextField numberfield = new JTextField(20);
		JTextArea output = new JTextArea();
		setSize(600,400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("ȸ�� ���� ���� ����ũ�� ���ø����̼�");
		setLayout(new FlowLayout());
		JButton addbutt = new JButton("ȸ�� �߰�");
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
						throw new Exception("�����ͺ��̽��� �Է��� �� �����ϴ�.");
					stmt = conn.createStatement();
					ResultSet rs = stmt.executeQuery("select * from userinfo where name='"+name+"';");
					output.setText("");
					if(rs.next()) output.append("�����ϴ� ȸ���Դϴ�.\n");
					else {
						String command = String.format("insert into userinfo"+
								" (name, date, number) values ('%s' , '%s','%s');", name, date, number);
						int rowNUM = stmt.executeUpdate(command);
						if(rowNUM < 1)
							throw new Exception("�����͸� DB�� �Է��� �� �����ϴ�.");
						output.append("ȸ�� �߰� �Ϸ�\n");
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
		JButton viewbutt = new JButton("ȸ�� ��� ��ȸ");
		viewbutt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection conn = null;
				Statement stmt = null;
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					conn=DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/desktopdb?serverTimezone=UTC","root","1234567890");
					if(conn==null)
						throw new Exception("�����ͺ��̽��� �Է��� �� �����ϴ�.");
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
		JButton searchbutt = new JButton("ȸ�� ã��");
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
						throw new Exception("�����ͺ��̽��� �Է��� �� �����ϴ�.");
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
		JButton modifybutt = new JButton("ȸ�� ���� ����");
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
						throw new Exception("�����ͺ��̽��� �Է��� �� �����ϴ�.");
					stmt = conn.createStatement();
					ResultSet rs = stmt.executeQuery("select * from userinfo where name='"+name+"';");
					output.setText("");
					if(rs.next()) {
						String command = String.format("update userinfo set "+
								"number := '"+number+"' where name = '"+name+"';");
						int rowNUM = stmt.executeUpdate(command);
						if(rowNUM < 1)
							throw new Exception("�����͸� DB�� �Է��� �� �����ϴ�.");
						output.append("ȸ�� ���� ���� �Ϸ�\n");
					}
					else {
						output.append("�������� �ʴ� ȸ���Դϴ�.\n");
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
		JButton deletebutt = new JButton("ȸ�� ����");
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
						throw new Exception("�����ͺ��̽��� �Է��� �� �����ϴ�.");
					stmt = conn.createStatement();
					ResultSet rs = stmt.executeQuery("select * from userinfo where name='"+name+"';");
					output.setText("");
					if(rs.next()) {
						String command = String.format("delete from userinfo where name='"+name+"';");
						int rowNUM = stmt.executeUpdate(command);
						if(rowNUM < 1)
							throw new Exception("�����͸� DB�� �Է��� �� �����ϴ�.");
						output.append("ȸ�� ���� �Ϸ�\n");
					}
					else {
						output.append("�������� �ʴ� ȸ���Դϴ�.\n");
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
