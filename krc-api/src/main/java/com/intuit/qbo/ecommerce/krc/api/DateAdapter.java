/**
 * 
 */
package com.intuit.qbo.ecommerce.krc.api;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.bind.DatatypeConverter;

/**
 * 
 * @author Vijayan Srinivasan
 * @since Dec 24, 2013 6:41:10 PM
 *
 */

public class DateAdapter {
	
	public static Date parseDate(String s) {
		return DatatypeConverter.parseDate(s).getTime();
	}

	public static String printDate(Date dt) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(dt);
		return DatatypeConverter.printDateTime(cal);
	}

}
