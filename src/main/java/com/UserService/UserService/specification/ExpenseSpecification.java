package com.UserService.UserService.specification;

import org.springframework.data.jpa.domain.Specification;

import com.UserService.UserService.dto.ExpenseFilterDTO;
import com.UserService.UserService.entity.ExpenseEntity;

import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

public class ExpenseSpecification {

    public static Specification<ExpenseEntity> filterExpenses(
            ExpenseFilterDTO filter) {

        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (filter.getCategory() != null &&
                    !filter.getCategory().isBlank()) {

                predicates.add(
                        cb.equal(root.get("category"),
                                filter.getCategory()));
            }

            if (filter.getMinAmount() != null) {

                predicates.add(
                        cb.greaterThanOrEqualTo(
                                root.get("amount"),
                                filter.getMinAmount()));
            }

            if (filter.getMaxAmount() != null) {

                predicates.add(
                        cb.lessThanOrEqualTo(
                                root.get("amount"),
                                filter.getMaxAmount()));
            }

            if (filter.getStartDate() != null) {

                predicates.add(
                        cb.greaterThanOrEqualTo(
                                root.get("expenseDate"),
                                filter.getStartDate()));
            }

            if (filter.getEndDate() != null) {

                predicates.add(
                        cb.lessThanOrEqualTo(
                                root.get("expenseDate"),
                                filter.getEndDate()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

}
