package com.bdf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bdf.bootstrap.BootstrapThread;
 
/**
 * Meeting service.
 * 
 */
@Transactional
@Service("meetingService")
public class _BootstrapService {
	
	private BootstrapThread serviceThread;
	
	@Autowired
    private BookcategoryService categoryService;

	public _BootstrapService() {
		super();
	}

    public void startThread() {
    	this.serviceThread = new BootstrapThread();
    	this.serviceThread.setService(this);
    	this.serviceThread.start();
    }
    
    public BookcategoryService getCategoryService() { return categoryService; }
    public void setCategoryService(BookcategoryService categoryService) { this.categoryService = categoryService; }
}