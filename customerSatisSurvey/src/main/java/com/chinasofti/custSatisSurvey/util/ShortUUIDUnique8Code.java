package com.chinasofti.custSatisSurvey.util;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

public class ShortUUIDUnique8Code {

	
	public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",  
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",  
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",  
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",  
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",  
            "W", "X", "Y", "Z" };  
  
	private ShortUUIDUnique8Code() {
		
	}
	
    private static class ShortUUIDUnique8CodeInstance{
    	static ShortUUIDUnique8Code instance = new ShortUUIDUnique8Code();
    }
    
    public static ShortUUIDUnique8Code getInstance() {
    	return ShortUUIDUnique8CodeInstance.instance;
    }
	public static String generateShortUuid() {  
	    StringBuffer shortBuffer = new StringBuffer();  
	    String uuid = UUID.randomUUID().toString().replace("-", "");  
	    for (int i = 0; i < 8; i++) {  
	        String str = uuid.substring(i * 4, i * 4 + 4);  
	        int x = Integer.parseInt(str, 16);  
	        shortBuffer.append(chars[x % 0x3E]);  
	    }  
	    return shortBuffer.toString();  
	  
	}  
	
	public static void main(String[] args) {
		
		Set<String> randomCode = new HashSet<String>();
	       int j=0;
	       
	       for(int i=0; i<100; i++) {
		    	randomCode.add(generateShortUuid());
		    	
	       }
	       
	       Iterator<String> it = randomCode.iterator();
	       while(it.hasNext()) {
	    	   j++;
	    	   System.out.println(j);
	    	   System.out.println(it.next());
	       }

	}

}
