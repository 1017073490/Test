package View;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

/*
 * 最重要的方法类，是将数据出入。其中，
 * AbstractTableModel要创建一个具体的 TableModel 作为 AbstractTableModel 的子类，只需提供对以下三个方法的实现： 
  public int getRowCount();
  public int getColumnCount();
  public Object getValueAt(int row, int column);
 */
public class StudentData extends AbstractTableModel {
	static Vector rowData, columnName;
	PreparedStatement ptmt;
	Connection conn;
	ResultSet rs;

	/*
	 * 主要的方法，是将数据
	 */
	public void act(String sql) {

		if (sql.equals("")) {
			sql = "select * from Students";
		}
		columnName = new Vector();
		columnName.add("学号");
		columnName.add("姓名");
		columnName.add("年龄");
		columnName.add("性别");
		columnName.add("身份证");
		columnName.add("学习状况");
		rowData = new Vector();
		try {
			try {
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;DatabaseName=OldManData", "sa",
						"sasasa");
				ptmt = conn.prepareStatement(sql);
				rs = ptmt.executeQuery();
				while (rs.next()) {
					/*
					 * 集合类，存入数据
					 */
					Vector row = new Vector();
					row.add(rs.getString(1));
					row.add(rs.getString(2));
					row.add(rs.getString(3));
					row.add(rs.getString(4));
					row.add(rs.getString(5));
					row.add(rs.getString(6));
					rowData.add(row);
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				ptmt.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * 两个构造方法构成重载,用来区别增删改查的sql语句
	 */
	public StudentData(String sql) {
		this.act(sql);
	}

	public StudentData() {
		this.act("");
	}

	/*
	 * getColumnCount()，getRowCount()，getValueAt由系统自动调用，、 分别返回行，列的长度和单元格值
	 */
	public int getColumnCount() {
		return this.columnName.size();
	}

	public int getRowCount() {

		return this.rowData.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		// 返回 row 和 column 位置的单元格值
		return ((Vector) this.rowData.get(rowIndex)).get(columnIndex);
	}

	public String getColumnName(int column) {
		// 返回列默认名称，但传入参数将默认名称改为我们设置的名称
		return (String) this.columnName.get(column);
	}
}
