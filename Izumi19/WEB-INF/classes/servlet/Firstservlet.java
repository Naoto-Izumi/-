package servlet;

import gobou.*;   
import behavior.*; 
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.RequestDispatcher;
import java.io.*;
import java.util.ArrayList;


public class Firstservlet extends HttpServlet{
			
	public void doPost(HttpServletRequest req,HttpServletResponse res)throws IOException,ServletException{
		req.setCharacterEncoding("Windows-31J");
	}
	
	public void doGet(HttpServletRequest req,HttpServletResponse res)throws IOException,ServletException{
		req.setCharacterEncoding("Windows-31J");
	
		String destination="/index.jsp";
		HttpSession session=req.getSession();
		//ここで人気順のリストを取り出す処理もやる。まだ実装してない
		//OracleDBA.startSQLはとりあえずすべてのスレッドを日付順で取り出してリクエストスコープに入れただけ。
		session.setAttribute("thrList",OracleDBA.startSQL());
		session.setAttribute("popList",OracleDBA.popularSQL());
		System.out.println("セッションスコープにthrListを入れました。 Firstservlet 30行目");
		System.out.println("セッションスコープにpopListを入れました。 Firstservlet 31行目");
		RequestDispatcher dispatcher=req.getRequestDispatcher(destination);
		dispatcher.forward(req,res);
		
	}
	
}
