package com.imooc.web.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.imooc.dto.User;
import com.imooc.dto.UserQueryCondition;
import com.imooc.exception.UserNotExistException;

@RestController
public class UserController {
	private Logger logger=LoggerFactory.getLogger(getClass());
	
	@PostMapping(value="/user")
	@JsonView(User.UserDetailView.class)
	public User create(@Valid @RequestBody User user) {
		System.out.println(ReflectionToStringBuilder.toString(user, ToStringStyle.DEFAULT_STYLE));
		System.out.println(user.getBirthday());
		user.setId("1");
		user.setBirthday(new Date());
		return user;
	}
	@PutMapping(value="/user/{id:\\d+}")
	@JsonView(User.UserDetailView.class)
	public User update(@Valid @RequestBody User user,BindingResult errors,@PathVariable String id) {
		if (errors.hasErrors()) {
			errors.getAllErrors().stream().forEach(error->{
				FieldError fieldError=(FieldError) error;
				String message=fieldError.getField()+" "+fieldError.getDefaultMessage();
				System.out.println(message);
			});			
		};
		System.out.println(ReflectionToStringBuilder.toString(user, ToStringStyle.MULTI_LINE_STYLE));
		user.setBirthday(new Date());
		return user;
	}
	@DeleteMapping(value="/user/{id:\\d+}")
	public void delete(@PathVariable String id) {
		System.out.println(id);
	}
	@GetMapping(value="/user")
	@JsonView(User.UserSimpleView.class)
	public List<User> query(UserQueryCondition condition,@PageableDefault(page=1,size=15,sort="username,ASC") Pageable pageable) {
		System.out.println(ReflectionToStringBuilder.toString(condition, ToStringStyle.MULTI_LINE_STYLE));
		System.out.println(pageable.getPageNumber());
		System.out.println(pageable.getPageSize());
		System.out.println(pageable.getSort());
		List<User> users=new ArrayList<User>();
		users.add(new User());
		users.add(new User());
		users.add(new User());
		return users;
	}
	@GetMapping(value="/user/{id:\\d+}")
	@JsonView(User.UserDetailView.class)
	public User getInfo(@PathVariable String id) {
//		throw new RuntimeException(id);
		System.out.println("进入getInfo服务");
		User user=new User();
		user.setUsername("tom");
		return user;
	}
}
