package com.expensetracker.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.expensetracker.dto.ExpenseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class MasterData {

	public static ExpenseDTO getExpenseDTO() {
		ExpenseDTO expenseDTO = new ExpenseDTO();
		expenseDTO.setId(1L);
		expenseDTO.setName("Groceries");
		expenseDTO.setAmount(50.75);
		expenseDTO.setCategory("Food");
		expenseDTO.setDate(new Date());
		expenseDTO.setNote("Bought groceries for the week");
		return expenseDTO;
	}

	public static List<ExpenseDTO> getExpenseDTOList() {
		List<ExpenseDTO> expenseDTOList = new ArrayList<>();

		ExpenseDTO expenseDTO = new ExpenseDTO();
		expenseDTO.setId(1L);
		expenseDTO.setName("Groceries");
		expenseDTO.setAmount(50.75);
		expenseDTO.setCategory("Food");
		expenseDTO.setDate(new Date());
		expenseDTO.setNote("Bought groceries for the week");

		expenseDTOList.add(expenseDTO);

		return expenseDTOList;
	}

	public static String asJsonString(final Object obj) {
		try {
			final ObjectMapper mapper = new ObjectMapper();
			mapper.registerModule(new JavaTimeModule());
			mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
			final String jsonContent = mapper.writeValueAsString(obj);

			return jsonContent;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String randomStringWithSize(int size) {
		String s = "";
		for (int i = 0; i < size; i++) {
			s.concat("A");
		}
		return s;
	}
}