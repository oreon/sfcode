package com.cc.civlit.domain.service.impl;

import javax.persistence.*;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import org.apache.log4j.Logger;
import javax.jws.WebService;

import java.util.Date;

@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
@WebService(endpointInterface = "com.cc.civlit.domain.service.GrantedRoleService", serviceName = "GrantedRoleService")
public class GrantedRoleServiceImpl extends GrantedRoleServiceImplBase
		implements
			java.io.Serializable {

	private static final Logger log = Logger
			.getLogger(GrantedRoleServiceImpl.class);
	private static final long serialVersionUID = 1L;

}
