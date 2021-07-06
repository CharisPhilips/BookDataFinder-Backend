package com.bdf.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.bdf.dao.base.AbstractDao;
import com.bdf.entity.Bookcategory;
 
/**
 * Meeting DAO.
 * 
 */
@Repository("bookcategoryDAO")
public class BookcategoryDAO extends AbstractDao<Long, Bookcategory> {
 
    public Bookcategory findById(Long id) {
    	Bookcategory user = getByKey(id);
        return user;
    }
 
    public long insert(Bookcategory user) {
		Boolean result = persist(user);
		long id = user.getId();
		return id;
    }
    
    public Boolean update(Bookcategory user) {
    	Boolean result = update(user);
    	return result;
    }
    
    public List<Bookcategory> findByLevel(int level) {
    	Session session = getSession().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(Bookcategory.class);
		criteria.add(Restrictions.eq("categorylevel", level));
		//criteria.addOrder(Order.asc("categoryorder"));
		criteria.addOrder(Order.asc("categoryname"));
		return criteria.list();
    }

	public List<Bookcategory> findByParent(Long parentid) {
		Session session = getSession().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(Bookcategory.class);
		criteria.add(Restrictions.eq("categoryparentid", parentid));
		//criteria.addOrder(Order.asc("categoryorder"));
		criteria.addOrder(Order.asc("categoryname"));
		return criteria.list();
	}
}