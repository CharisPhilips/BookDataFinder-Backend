package com.bdf.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bdf.dao.UserDAO;
import com.bdf.entity.User;
import com.bdf.entity.mix.Page;
 
/**
 * User service.
 */
@Transactional
@Service("userService")
public class UserService {
 
    @Autowired
    private UserDAO dao;
 
    public List<User> list() {
        return dao.list();
    }    

	public Page listByPage(Page page) {
		return dao.listByPageist(page);
	}
	
    public User findById(Long id) {
        return dao.findById(id);
    }
 
	public User findByEmail(String email) throws Exception {
		List<User>userList = dao.findUser(email);
		if(userList.size() > 0) {
			throw new Exception("Your email exist already");
		}
		return userList.get(0);
	}

	public User login(String email, String password) {
		return dao.findUser(email, password);
	}

	public boolean signup(User user) throws Exception {
		List<User>userList = dao.findUser(user.getEmail());
		if(userList.size() > 0) {
			throw new Exception("Your email exist already");
		}
		return saveUser(user);
	}
	
    public Boolean saveUser(User user) throws Exception {
    	List<User>userList = dao.findUser(user.getEmail());
		if(userList.size() > 0) {
			throw new Exception("Your email exist already");
		}
        Boolean result = dao.save(user);
        return result;
    }
 
    public Boolean updateUser(User user) {
    	User entity = dao.findById(user.getId());
        if(entity != null){
        	entity.setEmail(user.getEmail());
        	entity.setPassword(user.getPassword());
        	dao.update(entity);
        	return true;
        }else{
        	return false;
        }
    }
    
    public Boolean deleteById(Long id) {
    	User entity = dao.findById(id);
        if(entity != null){
        	dao.delete(entity);
        	return true;
        }else{
        	return false;
        }
    }

	public User changePassword(String email, String oldpassword, String password) {
		User user = dao.findUser(email, oldpassword);
		if(user==null) {
			return null;
		}
		user.setPassword(password);
		if(dao.update(user)) {
			return user;
		}
		return null;
	}

	public boolean isAllowDownload(long userId) {
		User user = dao.findById(userId);
		if(user==null) {
			return false;
		}
		return user.isAllowService();
	}
}