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
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Threadservlet extends HttpServlet{
			
	public void doPost(HttpServletRequest req,HttpServletResponse res)throws IOException,ServletException{
		req.setCharacterEncoding("Windows-31J");

		String title=req.getParameter("title");
		String titleSentence=req.getParameter("titleSentence");
		String creator=req.getParameter("creator");
		String createthr=req.getParameter("createthr");
		req.setAttribute("createthr",createthr);
		String day=null;
		Calendar cl=Calendar.getInstance();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd(/*日曜*/)HH:mm:dd");
		try{
			day=sdf.format(cl.getTime());
		}catch(NullPointerException e){
			e.printStackTrace();
		}
		System.out.println("thrservlet内のdayの値は"+day);
		
		
		
		HttpSession session=req.getSession();
		//この変数で行き先を決める。
		String destination="/createboard.jsp";
		System.out.println("スレッドサーブレットのtitleは"+title);
		System.out.println("スレッドサーブレットのtitleSentenceは"+titleSentence);
		System.out.println("スレッドサーブレットのcreatorは"+creator);
		System.out.println("スレッドサーブレットのdayは"+day);
		
		Thrbeans thrbeans = new Thrbeans();
		Buildlogic buildlogic=new Buildlogic();
		
		thrbeans.setThreadName(title);
		thrbeans.setThreadSentence(titleSentence);
		thrbeans.setT_UserName(creator);
		thrbeans.setCreatedDate(day);
		thrbeans.setLastUpdate(day);
		
		buildlogic.thrbuildNull(thrbeans);
		if(thrbeans.getHantei()==true){
			////////////題名が入っていたらindex.jspに戻るようになる。
			//ここがindex.jspに戻るのではなくて、thread.jspに行くようにする。
			destination="/thread.jsp";
			//ここではスレッドの情報をインサートしてすべてのスレッドを引き出している。
			//ここでシーケンスごとインサートする。で、idを取り出してまたThrbeansに入れる。
			
			//ここでindex.jspで表示させるべきリストを更新。今さっき作ったスレッドを含むリストを変える。
			ArrayList<Thrbeans> thrList=buildlogic.thrbuild(thrbeans);
			String id=OracleDBA.searchId(creator);
			System.out.println("Threadservleで取り出したid:"+id);
			//ここで前に投稿したときの値を消すためにresListを消す。
			session.removeAttribute("resList");
			session.setAttribute("thread_id",id);//
			session.setAttribute("threadname",title);
			session.setAttribute("titleSentence",titleSentence);//
			session.setAttribute("thrList",thrList);//
			session.setAttribute("threadTime",day);//
			session.setAttribute("threadusername",creator);
			System.out.println("thrListをセッションに入れました。スレッドの登録、その後のデータの取得がうまくいきました。");//ここでとってきた新着順のスレッドのリストをセッションに入れている。
										
			
		}
		else{
			session.setAttribute("errors",thrbeans.getError());
		}
		
		
		RequestDispatcher dispatcher=req.getRequestDispatcher(destination);
		dispatcher.forward(req,res);
		
		
		
	}
	
	
	
	public void doGet(HttpServletRequest req,HttpServletResponse res)throws IOException,ServletException{
		req.setCharacterEncoding("Windows-31J");
		
	}
		
	
	
}
