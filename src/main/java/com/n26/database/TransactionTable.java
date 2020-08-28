package com.n26.database;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import com.n26.model.Transactions;


public class TransactionTable {

	private List<Transactions> trsnList;
	public TransactionTable() {
		trsnList = new ArrayList<Transactions>(1000);
	}



	public List<Transactions> getTrsnList() {
		
		return trsnList;
	}
}
