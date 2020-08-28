package com.n26.database;

import java.math.BigDecimal;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.n26.model.Transactions;
import com.n26.model.TransactionsStats;
import com.n26.utility.UtilityFunctions;


public class DBOperations {

	@Autowired
	private TransactionTable transactionTable;
	
	@Autowired
	private TransactionsStats transactionsStats;

	public boolean addTransaction(Transactions transactions) {
		boolean addSuccess = false;
		synchronized (transactionTable) {
			System.out.println("Start post");
			List<Transactions> trnList = transactionTable.getTrsnList();
			addSuccess = trnList.add(transactions);
			
			System.out.println("end post");
		}
		cordinator();

		return addSuccess;
	}

	private void removeOlderTransactions() {
		transactionTable.getTrsnList()
				.removeIf(transaction ->  UtilityFunctions.timeStampDurationCalulator(transaction.getTimestamp()) >=59);
	}

	@Scheduled(initialDelay = 5000, fixedDelay = 50000000)
	void generateStats() {

		synchronized (transactionTable) {
			synchronized (transactionsStats) {
				System.out.println(transactionTable.getTrsnList().size() + "---------before removing----------"+transactionTable.getTrsnList());
				removeOlderTransactions();
				System.out.println(transactionTable.getTrsnList().size() + "-------------------");
				DoubleSummaryStatistics statsStreams = transactionTable.getTrsnList().stream()
						.mapToDouble(transaction -> Double.parseDouble(transaction.getAmount())).summaryStatistics();
				
				
				
				
				
				
				final double POSITIVE_INFINITY = 1.0 / 0.0;
				final double NEGATIVE_INFINITY = -1.0 / 0.0;

				//BigDecimal num = new BigDecimal(statsStreams.getMin());
				//num.setScale(2, BigDecimal.ROUND_HALF_UP).toString();

				
				
				if (statsStreams.getMin() == POSITIVE_INFINITY) {
					transactionsStats.setMin("0.00");

				} else {
					
					BigDecimal min = new BigDecimal(statsStreams.getMin());
					//min.setScale(2, BigDecimal.ROUND_HALF_UP);
					transactionsStats.setMin(min.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
				}

				if (statsStreams.getMax() == NEGATIVE_INFINITY) {

					transactionsStats.setMax("0.00");
				} else {
					BigDecimal max = new BigDecimal(statsStreams.getMax());
					//max.setScale(2, BigDecimal.ROUND_HALF_UP);
					transactionsStats.setMax(max.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
				}
				
				
				BigDecimal avg = new BigDecimal(statsStreams.getAverage());
				//avg.setScale(2, BigDecimal.ROUND_HALF_UP);

				transactionsStats.setAvg(avg.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
				
				
				BigDecimal sum = new BigDecimal(statsStreams.getSum());
				//sum.setScale(2, BigDecimal.ROUND_HALF_UP);
				transactionsStats.setSum(sum.setScale(2, BigDecimal.ROUND_HALF_UP).toString());

				
				BigDecimal count = new BigDecimal(statsStreams.getCount());
				//count.setScale(2, BigDecimal.ROUND_HALF_UP);
				transactionsStats.setCount(Integer.valueOf(count.toString()));
				
				
				System.out.println("*sum* "+transactionsStats.getSum()+" *min*  "+transactionsStats.getMin() +" *max*  "+transactionsStats.getMax()+" *count*  "+transactionsStats.getCount());

			}
		}
	}

	public TransactionsStats getTransactionsStats() {

		synchronized (transactionsStats) {

			//Optional<TransactionsStats> optional = Optional.ofNullable(transactionsStats);
			System.out.println("getstat` start");
			System.out.println("getstat end");
			return transactionsStats;
		}

	}

	@Async
	private void cordinator() {
		generateStats();
	}
	
	public void deleteTransactions() {

		synchronized (transactionTable) {
			synchronized (transactionsStats) {
				System.out.println("Delete start");
				transactionTable.getTrsnList().clear();
				transactionsStats = new TransactionsStats();
				System.out.println("Delete end");
			}
		}
		
	}

}
