package com.bdf.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.bdf.dao.base.AbstractDao;
import com.bdf.entity.User;
import com.bdf.entity.mix.Page;
 
/**
 * Meeting DAO.
 * 
 */
@Repository("userDAO")
public class UserDAO extends AbstractDao<Long, User> {
 
	public User findById(Long id) {
    	User user = getByKey(id);
        return user;
    }
 
    public Boolean save(User user) {
		Boolean result = persist(user);
		return result;
    }

	public User findUser(String email, String password) {
		Session session = getSession().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(User.class);
		if (email != null && password !=null) {
			criteria.add(Restrictions.eq("email", email));
			criteria.add(Restrictions.eq("password", password));
		}
		List<User> login = criteria.list();
		if(login.size()==1) {
			return login.get(0);
		}
		return null;
	}
	
	public List<User> findUser(String email) {
		Session session = getSession().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(User.class);
		if (email != null) {
			criteria.add(Restrictions.eq("email", email));
		}
		return criteria.list();
	}

	public Page listByPageist(Page page) {
		Session session = getSession().getSessionFactory().getCurrentSession();
		long count = ((Number)session.createCriteria(User.class).setProjection(Projections.rowCount()).uniqueResult()).longValue();
		page.totalCount = count;
		Criteria criteria = session.createCriteria(User.class);
		criteria.setFirstResult((page.page) * page.rowsPerPage);
		criteria.setMaxResults(page.rowsPerPage);
		criteria.addOrder(Order.desc("id"));
		List<User> users = (List<User>) criteria.list();
		page.result = users;
		return page;
	}
}