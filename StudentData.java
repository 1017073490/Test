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
 * ����Ҫ�ķ����࣬�ǽ����ݳ��롣���У�
 * AbstractTableModelҪ����һ������� TableModel ��Ϊ AbstractTableModel �����ֻ࣬���ṩ����������������ʵ�֣� 
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
	 * ��Ҫ�ķ������ǽ�����
	 */
	public void act(String sql) {

		if (sql.equals("")) {
			sql = "select * from Students";
		}
		columnName = new Vector();
		columnName.add("ѧ��");
		columnName.add("����");
		columnName.add("����");
		columnName.add("�Ա�");
		columnName.add("���֤");
		columnName.add("ѧϰ״��");
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
					 * �����࣬��������
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
	 * �������췽����������,����������ɾ�Ĳ��sql���
	 */
	public StudentData(String sql) {
		this.act(sql);
	}

	public StudentData() {
		this.act("");
	}

	/*
	 * getColumnCount()��getRowCount()��getValueAt��ϵͳ�Զ����ã��� �ֱ𷵻��У��еĳ��Ⱥ͵�Ԫ��ֵ
	 */
	public int getColumnCount() {
		return this.columnName.size();
	}

	public int getRowCount() {

		return this.rowData.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		// ���� row �� column λ�õĵ�Ԫ��ֵ
		return ((Vector) this.rowData.get(rowIndex)).get(columnIndex);
	}

	public String getColumnName(int column) {
		// ������Ĭ�����ƣ������������Ĭ�����Ƹ�Ϊ�������õ�����
		return (String) this.columnName.get(column);
	}
}
