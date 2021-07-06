package com.wqs.jdbc.utils;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;

import com.alibaba.druid.pool.DruidDataSourceFactory;

public class JDBCUtil {
	
	public static DataSource ds = null;
	public static QueryRunner qr = null;
	//public static BasicDataSource ds = null;
	static {
		try {
			Properties p = new Properties();
			//获取配置文件字节码路径
			String path = JDBCUtil.class.getClassLoader().getResource("db.properties").getPath();
			System.out.println("path: "+path);
			FileInputStream in = new FileInputStream(path);//读取配置文件
			p.load(in);//加载配置
			//配置导入
			//ds = BasicDataSourceFactory.createDataSource(p); //DBCP 
			ds = DruidDataSourceFactory.createDataSource(p); //Druid
			qr = new QueryRunner(ds);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static QueryRunner getQueryRunner() {
		return qr;
	}
	
	public static DataSource getDataSource() {
		return ds;
	}
	
	public static Connection getConn() {
		try {
			//2、通过ds对象发起连接，获取连接对象
			return ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static void close(Connection conn,Statement ps,ResultSet rs) {
		//5、释放资源
		if(rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
