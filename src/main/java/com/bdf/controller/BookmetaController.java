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
import com.bdf.entity.Bookmeta;
import com.bdf.service.BookmetaService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 */
@RestController
@RequestMapping({"/bookDataFinder/api/"})
@Slf4j
@RequiredArgsConstructor
public class BookmetaController extends BaseController {
	
	@Autowired
    private BookmetaService service;

	@GetMapping("/bookmetas")
    public ResponseEntity<List<Bookmeta>> getBookmetas() {
		List<Bookmeta> bookmeta = this.service.list();
    	return new ResponseEntity<List<Bookmeta>>(bookmeta, HttpStatus.OK);
    }
    
    @GetMapping(value = "/bookmeta/{id}")
    public ResponseEntity<Bookmeta> getBookmetaById(@PathVariable("id") Long id) {
    	Bookmeta bookmeta = this.service.findById(id);
    	if(bookmeta == null){
    		return new ResponseEntity<Bookmeta>(HttpStatus.NOT_FOUND);
    	}else{
    		return new ResponseEntity<Bookmeta>(bookmeta, HttpStatus.OK);
    	}    	
    }
    
    @PostMapping(value = "/bookmeta")
	public ResponseEntity<Bookmeta> addBookmeta(@RequestBody Bookmeta bookmeta) {
    	this.service.saveBookmeta(bookmeta);
		return new ResponseEntity<Bookmeta>(bookmeta, HttpStatus.CREATED);
	}
    
    @PutMapping(value = "/bookmeta/{id}")
	public ResponseEntity<Bookmeta> updateBookmeta(@PathVariable Long id, @RequestBody Bookmeta bookmeta) {
    	this.service.updateBookmeta(bookmeta);
		return new ResponseEntity<Bookmeta>(bookmeta, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/bookmeta/{id}")
	public ResponseEntity<Void> deleteBookmeta(@PathVariable("id") Long id) {
		this.service.deleteById(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}    
}