package com.apress.catalog.specification;

import com.apress.catalog.model.Currency;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class CurrencySpecification implements Specification<Currency> {
	
	private static final long serialVersionUID = 2753473399996931822L;
	
	Currency entity;

    public CurrencySpecification(Currency entity) {
        this.entity = entity;
    }
	
	@Override
	public Predicate toPredicate(Root<Currency> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		return builder.equal(root.get("code"), entity.getCode());
	}
}
