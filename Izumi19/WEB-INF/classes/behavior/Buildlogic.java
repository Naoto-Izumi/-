//�����ŃX���b�h�𗧂Ă�Ƃ��⃌�X�𓊍e����Ƃ��ɏ��͓��͂���Ă��邩�̊m�F�B
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
			thr.addError("�薼���󗓂ł��B");
		}
		else{
			thr.setHantei(true);
		}
	}
	
	public ArrayList<Thrgobou> thrbuild(Thrgobou thr){
		//�����ŗ��Ă��X���b�h�̏����f�[�^�x�[�X�ɓo�^�B���̌��select��
		//�ŏI�ҏW���̎��Ԃŕ��בւ��Ă����Ɋi�[�B���̌ケ���\������B
		ArrayList<Thrgobou> thrList=oracle.thrInsert(thr);
		return thrList;
	}
	
	//�X���b�h�l�[���̌���
	public String thrnamesearch(int threadid){
		String name=oracle.thrsearch(threadid);
	
		return name;
	}
	
	//���ׂẴ��X����X���b�hid�̂���������o���B
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