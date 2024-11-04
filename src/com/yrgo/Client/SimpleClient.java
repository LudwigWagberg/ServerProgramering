package com.yrgo.Client;

import com.yrgo.domain.Customer;
import com.yrgo.services.customers.CustomerManagementService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

public class SimpleClient {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext container = new
                ClassPathXmlApplicationContext("application.xml");

        CustomerManagementService customerManagment = container.getBean(CustomerManagementService.class);

        List<Customer> customers = customerManagment.getAllCustomers();

        for (Customer customer : customers) {
            System.out.println(customer);
        }

        container.close();

    }
}
