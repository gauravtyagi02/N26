package com.n26.service;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.n26.model.Transactions;
import com.n26.model.TransactionsStats;

@Service
public interface TransactionService {

	public boolean processTransactions(Transactions trns) throws Exception;

	public Optional<TransactionsStats> getStatistics();

	public void deleteTransactions();

}
