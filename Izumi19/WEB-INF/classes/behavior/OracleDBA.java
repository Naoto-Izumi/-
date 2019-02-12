//今ここでsql文を作っているが、それよりもselectだけするメソッド、insertだけするメソッドなどやる命令ごとにsql文を
//渡して動くメソッドを作るほうがいい気がしている。だから最終的に四つくらいに収められたらよいかなー
//oracledbaの手前でgobouを入れるJavaファイルでsql文を作る。そのsql文をOracleDBAにいれてOracleDBAはただsql文の命令を
//聞くだけってしたほうが良いと思う。のちのちやろう。
package behavior;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import gobou.Usergobou;
import gobou.Resgobou;
import gobou.Thrgobou;
import java.text.SimpleDateFormat;
public class OracleDBA{
	private static Connection conn=null;
	private PreparedStatement st = null;
	private ResultSet rs=null;
	//sql文で該当した結果をgobouにいれてさらにそのgobouをリストとして返すためのリスト。
	//private ArrayList<Resgobou> resList=new ArrayList<Resgobou>();
	//private ArrayList<Thrgobou> thrList=new ArrayList<Thrgobou>();
	//このリストには多分一個しか入らない。ログインできるかどうかはこのリストにものが一個だけ入っているかどうか。
	
	
	
	//OracleDBAファイルを使うとき絶対に最初にやるやつ。
	//getConnectionは名前を変える。setConnectionみたいな名前にする。
	public static void getConnection(){
		try{
			System.out.println("jdbcドライバの設置");
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","info","pro");
			System.out.println("接続完了");
			//rs.close();
		}
		catch(Exception e){
			System.out.println("getConnectionの例外です。");
			e.printStackTrace();
		}
		
		
	}
	
	
	
	//date
	public ArrayList<Thrgobou> thrInsert(Thrgobou thr){
		System.out.println("thrInsert50行目開始");
		boolean hantei=false;
		ArrayList<Thrgobou> thrList=new ArrayList<Thrgobou>();
		ArrayList<Thrgobou> popList=new ArrayList<Thrgobou>();
		getConnection();
		//selectで何件のデータを取り出せているか
		int count=0;
		try{
			System.out.println("thrInsertのtry句開始");
			//デフォルトでデータベースに作られた時の時間が入っているからここでインサートしなくてもよい。
			//String sql="insert into board_thread values(?,?,?,?,?,?,thread_seq.NEXTVAL,?)";
			
			String sql="insert into board_thread(T_Thread_id,ThreadName,ThreadSentence,T_UserName,CreatedDate,LastUpdate) values(thread_seq.NEXTVAL,?,?,?,?,?)";
			st=conn.prepareStatement(sql);//ここがエラー
			System.out.println("thrInsertのstうまくいっている63行目");
			
			//st.setInt(1,thr.getT_Thread_id());//多分ここはやらなくてもthread_seq.NEXTVALでインサートされてると思う。
			st.setString(1,thr.getThreadName());
			st.setString(2,thr.getThreadSentence());
			//st.setInt(3,thr.getResCount());
			//st.setString(5,thr.getCreatedDate());
			//st.setString(6,thr.getLastUpdate());
			st.setString(3,thr.getT_UserName());
			st.setString(4,thr.getCreatedDate());
			st.setString(5,thr.getLastUpdate());

			//st.setInt(4,thread_seq.NEXTVAL);
//			st.setInt(4,thr.getReply_nb());
			//st.setDate(6,thr.getCreated_date()); //ここでは6つ目の?にsysdate入れたらよいかも。
			//st.setDate(7,thr.getLast_update());//updateはわからん。デフォルトで作った日を入れておく。その後に編集ボタンが押されたらそこに編集した時間を追加するって流れかな？
			System.out.println("thrInsertのset系終了p77行目");
			int num=st.executeUpdate();
			if(num==1){
				System.out.println("スレッドのinsertが成功しました。(thrInsertのやつ)。80行目");
			}
			sql="select * from board_thread where VISUAL = 'true' order by T_Thread_id desc";
			st=conn.prepareStatement(sql);
			System.out.println("thrInsertの二回目のstが成功。p84");
		
			rs=st.executeQuery();
			System.out.println("thrInsertのexecuteQuery()が開始。p87");
			while(rs.next()){
				
				Thrgobou tb=new Thrgobou();
				tb.setT_Thread_id(rs.getInt("T_Thread_id"));
				tb.setThreadName(rs.getString("ThreadName"));
				tb.setThreadSentence(rs.getString("ThreadSentence"));
				tb.setResCount(rs.getInt("ResCount"));
				tb.setCreatedDate(rs.getString("CreatedDate"));
				tb.setLastUpdate(rs.getString("LastUpdate"));
				thrList.add(tb);
				count=count+1;
			}
			System.out.println("thrInsert selectで"+count+"件データを取得できた。p100");
			
			
			//人気順のselect
			sql="select * from board_Thread where VISUAL = 'true' order by RESCOUNT desc ";
			st=conn.prepareStatement(sql);
			System.out.println("popListのselectが成功した OracleDBA:105行目");
			
			count = 0;
			rs=st.executeQuery();
			System.out.println("popListのexecuteQuery()が開始。p108");
			while(rs.next()){
				
				Thrgobou ptb=new Thrgobou();
				ptb.setT_Thread_id(rs.getInt("T_Thread_id"));
				ptb.setThreadName(rs.getString("ThreadName"));
				ptb.setThreadSentence(rs.getString("ThreadSentence"));
				ptb.setResCount(rs.getInt("ResCount"));
				ptb.setCreatedDate(rs.getString("CreatedDate"));
				ptb.setLastUpdate(rs.getString("LastUpdate"));
				popList.add(ptb);
				count=count+1;
			}
			System.out.println("popList selectで"+count+"件のデータを取得できた。OracleDBA122行目");
			st.close();
			
		}catch(SQLException e){
			System.out.println("thrInsertの例外です。");
			e.printStackTrace();
		}
		System.out.println("thrInsertメソッド終了");
		return thrList;
	}

	
	//Loginファイルでloginメソッドを使うときに使われる。
	public boolean userSelect(Usergobou user){
		System.out.println("userSelectメソッド開始");
		boolean hasClient=false;
		getConnection();
		//private static Connection conn=null;
		//private PreparedStatement st = null;
		//private ResultSet rs=null;
		
		try{
			
			String sql="select * from board_user where U_UserName = ? and PassWord = ?";
		
			st=conn.prepareStatement(sql);
			
			System.out.println("userSelectのstはうまくいったp127");
			st.setString(1,user.getU_UserName());
			st.setString(2,user.getPassWord());
			System.out.println("userSelectのsetStringがうまくいった。p130");
			rs=st.executeQuery();
			System.out.println("userSelectのrsがうまくいったp132");
			
			
			//レコードが選択されていない可能性がある。
			if(rs.next()){
				hasClient = true;
			}
			st.close();
		}catch(SQLException e){
			System.out.println("userSelectの例外です。");
			e.printStackTrace();
			
		
		}
		System.out.println("userSelectメソッド終了");
		return hasClient;
		
	}
	
	public boolean userInsert(Usergobou user){
		System.out.println("userInsertメソッド開始");
		boolean hantei=false;
		try{
			getConnection();
			String sql="insert into board_user values(?, ?)";
			st=conn.prepareStatement(sql);
			System.out.println("userInsertのstはうまくいった。158行目");
			st.setString(1,user.getU_UserName());
			st.setString(2,user.getPassWord());
			System.out.println("userInsertのstがうまくいった。162行目");
			int num=st.executeUpdate();
			//System.out.println("結果が返ってきた。");
			System.out.println("executeUpdateがうまくいった。165行目");
				
			if(num==1){
				hantei=true;
			}

			st.close();
		}
		catch(SQLException e){
			System.out.println("userInsertの例外です。");
			e.printStackTrace();
			
		}
		System.out.println("userInsertメソッド終了");
		return hantei;
		
	}
	
	
	public static ArrayList<Thrgobou> startSQL(){
		System.out.println("startSQLメソッド開始");
		ArrayList<Thrgobou> thrList=new ArrayList<Thrgobou>();
		try{
			String sql="select * from board_Thread where VISUAL = 'true' order by CreatedDate desc ";
			getConnection();
			
			PreparedStatement st=conn.prepareStatement(sql);
			System.out.println("startSQLのstがうまくいった。192行目");
			ResultSet rs=st.executeQuery();
			System.out.println("startSQLのrsに結果が入った。p194行目");
			while(rs.next()){
				Thrgobou tb=new Thrgobou();
				tb.setT_Thread_id(rs.getInt("T_Thread_id"));
				tb.setThreadName(rs.getString("ThreadName"));
				tb.setThreadSentence(rs.getString("ThreadSentence"));
				tb.setResCount(rs.getInt("ResCount"));
				tb.setCreatedDate(rs.getString("CreatedDate"));
				tb.setLastUpdate(rs.getString("LastUpdate"));
				tb.setT_UserName(rs.getString("T_UserName"));
				tb.setVisual(rs.getString("Visual"));
				thrList.add(tb);
				
			}
			System.out.println("startSQLでthrListを返す。208行目");
			st.close();
		}catch(SQLException e){
			System.out.println("startSQLの例外です。");
			e.printStackTrace();
		}
		System.out.println("startSQLメソッド終了");
		return thrList;
	}
	
	//ほんとはここはスレッドのidで検索すれば一発だけど今の時点で
	//うまくidを付ける方法が分からないからとりあえずtitleとcreatorで
	//検索をかけている。
	//このメソッド必要なくなるかも。
	public String thrsearch(int T_Thread_id){
		System.out.println("thrsearchメソッド開始");
		String ThreadName="";
		try{
			getConnection();
			String sql="select ThreadName from board_thread where T_Thread_id=? and VISUAL = 'true'";
			st=conn.prepareStatement(sql);
			st.setInt(1,T_Thread_id);
			System.out.println("thrsearchのstがうまくいった。230行目");
			rs=st.executeQuery();
			System.out.println("thrsearchのrsがうまくいった。232行目");
			if(rs.next()){
				ThreadName=rs.getString("ThreadName");
				System.out.println("thrsearchでスレッド名取得できた。235行目");
			}
			
			st.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		System.out.println("thrsearchメソッド終了");
		return ThreadName;
		
	}
	
	public ArrayList<Resgobou> ressearch(int threadid){
		System.out.println("ressearchメソッド開始");
		ArrayList<Resgobou> resList=new ArrayList<Resgobou>();
		
		try{
			getConnection();
			String sql="select * from board_res where R_Thread_id =? order by Res_id  desc";
			st=conn.prepareStatement(sql);
			System.out.println("ressearchのstがうまくいった。255行目");
			st.setInt(1,threadid);
			rs=st.executeQuery();
			System.out.println("ressearchのrsがうまくいった。258行目");
			while(rs.next()){
				Resgobou res=new Resgobou();
				res.setR_Thread_id(rs.getInt("R_Thread_id"));
				res.setRes_id(rs.getInt("Res_id"));
				res.setR_UserName(rs.getString("R_UserName"));
				res.setRes(rs.getString("Res"));
				res.setResTime(rs.getString("ResTime"));
				resList.add(res);
			}
			st.close();
		}catch(SQLException e){
			System.out.println("ressearchの例外です。");
			e.printStackTrace();
		}
		System.out.println("ressearchメソッド終了");
		return resList;
		
	}
	
	//ここで投稿されたレスを追加後、追加後のレスをすべてとってくる。
	//とりあえずcomentnumberは無視
	public void resInsert(Resgobou res,String resnumber){
		System.out.println("resInsertメソッド開始");
		int threadid=res.getR_Thread_id();
		int number=Integer.parseInt(resnumber);
		String restime=res.getResTime();
		System.out.println("resInsert内のrestimeは"+restime+"287行目");
		++number;
		try{
			getConnection();
			
			//res.getR_ThreadName() ResをインサートするときにidをThread.jspから送られるようにする。
			//String sql="insert into board_res values(?,?,?,"+res.getR_Thread_id()+"_seq.NEXTVAL"+",?,?)";
			System.out.println("insertするときのnumberは"+number+"です。");
			String sql="insert into board_res(R_Thread_id,Res_id,R_UserName,Res,ResTime) values(?,"+number+",?,?,?)";
			//sql2はインサートした後のResCountの更新のsql;
			String sql2="update board_thread set ResCount="+number+"where T_Thread_id="+threadid;
			
			st=conn.prepareStatement(sql);
			System.out.println("resinsertのst成功している。292行目");
			st.setInt(1,res.getR_Thread_id());
			//レスidはシーケンスをそのままインサートする。
			//st.setInt(2,res.getRes_id());
			st.setString(2,res.getR_UserName());
			st.setString(3,res.getRes());
			st.setString(4,res.getResTime());
			//st.setString(5,res.getResTime());
			int num=st.executeUpdate();
			System.out.println(" resinsertのexecuteUpdateがうまくいった。300行目");
			
			if(num==1){
				System.out.println("resinsert 投稿できた。insert成功 303行目");
			}
			else{
				System.out.println("resinsert 投稿できませんでした。insert失敗 306行目");
			}
			st=conn.prepareStatement(sql2);
			System.out.println("T_Thread_idのst成功。315行目");
			int num2=st.executeUpdate();
			if(num2==1){
				System.out.println("投稿後にthread表のrescountの更新に成功。318行目");
			}else{
				System.out.println("投稿後にrescountの更新に失敗している。320行目");
			}
			updateLasttime(threadid,restime);
			
			st.close();
		}catch(SQLException e){
			System.out.println("resInsertの例外です。");
			e.printStackTrace();

		}
		System.out.println("resInsertメソッド終了");
		
	}
	
	//そのスレッドに関するシーケンスがあるかどうかを判定。
	//なかったらスレッド名.seqというシーケンスを作る。
	//あったらそのままシーケンスを使う。
	//いったんresgobouを引数に取る。
	//
	//このメソッドはやめる。シーケンスの連番はインクリメントでする。
	public void creresseq(Resgobou res){
		try{
			getConnection();
			String sql="select max(Res_id) from board_res where R_Thread_id=?";
			st=conn.prepareStatement(sql);
			System.out.println("creresseq conn.prepareStatementはうまくいっている。330行目");
			st.setInt(1,res.getR_Thread_id());
			rs=st.executeQuery();
			System.out.println("st.executeQueryはうまくいっている。333行目");
			rs.next();
			System.out.println("rs.nextはうまくいっている。335行目");
			
			int hantei=rs.getInt(1);
			System.out.println("creresseq 判定の中身は"+hantei+"338行目");
			if(hantei==0){
				System.out.println("シーケンスの判断のための判定がうまくいった。");
				sql="CREATE SEQUENCE "+res.getR_Thread_id()+"_seq START WITH 1 INCREMENT BY 1 NOMAXVALUE";
				st=conn.prepareStatement(sql);
				System.out.println("if文の中のprepareStatemenetがうまくいった。");
				st.executeUpdate(sql);
				System.out.println("シーケンスを作成することができた。");
			}else{
				System.out.println("creresseq作成失敗");
				System.out.println(res.getR_Thread_id()+"_seqはすでに存在しています。");
			}
			st.close();
		}catch(SQLException e){
			System.out.println("creresseqの例外です。");
			e.printStackTrace();
		}
		System.out.println("creresseqメソッド終了");
	}
	
	//このメソッドもやめておいたほうが良いのかもしれない。
	//あらかじめデータベースに自分で作っておくとよいかも。
	//スレッドごとに毎回この判断するのは効率が悪い。
	public void crethrseq(){
		try{
			getConnection();
			String sql="select max(T_Thread_id) from board_thread";
			st=conn.prepareStatement(sql);
			rs=st.executeQuery();
			
			rs.next();
			//thr.setThread_id(rs.getInt(1));
			
			int hantei=rs.getInt(1);
			System.out.println("シーケンスの判断のための判定がうまくいった。");
			System.out.println("判定の中身は"+hantei);
			if(hantei==0){
				System.out.println("シーケンスの判断のためのif文開始");
				sql="CREATE SEQUENCE thread_seq START WITH 1 INCREMENT BY 1 NOMAXVALUE";
				st=conn.prepareStatement(sql);
				System.out.println("if文の中のprepareStatemenetがうまくいった。");
				st.executeUpdate(sql);
				System.out.println("シーケンスを作成することができた。");
			}
			else{
				System.out.println("すでにthread_seqは存在します。");
			}
			
			st.close();
		}catch(SQLException e){
			System.out.println("crethrseqの例外です。");
			e.printStackTrace();
		}
		System.out.println("crethrseqメソッド終了");
	}

	public ArrayList<Resgobou> resSelect(int R_Thread_id){
		System.out.println("resSelectメソッド開始");
		ArrayList<Resgobou> resList=new ArrayList<Resgobou>();
		try{
			getConnection();
			String sql="select * from board_res where R_Thread_id =? order by Res_id desc ";
			st=conn.prepareStatement(sql);
			System.out.println("resSelectのstがうまくいっている。401行目");
			st.setInt(1,R_Thread_id);
			rs=st.executeQuery();
			System.out.println("resSelectのexecutequeryがうまくいっている 404行目");
			while(rs.next()){
				Resgobou rb=new Resgobou();
				rb.setR_UserName(rs.getString("R_UserName"));
				rb.setRes(rs.getString("Res"));
				rb.setResTime(rs.getString("ResTime"));
				rb.setRes_id(rs.getInt("Res_id"));
				//System.out.println("comentnumberの値:"+rb.getRes_id());
				rb.setR_Thread_id(rs.getInt("R_Thread_id"));
//				rb.setThreadname(rs.getString("threadname"));	board_res表にはThreadNameは無い indexを変えよう。
				
				resList.add(rb);
			}
			st.close();
		}catch(SQLException e){
			System.out.println("resSelectの例外です。");
			e.printStackTrace();
		}
		System.out.println("resSelectメソッド終了");
		return resList;
	}
	
	
	public static ArrayList<Thrgobou> EditList(String ThreadName){
		System.out.println("EditListメソッド開始");
		ArrayList<Thrgobou> edit=new ArrayList<Thrgobou>();
		try{
			//
			String sql="select ThreadName,T_Thread_id,ResCount,CreatedDate from board_thread where T_UserName=? and VISUAL = 'true' order by T_Thread_id desc";
			getConnection();
			
			PreparedStatement st=conn.prepareStatement(sql);
			st.setString(1,ThreadName);
			System.out.println("EditListのstがうまくいっている。436行目");
			ResultSet rs=st.executeQuery();
			System.out.println("EditListのexecuteQueryがうまくいっている。438行目");
			int count=0;
			while(rs.next()){
				Thrgobou tb=new Thrgobou();
				tb.setThreadName(rs.getString("ThreadName"));
				System.out.println("EditList内のThreadNameの値:"+rs.getString("ThreadName"));
				tb.setT_Thread_id(rs.getInt("T_Thread_id"));
				tb.setResCount(rs.getInt("ResCount"));
				tb.setCreatedDate(rs.getString("CreatedDate"));
				edit.add(tb);
				count++;
				
			}
			System.out.println("EditListでは"+count+"回繰り返している。448行目");
			st.close();
		}catch(SQLException e){
			System.out.println("EditListの例外です。");
			e.printStackTrace();
		}	
		System.out.println("EditListメソッド終了");
	
		return edit;
	}
	public static void VisualThread(String T_Thread_id){
		System.out.println("VisualThreadメソッド開始");
		try{
			String sql = "update board_thread set Visual = 'false' where T_Thread_id =?";
			getConnection();
	
			PreparedStatement st=conn.prepareStatement(sql);
			System.out.println("VisualThreadのstがうまくいっている。465行目");
			st.setString(1,T_Thread_id);
	
			int num=st.executeUpdate();
			System.out.println("VisualThreadのexecuteUpdateがうまくいっている。469行目");
			System.out.println("VisualThreadで結果が返ってきた。440行目");
				
			if(num==1){
				System.out.println("VisualThread falesにした。update成功。473行目");
			}
			else{
				System.out.println("VisualThread trueのまま。updateしない。476行目");
			}
			conn.commit();
			st.close();
		}catch(SQLException e){
			System.out.println("VisualThreadの例外です。");
			e.printStackTrace();

		}
		
		System.out.println("VisualThreadメソッド終了");
	}
	
	
	//スレッドidだけを検索するメソッド
	public static String searchId(String name){
		String number=null;
		System.out.println("searchIdメソッド開始");
		try{
			getConnection();
			String sql="select max(T_Thread_id) from board_thread where T_UserName=? ";
			PreparedStatement st=conn.prepareStatement(sql);
			st.setString(1,name);
			ResultSet rs=st.executeQuery();	
			rs.next();
			number=rs.getString(1);
			if(number!=null||number.length()!=0){
				System.out.println("スレッドidを検索できました。p501");
			}
			else{
				System.out.println("スレッドの検索に失敗しました。p504");
			}
		}catch(SQLException e){
			System.out.println("searchIdの例外です。");
			e.printStackTrace();
		}
		System.out.println("searchIdメソッド終了");
		return number;
		
	}
	//テストでうまくいかなかったらインスタンスメソッドにする。
	public static String rescountSearch(String id){
		System.out.println("insertid開始");
		String number=null;
		
		try{
			getConnection();
			String sql="select max(ResCount) from board_thread where T_Thread_id=?";
			PreparedStatement st=conn.prepareStatement(sql);
			st.setString(1,id);
			ResultSet rs=st.executeQuery();	
			rs.next();
			number=rs.getString(1);
			if(number!=null||number.length()!=0){
				System.out.println("board_threadからResCountを検索できました。p530");
			}
			else{
				System.out.println("board_threadからResCountを検索できませんでした。p533");
			}
			
		}catch(SQLException e){
			System.out.println("insertidの例外です。");
			e.printStackTrace();
		}
		return number;
		
	}
	
	//boardthreadの更新時間を更新する処理
	//resinsertに使われる。
	public static void updateLasttime(int threadid,String restime){
		System.out.println("updateLasttimeが開始する。563行目");
		System.out.println("更新するべき時間："+restime);
		String sql="update board_thread set LastUpdate ="+"'"+restime+"'"+"where T_Thread_id ="+threadid;
		String day=null;
		try{
			
			PreparedStatement st=conn.prepareStatement(sql);
			System.out.println("stがうまくいっている。"+"574行目");
			int num=st.executeUpdate();
			System.out.println("executeUpdateがうまくいっている。"+"576行目");
			
			if(num==1){
				System.out.println("最終更新時間を更新できました。578行目");
			}else{
				System.out.println("最終更新時間を更新できませんでした。580行目");
			}
			conn.commit();
			
		}catch(SQLException e){
			System.out.println("updateLasttimeの例外。585行目");
			e.printStackTrace();
		}
		
		
	}
	
	
	
	
	
	
	
	
	
	public static String searchSent(int id){
		String threadsent=null;
		System.out.println("searchSentメソッド開始");
		try{
			getConnection();
			String sql="select ThreadSentence from board_thread where T_Thread_id="+id;
			PreparedStatement st=conn.prepareStatement(sql);
			ResultSet rs=st.executeQuery();	
			rs.next();
			threadsent=rs.getString(1);
			if(threadsent!=null||threadsent.length()!=0){
				System.out.println("threadsentenceを検索できました。p610");
			}
			else{
				System.out.println("threadsentence検索に失敗しました。p613");
			}
		}catch(SQLException e){
			System.out.println("searchSentの例外です。");
			e.printStackTrace();
		}
		System.out.println("searchSentメソッド終了");
		return threadsent;
		
	}
	
	public static String searchDay(int id){
		String day=null;
		System.out.println("searDayメソッド開始");
		try{
			getConnection();
			String sql="select CreatedDate from board_thread where T_Thread_id="+id;
			PreparedStatement st=conn.prepareStatement(sql);
			ResultSet rs=st.executeQuery();	
			rs.next();
			day=rs.getString(1);
			if(day!=null||day.length()!=0){
				System.out.println("dayを検索できました。p501");
			}
			else{
				System.out.println("dayの検索に失敗しました。p504");
			}
		}catch(SQLException e){
			System.out.println("searchDayの例外です。");
			e.printStackTrace();
		}
		System.out.println("searchDayメソッド終了");
		return day;
		
	}
	
	public static String searchThreadname(int id){
		String threadname=null;
		System.out.println("searchThreadnameメソッド開始");
		try{
			getConnection();
			String sql="select ThreadName from board_thread where T_Thread_id="+id;
			PreparedStatement st=conn.prepareStatement(sql);
			ResultSet rs=st.executeQuery();	
			rs.next();
			threadname=rs.getString(1);
			if(threadname!=null||threadname.length()!=0){
				System.out.println("threadnameを検索できました。p660");
			}
			else{
				System.out.println("スレッドの検索に失敗しました。p663");
			}
		}catch(SQLException e){
			System.out.println("searchThreadnameの例外です。");
			e.printStackTrace();
		}
		System.out.println("searchThreadnameメソッド終了");
		return threadname;
		
	}
	
	public static String searchThrUser(int id){
		String thruser=null;
		System.out.println("searchThrUserメソッド開始");
		try{
			getConnection();
			String sql="select T_UserName from board_thread where T_Thread_id="+id;
			PreparedStatement st=conn.prepareStatement(sql);
			ResultSet rs=st.executeQuery();	
			rs.next();
			thruser=rs.getString(1);
			if(thruser!=null||thruser.length()!=0){
				System.out.println("thruserを検索できました。p685");
			}
			else{
				System.out.println("thruserの検索に失敗しました。p688");
			}
		}catch(SQLException e){
			System.out.println("searchThrUserの例外です。");
			e.printStackTrace();
		}
		System.out.println("searchThrUserメソッド終了");
		return thruser;
		
	}
	
	
	public static String searchRescount(int id){
		String rescount=null;
		System.out.println("searchResnumberメソッド開始");
		try{
			getConnection();
			String sql="select ResCount from board_thread where T_Thread_id="+id;
			PreparedStatement st=conn.prepareStatement(sql);
			ResultSet rs=st.executeQuery();	
			rs.next();
			rescount=rs.getString(1);
			if(rescount!=null||rescount.length()!=0){
				System.out.println("rescountを検索できました。p685");
			}
			else{
				System.out.println("rescountの検索に失敗しました。p688");
			}
		}catch(SQLException e){
			System.out.println("searchRescountの例外です。");
			e.printStackTrace();
		}
		System.out.println("searchRescountメソッド終了");
		return rescount;
		
	}
	
	//人気順のselect	
	public static ArrayList<Thrgobou> popularSQL(){
		System.out.println("popularSQLメソッド開始");
		ArrayList<Thrgobou> popList=new ArrayList<Thrgobou>();
		try{
			String sql="select * from board_Thread where VISUAL = 'true' order by RESCOUNT desc ";
			getConnection();
			
			PreparedStatement st=conn.prepareStatement(sql);
			System.out.println("popularSQLのstがうまくいった。566行目");
			ResultSet rs=st.executeQuery();
			System.out.println("popularSQLのrsに結果が入った。p568行目");
			while(rs.next()){
				Thrgobou tb=new Thrgobou();
				tb.setT_Thread_id(rs.getInt("T_Thread_id"));
				tb.setThreadName(rs.getString("ThreadName"));
				tb.setThreadSentence(rs.getString("ThreadSentence"));
				tb.setResCount(rs.getInt("ResCount"));
				tb.setCreatedDate(rs.getString("CreatedDate"));
				tb.setLastUpdate(rs.getString("LastUpdate"));
				tb.setT_UserName(rs.getString("T_UserName"));
				tb.setVisual(rs.getString("Visual"));
				popList.add(tb);
				
			}
			System.out.println("popularSQLでpopListを返す。582行目");
			
			int size = popList.size();
			System.out.println("popListのsize : "+size);
				
			st.close();
		}catch(SQLException e){
			System.out.println("popularSQLの例外です。");
			e.printStackTrace();
		}
		System.out.println("popularSQLメソッド終了");
		return popList;
	}
	
	
	
	public static ArrayList<Thrgobou> Search(String word){
		ArrayList<Thrgobou> thrList=new ArrayList<Thrgobou>();
		try{
			getConnection();
			String sql="SELECT * FROM board_thread WHERE ThreadName LIKE '%"+word+"%'";
			PreparedStatement st=conn.prepareStatement(sql);
			//st.setString(1,word);
			ResultSet rs=st.executeQuery();
			System.out.println("while文までは成功している。");
			while(rs.next()){
				Thrgobou th=new Thrgobou();				
				th.setT_Thread_id(rs.getInt("T_Thread_id"));
				th.setThreadName(rs.getString("ThreadName"));
				th.setThreadSentence(rs.getString("ThreadSentence"));
				th.setResCount(rs.getInt("ResCount"));
				th.setCreatedDate(rs.getString("CreatedDate"));
				th.setLastUpdate(rs.getString("LastUpdate"));
				th.setT_UserName(rs.getString("T_UserName"));
				th.setVisual(rs.getString("Visual"));

				
				thrList.add(th);
			}
			st.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return thrList;
	}
	
	

}
