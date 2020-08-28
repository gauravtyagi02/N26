package com.n26.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.n26.model.Transactions;
import com.n26.model.TransactionsStats;
import com.n26.service.TransactionService;

@RestController
public class TransactionOperations {
	
	@Autowired
	private TransactionService transactionService;

	@GetMapping(path = "/statistics", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TransactionsStats> getStatistics() {
		
		Optional<TransactionsStats> trnsOptional= transactionService.getStatistics();
		if (trnsOptional.isPresent()) {
			return new ResponseEntity<TransactionsStats>(trnsOptional.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<TransactionsStats>(new TransactionsStats(), HttpStatus.NO_CONTENT);
		}
		
		
	}

	@PostMapping(path = "/transactions", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> postTransactions(@Valid  @RequestBody Transactions transactions) throws Exception  {
		
		System.out.println(
		transactions.getTimestamp()+"     --------s      "+
		transactions.getAmount());
		
		boolean result=  transactionService.processTransactions(transactions);
		
		if (result == true) {
			return ResponseEntity.status(HttpStatus.CREATED).body("");

		} else {
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("");
		}
		
	}

	@DeleteMapping(path="/transactions" , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> deleteTransactions() {
		transactionService.deleteTransactions();
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
	}

}
