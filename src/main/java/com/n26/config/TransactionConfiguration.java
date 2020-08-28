package com.n26.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.n26.database.DBOperations;
import com.n26.database.TransactionTable;
import com.n26.model.TransactionsStats;

@Configuration
public class TransactionConfiguration {
	
	
	
	@Bean
	public TransactionTable getTransactionTable()
	{
		return new TransactionTable();
	}
	
	
	@Bean
	public TransactionsStats getTransactionsStats()
	{
		return new TransactionsStats();
	}
	
	@Bean
	public DBOperations getDBOperations()
	{
		return new DBOperations();
	}
	
	

}
