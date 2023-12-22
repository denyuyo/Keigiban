package jp.sljacademy.bbs.bean;

public class AccountBean  {
	
	private String name;
	private String password;
	
	public AccountBean() {}
	
	public AccountBean(String name, String password) {
		this.name = name;
		this.password = password;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() { // 1. ユーザー名取得
		return this.name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() { // 2. パスワード取得
		return this.password;
	}
}
   