package com.imooc.dto;

import java.util.Date;

import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonView;
import com.imooc.validator.MyConstraint;

import lombok.Data;

@Data
public class User {
	public interface UserSimpleView{};
	
	public interface UserDetailView extends UserSimpleView{};
	
	@JsonView(UserSimpleView.class)
	private String id;
	@JsonView(UserSimpleView.class)
	@MyConstraint(message="这是一个测试")
	private String username;
	@JsonView(UserSimpleView.class)
	@Past(message="生日必须是过去的时间")
	private Date birthday;
	@JsonView(UserDetailView.class)
	@NotBlank(message="密码不能为空")
	private String password;
	
}
