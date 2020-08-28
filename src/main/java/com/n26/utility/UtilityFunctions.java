package com.n26.utility;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class UtilityFunctions {
	
	
	public static long timeStampDurationCalulator(String transTimeStamp)
	{
		
		DateTimeFormatter iso_8601_UTCFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")
				.withZone(ZoneId.of("UTC"));

		Instant timestamp = Instant.now();

		iso_8601_UTCFormat.format(timestamp);
		
		System.out.println("@@@@@@@@timeStampDurationCalulator@@@@@@@@@@@@   "+timestamp +"   " +LocalDateTime.now(ZoneId.of("UTC")).toString() + "Z");
		
		
		LocalDateTime dateTimeStampReceived = LocalDateTime.parse(transTimeStamp, iso_8601_UTCFormat);
		String currentDateStr=LocalDateTime.now(ZoneId.of("UTC")).toString();
		if(currentDateStr.contains("999"))
		{
			currentDateStr=currentDateStr.replace("999", "998")+"Z";
		}
		else if(currentDateStr.length()==19)
		{
			currentDateStr=currentDateStr+".001Z";
		}
		else
		{
			currentDateStr=currentDateStr+"Z";
		}
		

		LocalDateTime currentDateTime = LocalDateTime.parse(currentDateStr,iso_8601_UTCFormat);

		//long durationBwt = Duration.between(currentDateTime, dateTimeStampReceived).abs().getSeconds();
		
		long durationBwt = Duration.between(dateTimeStampReceived,currentDateTime ).getSeconds();
		
		if(durationBwt>60)
		{
			System.out.println(currentDateTime+"**************************"+dateTimeStampReceived+"  inside utility function@@@@   "+durationBwt);
		}
		else
		
		System.out.println(currentDateTime+"-----------------------------"+dateTimeStampReceived+"  inside utility function$$$$   "+durationBwt);
		
		return durationBwt;
	}
	
	public static LocalDateTime getCurrentLocalDateTimeStamp()
	{
		DateTimeFormatter iso_8601_UTCFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")
				.withZone(ZoneId.of("UTC"));

		
		Instant timestamp = Instant.now();

		iso_8601_UTCFormat.format(timestamp);
		
		
		
		System.out.println("@@@@@@@@@@@@@@@@@@@@  "+timestamp +"   " +LocalDateTime.now(ZoneId.of("UTC")).toString() + "Z");
		
		
		
		String currentDateStr=LocalDateTime.now(ZoneId.of("UTC")).toString();
		if(currentDateStr.contains("999"))
		{
			currentDateStr=currentDateStr.replace("999", "998")+"Z";
		}
		else if(currentDateStr.length()==19)
		{
			currentDateStr=currentDateStr+".001Z";
		}
		else
		{
			currentDateStr=currentDateStr+"Z";
		}
		
		LocalDateTime currentLocalDateTimeStamp = LocalDateTime.parse(currentDateStr, iso_8601_UTCFormat);
		
		
		
		

		return currentLocalDateTimeStamp;
	}

}
