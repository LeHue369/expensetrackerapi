package com.huele.expensetrackerapi.controller;

import com.huele.expensetrackerapi.entity.Expense;
import com.huele.expensetrackerapi.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/expense")
public class ExpenseController {

	@Autowired
	private ExpenseService expenseService;

	@GetMapping
	public Page<Expense> getAllExpenses(Pageable page) {
		return expenseService.getAllExpenses(page);
	}

	@GetMapping("/{id}")
	public Expense getExpenseById(@PathVariable Long id){
		return expenseService.getExpenseById(id);
	}

	@DeleteMapping
	public void deleteExpenseById(@RequestParam Long id){
		expenseService.deleteExpenseById(id);
	}

	@PostMapping
	public Expense saveExpenseDetails(@Valid @RequestBody Expense expense){
		return expenseService.saveExpenseDetails(expense);
	}

	@PutMapping
	public Expense updateExpenseDetails(@RequestBody Expense expense, @PathVariable Long id){
		return expenseService.updateExpenseDetails(id, expense);
	}

	@GetMapping("/category")
	public List<Expense> getAllExpensesByCategory(@RequestParam String category, Pageable page){
		return expenseService.readByCategory(category, page);
	}

	@GetMapping("/keyword")
	public List<Expense> getAllExpensesByName(@RequestParam String keyword, Pageable page){
		return expenseService.readByName(keyword, page);
	}

	@GetMapping("/date")
	public List<Expense> getAllExpenseByDate(
		@RequestParam(required = false) Date startDate,
		@RequestParam(required = false) Date endDate,
		Pageable page){
		return expenseService.readByDate(startDate, endDate, page);
	}

	@GetMapping("/test")
	public String test(){
		return "testing versioning";
	}
}


