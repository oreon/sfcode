package bizobjects.service.impl;

import javax.persistence.*;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import org.apache.log4j.Logger;

@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class OrderItemServiceImpl extends OrderItemServiceImplBase {

	private static final Logger log = Logger
			.getLogger(OrderItemServiceImpl.class);

	public OrderItemServiceImpl orderItemServiceImplInstance() {
		return this;
	}
}
