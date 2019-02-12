package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;
import info.Profile;

public class QueryTest{
	
	public static void main(String[] args){
		
		List<Profile> al = getQueryList();
		System.out.println("username      password"); //見出し

		for(int i = 0; i < al.size();i++){
			Profile prof = al.get(i);
			System.out.println(prof.getName()+"\t"+prof.getPass());
		
		}
	
	
	}
	
	public static List<Profile> getQueryList(){
		
		List<Profile> userList = new ArrayList<Profile>();
	
	
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");

			//Oracleに接続する
			Connection cn=
				DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","tuser","pass");
			System.out.println("接続完了");
			
			//select文
			String sql="select username, password from user_table";

			//Statementインターフェイスを実装するクラスをインスタンス化する
			Statement st=cn.createStatement();

			//select文を実行し
			//ResultSetインターフェイスを実装したクラスの
			//インスタンスが返る
			ResultSet rs=st.executeQuery(sql);

			//カーソルを一行だけスクロールし、データをフェッチする
			while(rs.next()){
				Profile prof = new Profile();
				
				String name = rs.getString(1);	//1列目のデータを取得
				String pass = rs.getString(2);	//2列目のデータを取得
				prof.setName(name);
				prof.setPass(pass);
				
				userList.add(prof);
				
				//System.out.println("username"+"\t"+"password"); //確認表示
				//System.out.println(name+"\t"+pass);				//確認その２
			}

			
			//Oracleから切断する
			cn.close();

			System.out.println("切断完了");
		}catch(ClassNotFoundException e){
			e.printStackTrace();
			System.out.println("クラスがないみたい。");
		}catch(SQLException e){
			e.printStackTrace();
			System.out.println("SQL関連の例外みたい。");
		}catch(Exception e){
			e.printStackTrace();
		}
		return userList;
		
	}
}











