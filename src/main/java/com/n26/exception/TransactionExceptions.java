package com.n26.exception;

import java.time.format.DateTimeParseException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

@ControllerAdvice
public class TransactionExceptions {


	@ExceptionHandler(value = { Exception.class })
	public ResponseEntity<?> exception(Exception exe) {
		// com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException
		if (exe instanceof UnrecognizedPropertyException) {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		} 
		else if(exe instanceof java.lang.NumberFormatException) {
			//return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Number format");
			return new ResponseEntity<Object>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
		else if(exe instanceof DateTimeParseException)
		{
			//return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Timestamp not proper");
			return new ResponseEntity<Object>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
		else if(exe.getMessage().equalsIgnoreCase("timestamp of future date"))
		{
			//return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Future Date");
			return new ResponseEntity<Object>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
		else if(exe.getMessage().equalsIgnoreCase("timestamp is older than 60 seconds"))
		{
			//return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("timestamp is older than 60 seconds");
			//return new ResponseEntity<Object>(HttpStatus.UNPROCESSABLE_ENTITY);
			return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
		}
		else if (exe.getMessage().equalsIgnoreCase("Invalid Jason")) {
			//return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Jason");
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		} else {
			exe.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("cooookkkkkkkkkk");
		}
	}

}
