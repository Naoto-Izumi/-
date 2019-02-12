package servlet;


import java.io.IOException;

import java.util.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import behavior.*;
import gobou.*;

public class Searchservlet extends HttpServlet{
	
	public void doGet(HttpServletRequest req,HttpServletResponse res)
	throws IOException,ServletException{
		
		req.setCharacterEncoding("Windows-31J");
		//OracleDBAのSearchメソッドに取ってきた値を引数に渡す。
		//そのあとSearchメソッドはArrayList<Thrgobou>を戻すから
		//それを入れるための変数を用意する。
		//そのあとその変数をjspで表示させるためにsessionにその変数を渡す。
		//そのあとその変数をjsp側で取り出す。
		
		HttpSession session=req.getSession();
		
		String s=req.getParameter("serch");
		//ここでjspからパラメータを取ってる
		System.out.println("Searchservletの中の検索ワード:"+s);
		
		//oracleに"word"をわたす
		
		//かいてきた値を listとでとってる
		
		ArrayList<Thrgobou> result = OracleDBA.Search(s);
		//ここで値をとってきた
		
		
		session.setAttribute("result",result);

		
		RequestDispatcher dispatcher=
		req.getRequestDispatcher("result.jsp");
		
		dispatcher.forward(req,res);
	}
}