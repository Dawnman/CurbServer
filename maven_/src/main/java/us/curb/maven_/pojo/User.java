package us.curb.maven_.pojo;

public class User {

	private int userId;

    private String account;

    private String password;

    public User(){
    	
    }
    public User(int id ,String account, String password){
    	this.userId = id;
    	this.account = account;
    	this.password = password;
    }
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

	@Override
	public String toString() {
		return "User [userId=" + userId + ", account=" + account + ", password=" + password + "]";
	}
}
