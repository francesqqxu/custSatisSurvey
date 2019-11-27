package com.chinasofti.custSatisSurvey.util;

import java.util.Random;
import java.util.UUID;

public class UniqueIDtest {
	
	public static   String methodOne() {
		Random r = new Random();
		String n = System.nanoTime() + "" + r.nextInt();
		
		return n;
	}
	
	public static String methodTwo() {
		String s = UUID.randomUUID().toString();
		return s;
	}
	
	public static String getRandString(int length)
    {
        String charList = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        String rev = "";
        Random f = new Random();
        for(int i=0;i<length;i++)
        {
           rev += charList.charAt(Math.abs(f.nextInt())%charList.length());
        }
        return rev;
    }


	
	public static void main(String[] args) {
		
		/*
		 * String uniqueId = methodOne(); System.out.println(uniqueId);
		 */
         
        // System.out.println(methodTwo());
		for(int i=0; i<100; i++) {
		System.out.println(getRandString(6));
		}
	}

}
