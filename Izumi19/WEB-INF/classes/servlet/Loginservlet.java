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
		//javascript�Ń��b�Z�[�W���o�����߂Ɏ��o���l
		String loginlog=req.getParameter("loginlog");
		System.out.println("Loginservlet��loginlog�̒l��:"+loginlog);
		//���̕ϐ��ōs��������߂�B
		String destination="/login.jsp";
		HttpSession session=req.getSession();
		req.setAttribute("loginlogg",loginlog);
		System.out.println("Loginservlet�̒��̃Z�b�V�����ɓ��ꂽloginlog�̒l��"+session.getAttribute("loginlog"));
		
		Usergobou usergobou=new Usergobou();
		Login login=new Login();
		
		//usergobou.setUsername(username);
		//usergobou.setPassword(password);
		usergobou.setU_UserName(username);
		usergobou.setPassWord(password);
		
		
		login.loginNull(usergobou);
		//�����ŃZ�b�V�����ɓ���Ă���͈̂��L��������f�[�^�������Ă��܂��Ƃ���
		//��Ԃ�h�����߁B���O�C���~�X���Ă��f�[�^���c��悤�ɁB
		session.setAttribute("userlog",usergobou);
		
		if(usergobou.getHantei()==true){
			//�����Ń��O�C�����Ă���B
			
			if(login.login(usergobou)==true){
				destination="/index.jsp";
				//"user"�̒l�����邩�ǂ����Ń��O�C�����Ă��邩�ǂ��������߂�B
				session.setAttribute("user",usergobou);
			}
			else{
				usergobou.addError("���O�܂��̓p�X���[�h���Ԉ���Ă��܂��B");
				session.setAttribute("errors",usergobou.getError());
				//System.out.println("�ɒl�����܂����B");
				//destination="kakunin.jsp";
			}
		}
		else{
			//21�����V
			req.setAttribute("errors",usergobou.getError());
		}
		
		RequestDispatcher dispatcher=req.getRequestDispatcher(destination);
		dispatcher.forward(req,res);
		
		
		
	}
	
	
	
	
	public void doGet(HttpServletRequest req,HttpServletResponse res)throws IOException,ServletException{
		req.setCharacterEncoding("Windows-31J");
		
	}
}


