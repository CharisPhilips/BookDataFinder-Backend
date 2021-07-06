package com.bdf.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bdf.controller.base.BaseController;
import com.bdf.entity.User;
import com.bdf.entity.mix.Page;
import com.bdf.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 */
@RestController
@RequestMapping({"/bookDataFinder/api/"})
@Slf4j
@RequiredArgsConstructor
public class UserController extends BaseController {

	@Autowired
	private UserService service;

	@PostMapping("/loginTest")
	public ResponseEntity<User> loginTest(@RequestBody User user) throws Exception {
		if(user==null) {
			throw new Exception("Can't login");
		}
		return new ResponseEntity<User>(user, HttpStatus.CREATED);
	}
	
	@GetMapping("/login")
	@ResponseBody
	public ResponseEntity<User> login(@RequestParam Map<String, String> loginParams) throws Exception {
		String email = loginParams.get("email");
		String password = loginParams.get("password");
		User user = service.login(email, password);
		if(user==null) {
			throw new Exception("Can't login");
		}
		return new ResponseEntity<User>(user, HttpStatus.CREATED);
	}

	@PostMapping("/signup")
	public ResponseEntity<User> signup(@RequestBody User user) throws Exception {
		if(!this.service.signup(user)) {
			throw new Exception("Signup is failed");
		}
		return new ResponseEntity<User>(user, HttpStatus.CREATED);
	}
	
	@GetMapping("/changepwd")
	public ResponseEntity<User> changepwd(@RequestParam Map<String, String> changeParams) throws Exception {
		String email = changeParams.get("email");
		String oldpassword = changeParams.get("oldpassword");
		String password = changeParams.get("password");
		User user = service.changePassword(email, oldpassword, password);
		return new ResponseEntity<User>(user, HttpStatus.CREATED);
	}

	@GetMapping("/users")
	public ResponseEntity<List<User>> getUsers() {
		List<User> user = this.service.list();
		return new ResponseEntity<List<User>>(user, HttpStatus.OK);
	}

	@GetMapping("/usersBypage")
	public ResponseEntity<Page> getUsersByPage(@RequestParam Map<String, String> requestParams) {
		Page page = new Page();
		page.page = Integer.parseInt(requestParams.get("page"));
		page.rowsPerPage = Integer.parseInt(requestParams.get("rowsPerPage"));
		this.service.listByPage(page);
		return new ResponseEntity<Page>(page, HttpStatus.OK);
	}
	
	@GetMapping(value = "/user/{id}")
	public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
		User user = this.service.findById(id);
		if (user == null) {
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
	}

	@PostMapping(value = "/user")
	public ResponseEntity<User> addUser(@RequestBody User user) throws Exception {
		this.service.saveUser(user);
		return new ResponseEntity<User>(user, HttpStatus.CREATED);
	}

	@PutMapping(value = "/user/{id}")
	public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
		this.service.updateUser(user);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@DeleteMapping(value = "/user/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
		this.service.deleteById(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}