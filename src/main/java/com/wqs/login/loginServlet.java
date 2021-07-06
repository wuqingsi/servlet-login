package com.wqs.login;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;*/

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.wqs.domain.User;
import com.wqs.jdbc.utils.JDBCUtil;

/**
 * Servlet implementation class loginServlet
 */
@WebServlet("/loginServlet")
public class loginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1、获取请求参数
		String name = request.getParameter("username");
		String pwd = request.getParameter("pwd");
		System.out.println("name = "+name+ "pwd = "+pwd);
		//2、使用dbUtils连接到数据库
		System.out.println(JDBCUtil.getConn());
		
		QueryRunner qr =  new QueryRunner(JDBCUtil.getDataSource());
		String sql = "select * from user where name = ? and pwd = ?";
		User user = null;
		try {
			user = qr.query(sql, new BeanHandler<User>(User.class),name,pwd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//3、检查用户名密码是否正确
		if(user!=null) {
			response.getWriter().write("login success!! "+user.getName());
		}else {
			response.getWriter().write("login failed!!");
		}
		
	}

}
