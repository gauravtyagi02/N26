package com.n26.model;

import java.io.Serializable;

public class TransactionsStats implements Serializable {

	private static final long serialVersionUID = -2530132815064383025L;
	private String sum = "0.00";
	private String avg = "0.00";
	private String max = "0.00";
	private String min = "0.00";;
	private int count = 0;

	public String getSum() {
		return sum;
	}

	public void setSum(String sum) {
		this.sum = sum;
	}

	public String getAvg() {
		return avg;
	}

	public void setAvg(String avg) {
		this.avg = avg;
	}

	public String getMax() {
		return max;
	}

	public void setMax(String max) {
		this.max = max;
	}

	public String getMin() {
		return min;
	}

	public void setMin(String min) {
		this.min = min;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "TransactionsStats [sum=" + sum + ", avg=" + avg + ", max=" + max + ", min=" + min + ", count=" + count
				+ "]";
	}

}
