package com.expensetracker.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.expensetracker.dto.ExpenseDTO;
import com.expensetracker.service.ExpenseService;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

	@Autowired
	private ExpenseService expenseService;

	@GetMapping
	public ResponseEntity<List<ExpenseDTO>> getAllProducts() {
		List<ExpenseDTO> expenses = expenseService.getAllExpenses();
		return new ResponseEntity<>(expenses, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ExpenseDTO> getProductById(@PathVariable Long id) {
		ExpenseDTO expense = expenseService.getExpenseById(id);
		return new ResponseEntity<>(expense, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<ExpenseDTO> createProduct(@Valid @RequestBody ExpenseDTO ExpenseDTO) {
		ExpenseDTO createdExpense = expenseService.createExpense(ExpenseDTO);
		return new ResponseEntity<>(createdExpense, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ExpenseDTO> updateProduct(@PathVariable Long id, @Valid @RequestBody ExpenseDTO expenseDTO) {
		ExpenseDTO updatedProduct = expenseService.updateExpense(id, expenseDTO);
		return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
		expenseService.deleteExpense(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
