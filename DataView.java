package View;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class DataView extends JFrame implements ActionListener{
	/*
	 * ������������ӻ�����Ļ���Ԫ�� ����Ƚϼ򵥣�ֻ�ǽ�������ʾ����
	 */
	JPanel p1;
	JButton bt1;
	JLabel bq1;
	JTable table1;
	JScrollPane jsp;
	GradeData phy;
	/*
	 * ��Ҫ�ķ�������������
	 */
	public DataView() {
		p1 = new JPanel();
		bq1 = new JLabel("��ӭ��");
		bq1.setFont(new Font("����", Font.ROMAN_BASELINE, 30));
		bq1.setBackground(Color.getHSBColor(120, 120, 80));
		
		bt1=new JButton("����");
		bt1.setFont(new Font("����", Font.ROMAN_BASELINE, 18));
		bt1.setBackground(Color.getHSBColor(120, 80, 80));
		bt1.setMnemonic(KeyEvent.VK_S);
		bt1.setBorderPainted(false);
		bt1.addActionListener(this);
		bt1.setActionCommand("return");
		p1.add(bq1);
		p1.add(bt1);
		phy = new GradeData();
		table1 = new JTable(phy);
		jsp = new JScrollPane(table1);
		
		
		this.add(jsp);
		this.add(p1, "North");
		this.setTitle("��ѯϵͳ");
		this.setSize(700, 300);
		this.setLocation(201, 181);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setBackground(Color.BLUE);
		phy = new GradeData();
		table1.setModel(phy);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("return")) {
			try {
				new Login();
			} catch (Exception e1) {
				e1.printStackTrace();
				this.dispose();
			}
		}
		
	}

}
