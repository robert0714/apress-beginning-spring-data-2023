package com.apress.catalog.controller;

import com.apress.catalog.dto.CountryDTO;
import com.apress.catalog.service.CountryService;

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
@RequestMapping("/country")
public class CountryController {

    private CountryService countryService;
 
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

//    @GetMapping(value = "/{id}")
//    public ResponseEntity<CountryDTO> getById(@PathVariable Long id) {
//        CountryDTO response = countryService.getById(id);
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
    
    @GetMapping(value = "/{id}")
    public ResponseEntity<Mono<CountryDTO>> getById(@PathVariable Long id) {
    	Mono<CountryDTO> response = countryService.getByIdMono(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<Mono<CountryDTO>> save(@RequestBody  CountryDTO  countryDTO) {
    	Mono<CountryDTO> response = countryService.save(countryDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    @PutMapping(value = "/{id}")
    public ResponseEntity<Mono<CountryDTO>> update(@RequestBody  CountryDTO  countryDTO) {
    	Mono<CountryDTO> response = countryService.update(countryDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws InterruptedException {
        countryService.delete(id);
        return ResponseEntity.ok().build();
    }


}
