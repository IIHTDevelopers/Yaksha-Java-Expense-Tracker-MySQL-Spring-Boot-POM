package com.expensetracker.service;

import java.util.List;
import com.expensetracker.dto.ExpenseDTO;

public interface ExpenseService {
	List<ExpenseDTO> getAllExpenses();

	ExpenseDTO getExpenseById(Long id);

	ExpenseDTO createExpense(ExpenseDTO expenseDTO);

	ExpenseDTO updateExpense(Long id, ExpenseDTO expenseDTO);

	boolean deleteExpense(Long id);

	List<String> getMostExpendedCategory();
}