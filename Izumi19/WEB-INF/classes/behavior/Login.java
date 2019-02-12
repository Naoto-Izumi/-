//すべての項目を入力されているのか、値は正しいものであるかの判断をここでする。
//判定してtrue,falseで値を返す。trueであればsessionに値を入れる。falseであれば
//不備がある点を指摘する。
//項目が入力されているか+データベースにあるかどうかを判定しないといけない
//ここから直接OrackeDBAを使えるようにしたい。
package behavior;
import gobou.Usergobou;
import behavior.OracleDBA;
import java.util.ArrayList;
public class Login{
	
	//このメソッドはusergobouに持たせているログインの判定を変える+
	//エラーメッセージを加えている。本当はここで入力不備があるかどうか
	//もっと詳しくエラーメッセージを加えたい。
	OracleDBA oracle=new OracleDBA();
	
	//ここでusergobouのhantei変数を変える。
	public void loginNull(Usergobou user){
		String username=user.getU_UserName();
		String password=user.getPassWord();
		
		if(username==null||username.length()==0){
			user.setHantei(false);
			user.addError("usernameが入っていません。");
		}
		if(password==null||password.length()==0){
			user.setHantei(false);
			user.addError("passwordが入っていません。");
		}
		if(username!=null&&password!=null&&username.length()!=0&&password.length()!=0){
			user.setHantei(true);
		}
	}
	
	public boolean login(Usergobou user){
		boolean loginkakunin=oracle.userSelect(user);
		return loginkakunin;
	}
	
	
}