package com.apress.catalog.controller;

import com.apress.catalog.dto.CountryDTO;
import com.apress.catalog.dto.CurrencyDTO;
import com.apress.catalog.service.CurrencyService;

import reactor.core.publisher.Mono;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CurrencyControllerTest {
    @Mock
    private CurrencyService currencyService;

    @InjectMocks
    private CurrencyController currencyController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void should_get_currency_by_id() {
        CurrencyDTO currencyDTO = new CurrencyDTO(1L, "USD", "Dollar", true, 2);
        when(currencyService.getById(1L)).thenReturn(currencyDTO);

        ResponseEntity<CurrencyDTO> response = currencyController.getById(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(currencyDTO, response.getBody());
    }

    @Test
    public void should_save_currency() {
        CurrencyDTO currencyDTO = new CurrencyDTO(2L, "EUR", "Euro", true, 2);
        Mono<CurrencyDTO> mono = mock(Mono.class);
        when(mono.block()).thenReturn(currencyDTO);
        when(currencyService.save(currencyDTO)).thenReturn(mono);

        ResponseEntity<Mono<CurrencyDTO>> response = currencyController.save(currencyDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(currencyDTO, response.getBody());
    }

    @Test
    public void should_update_currency() {
        CurrencyDTO currencyDTO = new CurrencyDTO(3L, "GBP", "Pound", true, 2);
        Mono<CurrencyDTO> mono = mock(Mono.class);
        when(mono.block()).thenReturn(currencyDTO);
        when(currencyService.update(currencyDTO)).thenReturn(mono);

        ResponseEntity<Mono<CurrencyDTO>> response  = currencyController.update(currencyDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(currencyDTO, response.getBody());
    }

    @Test
    public void should_delete_currency() {
        doNothing().when(currencyService).delete(4L);

        ResponseEntity<Void> response = currencyController.delete(4L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(currencyService, times(1)).delete(4L);
    }
}