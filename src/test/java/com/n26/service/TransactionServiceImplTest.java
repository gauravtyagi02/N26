package com.n26.service;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.n26.model.Transactions;
import com.n26.model.TransactionsStats;
import com.n26.repository.TransactionDAOImpl;
import com.n26.utility.UtilityFunctions;

@RunWith(PowerMockRunner.class)
@PrepareForTest(UtilityFunctions.class)
public class TransactionServiceImplTest {

	@Rule
	public MockitoRule mockitoRule = MockitoJUnit.rule();

	@Mock
	TransactionDAOImpl transactionDAOImpl;

	@InjectMocks
	TransactionServiceImpl transactionServiceImpl;

	private DateTimeFormatter iso_8601_UTCFormat;
	

	private Transactions trnasactionInput;
	@Before
	public void initMocks() {
		iso_8601_UTCFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX").withZone(ZoneId.of("UTC"));
		 trnasactionInput = new Transactions();
		
	}

	@Test
	public void processTransactions_validTimestamp_shouldPost() throws Exception {
		
		trnasactionInput.setAmount("4");
		trnasactionInput.setTimestamp("2020-08-04T17:11:50.124Z");
		Mockito.when(transactionDAOImpl.processTransactions(Mockito.any(Transactions.class))).thenReturn(true);

		PowerMockito.mockStatic(UtilityFunctions.class);
		PowerMockito.when(UtilityFunctions.getCurrentLocalDateTimeStamp())
				.thenReturn(LocalDateTime.parse("2020-04-04T17:12:10.124Z", iso_8601_UTCFormat));

		PowerMockito.when(UtilityFunctions.timeStampDurationCalulator(Mockito.anyString()))
		.thenReturn(Long.valueOf(20));
		
		assertEquals(true, transactionServiceImpl.processTransactions(trnasactionInput));

	}

	@Test(expected = Exception.class)
	public void processTransactions_futureTimestamp_shouldthrowException() throws Exception {
		
		trnasactionInput.setAmount("4");
		trnasactionInput.setTimestamp("\"2020-04-04T17:11:50.124Z");
		PowerMockito.mockStatic(UtilityFunctions.class);
		PowerMockito.when(UtilityFunctions.getCurrentLocalDateTimeStamp())
				.thenReturn(LocalDateTime.parse("2020-04-04T17:12:10.124Z", iso_8601_UTCFormat));

		transactionServiceImpl.processTransactions(trnasactionInput);

	}

	@Test(expected = Exception.class)
	public void processTransactions_incorrectTimestamp_shouldthrowException() throws Exception {
		
		trnasactionInput.setAmount("4");
		trnasactionInput.setTimestamp("\"2020-04-04T17:11:50.124Z");
		PowerMockito.mockStatic(UtilityFunctions.class);
		PowerMockito.when(UtilityFunctions.getCurrentLocalDateTimeStamp())
				.thenReturn(LocalDateTime.parse("2020-04-04T17:12:10.124Z", iso_8601_UTCFormat));

		transactionServiceImpl.processTransactions(trnasactionInput);

	}

	@Test(expected = Exception.class)
	public void processTransactions_incorrectInputJasonString_shouldthrowException() throws Exception {
		
		//trnasactionInput.setAmount("4");
		trnasactionInput.setTimestamp("2020-04-04T17:11:50.124Z");
		PowerMockito.mockStatic(UtilityFunctions.class);
		PowerMockito.when(UtilityFunctions.getCurrentLocalDateTimeStamp())
				.thenReturn(LocalDateTime.parse("2020-04-04T17:12:10.124Z", iso_8601_UTCFormat));

		transactionServiceImpl.processTransactions(trnasactionInput);

	}

	@Test(expected = Exception.class)
	public void processTransactions_InputJasonStringhasOnlyTimestamp_shouldthrowException() throws Exception {
		
		trnasactionInput.setAmount("4");
		trnasactionInput.setTimestamp("\"2020-04-04T17:11:50.124Z");
		PowerMockito.mockStatic(UtilityFunctions.class);
		PowerMockito.when(UtilityFunctions.getCurrentLocalDateTimeStamp())
				.thenReturn(LocalDateTime.parse("2020-04-04T17:12:10.124Z", iso_8601_UTCFormat));

		transactionServiceImpl.processTransactions(trnasactionInput);

	}

	@Test
	public void getStatistics_noStatistic_returnOptionhavingNull() {
		TransactionsStats expectedStats = new TransactionsStats();
		expectedStats.setCount(0);

		Mockito.when(transactionDAOImpl.getStatistics()).thenReturn(expectedStats);

		assertEquals(false, transactionServiceImpl.getStatistics().isPresent());

	}

	@Test
	public void getStatistics_noStatistic_returnOptionwithValue() {
		TransactionsStats expectedStats = new TransactionsStats();
		expectedStats.setCount(4);

		Mockito.when(transactionDAOImpl.getStatistics()).thenReturn(expectedStats);

		assertEquals(true, transactionServiceImpl.getStatistics().isPresent());

	}

	@Test
	public void deleteTransactions_daoDeleteShouldgetcalled() {

		transactionServiceImpl.deleteTransactions();
		Mockito.verify(transactionDAOImpl).deleteTransactions();
	}
}
