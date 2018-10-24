package com.rsa;

import java.math.BigInteger;
import java.util.Random;

public class Main {
	private static final int BITLENGTH = 2048;
	
	public static void main(String[] args) {
		Random r = new Random();
		BigInteger p = BigInteger.probablePrime(BITLENGTH,r);
		BigInteger q = BigInteger.probablePrime(BITLENGTH,r);
		BigInteger n = p.multiply(q);
		BigInteger phiN = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
		System.out.println("p: " + p.toString(16));
		System.out.println("q: " + q.toString(16));
		System.out.println("n: " + n.toString(16));
		System.out.println("BitLength: " + n.toString(2).length());
		
		
		BigInteger e = BigInteger.probablePrime(BITLENGTH,r);
		BigInteger d = e.modInverse(phiN);
		System.out.println("e: " + e.toString(16));
		System.out.println("d(hex): " + d.toString(16) + " (dec)" + d.toString());
		
		//message is fed as integer how to send in ascii
		String m1 = "kiran";
		byte[] m = m1.getBytes();
		BigInteger mf = new BigInteger("Kiran".getBytes());
		System.out.println("bitlength of message: " +mf.bitCount());
		System.out.println("print message in ascii?? :" + mf.toString());
		BigInteger enc = myPow(mf,e,n);
		System.out.println("encryption(hex): " + enc.toString(16) + " (dec)"+ enc.toString() + " bitlength: " + enc.toString(2).length() );
		BigInteger dec = crt(enc,d,p,q,n);
		System.out.println("decryption(hex): " + new String(dec.toByteArray()) + " (dec)");
		System.out.println("print decrypted message in ascii?: " + dec.toString());
		
	}
	
	public static BigInteger crt(BigInteger dec, BigInteger d, BigInteger p, BigInteger q, BigInteger n) {
	
		
		BigInteger yp = dec.mod(p);
		BigInteger yq = dec.mod(q);
		
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
