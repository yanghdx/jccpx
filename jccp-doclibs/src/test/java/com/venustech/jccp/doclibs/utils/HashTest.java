package com.venustech.jccp.doclibs.utils;

import com.jfinal.kit.HashKit;

public class HashTest {

	public String md5(String str) {
		return HashKit.md5(str);
	}
	
	public String getPwd(String user, String pass) {
		return md5(md5(pass) + user);
	}
	
	public static void main(String[] args) {
		HashTest ht = new HashTest();
		System.out.println(ht.getPwd("admin", "123456"));

	}

}
