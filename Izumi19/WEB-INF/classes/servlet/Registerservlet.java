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


public class Registerservlet extends HttpServlet{
			
	public void doPost(HttpServletRequest req,HttpServletResponse res)throws IOException,ServletException{
		req.setCharacterEncoding("Windows-31J");

		String username=req.getParameter("username");
		String password=req.getParameter("password");
		String registerr=req.getParameter("registerhantei");
		req.setAttribute("registerhantei",registerr);
		
		//���̕ϐ��ōs��������߂�B
		String destination="/register.jsp";
		HttpSession session=req.getSession();
		
		Usergobou usergobou=new Usergobou();
		
		usergobou.setU_UserName(username);
		usergobou.setPassWord(password);
	
		Register register=new Register();
		//�����Ŗ��L�����Ȃ����m�F
		register.registerNull(usergobou);
		
		//���L�����Ȃ����
		if(usergobou.getHantei()==true){
			if(register.register(usergobou)==true){
				destination="/index.jsp";
				session.setAttribute("user",usergobou);
				System.out.println("���[�U�[�o�^�ł��܂����B");
				
			}
			
		}
		else{ //���L�����������Ȃ�
			req.setAttribute("regierrors",usergobou.getError());
			
		}
		
		
		RequestDispatcher dispatcher=req.getRequestDispatcher(destination);
		dispatcher.forward(req,res);
		
		
		
	}
	
	
	public void doGet(HttpServletRequest req,HttpServletResponse res)throws IOException,ServletException{
		req.setCharacterEncoding("Windows-31J");
		
	}
	
	
}


