package com.apress.catalog.service;

import com.apress.catalog.dto.CurrencyDTO;
import com.apress.catalog.mapper.ApiMapper;
import com.apress.catalog.model.Currency;
import com.apress.catalog.repository.CurrencyRepository;

import jakarta.validation.Validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Set;

public class CurrencyServiceTest {
    @Mock
    private CurrencyRepository repository;

    @InjectMocks
    private CurrencyService currencyService;
    
    @Mock
    private Validator validator;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void should_get_currency_by_id() {
        Currency currency = new Currency(1L, "USD", "Dollar", true, 2, "$");
        when(repository.findById(1L)).thenReturn(Mono.just(currency));
        
        Mono<CurrencyDTO> result = currencyService.getByIdMono(1L);
        
        StepVerifier.create(result)
                .assertNext(currencyDTO -> {
                    assertNotNull(currencyDTO);
                    assertEquals("USD", currencyDTO.getCode());
                })
                .verifyComplete();
    }

    @Test
    public void should_save_currency() {
        CurrencyDTO currencyDTO = new CurrencyDTO(2L, "EUR", "Euro", true, 2);
        Currency currency = ApiMapper.INSTANCE.DTOToEntity(currencyDTO);
        when(repository.save(any(Currency.class))).thenReturn(Mono.just(currency));

        Mono<CurrencyDTO> result = currencyService.save(currencyDTO);

        StepVerifier.create(result)
                .assertNext(savedCurrency -> assertEquals("EUR", savedCurrency.getCode()))
                .verifyComplete();
    }

    @Test
    public void should_update_currency() {
        CurrencyDTO currencyDTO = new CurrencyDTO(3L, "GBP", "Pound", true, 2);
        Currency currency = ApiMapper.INSTANCE.DTOToEntity(currencyDTO);
        when(repository.save(any(Currency.class))).thenReturn(Mono.just(currency));

        Mono<CurrencyDTO> result = currencyService.update(currencyDTO);

        StepVerifier.create(result)
                .assertNext(updatedCurrency -> assertEquals("GBP", updatedCurrency.getCode()))
                .verifyComplete();
    }

    @Test
    public void should_delete_currency() {
        Currency currency = new Currency(4L, "JPY", "Yen", true, 2, "¥");
        when(repository.findById(4L)).thenReturn(Mono.just(currency));
        when(repository.save(any(Currency.class))).thenReturn(Mono.empty());

        Mono<Void> result = currencyService.delete(4L);

        StepVerifier.create(result)
                .verifyComplete();

        verify(repository, times(1)).save(currency);
        assertFalse(currency.getEnabled());
    }

    @Test
    public void should_handle_exception_on_save() {
        CurrencyDTO currencyDTO = new CurrencyDTO(2L, "EUR", "Euro", true, 2);
        when(validator.validate(any())).thenReturn(Set.of());

        when(repository.save(any(Currency.class)))
                .thenThrow(new RuntimeException("Simulated Exception"));

        Mono<CurrencyDTO> result = currencyService.save(currencyDTO);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && throwable.getMessage().equals("Simulated Exception"))
                .verify();

        verify(repository, times(1)).save(any(Currency.class));
    }
}
