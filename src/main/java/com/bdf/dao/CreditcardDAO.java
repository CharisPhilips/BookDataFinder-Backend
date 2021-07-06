package com.bdf.dao;

import org.springframework.stereotype.Repository;

import com.bdf.dao.base.AbstractDao;
import com.bdf.entity.Creditcard;
 
/**
 */
@Repository("creditcardDAO")
public class CreditcardDAO extends AbstractDao<Long, Creditcard> {
 
    public Creditcard findById(Long id) {
    	Creditcard user = getByKey(id);
        return user;
    }
 
    public Boolean save(Creditcard card) {
    	card.setId(null);
		Boolean result = persist(card);
		return result;
    }
}