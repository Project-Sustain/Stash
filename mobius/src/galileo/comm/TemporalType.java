package galileo.comm;

import java.time.Period;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Months;

import galileo.dht.hash.TemporalHash;

public enum TemporalType {
	YEAR(Calendar.YEAR), MONTH(Calendar.MONTH), DAY_OF_MONTH(Calendar.DAY_OF_MONTH), HOUR_OF_DAY(Calendar.HOUR_OF_DAY);
	
	private int type;
	
	private TemporalType(int type){
		this.type = type;
	}
	
	public int getType(){
		return this.type;
	}
	
	public static TemporalType fromType(int type){
		for(TemporalType tType : TemporalType.values())
			if (tType.getType() == type)
				return tType;
		return TemporalType.DAY_OF_MONTH;
	}
	
	public static int getLevel(TemporalType temporalType) {

		int temporalLevel = 1;
		switch (temporalType) {
		case HOUR_OF_DAY:
			temporalLevel = 4;
		case DAY_OF_MONTH:
			temporalLevel = 3;
		case MONTH:
			temporalLevel = 2;
		case YEAR:
			temporalLevel = 1;
		}
		return temporalLevel;
	}
	
	/**
	 * 
	 * @author sapmitra
	 * @param startTimeStamp the starting timestamp of a block
	 * @param currentTimeStamp the timestamp of the record
	 * @param desiredLevel the number of desired units between the two timestamps, could be month, hour...so on
	 * @return
	 */
	public static long getTemporalIndex (DateTime d1, long currentTimeStamp, int desiredLevel) {
		
		DateTime d2 = new DateTime(currentTimeStamp);
		
		long tmpIndex = 0;
		
		if(desiredLevel == 2)
			tmpIndex = Months.monthsBetween(d1, d2).getMonths();
		else if(desiredLevel == 3)
			tmpIndex = Days.daysBetween(d1, d2).getDays();
		else if(desiredLevel == 4)
			tmpIndex = Hours.hoursBetween(d1, d2).getHours();
		
		
		return tmpIndex;
		
	}
	
	/**
	 * Adds a certain amount of units to a given timestamp and returns the resulting timestamp
	 * @author sapmitra
	 * @param startTimestamp
	 * @param amount
	 * @param unit
	 * @return
	 */
	public static long addTime(long startTimestamp, int amount, int unit) {
		
		Calendar c = Calendar.getInstance();
		c.setTimeZone(TemporalHash.TIMEZONE);
		c.setTimeInMillis(startTimestamp);
		
		if(unit == 1)
			c.add(Calendar.YEAR, amount);
		else if (unit ==2)
			c.add(Calendar.MONTH, amount);
		else if (unit ==3)
			c.add(Calendar.DAY_OF_MONTH, amount);
		else if (unit ==4)
			c.add(Calendar.HOUR, amount);
		
		return c.getTimeInMillis();
		
		
	}
	
	
	public static void main(String arg[]) {
		
		//Monday, October 1, 2018 12:00:00 AM
		DateTime d1 = new DateTime(1535760000000l);
		
		//Wednesday, October 3, 2018 6:56:35 PM
		System.out.println(getTemporalIndex(d1, 1538352000000l, 2));
		System.out.println(getTemporalIndex(d1, 1538352000000l, 3));
		System.out.println(getTemporalIndex(d1, 1538352000000l, 4));
	}
	
}
