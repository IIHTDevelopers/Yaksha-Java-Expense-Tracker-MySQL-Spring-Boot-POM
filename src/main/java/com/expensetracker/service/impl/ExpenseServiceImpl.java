package com.expensetracker.service.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.expensetracker.dto.ExpenseDTO;
import com.expensetracker.entity.Expense;
import com.expensetracker.exception.NotFoundException;
import com.expensetracker.repo.ExpenseRepository;
import com.expensetracker.service.ExpenseService;

@Service
public class ExpenseServiceImpl implements ExpenseService {

	@Autowired
	private ExpenseRepository expenseRepository;

	@Override
	public List<ExpenseDTO> getAllExpenses() {
		List<Expense> expenses = expenseRepository.findAll();
		return expenses.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public ExpenseDTO getExpenseById(Long id) {
		Optional<Expense> optionalExpense = expenseRepository.findById(id);
		if (optionalExpense.isPresent()) {
			return convertToDTO(optionalExpense.get());
		} else {
			throw new NotFoundException("Expense not found");
		}
	}

	@Override
	@Transactional
	public ExpenseDTO createExpense(ExpenseDTO expenseDTO) {
		Expense expense = convertToEntity(expenseDTO);
		expense = expenseRepository.save(expense);
		return convertToDTO(expense);
	}

	@Override
	@Transactional
	public ExpenseDTO updateExpense(Long id, ExpenseDTO expenseDTO) {
		Optional<Expense> optionalExpense = expenseRepository.findById(id);
		if (optionalExpense.isPresent()) {
			Expense expense = optionalExpense.get();
			expense = convertToEntity(expenseDTO);
			return convertToDTO(expense);
		} else {
			throw new NotFoundException("Expense not found");
		}
	}

	@Override
	@Transactional
	public boolean deleteExpense(Long id) {
		Optional<Expense> optionalExpense = expenseRepository.findById(id);
		if (optionalExpense.isPresent()) {
			expenseRepository.deleteById(id);
			return true;
		} else {
			throw new NotFoundException("Expense not found");
		}
	}

	@Override
	public List<String> getMostExpendedCategory() {
		List<ExpenseDTO> expenses = getAllExpenses();
		Map<String, Double> categoryMap = new HashMap<>();

		// Calculate total amount for each category
		for (ExpenseDTO expense : expenses) {
			String category = expense.getCategory();
			double amount = expense.getAmount();
			categoryMap.put(category, categoryMap.getOrDefault(category, 0.0) + amount);
		}

		Optional<Map.Entry<String, Double>> maxCategory = categoryMap.entrySet().stream()
				.max(Comparator.comparingDouble(Map.Entry::getValue));

		if (maxCategory.isPresent()) {
			double maxAmount = maxCategory.get().getValue();
			return categoryMap.entrySet().stream().filter(entry -> entry.getValue() == maxAmount).map(Map.Entry::getKey)
					.collect(Collectors.toList());
		} else {
			return Collections.emptyList();
		}
	}

	private ExpenseDTO convertToDTO(Expense expense) {
		ExpenseDTO expenseDTO = new ExpenseDTO();
		expenseDTO.setAmount(expense.getAmount());
		expenseDTO.setCategory(expense.getCategory());
		expenseDTO.setDate(expense.getDate());
		expenseDTO.setId(expense.getId());
		expenseDTO.setName(expense.getName());
		expenseDTO.setNote(expense.getNote());
		return expenseDTO;
	}

	private Expense convertToEntity(ExpenseDTO expenseDTO) {
		Expense expense = new Expense();
		expense.setAmount(expenseDTO.getAmount());
		expense.setCategory(expenseDTO.getCategory());
		expense.setDate(expenseDTO.getDate());
		expense.setId(expenseDTO.getId());
		expense.setName(expenseDTO.getName());
		expense.setNote(expenseDTO.getNote());
		return expense;
	}
}
