package com.n26.controller;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import com.n26.model.Transactions;
import com.n26.model.TransactionsStats;
import com.n26.service.TransactionServiceImpl;

//@RunWith(SpringJUnit4ClassRunner.class)
@RunWith(org.mockito.runners.MockitoJUnitRunner.class)
public class ControllerTest {
	
	@Mock
	private TransactionServiceImpl transactionServiceMock;
	
	@InjectMocks
	private TransactionOperations transactionOperations;

	//private MockMvc mockMVC;
	
//	@Before
//	public void setup() throws Exception
//	{
//		mockMVC=MockMvcBuilders.standaloneSetup(transactionOperations).build();
//	}
	
	
	@Test
	public void testGetStatistics_NullStatsarereturn_ShouldNoContentStatus() {
		
		
		Mockito.when(transactionServiceMock.getStatistics()).thenReturn(Optional.ofNullable(null));
		ResponseEntity<TransactionsStats> response =transactionOperations.getStatistics();
		
		assertEquals(204, response.getStatusCodeValue());
		
		
		
		
	}
	
	@Test
	public void testGetStatistics_NullStatsarereturn_ShouldOkStatus() {
		
		
		Mockito.when(transactionServiceMock.getStatistics()).thenReturn(Optional.ofNullable(new TransactionsStats() ));
		ResponseEntity<TransactionsStats> response =transactionOperations.getStatistics();
		
		assertEquals(200, response.getStatusCodeValue());
		
		
		
		
	}

	@Test
	public void testPostTransactions_Shouldposttransaction_returnStatusCreated() throws Exception {
		
		
		Transactions trnasactionInput = new Transactions();
		trnasactionInput.setAmount("4");
		trnasactionInput.setTimestamp("\"2020-04-04T17:11:50.124Z");
		Mockito.when(transactionServiceMock.processTransactions(trnasactionInput)).thenReturn(true);
		ResponseEntity<Object> response =transactionOperations.postTransactions(trnasactionInput);
		
		assertEquals(201, response.getStatusCodeValue());
		
		
		
		//mockMVC.perform(MockMvcRequestBuilders.post("/transactions")).andExpect(MockMvcResultMatchers.status().isCreated());
		
	}
	
	@Test
	public void testPostTransactions_Shouldposttransaction_returnStatusBadGateway() throws Exception {
		
		
		Transactions trnasactionInput = new Transactions();
		trnasactionInput.setAmount("4");
		trnasactionInput.setTimestamp("\"2020-04-04T17:11:50.124Z");
		Mockito.when(transactionServiceMock.processTransactions(trnasactionInput)).thenReturn(false);
		ResponseEntity<Object> response =transactionOperations.postTransactions(trnasactionInput);
		
		assertEquals(502, response.getStatusCodeValue());
		
		
		
		//mockMVC.perform(MockMvcRequestBuilders.post("/transactions")).andExpect(MockMvcResultMatchers.status().isCreated());
		
	}

	@Test
	public void testDeleteTransactions_shouldNoContent() {
		ResponseEntity<Object> response=transactionOperations.deleteTransactions();
		assertEquals(204, response.getStatusCodeValue());
	}

}
