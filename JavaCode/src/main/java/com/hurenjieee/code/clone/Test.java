package com.hurenjieee.code.clone;

import java.io.Serializable;

class Test implements Serializable {

	private static final long serialVersionUID = -477187085422294523L;
	
	public String userData = null;

	public Test(String userData) {
		this.userData = userData;
	}

	public Test() {
		super();
	}

	public String getUserData() {
		return userData;
	}

	public void setUserData(String userData) {
		this.userData = userData;
	}
	
	
}