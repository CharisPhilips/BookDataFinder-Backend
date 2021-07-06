package com.bdf.controller;

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

import com.bdf.common.Global;
import com.bdf.controller.base.BaseController;
import com.bdf.entity.Bookcategory;
import com.bdf.service.BookcategoryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 */
@RestController
@RequestMapping({"/bookDataFinder/api/"})
@Slf4j
@RequiredArgsConstructor
public class BookcategoryController extends BaseController {
	
	@Autowired
    private BookcategoryService service;

	@GetMapping("/bookcategories")
    public ResponseEntity<Bookcategory> getBookcategorys() {
//		List<Bookcategory> bookcategory = this.service.list();
    	return new ResponseEntity<Bookcategory>(Global.g_category, HttpStatus.OK);
    	
    }
    
    @GetMapping(value = "/bookcategory/{id}")
    public ResponseEntity<Bookcategory> getBookcategoryById(@PathVariable("id") Long id) {
    	Bookcategory bookcategory = this.service.findById(id);
    	if(bookcategory == null){
    		return new ResponseEntity<Bookcategory>(HttpStatus.NOT_FOUND);
    	}else{
    		return new ResponseEntity<Bookcategory>(bookcategory, HttpStatus.OK);
    	}    	
    }
    
    @PostMapping(value = "/bookcategory")
	public ResponseEntity<Bookcategory> addBookcategory(@RequestBody Bookcategory bookcategory) {
    	this.service.saveBookcategory(bookcategory);
		return new ResponseEntity<Bookcategory>(bookcategory, HttpStatus.CREATED);
	}
    
    @PutMapping(value = "/bookcategory/{id}")
	public ResponseEntity<Bookcategory> updateBookcategory(@PathVariable Long id, @RequestBody Bookcategory bookcategory) {
    	this.service.updateBookcategory(bookcategory);
		return new ResponseEntity<Bookcategory>(bookcategory, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/bookcategory/{id}")
	public ResponseEntity<Void> deleteBookcategory(@PathVariable("id") Long id) {
		this.service.deleteById(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}    
}