import javax.servlet.http.HttpServlet;	
import javax.servlet.http.HttpServletRequest;	
import javax.servlet.http.HttpServletResponse;	
import javax.servlet.ServletException;	
import javax.servlet.RequestDispatcher;	
import java.io.IOException;		//�K�{�ȃC���|�[�g��
import java.io.PrintWriter;		//�K�{�ȃC���|�[�g��
import info.Profile;	//�K�{�ȃC���|�[�g��
import database.InsertTest;	//�K�{�ȃC���|�[�g��
import database.QueryTest;	//�K�{�ȃC���|�[�g��
import java.util.List;	//�K�v�ȃC���|�[�g��

//import java.Profile;
//import java.util.InsertTest;
//import java.util.QueryTest;

public class KamataServlet extends HttpServlet{
	
	public void doPost(HttpServletRequest req,HttpServletResponse res)
	throws IOException,ServletException{
		
		//�p�����[�^���󂯎�肽��
		req.setCharacterEncoding("SJIS");
	
		String name = req.getParameter("name");
		String pass = req.getParameter("pass");
		
		//�f�[�^�x�[�X���������݂����B
		InsertTest.insertUser_Table(name,pass);
		
		//�f�[�^�x�[�X���烊�X�g�����炢�����B
		List<Profile> pList=getList();
		
		
		
		//�p�����[�^�[��JSP�ɓ]��������
		
		req.setAttribute("users",pList);
		
		//�]�����JSP���w��B
		RequestDispatcher dis = req.getRequestDispatcher("/list");
	
		//JSP�ɓ]��
		dis.forward(req,res);
	
	}
	
	
	public void doGet(HttpServletRequest req,HttpServletResponse res)
	throws IOException,ServletException{
	
		//�f�[�^�x�[�X���烊�X�g�����炢�����B
		List<Profile> pList=getList();
		
		
		
		//�p�����[�^�[��JSP�ɓ]��������
		
		req.setAttribute("users",pList);	//�����ł���
		
		//�]�����JSP���w��B
		RequestDispatcher dis = req.getRequestDispatcher("/list");		//�����ł���
	
		//JSP�ɓ]��
		dis.forward(req,res);
	
	}
	public List<Profile> getList(){
		List<Profile> pList = QueryTest.getQueryList();
		
		return pList;
	}
}
