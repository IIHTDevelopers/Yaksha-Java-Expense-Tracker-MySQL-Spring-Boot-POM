package com.expensetracker.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class ExpenseDTO {

	private Long id;

	@NotBlank(message = "Expense name is required")
	private String name;

	@NotNull(message = "Expense amount is required")
	@Positive(message = "Expense amount must be positive")
	private Double amount;

	@NotBlank(message = "Expense category is required")
	private String category;

	private Date date;

	private String note;

	public ExpenseDTO() {
		super();
	}

	public ExpenseDTO(Long id, @NotBlank(message = "Expense name is required") String name,
			@NotNull(message = "Expense amount is required") @Positive(message = "Expense amount must be positive") Double amount,
			@NotBlank(message = "Expense category is required") String category, Date date, String note) {
		super();
		this.id = id;
		this.name = name;
		this.amount = amount;
		this.category = category;
		this.date = date;
		this.note = note;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Override
	public String toString() {
		return "ExpenseDTO [id=" + id + ", name=" + name + ", amount=" + amount + ", category=" + category + ", date="
				+ date + ", note=" + note + "]";
	}
}
