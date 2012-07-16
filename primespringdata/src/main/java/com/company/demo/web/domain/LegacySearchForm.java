/*
 * (c) Copyright 2005-2012 JAXIO, www.jaxio.com
 * Source code generated by Celerio, a Jaxio product
 * Want to use Celerio within your company? email us at info@jaxio.com
 * Follow us on twitter: @springfuse
 * Template pack-jsf2-primefaces-sd:src/main/java/SearchForm.e.vm.java
 */
package com.company.demo.web.domain;

import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import com.company.demo.domain.Legacy;
import com.company.demo.repository.LegacyRepository;
import com.company.demo.web.domain.support.GenericSearchForm;

/**
 * SearchForm for {@link Legacy}
 */
@Component
@Scope("session")
public class LegacySearchForm extends GenericSearchForm<Legacy> implements Serializable {
    private static final long serialVersionUID = 1L;
    // make it static to avoid http://jira.springframework.org/browse/SWF-1224
    private static LegacyRepository legacyRepository;

    private Legacy legacy = new Legacy();

    @Autowired
    public LegacySearchForm(LegacyRepository instance) {
        if (legacyRepository == null) {
            legacyRepository = instance;
        }
    }

    /**
     * Server side pagination with lazy model.
     * Automatically called by PrimeFaces component (via the GenericSearchForm).
     */
    @Override
    protected List<Legacy> load(PageRequest pageRequest) {
        Page<Legacy> page = legacyRepository.findWithExample(legacy, pageRequest);
        setRowCount((int) page.getTotalElements());
        return page.getContent();
    }

    /**
     * The root legacy for search by example.
     * Used from the view.
     */
    public Legacy getLegacy() {
        return legacy;
    }
}