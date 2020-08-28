package com.n26.service;

import static org.hamcrest.CoreMatchers.anything;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.never;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
//import org.powermock.modules.junit4.PowerMockRunner;

import com.n26.model.Transactions;
import com.n26.model.TransactionsStats;
import com.n26.repository.TransactionDAO;

import junit.framework.Assert;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceImplTest_Old {
	@Mock
	TransactionDAO transactionDAOImpl;
	//@Mock
	//private Clock clock;
	
	@Mock
	private LocalDateTime localDateTime_currentTime;
	
	private LocalDateTime  fixedlocalDateTime;

	//private Clock fixedClock;

	@InjectMocks
	TransactionServiceImpl transactionServiceImpl;

	@Before
	public void initMocks() {
		
		//fixedClock = Clock.fixed(Instant.parse("2020-04-04T17:11:50.124Z"), ZoneId.of("UTC"));
		
		DateTimeFormatter iso_8601_UTCFormat =DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")
				   .withZone(ZoneId.of("UTC"));

		fixedlocalDateTime =LocalDateTime.parse("2020-04-04T17:11:50.124Z",iso_8601_UTCFormat);
			
			
		//Mockito.when(clock.instant()).thenReturn(fixedClock.instant());
		//Mockito.when(clock.getZone()).thenReturn(fixedClock.getZone());
		
		Mockito.when(localDateTime_currentTime).thenReturn(fixedlocalDateTime);

	}

	@Test
	public void shouldPostValidTransactionToDao() throws Exception {
		
		Transactions trnasactionInput = new Transactions();
		trnasactionInput.setAmount("4");
		trnasactionInput.setTimestamp("\"2020-04-04T17:11:50.124Z");
		Mockito.when(transactionDAOImpl.processTransactions(Mockito.any(Transactions.class))).thenReturn(true);

		assertTrue(transactionServiceImpl.processTransactions(trnasactionInput));
	}

	@Test
	public void shouldgetStatisticsIfCountIsGreaterThanZero() {
		TransactionsStats expectedStats = new TransactionsStats();
		expectedStats.setCount(5);
		Mockito.when(transactionDAOImpl.getStatistics()).thenReturn(expectedStats);

		Optional<TransactionsStats> actualStatis = transactionServiceImpl.getStatistics();

		assertEquals(5.0, Double.valueOf(actualStatis.get().getCount()), 0.0);
	}

	@Test
	public void shouldgeNulltStatisticsIfCountIsEqualToZero() {
		TransactionsStats expectedStats = new TransactionsStats();
		expectedStats.setCount(0);
		Mockito.when(transactionDAOImpl.getStatistics()).thenReturn(expectedStats);

		Optional<TransactionsStats> actualStatis = transactionServiceImpl.getStatistics();

		assertFalse(actualStatis.isPresent());
	}

	@Test
	public void shouldDeleteTransaction() {
		transactionServiceImpl.deleteTransactions();

		Mockito.verify(transactionDAOImpl).deleteTransactions();

	}

}
