package com.n26.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;

import com.n26.database.DBOperations;
import com.n26.model.Transactions;
import com.n26.model.TransactionsStats;

@RunWith(org.mockito.runners.MockitoJUnitRunner.class)
public class TransactionDAOImplTest {
	
	@Mock
	DBOperations dbOperations;
	
	@InjectMocks
	TransactionDAOImpl dao;
	
	@Test
	public void shouldProcessTransactions() throws Exception {
		Transactions transactions = new Transactions();
		Mockito.when(dbOperations.addTransaction(transactions)).thenReturn(true);
		boolean processTransactions = dao.processTransactions(transactions);
		
		Mockito.verify(dbOperations).addTransaction(transactions);
		assertTrue(processTransactions);
	}

	
	@Test
	public void shouldGetStatisticsFromDbOperations() {
		TransactionsStats expectedStats = new TransactionsStats();
		expectedStats.setCount(4);
		Mockito.when(dbOperations.getTransactionsStats()).thenReturn(expectedStats);

		TransactionsStats actualStats= dao.getStatistics();
		
		//assertEquals(expectedStats.getCount(), actualStats.getCount(), 0.0);
		assertEquals(expectedStats.getCount(), actualStats.getCount(), 0.0);
		
		Mockito.verify(dbOperations).getTransactionsStats();
	}
	
	@Test
	public void shouldDeleteTransactions() {
		dao.deleteTransactions();
		
		Mockito.verify(dbOperations).deleteTransactions();
	}

}
