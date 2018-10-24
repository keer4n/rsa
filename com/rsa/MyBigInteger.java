package com.rsa;

import java.math.BigInteger;

public class MyBigInteger extends java.math.BigInteger{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	public MyBigInteger(String val) {
		super(val);
	}
	
	public MyBigInteger(byte[] val) {
		super(val);
	}
	
	
	//exponentiation using square and multiply algorithm
	public BigInteger myPow(BigInteger exponent, BigInteger mod) {
		String exponentBits = exponent.toString(2);
		BigInteger res = new BigInteger(this.toString());

		for (int i = 1; i < exponentBits.length(); i++) {
			res = res.multiply(res).mod(mod) ;
			if(exponentBits.charAt(i) == '1' ) {
				res = res.multiply(this).mod(mod);
			
			}
		
		}
		return res;
	}
}
