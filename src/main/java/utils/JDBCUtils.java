package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCUtils {
	public Connection conn;
	public PreparedStatement stat;
	public ResultSet rs;

	public JDBCUtils() {
		getConnection();
	}

	private void getConnection() {
		try {
			Class.forName(DBInfo.JDBC_DRIVER);
			conn = DriverManager.getConnection(DBInfo.DB_URL, DBInfo.USER, DBInfo.PASSWORD);
//			System.out.println(conn);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public ResultSet executeQuery(String sql, Object... obj) {
		try {
			stat = conn.prepareStatement(sql);
			for (int i = 0; i < obj.length; i++) {
				stat.setObject(i + 1, obj[i]);
			}
			rs = stat.executeQuery();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	public int executeUpdate(String sql, Object... obj) {
		int num = 0;
		try {
			stat = conn.prepareStatement(sql);
			for (int i = 0; i < obj.length; i++) {
				stat.setObject(i + 1, obj[i]);
			}
			num = stat.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return num;
	}

	public void close() {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (stat != null) {
			try {
				stat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public void commit(){
		if(conn!=null){
			try {
				conn.commit();
			}catch (SQLException e){
				e.printStackTrace();
			}
		}
	}
	public void setAutoCommit(Boolean truth){
		try {
			conn.setAutoCommit(truth);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}