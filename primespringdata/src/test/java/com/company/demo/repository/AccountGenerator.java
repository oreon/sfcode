/*
 * (c) Copyright 2005-2012 JAXIO, www.jaxio.com
 * Source code generated by Celerio, a Jaxio product
 * Want to use Celerio within your company? email us at info@jaxio.com
 * Follow us on twitter: @springfuse
 * Template pack-backend-sd:src/test/java/repository/ModelGenerator.e.vm.java
 */
package com.company.demo.repository;

import java.util.Date;
import org.springframework.stereotype.Service;
import com.company.demo.domain.Account;
import com.company.demo.domain.enums.Civility;
import com.company.demo.util.ValueGenerator;

/**
 * Helper class to create transient entities instance for testing purposes.
 * Simple properties are pre-filled with random values.
 */
@Service
public class AccountGenerator {

    /**
     * Returns a new Account instance filled with random values.
     */
    public Account getAccount() {
        Account account = new Account();

        // simple attributes follows
        account.setUsername(ValueGenerator.getUniqueString(255));
        account.setPassword("a");
        account.setEmail(ValueGenerator.getUniqueEmail());
        account.setIsEnabled(true);
        account.setCivility(Civility.MR);
        account.setFirstName("a");
        account.setLastName("a");
        account.setBirthDate(new Date());
        return account;
    }

}