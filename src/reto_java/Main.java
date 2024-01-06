package reto_java;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Cuál es la fecha a consultar? (formato yyyy-mm-dd)");
		Scanner scanner = new Scanner(System.in);
		String date_string=scanner.nextLine();
		LocalDate dateIn = LocalDate.parse(date_string);
		
		LocalDate nextPaymentDate= getNextPaymentDate(dateIn);
		
		int daysCount = getWorkDaysBetweenTwoDate(dateIn,nextPaymentDate);
		//System.out.println("La fecha de pago próxima es"+nextPaymentDate);
		//System.out.println("Cantidad de dia a SUma: "+daysCount);
		
		LocalDate datePaymentFin= addDaysSkippingWeekends (dateIn,daysCount);
		System.out.println("La fecha de pago es: "+datePaymentFin);
	}
	
	public static int getWorkDaysBetweenTwoDate(LocalDate start, LocalDate end) {
		final DayOfWeek startW = start.getDayOfWeek();
		final DayOfWeek endW = end.getDayOfWeek();
		final long days = ChronoUnit.DAYS.between(start, end);
		final long daysWithoutWeekends= days-2 * ((days + startW.getValue())/7);
		int result=0;
		if(start.isEqual(end)) {
			
			if(startW == DayOfWeek.SUNDAY) {
				result= -2;
			}
			else if (startW == DayOfWeek.SATURDAY ) {
				result= -1;
			}
			else {
				result= (int)
						(daysWithoutWeekends
								+ (startW == DayOfWeek.SUNDAY ? 1 : 0)
								+ (startW == DayOfWeek.SATURDAY ? 1 : 0)
								+ (endW == DayOfWeek.SUNDAY ? 1 : 0)
								+ (endW == DayOfWeek.SATURDAY ? 1 : 0));
			}
		}
		else {
			result= (int)
					(daysWithoutWeekends
							+ (startW == DayOfWeek.SUNDAY ? 1 : 0)
							+ (startW == DayOfWeek.SATURDAY ? 1 : 0)
							+ (endW == DayOfWeek.SUNDAY ? 1 : 0)
							+ (endW == DayOfWeek.SATURDAY ? 1 : 0));
		}
		
		return result;
		
	}
	
	public static LocalDate getNextPaymentDate (LocalDate dateToQuery) {
		
		int dayQ = dateToQuery.getDayOfMonth();
		int monthQ = dateToQuery.getMonthValue();
		int yearQ = dateToQuery.getYear();
		int dayPay = 0;
		int monthPay =0;
		int yearPay =0;
		String monthStr ;
		if (dayQ>=0 && dayQ<=15) {
			dayPay=15;
			monthPay=monthQ;
			yearPay = yearQ ; 
			
		}
		else if (dayQ>15 && dayQ<=30) {
			dayPay = 30;
			monthPay = monthQ;
			yearPay = yearQ;
		}
		else if (dayQ==31) {
			dayPay=15;
			if(monthQ==12) {
				monthPay=1;
				yearPay = yearQ+1;
			}
			else {
				monthPay=monthQ+1;
				yearPay = yearQ;
			}
		}
		else{
			//
		}
		
		if(monthPay<10) {
			monthStr = "0"+ String.valueOf(monthPay);
		}
		else {
			monthStr = String.valueOf(monthPay);
		}
		LocalDate dateR = LocalDate.parse( String.valueOf(yearPay) +"-" + monthStr +"-"+String.valueOf(dayPay));
	
		return dateR;
	}
	
	public static LocalDate addDaysSkippingWeekends(LocalDate date, int days) {
	    LocalDate result = date;
	    int addedDays = 0;
	    while (addedDays < days) {
	        result = result.plusDays(1);
	        if (!(result.getDayOfWeek() == DayOfWeek.SATURDAY || result.getDayOfWeek() == DayOfWeek.SUNDAY)) {
	            ++addedDays;
	        }
	    }
	    int addedDays2 = 0;
	    while (addedDays2 > days) {
	        result = result.plusDays(-1);
	        if (!(result.getDayOfWeek() == DayOfWeek.SATURDAY || result.getDayOfWeek() == DayOfWeek.SUNDAY)) {
	            --addedDays2;
	        }
	    }
	    return result;
	}

}
