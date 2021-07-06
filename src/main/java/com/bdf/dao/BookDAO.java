package com.bdf.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.bdf.common.Global;
import com.bdf.dao.base.AbstractDao;
import com.bdf.entity.Book;
import com.bdf.entity.mix.Page;
 
/**
 * Meeting DAO.
 * 
 */
@Repository("bookDAO")
public class BookDAO extends AbstractDao<Long, Book> {
 
    public Book findById(Long id) {
    	Book user = getByKey(id);
        return user;
    }
 
    public Boolean save(Book user) {
		Boolean result = persist(user);
		return result;
    }

	public List<Book> listByCategory(long categoryId) {
		Session session = getSession().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(Book.class);
		criteria.add(Restrictions.eq("categoryid", categoryId));
		criteria.addOrder(Order.asc("bookname"));
		return criteria.list();
	}

	public List<Book> searchByTitle(String strTitle) {
		Session session = getSession().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(Book.class);
		criteria.add(Restrictions.like("bookname",  "%" + strTitle + "%"));
		criteria.addOrder(Order.asc("bookname"));
		criteria.setMaxResults(Global.MAX_RESULT);
		return criteria.list();
	}

	public Page listByCategoryPage(long categoryId, Page page) {
		Session session = getSession().getSessionFactory().getCurrentSession();
		long count = ((Number)session.createCriteria(Book.class).add(Restrictions.eq("categoryid", categoryId)).setProjection(Projections.rowCount()).uniqueResult()).longValue();
		page.totalCount = count;
		Criteria criteria = session.createCriteria(Book.class);
		criteria.add(Restrictions.eq("categoryid", categoryId));
		criteria.addOrder(Order.asc("bookname"));
		criteria.setFirstResult((page.page) * page.rowsPerPage);
		criteria.setMaxResults(page.rowsPerPage);
		List<Book> books = (List<Book>) criteria.list();
		page.result = books;
		return page;
	}
}