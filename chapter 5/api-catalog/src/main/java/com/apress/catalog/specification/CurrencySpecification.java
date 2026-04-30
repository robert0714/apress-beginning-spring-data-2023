package com.apress.catalog.specification;

import com.apress.catalog.model.Currency;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class CurrencySpecification implements Specification<Currency> {
	
	private static final long serialVersionUID = 2753473399996931822L;
	
	Currency entity;

    public CurrencySpecification(Currency entity) {
        this.entity = entity;
    }
	
	@Override
	public Predicate toPredicate(Root<Currency> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		
        //create a new predicate list
        List<Predicate> predicates = new ArrayList<>();

	    // Define all the conditions of the query
	    if (entity.getCode() != null) {
	        Predicate codePredicate = builder.equal(root.get("code"), entity.getCode());
	        predicates.add(codePredicate);
	    }

	    if (predicates.size() == 1) {
	        return predicates.get(0);
	    }
	    return builder.and(predicates.toArray(new Predicate[0]));
	}
}
