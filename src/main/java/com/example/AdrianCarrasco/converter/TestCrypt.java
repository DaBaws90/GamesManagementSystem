package com.example.AdrianCarrasco.converter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TestCrypt {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BCryptPasswordEncoder passEnc = new BCryptPasswordEncoder();
		System.out.println(passEnc.encode("user"));
	}

}
