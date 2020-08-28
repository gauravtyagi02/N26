package com.n26.repository;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.n26.model.Transactions;
import com.n26.model.TransactionsStats;

@Repository
public interface TransactionDAO {
	public boolean processTransactions(Transactions transactions) throws Exception;
	public TransactionsStats getStatistics();
	public void deleteTransactions();
}
