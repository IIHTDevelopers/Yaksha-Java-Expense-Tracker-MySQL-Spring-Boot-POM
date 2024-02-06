package com.expensetracker.functional;

import static com.expensetracker.utils.MasterData.getExpenseDTO;
import static com.expensetracker.utils.MasterData.getExpenseDTOList;
import static com.expensetracker.utils.TestUtils.businessTestFile;
import static com.expensetracker.utils.TestUtils.currentTest;
import static com.expensetracker.utils.TestUtils.testReport;
import static com.expensetracker.utils.TestUtils.yakshaAssert;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
public class ExpenseControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ExpenseService expenseService;

	@AfterAll
	public static void afterAll() {
		testReport();
	}

	@Test
	public void testGetAllExpenses() throws Exception {
		List<ExpenseDTO> expenseDTOS = getExpenseDTOList();

		when(this.expenseService.getAllExpenses()).thenReturn(expenseDTOS);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/expenses")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contentEquals(MasterData.asJsonString(expenseDTOS)) ? "true"
						: "false"),
				businessTestFile);
	}

	@Test
	public void testGetExpenseById() throws Exception {
		ExpenseDTO expenseDTO = getExpenseDTO();
		when(this.expenseService.getExpenseById(expenseDTO.getId())).thenReturn(expenseDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/expenses/" + expenseDTO.getId())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contentEquals(MasterData.asJsonString(expenseDTO)) ? "true"
						: "false"),
				businessTestFile);
	}

	@Test
	public void testCreateExpense() throws Exception {
		ExpenseDTO expenseDTO = getExpenseDTO();

		when(this.expenseService.createExpense(any())).thenReturn(expenseDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/expenses")
				.content(MasterData.asJsonString(expenseDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contentEquals(MasterData.asJsonString(expenseDTO)) ? "true"
						: "false"),
				businessTestFile);
	}

	@Test
	public void testUpdateExpense() throws Exception {
		ExpenseDTO expenseDTO = getExpenseDTO();

		when(this.expenseService.updateExpense(eq(expenseDTO.getId()), any())).thenReturn(expenseDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/expenses/" + expenseDTO.getId())
				.content(MasterData.asJsonString(expenseDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contentEquals(MasterData.asJsonString(expenseDTO)) ? "true"
						: "false"),
				businessTestFile);
	}

	@Test
	public void testDeleteExpense() throws Exception {
		ExpenseDTO expenseDTO = getExpenseDTO();
		when(this.expenseService.deleteExpense(expenseDTO.getId())).thenReturn(true);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/expenses/" + expenseDTO.getId())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), (result.getResponse().getContentAsString().contentEquals("") ? "true" : "false"),
				businessTestFile);
	}
}
