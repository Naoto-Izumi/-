import javax.servlet.http.HttpServlet;	
import javax.servlet.http.HttpServletRequest;	
import javax.servlet.http.HttpServletResponse;	
import javax.servlet.ServletException;	
import javax.servlet.RequestDispatcher;	
import java.io.IOException;		//必須なインポート文
import java.io.PrintWriter;		//必須なインポート文
import info.Profile;	//必須なインポート文
import database.InsertTest;	//必須なインポート文
import database.QueryTest;	//必須なインポート文
import java.util.List;	//必要なインポート文

//import java.Profile;
//import java.util.InsertTest;
//import java.util.QueryTest;

public class KamataServlet extends HttpServlet{
	
	public void doPost(HttpServletRequest req,HttpServletResponse res)
	throws IOException,ServletException{
		
		//パラメータを受け取りたい
		req.setCharacterEncoding("SJIS");
	
		String name = req.getParameter("name");
		String pass = req.getParameter("pass");
		
		//データベースを書きこみたい。
		InsertTest.insertUser_Table(name,pass);
		
		//データベースからリストをもらいたい。
		List<Profile> pList=getList();
		
		
		
		//パラメーターをJSPに転送したい
		
		req.setAttribute("users",pList);
		
		//転送先のJSPを指定。
		RequestDispatcher dis = req.getRequestDispatcher("/list");
	
		//JSPに転送
		dis.forward(req,res);
	
	}
	
	
	public void doGet(HttpServletRequest req,HttpServletResponse res)
	throws IOException,ServletException{
	
		//データベースからリストをもらいたい。
		List<Profile> pList=getList();
		
		
		
		//パラメーターをJSPに転送したい
		
		req.setAttribute("users",pList);	//無視できる
		
		//転送先のJSPを指定。
		RequestDispatcher dis = req.getRequestDispatcher("/list");		//無視できる
	
		//JSPに転送
		dis.forward(req,res);
	
	}
	public List<Profile> getList(){
		List<Profile> pList = QueryTest.getQueryList();
		
		return pList;
	}
}
