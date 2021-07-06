package com.bdf.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bdf.dao.CreditcardDAO;
import com.bdf.entity.Creditcard;
 
/**
 * Creditcard service.
 * 
 */
@Transactional
@Service("creditcardService")
public class CreditcardService {
 
    @Autowired
    private CreditcardDAO dao;
 
    public List<Creditcard> list() {
        return dao.list();
    }    
    
    public Creditcard findById(Long id) {
        return dao.findById(id);
    }
 
    public Boolean saveCreditcard(Creditcard user) {
        Boolean result = dao.save(user);
        return result;
    }
 
    public Boolean updateCreditcard(Creditcard user) {
    	Creditcard entity = dao.findById(user.getId());
        if(entity != null){
        	entity.setCardno(user.getCardno());
        	entity.setExpmonth(user.getExpmonth());
        	entity.setExpyear(user.getExpyear());
        	entity.setCardcvc(user.getCardcvc());
        	entity.setUserid(user.getUserid());
        	dao.update(entity);
        	return true;
        }else{
        	return false;
        }
    }
    
    public Boolean deleteById(Long id) {
    	Creditcard entity = dao.findById(id);
        if(entity != null){
        	dao.delete(entity);
        	return true;
        }else{
        	return false;
        }
    }
}