package com.bdf.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bdf.dao.BookmetaDAO;
import com.bdf.entity.Bookmeta;

/**
 * BookMeta service.
 * 
 */
@Transactional
@Service("metaService")
public class BookmetaService {
 
    @Autowired
    private BookmetaDAO dao;
 
    public List<Bookmeta> list() {
        return dao.list();
    }    
    
    public Bookmeta findById(Long id) {
        return dao.findById(id);
    }
 
    public Boolean saveBookmeta(Bookmeta meta) {
        Boolean result = dao.save(meta);
        return result;
    }
 
    public Boolean updateBookmeta(Bookmeta meta) {
    	Bookmeta entity = dao.findById(meta.getId());
        if(entity != null){
        	entity.setMetaname(meta.getMetaname());
        	dao.update(entity);
        	return true;
        }else{
        	return false;
        }
    }
    
    public Boolean deleteById(Long id) {
    	Bookmeta entity = dao.findById(id);
        if(entity != null){
        	dao.delete(entity);
        	return true;
        }else{
        	return false;
        }
    }
}