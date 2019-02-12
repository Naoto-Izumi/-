//登録できるかできないか。
//具体的には入力情報に不備がないか。
//trueかfalseで判定してその後にtrueであればデータベースに登録
package behavior;
import gobou.Usergobou;
import behavior.OracleDBA;
import java.util.ArrayList;
public class Register{
	OracleDBA oracle=new OracleDBA();
	
	
	public void registerNull(Usergobou user){
		String username=user.getU_UserName();
		String password=user.getPassWord();
		int flag=0;
		if(username==null||username.length()==0){
			user.setHantei(false);
			user.addError("名前が未記入");
			flag++;
		}
		if(password==null||password.length()==0){
			user.setHantei(false);
			user.addError("パスワードが未記入");
			flag++;
		}
		
		if(flag==0){
			user.setHantei(true);
		}
	}
	
	public boolean register(Usergobou user){
		boolean hantei=oracle.userInsert(user);
		return hantei;
	}
}