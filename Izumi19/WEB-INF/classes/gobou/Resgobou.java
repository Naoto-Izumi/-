//レスを投稿するときに必要な情報を格納する入れ物的な役割
package gobou;
import java.util.Date;
public class Resgobou{
	//private String username="";
	//private String coment="";
	//private Date comenttime=new Date();
	//private int comentnumber;
	//private String threadname=""; //どのスレッドのコメントなのかの判別するための変数
	//private int like; ←いいねとかつけるのであれば必要
	//private int thread_id;
	private int r_Thread_id;
	private int res_id;
	private String r_UserName;
	private String res;
	//dateはStringにする
	//private Date ResTime;
	private String resTime=null;
	
	public void setR_UserName(String username){
		this.r_UserName=username;
	}
	public void setRes(String coment){
		this.res=coment;
	}
	public void setResTime(String comenttime){
		this.resTime=comenttime;
	}
	public void setRes_id(int comentnumber){
		this.res_id=comentnumber;
	}
	
	public void setR_Thread_id(int thread_id){
		this.r_Thread_id=thread_id;
	}
	
	public String getR_UserName(){
		return this.r_UserName;
	}
	public String getRes(){
		return this.res;
	}
	public String getResTime(){
		return this.resTime;
	}
	public int getRes_id(){
		return this.res_id;
	}
	
	public int getR_Thread_id(){
		return this.r_Thread_id;
	}
}