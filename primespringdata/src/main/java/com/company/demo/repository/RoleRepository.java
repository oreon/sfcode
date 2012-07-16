/*
 * (c) Copyright 2005-2012 JAXIO, www.jaxio.com
 * Source code generated by Celerio, a Jaxio product
 * Want to use Celerio within your company? email us at info@jaxio.com
 * Follow us on twitter: @springfuse
 * Template pack-backend-sd:src/main/java/project/repository/Repository.e.vm.java
 */
package com.company.demo.repository;

import com.company.demo.domain.Role;
import com.company.demo.repository.support.CustomRepository;

/**
 * Advanced CRUD Repository for {@link Role}.
 */
public interface RoleRepository extends CustomRepository<Role, Integer> {

    /**
     * Lookup a role by roleName.
     */
    Role getByRoleName(String roleName);
}