package com.pokeranch.game.object;

import java.util.Scanner;

public class Time {
	private int year, month, day, hour, minute;
	
	public Time() {
		// TODO Auto-generated constructor stub
	}
	
	public Time(Time source){
		year = source.year;
		month = source.month;
		day = source.day;
		hour = source.hour;
		minute = source.minute;
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
	
	public String toString(){
		StringBuilder str=new StringBuilder();	
		str.append(year+" "+month+" "+day+" "+hour+" "+minute);
		return str.toString();
	}
	
	public void set(int p,int q,int r,int s,int t) {
		year = p;
		month = q;
		day = r;
		hour = s;
		minute = t;
	}
		
	public void decMinute(int x){
		int i = 0;
		while(i < x){
			if((minute == 0) && (hour == 0) && (day == 0) && (month == 0) && (year == 0)){
				break;
			}
			else{
				if(minute > 0)
					minute--;
				else if(minute == 0){
					minute = 59;
					if(hour>0){
						hour--;
					}
					else if(hour == 0){
						hour = 23;
						if(day>0){
							day--;
						}
						else if(day==0){
							day = 30;
							if(month>0){
								month--;
							}
							else if(month == 0){
								month = 12;
								if(year>0){
									year--;
								}
								else{
									year = 0;
								}
							}
						}	
					}
				}
			}
			i++;
		}
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
