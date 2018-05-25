package View;

import java.awt.Color;
import java.awt.Event;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.JTableHeader;

public class View extends JFrame implements ActionListener {
	/*
	 * ������������ӻ�����Ļ�����ǩ��������ʼ��
	 */
	JPanel p1, p2;
	JLabel bq1;
	JTextField txtfield;
	JButton bt1, bt2, bt3, bt4,bt5;
	JTable table1; // ������ʾ�ͱ༭�����ά��Ԫ��
	JScrollPane jsp;
	StudentData lrxx1;

	public View() {
		p1 = new JPanel();
		bq1 = new JLabel("����������");
		bq1.setFont(new Font("����", Font.ROMAN_BASELINE, 18));
		bq1.setBackground(Color.getHSBColor(120, 120, 80));
		txtfield = new JTextField(10);
		bt1 = new JButton("��ѯ");
		bt1.setFont(new Font("����", Font.ROMAN_BASELINE, 18));
		bt1.setBackground(Color.getHSBColor(120, 80, 80));
		bt1.setMnemonic(KeyEvent.VK_S);
		bt1.setBorderPainted(false);
		bt1.addActionListener(this);
		bt1.setActionCommand("select");
		p1.add(bq1);
		p1.add(txtfield);
		p1.add(bt1);

		p2 = new JPanel();
		bt2 = new JButton("���");
		bt2.addActionListener(this);
		bt2.setActionCommand("add");
		bt2.setMnemonic(KeyEvent.VK_A);
		bt2.setBorderPainted(false);
		bt2.setFont(new Font("����", Font.ROMAN_BASELINE, 18));
		bt2.setBackground(Color.getHSBColor(120, 80, 80));
		bt3 = new JButton("�޸�");
		bt3.addActionListener(this);
		bt3.setActionCommand("update");
		bt3.setMnemonic(KeyEvent.VK_U);
		bt3.setBorderPainted(false);
		bt3.setFont(new Font("����", Font.ROMAN_BASELINE, 18));
		bt3.setBackground(Color.getHSBColor(120, 80, 80));
		bt4 = new JButton("ɾ��");
		bt4.addActionListener(this);
		bt4.setActionCommand("delete");
		bt4.setMnemonic(KeyEvent.VK_D);
		bt4.setBorderPainted(false);
		bt4.setFont(new Font("����", Font.ROMAN_BASELINE, 18));
		bt4.setBackground(Color.getHSBColor(120, 80, 80));
		bt5=new JButton("����");
		bt5.addActionListener(this);
		bt5.setActionCommand("return");
		bt5.setMnemonic(KeyEvent.VK_R);
		bt5.setBorderPainted(false);
		bt5.setFont(new Font("����", Font.ROMAN_BASELINE, 18));
		bt5.setBackground(Color.getHSBColor(120, 80, 80));
		p2.add(bt2);
		p2.add(bt3);
		p2.add(bt4);
		p2.add(bt5);

		lrxx1 = new StudentData();
		table1 = new JTable(lrxx1); // ��Lrxx�е����ݼ��뵽table������ʾ
		table1.setRowHeight(25);
		jsp = new JScrollPane(table1);

		this.add(jsp);
		this.add(p1, "North");
		this.add(p2, "South");

		this.setTitle("ѧ������ϵͳ");
		this.setSize(500, 400);
		this.setLocation(201, 181);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setBackground(Color.BLUE);
		lrxx1 = new StudentData();
		table1.setModel(lrxx1);
	}
	/*
	 * ʵ�ֲ�ͬ���ܵļ���
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("add")) {
			/*
			 * ����Add�࣬�������Զ����ɵģ� thisΪ��ǰ���������ɣ� "��Ӽ�¼"�����ɵ��´��ڵı��⣬ true�Ǳ���ر��µĴ��ڲſ��Բ���ԭ���Ĵ���
			 */
			Add add = new Add(this, "��Ӽ�¼", true);
			lrxx1 = new StudentData();
			// ��table1��������Ч��������ˢ�£�
			// �˱������ģ������Ϊ newModel��������ע���Ի�ȡ����������ģ�͵�������֪ͨ
			// ���������֪ͨ����Lrxx�̳���AbstractTableModel��
			table1.setModel(lrxx1);
		}
		//��ѯ����
		else if (e.getActionCommand().equals("select")) {
			/*
			 * �����µ�Sql��䣬����Lrxx�����ص�һ�����������µı� ����ѯ����������
			 */
			String xingming = this.txtfield.getText().trim();
			String sql = "select * from Students where ����='" + xingming + "'";
			lrxx1 = new StudentData(sql);
			table1.setModel(lrxx1); 						// ��table1��������Ч��������ˢ��
		} 
		//ɾ������
		else if (e.getActionCommand().equals("delete")) {
			int i = this.table1.getSelectedRow(); // ��������ѡ���е�����,���û��ѡ�У�������ֵΪ-1;
			if (i == -1) {
				JOptionPane.showMessageDialog(null, "����ѡ��һ��");
			}
			String st = (String) lrxx1.getValueAt(i, 0); // ��������Ϊojject���ͣ�ҪǿתΪString����
			Connection conn = null;
			PreparedStatement ptmt = null;
			try {
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;DatabaseName=OldManData", "sa",
						"sasasa");
				ptmt = conn.prepareStatement("delete from Students where ���=?");
				ptmt.setString(1, st);
				ptmt.executeUpdate(); // ִ��Sql��䣬����ɾ��Ч��
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (SQLException e1) {
				e1.printStackTrace();
			} finally {
				try {
					conn.close();
					ptmt.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			lrxx1 = new StudentData();
			table1.setModel(lrxx1); // ������Ч
		} 
		//�޸Ĳ���
		else if (e.getActionCommand().equals("update")) {
			/*
			 * ��add,delete���ƣ����õ�update�������в�ͬ
			 */
			int i = this.table1.getSelectedRow();
			if (i == -1) {
				JOptionPane.showMessageDialog(null, "����ѡ��һ��");
				return;
			}
			new Update(this, "�޸ļ�¼", true, lrxx1, i);
			lrxx1 = new StudentData();
			table1.setModel(lrxx1);
		}
		
		else if(e.getActionCommand().equals("return")) {
			try {
				new Login();
				this.dispose();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}

}
