package com.expensetracker.boundary;

import static com.expensetracker.utils.TestUtils.boundaryTestFile;
import static com.expensetracker.utils.TestUtils.currentTest;
import static com.expensetracker.utils.TestUtils.testReport;
import static com.expensetracker.utils.TestUtils.yakshaAssert;

import java.io.IOException;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.expensetracker.dto.ExpenseDTO;

public class ExpenseBoundaryTest {

	private static Validator validator;

	@BeforeAll
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@AfterAll
	public static void afterAll() {
		testReport();
	}

	@Test
	public void testNameNotNull() throws IOException {
		ExpenseDTO expenseDTO = new ExpenseDTO();
		expenseDTO.setName(null);
		Set<ConstraintViolation<ExpenseDTO>> violations = validator.validate(expenseDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

	@Test
	public void testNameMinThree() throws IOException {
		ExpenseDTO expenseDTO = new ExpenseDTO();
		expenseDTO.setName("ab"); // Less than 3 characters
		Set<ConstraintViolation<ExpenseDTO>> violations = validator.validate(expenseDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

	@Test
	public void testNameMax255() throws IOException {
		ExpenseDTO expenseDTO = new ExpenseDTO();
		expenseDTO.setName("a".repeat(256)); // More than 255 characters
		Set<ConstraintViolation<ExpenseDTO>> violations = validator.validate(expenseDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

	@Test
	public void testAmountNotNull() throws IOException {
		ExpenseDTO expenseDTO = new ExpenseDTO();
		expenseDTO.setAmount(null);
		Set<ConstraintViolation<ExpenseDTO>> violations = validator.validate(expenseDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

	@Test
	public void testAmountPositive() throws IOException {
		ExpenseDTO expenseDTO = new ExpenseDTO();
		expenseDTO.setAmount(-0.01); // Less than 0.01
		Set<ConstraintViolation<ExpenseDTO>> violations = validator.validate(expenseDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

	@Test
	public void testCategoryNotNull() throws IOException {
		ExpenseDTO expenseDTO = new ExpenseDTO();
		expenseDTO.setCategory(null);
		Set<ConstraintViolation<ExpenseDTO>> violations = validator.validate(expenseDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

	@Test
	public void testDateNotNull() throws IOException {
		ExpenseDTO expenseDTO = new ExpenseDTO();
		expenseDTO.setDate(null);
		Set<ConstraintViolation<ExpenseDTO>> violations = validator.validate(expenseDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

	@Test
	public void testNoteMaxSize() throws IOException {
		ExpenseDTO expenseDTO = new ExpenseDTO();
		expenseDTO.setNote("a".repeat(256)); // More than 255 characters
		Set<ConstraintViolation<ExpenseDTO>> violations = validator.validate(expenseDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

}
