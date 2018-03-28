package com.imooc.web.controller.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class MockQueue {
	private Logger logger=LoggerFactory.getLogger(getClass());
	private String placeOrder;

	private String completeOrder;

	public final void setPlaceOrder(String placeOrder) throws InterruptedException {
		new Thread(()->{
			logger.info("接到下单请求,"+placeOrder);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.completeOrder = placeOrder;
			logger.info("下单请求处理完毕,"+placeOrder);
		}).start();
	}
	
}
