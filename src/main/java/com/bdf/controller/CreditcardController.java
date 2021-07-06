package com.bdf.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.bdf.controller.base.BaseController;
import com.bdf.entity.Creditcard;
import com.bdf.service.CreditcardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 */
@RestController
@RequestMapping({"/bookDataFinder/api/"})
@Slf4j
@RequiredArgsConstructor
public class CreditcardController extends BaseController {
	
	@Autowired
    private CreditcardService service;

	@GetMapping("/creditcards")
    public ResponseEntity<List<Creditcard>> getCreditcards() {
		List<Creditcard> creditcard = this.service.list();
    	return new ResponseEntity<List<Creditcard>>(creditcard, HttpStatus.OK);
    }
    
    @GetMapping(value = "/creditcard/{id}")
    public ResponseEntity<Creditcard> getCreditcardById(@PathVariable("id") Long id) {
    	Creditcard creditcard = this.service.findById(id);
    	if(creditcard == null){
    		return new ResponseEntity<Creditcard>(HttpStatus.NOT_FOUND);
    	}else{
    		return new ResponseEntity<Creditcard>(creditcard, HttpStatus.OK);
    	}    	
    }
    
    @PostMapping("/registerCard")
	public ResponseEntity<Creditcard> addCreditcard(@RequestBody Creditcard creditcard) {
    	this.service.saveCreditcard(creditcard);
		return new ResponseEntity<Creditcard>(creditcard, HttpStatus.CREATED);
	}
    
    
    @PutMapping(value = "/creditcard/{id}")
	public ResponseEntity<Creditcard> updateCreditcard(@PathVariable Long id, @RequestBody Creditcard creditcard) {
    	this.service.updateCreditcard(creditcard);
		return new ResponseEntity<Creditcard>(creditcard, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/creditcard/{id}")
	public ResponseEntity<Void> deleteCreditcard(@PathVariable("id") Long id) {
		this.service.deleteById(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}    
}