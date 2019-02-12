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


public class Loginservlet extends HttpServlet{
			
	public void doPost(HttpServletRequest req,HttpServletResponse res)throws IOException,ServletException{
		req.setCharacterEncoding("Windows-31J");

		String username=req.getParameter("username");
		String password=req.getParameter("password");
		//javascriptでメッセージを出すために取り出す値
		String loginlog=req.getParameter("loginlog");
		System.out.println("Loginservletのloginlogの値は:"+loginlog);
		//この変数で行き先を決める。
		String destination="/login.jsp";
		HttpSession session=req.getSession();
		req.setAttribute("loginlogg",loginlog);
		System.out.println("Loginservletの中のセッションに入れたloginlogの値は"+session.getAttribute("loginlog"));
		
		Usergobou usergobou=new Usergobou();
		Login login=new Login();
		
		//usergobou.setUsername(username);
		//usergobou.setPassword(password);
		usergobou.setU_UserName(username);
		usergobou.setPassWord(password);
		
		
		login.loginNull(usergobou);
		//ここでセッションに入れているのは一回記入したらデータが消えてしまうという
		//状態を防ぐため。ログインミスってもデータが残るように。
		session.setAttribute("userlog",usergobou);
		
		if(usergobou.getHantei()==true){
			//ここでログインしている。
			
			if(login.login(usergobou)==true){
				destination="/index.jsp";
				//"user"の値があるかどうかでログインしているかどうかを決める。
				session.setAttribute("user",usergobou);
			}
			else{
				usergobou.addError("名前またはパスワードが間違っています。");
				session.setAttribute("errors",usergobou.getError());
				//System.out.println("に値を入れました。");
				//destination="kakunin.jsp";
			}
		}
		else{
			//21日野澤
			req.setAttribute("errors",usergobou.getError());
		}
		
		RequestDispatcher dispatcher=req.getRequestDispatcher(destination);
		dispatcher.forward(req,res);
		
		
		
	}
	
	
	
	
	public void doGet(HttpServletRequest req,HttpServletResponse res)throws IOException,ServletException{
		req.setCharacterEncoding("Windows-31J");
		
	}
}


