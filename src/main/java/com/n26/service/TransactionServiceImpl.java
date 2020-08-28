package com.n26.service;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.n26.model.Transactions;
import com.n26.model.TransactionsStats;
import com.n26.repository.TransactionDAO;
import com.n26.utility.UtilityFunctions;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private TransactionDAO transactionDAOImpl;

	@Override
	public boolean processTransactions(Transactions transactions) throws Exception {
		try {
			//Transactions transactions = new ObjectMapper().readValue(trns, Transactions.class);
			
			 //ObjectMapper ObjectMapper =new ObjectMapper();
			 
			
			
			if (transactions.getAmount() == null || transactions.getTimestamp() == null) {

				throw new Exception("Invalid Jason");
			} else {
				BigDecimal num = new BigDecimal(transactions.getAmount());
				transactions.setAmount(num.setScale(2, BigDecimal.ROUND_HALF_UP).toString());

				DateTimeFormatter iso_8601_UTCFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")
						.withZone(ZoneId.of("UTC"));

				LocalDateTime dateTimeStampReceived = LocalDateTime.parse(transactions.getTimestamp(),
						iso_8601_UTCFormat);

				System.out.println("Date time received ----  " + dateTimeStampReceived.toString());

				// LocalDateTime currentDateTime
				// =LocalDateTime.parse(LocalDateTime.now(clock).toString()+"Z",
				// iso_8601_UTCFormat);
				LocalDateTime currentDateTimeStamp = UtilityFunctions.getCurrentLocalDateTimeStamp();

				System.out.println("current time stamp ----  " + currentDateTimeStamp.toString());
				
				long durationBwt=UtilityFunctions.timeStampDurationCalulator(transactions.getTimestamp());
				
				//if (currentDateTimeStamp.getDayOfYear() < dateTimeStampReceived.getDayOfYear()) {
				if (durationBwt<0) {
					throw new Exception("timestamp of future date");
				}

				//long durationBwt = Duration.between(currentDateTimeStamp, dateTimeStampReceived).abs().getSeconds();

				

				
				System.out.println("Durations -------" + durationBwt);
				if (durationBwt >=60) {
					
					System.out.println("greater than 60 -------" + durationBwt);
					
					throw new Exception("timestamp is older than 60 seconds");
				}

			}

			return transactionDAOImpl.processTransactions(transactions);

		} catch (Exception exe) {
			exe.printStackTrace();
			throw exe;
		}

	}

	@Override
	public Optional<TransactionsStats> getStatistics() {
		// TODO Auto-generated method stub
		
		if (transactionDAOImpl.getStatistics().getCount()==0) {
			return Optional.ofNullable(null);
		} else {
			
			System.out.println("Insersfsdfnksdjfsdkj        "+transactionDAOImpl.getStatistics());
			return Optional.ofNullable(transactionDAOImpl.getStatistics());
		}

	}

	@Override
	public void deleteTransactions() {
		// TODO Auto-generated method stub
		transactionDAOImpl.deleteTransactions();
	}

}
