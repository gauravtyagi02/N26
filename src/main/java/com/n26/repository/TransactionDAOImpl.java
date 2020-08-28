package com.n26.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.n26.database.DBOperations;
import com.n26.model.Transactions;
import com.n26.model.TransactionsStats;

@Repository
public class TransactionDAOImpl implements TransactionDAO {
	
	@Autowired
	private DBOperations dbOperations;

	@Override
	//public ResponseEntity<Object> processTransactions(Transactions transactions) throws Exception {
	public boolean processTransactions(Transactions transactions) throws Exception {
		boolean result = dbOperations.addTransaction(transactions);
		return result;
	}

	@Override
	public TransactionsStats getStatistics()
	{
		 return dbOperations.getTransactionsStats();
		
	}

	@Override
	public void deleteTransactions() {
		dbOperations.deleteTransactions();
		
	}

}
