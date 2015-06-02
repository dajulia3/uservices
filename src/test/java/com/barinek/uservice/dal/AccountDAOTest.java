package com.barinek.uservice.dal;

import com.barinek.uservice.models.Account;
import com.barinek.uservice.support.TestDataSource;
import org.junit.Test;

import javax.sql.DataSource;

import static junit.framework.TestCase.assertEquals;

public class AccountDAOTest {
    @Test
    public void testFindFor() throws Exception {
        TestDataSource.cleanWithFixtures();
        DataSource dataSource = TestDataSource.getDataSource();

        int ownerId = 138;

        AccountDAO accountDAO = new AccountDAO(dataSource);
        accountDAO.create(new Account(ownerId, "anAccount"));
        Account anAccount = accountDAO.findFor(ownerId);

        assertEquals(ownerId, anAccount.getOwnerId());
    }
}