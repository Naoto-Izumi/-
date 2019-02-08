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


public class Resservlet extends HttpServlet{
			
	//こっちがスレッド名を押した後のやつ。
	public void doGet(HttpServletRequest req,HttpServletResponse res)throws IOException,ServletException{
		req.setCharacterEncoding("Windows-31J");
		String action=req.getParameter("action");
		HttpSession session=req.getSession();
			System.out.println("doget始まった");
			//thread_idを付けたことにより不要。thread_idでスレッド名を検索する。
			
			String thread_id=req.getParameter("thread_id");
			int threadid=Integer.parseInt(thread_id);
			ArrayList<Resbeans> resList=new ArrayList<Resbeans>();
			Buildlogic buildlogic=new Buildlogic();
			String destination="/thread.jsp";
			session.setAttribute("thread_id",thread_id);
			session.setAttribute("threadname",req.getParameter("threadname"));
			session.setAttribute("threadusername",req.getParameter("creator"));
			//ここでtitleSentenceを検索してセッションに入れてあげないといけない。
			//ここで作られた日付を検索してセッションに入れてあげないといけない。
			session.setAttribute("titleSentence",OracleDBA.searchSent(threadid));
			session.setAttribute("threadTime",OracleDBA.searchDay(threadid));
			
			
			
			System.out.println("titleとcreatorの取得の動作はされている。");
			
		
			
			//ここでthreadid(thread_idをint型にしたやつ)を直接いれて検索する。
			resList=buildlogic.ressearch(threadid);
			session.setAttribute("resList",resList);
			
			
			RequestDispatcher dispatcher=req.getRequestDispatcher(destination);
			dispatcher.forward(req,res);
			
		
	
			
		
		
	}
	
	//こっちが投稿欄からのやつ。
	public void doPost(HttpServletRequest req,HttpServletResponse res)throws IOException,ServletException{
		req.setCharacterEncoding("Windows-31J");
		String destination="/thread.jsp";
		String username=req.getParameter("username");
		String coment=req.getParameter("coment");
		String thread_id=req.getParameter("thread_id");
		
		//ここで投稿できたかどうかの確認するための値を格納
		req.setAttribute("toko",req.getParameter("toko"));
		System.out.println("Resservletでのtokoの値:"+req.getParameter("toko"));
		
		int threadid=Integer.parseInt(thread_id);
		HttpSession session=req.getSession();
		String restime=null;
		//Calendar cl=Calendar.getInstance();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日HH時mm分");
		try{
			restime=sdf.format(new Date());//cl.getTime());
		}catch(NullPointerException e){
			e.printStackTrace();
		}
		
		
		//System.out.println("Resservlet内のotamesiの値は"+otamesi);
		System.out.println("Resservlet内のrestimeの値は"+restime);
		coment=coment.replaceAll("\n","<br>");
		coment=coment.replaceAll("\r","");
		coment=coment.replaceAll("\t","&nbsp");
		
		
		Resbeans resbeans=new Resbeans();
		Buildlogic buildlogic=new Buildlogic();
		
		
		resbeans.setR_UserName(username);
		resbeans.setRes(coment);
		resbeans.setR_Thread_id(threadid);
		resbeans.setResTime(restime);
		//resのテーブルのthreadname列をなくしたからresbeansでも値を保持しないほうにしている。
		//resbeans.setThreadname(threadname);
		
		////////ここでシーケンスがなかったら作成。あったら何もしない。
		/////buildlogic.createseq(resbeans);
		
		//そのスレッドに関するboard_threadのrescountの値の最大値を取り出す。
		//
		String rescount=OracleDBA.rescountSearch(thread_id);
		System.out.println("Resservletの今のres数は:"+rescount+"aaaaaaaaaaaaaaaaaaaaaaaaa");
		
		
		
		
		
		
		//調べて数字が返ってくるから帰ってきた数字を引数に渡してその値をboard_resのRes_idにinsertする。
		
		
		
		
		//ここでスレッドに対するコメントを追加。さらに追加後のレスリストを返して
		//その後リクエストスコープに入れてthread.jspで表示
		buildlogic.resInsert(resbeans,rescount);
		
		ArrayList<Resbeans> resList=buildlogic.resSelect(threadid);
		//indexで表示させるthreadの情報をここで更新。
		ArrayList<Thrbeans> thrList=OracleDBA.startSQL();
		
		
		if(resList.size()!=0){
			destination="/thread.jsp";
			session.setAttribute("resList",resList);
			session.setAttribute("thrList",thrList);
			System.out.println("リクエストスコープに入れることができた。");
		}
		else{
			//ほんとはエラーメッセージの流れるjspに流したい。
			destination="/thread.jsp";
		}
		
		
		
		
		RequestDispatcher dispatcher=req.getRequestDispatcher(destination);
		dispatcher.forward(req,res);
		
		
		
	}
	
	
		
	
	
}


