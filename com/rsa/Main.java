package com.rsa;

import java.math.BigInteger;
import java.util.Random;

public class Main {
	private static final int BITLENGTH = 2048;
	private static BigInteger p,q,n,phiN,e;
	public static void main(String[] args) {
		Random r = new Random();
		p = BigInteger.probablePrime(BITLENGTH,r);
		q = BigInteger.probablePrime(BITLENGTH,r);
		n = p.multiply(q);
		phiN = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
		System.out.println("p: " + p.toString(16));
		System.out.println("q: " + q.toString(16));
		System.out.println("n: " + n.toString(16));
		System.out.println("BitLength: " + n.toString(2).length());
		
		
		e = BigInteger.probablePrime(BITLENGTH,r);
		BigInteger d = e.modInverse(phiN);
		System.out.println("e(hex): " + e.toString(16));
		System.out.println("d(hex): " + d.toString(16) + " (dec)" + d.toString());
		
		String message = "This is the secret message.";
		BigInteger mf = new BigInteger(message.getBytes());
		System.out.println("bitlength of message: " +mf.toString(2).length());
		System.out.println("message: " + message);
		BigInteger enc = encrypt(message);
		System.out.println("encryption(hex): " + enc.toString(16) + " (dec)"+ enc.toString() + " bitlength: " + enc.toString(2).length());
		
		
		BigInteger dec = decrypt(enc,d);
		System.out.println("decryption: " + new String(dec.toByteArray()));
		
	}
	
	public static BigInteger encrypt(String msg) {
		BigInteger msgInteger = new BigInteger(msg.getBytes());
		if(msgInteger.toString(2).length() > BITLENGTH*2) {
			System.out.println("ERROR: Message size greater than size of prime, exiting.");
			System.exit(1);
		}
		return myPow(msgInteger,e,n);
	}
	
	public static BigInteger decrypt(BigInteger enc, BigInteger d) {
		
		
		BigInteger yp = enc.mod(p);
		BigInteger yq = enc.mod(q);
		
		BigInteger dp = d.mod(p.subtract(BigInteger.ONE));
		BigInteger dq = d.mod(q.subtract(BigInteger.ONE));
		
		BigInteger xp = myPow(yp,dp,p);
		BigInteger xq = myPow(yq,dq,q);
		
		BigInteger cp = q.modInverse(p);
		BigInteger cq = p.modInverse(q);
		
		BigInteger res1 = q.multiply(cp).multiply(xp);
		BigInteger res2 = p.multiply(cq).multiply(xq);
		
		return new BigInteger(res1.add(res2).mod(n).toString());
	}

	private static BigInteger myPow(BigInteger base, BigInteger exponent, BigInteger mod) {
		String exponentBits = exponent.toString(2);
		BigInteger res = new BigInteger(base.toString());

		for (int i = 1; i < exponentBits.length(); i++) {
			res = res.multiply(res).mod(mod) ;
			if(exponentBits.charAt(i) == '1' ) {
				res = res.multiply(base).mod(mod);
			}
		
		}
		return res;
	}
}
