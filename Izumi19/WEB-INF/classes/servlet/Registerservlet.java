package servlet;

import beans.*;   
import arugo.*; 
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.RequestDispatcher;
import java.io.*;
import java.util.ArrayList;


public class Registerservlet extends HttpServlet{
			
	public void doPost(HttpServletRequest req,HttpServletResponse res)throws IOException,ServletException{
		req.setCharacterEncoding("Windows-31J");

		String username=req.getParameter("username");
		String password=req.getParameter("password");
		String registerr=req.getParameter("registerhantei");
		req.setAttribute("registerhantei",registerr);
		
		//この変数で行き先を決める。
		String destination="/register.jsp";
		HttpSession session=req.getSession();
		
		Userbeans userbeans=new Userbeans();
		
		userbeans.setU_UserName(username);
		userbeans.setPassWord(password);
	
		Register register=new Register();
		//ここで未記入がないか確認
		register.registerNull(userbeans);
		
		//未記入がなければ
		if(userbeans.getHantei()==true){
			if(register.register(userbeans)==true){
				destination="/index.jsp";
				session.setAttribute("user",userbeans);
				System.out.println("ユーザー登録できました。");
				
			}
			
		}
		else{ //未記入があったなら
			req.setAttribute("regierrors",userbeans.getError());
			
		}
		
		
		RequestDispatcher dispatcher=req.getRequestDispatcher(destination);
		dispatcher.forward(req,res);
		
		
		
	}
	
	
	public void doGet(HttpServletRequest req,HttpServletResponse res)throws IOException,ServletException{
		req.setCharacterEncoding("Windows-31J");
		
	}
	
	
}


