//�o�^�ł��邩�ł��Ȃ����B
//��̓I�ɂ͓��͏��ɕs�����Ȃ����B
//true��false�Ŕ��肵�Ă��̌��true�ł���΃f�[�^�x�[�X�ɓo�^
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
			user.addError("���O�����L��");
			flag++;
		}
		if(password==null||password.length()==0){
			user.setHantei(false);
			user.addError("�p�X���[�h�����L��");
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