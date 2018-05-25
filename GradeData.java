package View;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;
/*
 * AbstractTableModelҪ����һ������� TableModel ��Ϊ AbstractTableModel �����ֻ࣬���ṩ����������������ʵ�֣� 
  public int getRowCount();
  public int getColumnCount();
  public Object getValueAt(int row, int column);
 * ����Ҫ�ķ����࣬���Ǵ�������ݲ�ͬ��
 */
public class GradeData extends AbstractTableModel{
	static Vector rowData,columnName;
	PreparedStatement ptmt;
	Connection conn;
	ResultSet rs;

	public void act(String sql) {
//		Login getid=new Login();
//		String id=getid.getId();
//		Login login=new Login();
//		String id=login.txtUser.getText();
//		where admin=' " + id +  " '
		if(sql.equals("")) {
			sql="select * from GradeData ";
		}
		columnName=new Vector();
		columnName.add("����");
		columnName.add("ѧϰ״��");
		columnName.add("�����ν���");
		rowData=new Vector();
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn=DriverManager.getConnection("jdbc:sqlserver://localhost:1433;DatabaseName=OldManData", "sa", "sasasa");
			ptmt=conn.prepareStatement(sql);
			rs=ptmt.executeQuery();
			while(rs.next()) {
				Vector row=new Vector();
				row.add(rs.getString(1));
				row.add(rs.getString(2));
				row.add(rs.getString(3));
				rowData.add(row);
			}
		} catch (Exception e) {
			
		}
	}
	
	public GradeData(String sql) {
		this.act(sql);
	}
	
	public GradeData() {
		this.act("");
	}

	public int getColumnCount() {
		return this.columnName.size();
	}

	public int getRowCount() {
		
		return this.rowData.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		
		return ((Vector)this.rowData.get(rowIndex)).get(columnIndex);
	}
	
	public String getColumnName(int column) {
		return (String)this.columnName.get(column);
	}
}

