package com.yrgo.integrationtests;

import com.yrgo.domain.Customer;
import com.yrgo.services.customers.CustomerManagementService;
import com.yrgo.services.customers.CustomerNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(SpringExtension.class)
@ContextConfiguration( { "/other-tiers.xml", "/datasource-test.xml" } )
@Transactional
public class managingCustomerIntegrationTest {

    @Autowired
    private CustomerManagementService customerService;

    @Test
    public void testAddingANewCustomer() {

        customerService.newCustomer(new Customer("CS03939", "Acme", "Good Customer"));
        customerService.newCustomer(new Customer("CS03930", "Acme", "Good Customer"));

        int customerInDb = customerService.getAllCustomers().size();

        assertEquals(2, customerInDb, "two customer");
    }

    @Test
    public void testFindingCustomer() {

        String customerId = "CS03931";

        Customer testCustomer = new Customer(customerId, "Acme", "Good Customer");
        customerService.newCustomer(testCustomer);

        Customer foundCustomer = null;
        try {
            foundCustomer = customerService.findCustomerById(customerId);
            System.out.println("testCustomer: " + testCustomer);
            System.out.println("foundCustomer: " + foundCustomer);
            System.out.println("testCustomer.equals(foundCustomer): " + testCustomer.equals(foundCustomer));
            assertEquals(testCustomer, foundCustomer);
        }
        catch (CustomerNotFoundException e) {
            fail("No customer found");
        }
    }
}
