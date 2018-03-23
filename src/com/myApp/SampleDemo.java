package com.myApp;

public class SampleDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String str="navan";
		String str1="";
		for(int i=str.length()-1; i>=0; i--){
		str1=str1+str.charAt(i);
		}
		if (str.equals(str1))
	         System.out.println(str+" is a palindrome");
	      else
	         System.out.println(str+" is not a palindrome");
		
     
	}

}
