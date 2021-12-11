package com.aplazo.util;

import java.util.Calendar;
import java.util.Date;


public class Utileria {
	
	public static final Integer NUM_DAYS_WEEK=7;
	public static final Double DOUBLE_CERO=0.0;
	public static final Integer INTEGER_ONE=1;
	
	private Utileria() {}
	/**
	 * Metodo para calcular la fecha de siguiente semana n a partir de una fecha inicial
	 * @param numWeek numero de la semana a calcular a partir de la fecha inicial
	 * @param dateInitial fecha inicial para hacer el calculo
	 * @return fecha siguiente que contine el dia de la semana numWeek
	 */
   public static Date getNextDateWeek(int numWeek, Date dateInitial) {
	   
	   Calendar myCal = Calendar.getInstance();
	   myCal.setTime(dateInitial);  
	   
	   for(int i=0;i<numWeek;i++) {
	       myCal.add(Calendar.DAY_OF_YEAR, NUM_DAYS_WEEK);
	   }
	   return myCal.getTime();
   }

}
