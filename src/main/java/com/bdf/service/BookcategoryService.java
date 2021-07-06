package com.bdf.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bdf.dao.BookcategoryDAO;
import com.bdf.entity.Bookcategory;
 
/**
 * Bookcategory service.
 * 
 */
@Transactional
@Service("bookcategoryService")
public class BookcategoryService {
 
    @Autowired
    private BookcategoryDAO dao;
 
    public List<Bookcategory> list() {
        return dao.list();
    }    
    
    public Bookcategory findById(Long id) {
        return dao.findById(id);
    }
 
    public List<Bookcategory> findByLevel(int level) {
    	return dao.findByLevel(level);
    }

	public List<Bookcategory> findByParent(Long parentid) {
		return dao.findByParent(parentid);
	}
	
    public long saveBookcategory(Bookcategory bookcategory) {
        long result = dao.insert(bookcategory);
        return result;
    }
 
    public Boolean updateBookcategory(Bookcategory bookcategory) {
    	Bookcategory entity = dao.findById(bookcategory.getId());
        if(entity != null){
        	entity.setCategoryname(bookcategory.getCategoryname());
        	entity.setCategorylevel(bookcategory.getCategorylevel());
        	entity.setCategoryparentid(bookcategory.getCategoryparentid());
        	entity.setCategoryorder(bookcategory.getCategoryorder());
        	dao.update(entity);
        	return true;
        }else{
        	return false;
        }
    }
    
    public Boolean deleteById(Long id) {
    	Bookcategory entity = dao.findById(id);
        if(entity != null){
        	dao.delete(entity);
        	return true;
        }else{
        	return false;
        }
    }

}