package com.huele.expensetrackerapi.service;

import com.huele.expensetrackerapi.entity.Expense;
import com.huele.expensetrackerapi.exceptions.ResourceNotFoundException;
import com.huele.expensetrackerapi.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseServiceImpl implements ExpenseService {

	@Autowired
	private ExpenseRepository expenseRepo;

	@Autowired
	private UserService userService;

	@Override
	public Page<Expense> getAllExpenses(Pageable page) {

		return expenseRepo.findByUserId(userService.getLoggedInUser().getId(), page);
	}

	@Override
	public Expense getExpenseById(Long id) {
		Optional<Expense> expense = expenseRepo.findByUserIdAndId(userService.getLoggedInUser().getId(), id);
		if(expense.isPresent()){
			return expense.get();
		}
		throw new ResourceNotFoundException("Expense is not found for the id " + id);
	}

	@Override
	public void deleteExpenseById(Long id) {
		Expense expense = getExpenseById(id);
		expenseRepo.delete(expense);
	}

	@Override
	public Expense saveExpenseDetails(Expense expense) {
		expense.setUser(userService.getLoggedInUser());
		return expenseRepo.save(expense);
	}

	@Override
	public Expense updateExpenseDetails(Long id, Expense expense) {
		if (expense.getId() == null){
			expense.setId(id);
		}
		return expenseRepo.save(expense);
	}

	@Override
	public List<Expense> readByCategory(String category, Pageable page) {
		return expenseRepo.findByUserIdAndCategory(userService.getLoggedInUser().getId(), category, page).toList();
	}

	@Override
	public List<Expense> readByName(String keyword, Pageable page) {
		return expenseRepo.findByUserIdAndNameContaining(userService.getLoggedInUser().getId(), keyword, page).toList();
	}

	@Override
	public List<Expense> readByDate(Date startDate, Date endDate, Pageable page) {

		if(startDate == null){
			startDate =  new Date(0);
		}

		if(endDate == null){
			endDate =  new Date(System.currentTimeMillis());
		}

		Page<Expense> pages =  expenseRepo.findByUserIdAndDateBetween(userService.getLoggedInUser().getId(), startDate, endDate, page);

		return pages.toList();
	}
}