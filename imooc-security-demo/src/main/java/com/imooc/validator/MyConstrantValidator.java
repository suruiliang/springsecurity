package com.imooc.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.service.HelloService;

//不用加component
public class MyConstrantValidator implements ConstraintValidator<MyConstraint, Object> {
	@Autowired
	private HelloService helloService;

	@Override
	public void initialize(MyConstraint arg0) {
		System.out.println("my validator init");		
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		helloService.greeting("tom");
		System.out.println(value);
		return false;
	}



}
