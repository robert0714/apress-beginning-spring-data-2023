package com.apress.catalog.controller;

import com.apress.catalog.dto.CurrencyDTO;
import com.apress.catalog.service.CurrencyService;

import reactor.core.publisher.Mono;
 
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/currency")
public class CurrencyController {

    private CurrencyService currencyService;
 
    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

//    @GetMapping(value = "/{id}")
    @Deprecated
    public ResponseEntity<CurrencyDTO> getById(@PathVariable Long id) {
        CurrencyDTO response = currencyService.getById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @GetMapping(value = "/{id}")
    public ResponseEntity<Mono<CurrencyDTO>> getByIdMono(@PathVariable Long id) {
    	Mono<CurrencyDTO> response = currencyService.getByIdMono(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<Mono<CurrencyDTO>> save(@RequestBody CurrencyDTO currencyDTO) {
    	Mono<CurrencyDTO> response = currencyService.save(currencyDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    @PutMapping(value = "/{id}")
    public ResponseEntity<Mono<CurrencyDTO>> update(@RequestBody CurrencyDTO currencyDTO) {
    	Mono<CurrencyDTO> response = currencyService.update(currencyDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        currencyService.delete(id);
        return ResponseEntity.ok().build();
    }
}
