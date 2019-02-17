import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import info.Profile;
//import javax.servlet.NoContextException;

import database.InsertTest;
import database.QueryTest;

import java.util.List;
//import java.Profile;
//import java.util.InsertTest;
//import java.util.QueryTest;

import gobou.Resgobou;
import gobou.Thrgobou;
import gobou.Usergobou;

public class IzumiServlet extends HttpServlet{
	public void dpPost(HttpServletRequest req,HttpServletResponse res) 
	throws IOException,ServletException{
		req.setCharacterEncoding("Windows-31J");		//文字コードの名前
		
		String name = req.getParameter("name");			//組み込まれたデータをStringで返すもの。
		String pass = req.getParameter("pass");			//組み込まれたデータをStringで返すもの。
		
		req.setAttribute("name",name);		//requestに対してデータをセットするもの。
		req.setAttribute("pass",pass);		//requestに対してデータをセットするもの。
		
		//例外コード
		if(name==null||name.length()==0){
			throw new ServletException("入力内容がありません",null);
		}
		
		InsertTest.insertUser_Table(name,pass);
		
		List<Profile> pList=getList();
		
		Profile prof=new Profile();
		prof.setName(name);
		prof.setPass(pass);
		
		req.setAttribute("users",pList);
		
		//牛蒡班のソースを呼び出すソースコード(未完成)
		
		/*InsertTest.insertUser_Table(r_Thread_id,res_id,r_UserName,res,resTime);
		
		ArrayList<String> rList=getList();
		
		Resgobou res = new Resgobou();
		res.setR_UserName(username);
		res.setRes(coment);
		res.setResTime(comenttime);
		res.setRes_is(comentnumber);
		res.setR_Thread_id(thread_id);
		
		req.setAttribute("users",rList);*/
		
		//転送先のURL
		RequestDispatcher dis = req.getRequestDispatcher("/izumilist");
		
		dis.forward(req,res);
	}
	public void doGet(HttpServletRequest req,HttpServletResponse res) 
	throws IOException,ServletException{
			
		List<Profile> pList=getList();
			
		req.setAttribute("users",pList);
		
		RequestDispatcher dis = req.getRequestDispatcher("/izumilist");
		
		dis.forward(req,res);
	}
	public List<Profile> getList(){
		List<Profile> pList = QueryTest.getQueryList();
		
		return pList;
	}
}
