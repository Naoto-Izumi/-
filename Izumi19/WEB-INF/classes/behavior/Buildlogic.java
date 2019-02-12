//ここでスレッドを立てるときやレスを投稿するときに情報は入力されているかの確認。
package behavior;
import gobou.Thrgobou;
import gobou.Resgobou;
import behavior.OracleDBA;
import java.util.ArrayList;

public class Buildlogic{
	OracleDBA oracle=new OracleDBA();
	
	public void resbuildNull(Resgobou res){
		
	}
	public void thrbuildNull(Thrgobou thr){
		String title=thr.getThreadName();
		if(title==null||title.length()==0){
			thr.setHantei(false);
			thr.addError("題名が空欄です。");
		}
		else{
			thr.setHantei(true);
		}
	}
	
	public ArrayList<Thrgobou> thrbuild(Thrgobou thr){
		//ここで立てたスレッドの情報をデータベースに登録。その後にselectで
		//最終編集時の時間で並べ替えてここに格納。その後これを表示する。
		ArrayList<Thrgobou> thrList=oracle.thrInsert(thr);
		return thrList;
	}
	
	//スレッドネームの検索
	public String thrnamesearch(int threadid){
		String name=oracle.thrsearch(threadid);
	
		return name;
	}
	
	//すべてのレスからスレッドidのやつだけを取り出す。
	public ArrayList<Resgobou> ressearch(int threadid){
		ArrayList<Resgobou> resList=oracle.ressearch(threadid);
		return resList;
	}
	
	public void resInsert(Resgobou res,String id){
		oracle.resInsert(res,id);
	}
	
	public ArrayList<Resgobou> resSelect(int threadid){
		ArrayList<Resgobou> resList=oracle.resSelect(threadid);
		return resList;
	}
	
	public void createseq(){
		oracle.crethrseq();
	}
	public void createseq(Resgobou res){
		oracle.creresseq(res);
	}
	
	
	
	
	
}