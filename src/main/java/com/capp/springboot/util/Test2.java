package com.capp.springboot.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test2 {
public static void main(String[] args) throws ParseException {
	Long long1 = new Long(1643331482);
	
	Date today = new Date(System.currentTimeMillis());
	System.out.println("oday:"+today);
	Date exp = new Date(long1);
	System.out.println("exp:"+exp);
	Date tg = new Date(System.currentTimeMillis()+1000*60*60*24*60);
	System.out.println(tg.getTime());
	
	
	String myDate = "2014/10/29 18:10:45";
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	Date date = sdf.parse(tg.toString());
	long millis = date.getTime();
	
	
}
}
