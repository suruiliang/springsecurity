package com.imooc.web.controller.async;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import lombok.Data;

@Component
@Data
public class DeferredResultHolder {
	private Map<String, DeferredResult<String>> map=new HashMap<String, DeferredResult<String>>();

}
