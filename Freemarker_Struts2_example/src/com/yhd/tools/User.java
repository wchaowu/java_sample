package com.yhd.tools;

public class User {
	private String firstname;
	private String lastname;
	private Boolean flag;

	public User() {
	}

	public User(String firstname, String lastname) {
		this.firstname = firstname;
		this.lastname = lastname;

	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public static void main(String[] args) {
		User u = new User();
		if(null==u.getFlag()){
			System.out.println("true");
		}else{
			System.out.println(u.getFlag());
		}
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

}