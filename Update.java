package View;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;

/*
 *   添加纪录的方法类
 * 创建对话框窗口的主要类。可以使用此类创建自定义的对话框，
 * 或者调用 JOptionPane 中的多个类方法来创建各种标准对话框。
 */
class Update extends JDialog implements ActionListener {
	JLabel label1, label2, label3, label4, label5, label6;
	JTextField txtField1, txtField2, txtField3, txtField4, txtField5, txtField6;
	JButton btn1, btn2;
	JPanel panel1, panel2, panel3, panel4;
	/*
	 * 构造方法
	 */
	public Update(Frame frame, String nextTitle, boolean orExit, StudentData lrxx1, int row) {
		/*
		 * JDialog(Frame owner, String title, boolean modal)是 创建一个具有指定标题、所有者 Frame
		 * 和模式的对话框。 多加入参数是方法中使用。
		 */
		super(frame, nextTitle, orExit);
		label1 = new JLabel("               学号      ");
		label2 = new JLabel("               姓名      ");
		label3 = new JLabel("               年龄     ");
		label4 = new JLabel("               性别     ");
		label5 = new JLabel("               身份证     ");
		label6 = new JLabel("               学习状况     ");

		txtField1 = new JTextField();
		txtField1.setText((String) lrxx1.getValueAt(row, 0)); // 调用Lrxx中的getValue方法，得到原本单元格中的数据。
		txtField1.setEditable(false); // 不可编辑
		txtField2 = new JTextField(5);
		txtField2.setText((String) lrxx1.getValueAt(row, 1));
		txtField3 = new JTextField(5);
		txtField3.setText((String) lrxx1.getValueAt(row, 2));
		txtField4 = new JTextField(5);
		txtField4.setText((String) lrxx1.getValueAt(row, 3));
		txtField5 = new JTextField(5);
		txtField5.setText((String) lrxx1.getValueAt(row, 4));
		txtField6 = new JTextField(5);
		txtField6.setText((String) lrxx1.getValueAt(row, 5));

		btn1 = new JButton("修改");
		btn1.addActionListener(this);
		btn1.setActionCommand("update2");
		btn2 = new JButton("取消");
		btn2.addActionListener(this);
		btn2.setActionCommand("quxiao");

		panel1 = new JPanel();
		panel2 = new JPanel();
		panel3 = new JPanel();
		panel4 = new JPanel();

		panel1.setLayout(new GridLayout(6, 1));
		panel2.setLayout(new GridLayout(6, 1));

		panel1.add(label1);
		panel1.add(label2);
		panel1.add(label3);
		panel1.add(label4);
		panel1.add(label5);
		panel1.add(label6);

		panel2.add(txtField1);
		panel2.add(txtField2);
		panel2.add(txtField3);
		panel2.add(txtField4);
		panel2.add(txtField5);
		panel2.add(txtField6);

		panel3.add(btn1);
		panel3.add(btn2);

		this.add(panel1, BorderLayout.WEST);
		this.add(panel2);
		this.add(panel3, BorderLayout.SOUTH);
		this.add(panel4, BorderLayout.EAST);

		this.setSize(370, 270);
		this.setLocation(401, 281);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setVisible(true);
	}
	/*
	 * 实现监听
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("update2")) {
			PreparedStatement ptmt = null;
			Connection conn = null;
			ResultSet rs = null;

			try {
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;DatabaseName=OldManData", "sa",
						"sasasa");
				/*
				 * 更新数据库中的值，值分别是重新get到的文本框中的值
				 */
				String ss = ("update Students set 姓名=?,年龄=?,性别=?,身份证号=?,身体状况=? where 编号=?");
				ptmt = conn.prepareStatement(ss);
				ptmt.setString(1, txtField2.getText());
				ptmt.setString(2, txtField3.getText());
				ptmt.setString(3, txtField4.getText());
				ptmt.setString(4, txtField5.getText());
				ptmt.setString(5, txtField6.getText());
				ptmt.setString(6, txtField1.getText());
				ptmt.executeUpdate();

				this.dispose();
			} catch (Exception e2) {
			} finally {
				try {
					if (rs != null) {
						rs.close();
					}
					if (ptmt != null) {
						ptmt.close();
					}
					if (conn != null) {
						conn.close();
					}

				} catch (Exception e3) {
				}
			}
		} else if (e.getActionCommand().equals("quxiao")) {
			this.dispose();
		}
	}
}
