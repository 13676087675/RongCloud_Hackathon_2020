package com.article;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.datalink.DataLink;

public class DeleteReply extends HttpServlet {

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=gb2312");
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		String strarticle_no = request.getParameter("article_no");
		String speaker = request.getParameter("currentusername");
		String strid = request.getParameter("id");
		int id = Integer.parseInt(strid);
		String DELETE_SQL = "delete from re_article where id = ?";
		try{
			DataLink datalink = new DataLink();
			Connection con = datalink.getConnection();
			PreparedStatement ps = null;
			con.setAutoCommit(false);
			ps = con.prepareStatement(DELETE_SQL);
			ps.setInt(1,id);
			ps.executeUpdate();
			con.commit();
			try{
				ps.close();
				con.close();
			}catch(SQLException e2){
				session.setAttribute("dealforummsg","ɾ���ظ�ʧ��!");
				out.print("���ݿ�ر�ʧ��!");
				out.print("<script language='javascript'>");
				out.print("alert('��������');history.back();");
				out.print("</script>");
			}
		}catch(SQLException e){
			System.out.print(e.getMessage());
			session.setAttribute("dealforummsg","ɾ���ظ�ʧ��!");
			out.print("<script language='javascript'>");
			out.print("alert('��������');history.back();");
			out.print("</script>");
			out.flush();
		}
		session.setAttribute("dealforummsg","ɾ���ظ��ɹ�!");
		speaker = new String(speaker.getBytes("gb2312"),"ISO8859_1");
		response.sendRedirect("forum.jsp?username="+speaker+"&&numberpage=2&&serial="+strarticle_no);
		return;
	}

}
