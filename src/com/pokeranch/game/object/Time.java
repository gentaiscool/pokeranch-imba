package com.pokeranch.game.object;

import java.io.BufferedWriter;
import java.util.Scanner;

public class Time {
	private int year, month, day, hour, minute;
	
	public Time() {
		// TODO Auto-generated constructor stub
	}
	
	public void load(Scanner scan){
		try{
			year = scan.nextInt();
			month = scan.nextInt();
			day = scan.nextInt();
			hour = scan.nextInt();
			minute = scan.nextInt();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void save(BufferedWriter buf){
		try{
			buf.write(year + " ");
			buf.write(month + " ");
			buf.write(day + " ");
			buf.write(hour + " ");
			buf.write(minute);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void set(int p,int q,int r,int s,int t) {
		year = p;
		month = q;
		day = r;
		hour = s;
		minute = t;
	}
		
	public void addMinute(int x){
		int i = 0;
		while (i < x){
			if (minute < 59) 
				minute++;
			else {
				minute = 0;
				if(hour < 23) 
					hour++;
				else {	
					hour =0;
					if (day < 29) 
						day++;
					else {
						day = 1;
						if (month < 11) 
							month++;
						else {
							month = 1;
							year++;
						}
					}
				}
			}
			i++;
		}
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}
}
