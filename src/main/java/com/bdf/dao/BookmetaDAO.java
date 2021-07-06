package com.bdf.dao;

import org.springframework.stereotype.Repository;

import com.bdf.dao.base.AbstractDao;
import com.bdf.entity.Bookmeta;
 
/**
 * Meeting DAO.
 * 
 */
@Repository("bookmetaDAO")
public class BookmetaDAO extends AbstractDao<Long, Bookmeta> {
 
    public Bookmeta findById(Long id) {
    	Bookmeta user = getByKey(id);
        return user;
    }
 
    public Boolean save(Bookmeta user) {
		Boolean result = persist(user);
		return result;
    }
}