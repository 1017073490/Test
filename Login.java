package View;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Login extends JFrame implements ActionListener {

	/*
	 * 设置登陆面板的基本GUI标签，并初始化
	 */
	JPanel p1;
	JPanel p2;
	JLabel userName = new JLabel("用户名：");
	JTextField txtUser = new JTextField(10);
	JLabel password = new JLabel("密码");
	JPasswordField txtPw = new JPasswordField(10);
	static JButton btnNormal = new JButton("用户登陆");
	JButton btnLogin = new JButton("管理员登陆");
	JButton btnRestart = new JButton("重置");
	JButton btnCancel = new JButton("退出");
	
	
	/*
	 * 在Java中，类中的静态方法不能直接调用动态方法。
	 * 只有将某个内部类修饰为静态类，然后才能够在静态类中调用该类的成员变量与成员方法
	 */
//	static class Mythread extends JFrame implements Runnable{
//		public void run() { //throws Exception
//				try{
//	 				Thread.sleep(3000); //1000毫秒等于1秒
//	 				
//	          	}
//				catch (Exception e){  
//				}					
//			
//			try {
//				new Login();
//				
//				new Mythread().dispose();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		
//		public Mythread() {
//			JLabel label=new JLabel(new ImageIcon("D:/Eclipse/Java/HomeWork/src/zhangxing/work/mynew.gif"));
//			this.add(label);
//			this.setVisible(true);
//			this.setResizable(true);
//			this.setSize(450, 150);
//			this.setLocation(300, 300);
//		}
//	}
	/*
	 * 调用构造方法
	 */
public static void main(String[] args) throws Exception {
	new Login();	
}
	
	

	/*
	 * 定义构造方法，界面的定义在其中
	 */
	public Login() throws Exception {
		super("登陆界面");
		p1 = new JPanel();
		p2 = new JPanel();
		p1.setLayout(new GridLayout(2, 2));
		/*
		 * 为一些标签添加简单的样式与快捷键 其中，用户登陆没有选择快捷键
		 */
		this.setLayout(null);
		userName.setFont(new Font("微软雅黑", Font.ITALIC, 18));
		userName.setBounds(100, 20, 50, 30);
		password.setFont(new Font("微软雅黑", Font.ITALIC, 18));
		password.setBounds(100, 50, 50, 30);
		txtUser.setFont(new Font("黑体", Font.BOLD, 18));
		txtUser.setBounds(200, 20, 80, 30);
		txtPw.setBounds(200, 50, 80, 30);
		btnNormal.addActionListener(this);
		btnNormal.setActionCommand("norlogin");
		btnNormal.setFont(new Font("华文行楷", Font.PLAIN, 15));
		btnNormal.setBackground(Color.GREEN);
		btnNormal.setBorderPainted(false);
		btnNormal.setBounds(50, 100, 80, 30);
		btnLogin.addActionListener(this);
		btnLogin.setActionCommand("login");
		btnLogin.setFont(new Font("华文行楷", Font.PLAIN, 15));
		btnLogin.setBackground(Color.GREEN);
		btnLogin.setBorderPainted(false);
		btnLogin.setMnemonic(KeyEvent.VK_ENTER);
		
		btnRestart.addActionListener(this);
		btnRestart.setActionCommand("restart");
		btnRestart.setFont(new Font("华文行楷", Font.PLAIN, 15));
		btnRestart.setBackground(Color.GREEN);
		btnRestart.setBorderPainted(false);
		btnRestart.setMnemonic(KeyEvent.VK_R);
		btnCancel.addActionListener(this);
		btnCancel.setActionCommand("exit");
		btnCancel.setFont(new Font("华文行楷", Font.PLAIN, 15));
		btnCancel.setBackground(Color.GREEN);
		btnCancel.setBorderPainted(false);
		btnCancel.setMnemonic(KeyEvent.VK_E);
		backGround bg=new backGround();
		bg.setBounds(0, 0, 450, 150);
		/*
		 * 将标签添加到面板中
		 */
		p1.add(userName);
		p1.add(txtUser);
		p1.add(password);
		p1.add(txtPw);
		p2.add(btnNormal);
		p2.add(btnLogin);
		p2.add(btnRestart);
		p2.add(btnCancel);

		/*
		 * 将面板添加到当前JFrame中来显示
		 */

		this.add(p1);
		this.add(p2);
		//this.add(bg);
		this.setLayout(new FlowLayout());
		this.setLocation(300, 300);
		this.setSize(450, 150);
		this.setVisible(true);
		this.setResizable(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	class backGround extends JPanel{
		Image image;
		public backGround() throws Exception {
			image=ImageIO.read(new File("D:/Eclipse/Java/HomeWork/src/C_fw658.png"));
			
		}
		
		public void paintCom(Graphics g) {
			g.drawImage(image, 0, 0, 450, 150, this);
		}
	}
	
	

	/*
	 * 定义按钮的监听，不同的按钮监听不同的功能， 并在其中调用定义的私有方法判断能否进入到数据的查询页面
	 */
	public void actionPerformed(ActionEvent e) {
		Connection conn = null;
		PreparedStatement ptmt = null;
		ResultSet rs = null;
		if (e.getActionCommand().equals("login")) {
			if (txtUser.getText().equals("") || txtPw.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "登录名和密码不能为空");
			} else {
				try {
					if (existDate(txtUser.getText(), txtPw.getText(), "select * from Admin where admin=? and pw=?")) {
						new View();
						this.dispose(); // 关闭当前的登陆窗口
					} else {
						JOptionPane.showMessageDialog(null, "账号密码错误");
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		} else if (e.getActionCommand().equals("norlogin")) {
			if (txtUser.getText().equals("") || txtPw.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "登录名和密码不能为空");
			} else {
				try {
					if (existDate(txtUser.getText(), txtPw.getText(),"select * from GradeData where admin=? and pw=?")) {
						new DataView();
						this.dispose(); // 关闭当前的登陆窗口
					} else {
						JOptionPane.showMessageDialog(null, "账号密码错误");
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		} else if (e.getActionCommand().equals("restart")) {
			txtUser.setText("");
			txtPw.setText("");
		} else if (e.getActionCommand().equals("exit")) {
			System.exit(0);
		}
	}

//	public String getId() {
//		return this.txtUser.getText().trim();
//	}
	/*
	 * 定义一个简单的方法，返回值为boolean类型，用来判断输入的数据是否是 已经存在在数据库中的账号密码，参数值由上述的判断语句传入
	 */
	private boolean existDate(String name, String password, String sql) throws Exception {
		String location="com.microsoft.sqlserver.jdbc.SQLServerDriver";
		Connection conn = null;
		PreparedStatement ptmt = null;
		ResultSet rs = null;
		Class.forName(location);
		conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;DatabaseName=OldManData", "sa", "sasasa");
		ptmt = conn.prepareStatement(sql);
		ptmt.setString(1, name);
		ptmt.setString(2, password);
		rs = ptmt.executeQuery();
		if (rs.next()) {
			return true;
		} else {
			return false;
		}

	}
	
	
	

}
