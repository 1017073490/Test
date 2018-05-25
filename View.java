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
	 * 根据需求定义可视化界面的基本标签，但不初始化
	 */
	JPanel p1, p2;
	JLabel bq1;
	JTextField txtfield;
	JButton bt1, bt2, bt3, bt4,bt5;
	JTable table1; // 用来显示和编辑常规二维单元表
	JScrollPane jsp;
	StudentData lrxx1;

	public View() {
		p1 = new JPanel();
		bq1 = new JLabel("请输入姓名");
		bq1.setFont(new Font("隶书", Font.ROMAN_BASELINE, 18));
		bq1.setBackground(Color.getHSBColor(120, 120, 80));
		txtfield = new JTextField(10);
		bt1 = new JButton("查询");
		bt1.setFont(new Font("隶书", Font.ROMAN_BASELINE, 18));
		bt1.setBackground(Color.getHSBColor(120, 80, 80));
		bt1.setMnemonic(KeyEvent.VK_S);
		bt1.setBorderPainted(false);
		bt1.addActionListener(this);
		bt1.setActionCommand("select");
		p1.add(bq1);
		p1.add(txtfield);
		p1.add(bt1);

		p2 = new JPanel();
		bt2 = new JButton("添加");
		bt2.addActionListener(this);
		bt2.setActionCommand("add");
		bt2.setMnemonic(KeyEvent.VK_A);
		bt2.setBorderPainted(false);
		bt2.setFont(new Font("隶书", Font.ROMAN_BASELINE, 18));
		bt2.setBackground(Color.getHSBColor(120, 80, 80));
		bt3 = new JButton("修改");
		bt3.addActionListener(this);
		bt3.setActionCommand("update");
		bt3.setMnemonic(KeyEvent.VK_U);
		bt3.setBorderPainted(false);
		bt3.setFont(new Font("隶书", Font.ROMAN_BASELINE, 18));
		bt3.setBackground(Color.getHSBColor(120, 80, 80));
		bt4 = new JButton("删除");
		bt4.addActionListener(this);
		bt4.setActionCommand("delete");
		bt4.setMnemonic(KeyEvent.VK_D);
		bt4.setBorderPainted(false);
		bt4.setFont(new Font("隶书", Font.ROMAN_BASELINE, 18));
		bt4.setBackground(Color.getHSBColor(120, 80, 80));
		bt5=new JButton("返回");
		bt5.addActionListener(this);
		bt5.setActionCommand("return");
		bt5.setMnemonic(KeyEvent.VK_R);
		bt5.setBorderPainted(false);
		bt5.setFont(new Font("隶书", Font.ROMAN_BASELINE, 18));
		bt5.setBackground(Color.getHSBColor(120, 80, 80));
		p2.add(bt2);
		p2.add(bt3);
		p2.add(bt4);
		p2.add(bt5);

		lrxx1 = new StudentData();
		table1 = new JTable(lrxx1); // 将Lrxx中的数据加入到table中以显示
		table1.setRowHeight(25);
		jsp = new JScrollPane(table1);

		this.add(jsp);
		this.add(p1, "North");
		this.add(p2, "South");

		this.setTitle("学生管理系统");
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
	 * 实现不同功能的监听
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("add")) {
			/*
			 * 调用Add类，参数是自动生成的， this为当前窗口下生成， "添加纪录"是生成的新窗口的标题， true是必须关闭新的窗口才可以操作原来的窗口
			 */
			Add add = new Add(this, "添加纪录", true);
			lrxx1 = new StudentData();
			// 表table1立即产生效果，无须刷新，
			// 此表的数据模型设置为 newModel，并向其注册以获取来自新数据模型的侦听器通知
			// 这个监听器通知就用Lrxx继承了AbstractTableModel。
			table1.setModel(lrxx1);
		}
		//查询操作
		else if (e.getActionCommand().equals("select")) {
			/*
			 * 传入新的Sql语句，在类Lrxx中重载的一个方法产生新的表， 即查询出来的内容
			 */
			String xingming = this.txtfield.getText().trim();
			String sql = "select * from Students where 姓名='" + xingming + "'";
			lrxx1 = new StudentData(sql);
			table1.setModel(lrxx1); 						// 表table1立即产生效果，无须刷新
		} 
		//删除操作
		else if (e.getActionCommand().equals("delete")) {
			int i = this.table1.getSelectedRow(); // 返回所有选定行的索引,如果没有选中，即返回值为-1;
			if (i == -1) {
				JOptionPane.showMessageDialog(null, "请先选择一行");
			}
			String st = (String) lrxx1.getValueAt(i, 0); // 返回类型为ojject类型，要强转为String类型
			Connection conn = null;
			PreparedStatement ptmt = null;
			try {
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;DatabaseName=OldManData", "sa",
						"sasasa");
				ptmt = conn.prepareStatement("delete from Students where 编号=?");
				ptmt.setString(1, st);
				ptmt.executeUpdate(); // 执行Sql语句，产生删除效果
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
			table1.setModel(lrxx1); // 立即生效
		} 
		//修改操作
		else if (e.getActionCommand().equals("update")) {
			/*
			 * 与add,delete类似，调用的update方法稍有不同
			 */
			int i = this.table1.getSelectedRow();
			if (i == -1) {
				JOptionPane.showMessageDialog(null, "请先选择一行");
				return;
			}
			new Update(this, "修改纪录", true, lrxx1, i);
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
