package com.imooc.web.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.imooc.dto.User;
import com.imooc.dto.UserQueryCondition;
import com.imooc.security.core.properties.SecurityProperties;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@RestController
@RequestMapping(value="/user")
public class UserController {
	//private Logger logger=LoggerFactory.getLogger(getClass());
	@Autowired
	private SecurityProperties securityProperties;
	
	@GetMapping(value="/me")
	public Object getCurrentUser(Authentication user,HttpServletRequest request) throws Exception {
		String header=request.getHeader("Authorization");
		String token=StringUtils.substringAfter(header, "bearer ");
		Claims claims=Jwts.parser().setSigningKey(securityProperties.getOauth2().getJwtSignKey().getBytes("UTF-8")).parseClaimsJws(token).getBody();
		String company=(String)claims.get("company");
		System.out.println("-->"+company);
		return user;
	}
	@PostMapping
	@JsonView(User.UserDetailView.class)
	public User create(@Valid @RequestBody User user) {
		System.out.println(ReflectionToStringBuilder.toString(user, ToStringStyle.DEFAULT_STYLE));
		System.out.println(user.getBirthday());
		user.setId("1");
		user.setBirthday(new Date());
		return user;
	}
	@PutMapping(value="/{id:\\d+}")
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
	@DeleteMapping(value="/{id:\\d+}")
	public void delete(@PathVariable String id) {
		System.out.println(id);
	}
	@GetMapping
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
	@GetMapping(value="/{id:\\d+}")
	@JsonView(User.UserDetailView.class)
	public User getInfo(@PathVariable String id) {
//		throw new RuntimeException(id);
		System.out.println("进入getInfo服务");
		User user=new User();
		user.setUsername("tom");
		return user;
	}
}
