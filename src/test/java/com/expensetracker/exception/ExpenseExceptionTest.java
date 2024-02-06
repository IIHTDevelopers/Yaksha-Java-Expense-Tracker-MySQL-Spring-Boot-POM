package com.expensetracker.exception;

import static com.expensetracker.utils.MasterData.getExpenseDTO;
import static com.expensetracker.utils.TestUtils.currentTest;
import static com.expensetracker.utils.TestUtils.exceptionTestFile;
import static com.expensetracker.utils.TestUtils.testReport;
import static com.expensetracker.utils.TestUtils.yakshaAssert;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.expensetracker.controller.ExpenseController;
import com.expensetracker.dto.ExpenseDTO;
import com.expensetracker.service.ExpenseService;
import com.expensetracker.utils.MasterData;

@WebMvcTest(ExpenseController.class)
@AutoConfigureMockMvc
public class ExpenseExceptionTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ExpenseService expenseService;

	@AfterAll
	public static void afterAll() {
		testReport();
	}

	@Test
	public void testCreateExpenseInvalidDataException() throws Exception {
		ExpenseDTO expenseDTO = getExpenseDTO();
		expenseDTO.setName(null);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/expenses")
				.content(MasterData.asJsonString(expenseDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(),
				(result.getResponse().getStatus() == HttpStatus.BAD_REQUEST.value() ? "true" : "false"),
				exceptionTestFile);
	}

	@Test
	public void testUpdateExpenseInvalidDataException() throws Exception {
		ExpenseDTO expenseDTO = getExpenseDTO();
		expenseDTO.setName(null);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/expenses/" + expenseDTO.getId())
				.content(MasterData.asJsonString(expenseDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(),
				(result.getResponse().getStatus() == HttpStatus.BAD_REQUEST.value() ? "true" : "false"),
				exceptionTestFile);
	}

	@Test
	public void testGetExpenseByIdResourceNotFoundException() throws Exception {
		ExpenseDTO expenseDTO = getExpenseDTO();
		ErrorResponse exResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Expense not found");

		when(this.expenseService.getExpenseById(expenseDTO.getId()))
				.thenThrow(new NotFoundException("Expense not found"));
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/expenses/" + expenseDTO.getId())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contains(exResponse.getMessage()) ? "true" : "false"),
				exceptionTestFile);
	}
}
