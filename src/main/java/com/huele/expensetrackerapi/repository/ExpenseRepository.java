package com.huele.expensetrackerapi.repository;

import com.huele.expensetrackerapi.entity.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
	Page<Expense> findByCategory(String category, Pageable page);

	Page<Expense> findByNameContaining(String keyword, Pageable page);

	Page<Expense> findByDateBetween(Date startDate, Date endDate, Pageable page);

	Page<Expense> findByUserId(Long userId, Pageable pageable);

	Optional<Expense> findByUserIdAndId(Long userId, Long expenseId);

	Page<Expense> findByUserIdAndCategory(Long userId, String category, Pageable pageable);

	Page<Expense> findByUserIdAndNameContaining(Long userId, String keyword, Pageable pageable);

	Page<Expense> findByUserIdAndDateBetween(Long userId, Date startDate, Date endDate, Pageable page);
}


