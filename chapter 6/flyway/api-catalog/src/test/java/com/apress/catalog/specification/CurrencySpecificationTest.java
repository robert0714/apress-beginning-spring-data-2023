package com.apress.catalog.specification;

import com.apress.catalog.model.Currency;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CurrencySpecificationTest {

    private Currency currency;
    private CurrencySpecification currencySpecification;
    private CriteriaBuilder criteriaBuilder;
    private CriteriaQuery<Currency> criteriaQuery;
    private Root<Currency> root;

    @BeforeEach
    public void setUp() {
        this.currency = new Currency();
        this.currency.setCode("USD");

        this.currencySpecification = new CurrencySpecification(currency);
        this.criteriaBuilder = mock(CriteriaBuilder.class);
        this.criteriaQuery = mock(CriteriaQuery.class);
        this.root = mock(Root.class);
    }

    @Test
    public void testToPredicate() {
        Predicate predicate = mock(Predicate.class);
        Path<Object> path = mock(Path.class);
        when(root.get("code")).thenReturn(path);
        when(criteriaBuilder.equal(path, "USD")).thenReturn(predicate);
      
        Predicate result = currencySpecification.toPredicate( this.root,  this.criteriaQuery,  this.criteriaBuilder);

        assertNotNull(result);
    }
}