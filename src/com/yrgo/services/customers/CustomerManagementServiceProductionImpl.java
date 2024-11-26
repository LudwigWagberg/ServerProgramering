package com.yrgo.services.customers;

import com.yrgo.dataaccess.CustomerDao;
import com.yrgo.dataaccess.RecordNotFoundException;
import com.yrgo.domain.Call;
import com.yrgo.domain.Customer;

import java.util.List;

public class CustomerManagementServiceProductionImpl implements CustomerManagementService{

    private CustomerDao dao;

    public CustomerManagementServiceProductionImpl (CustomerDao dao ) {this.dao = dao;}

    public void newCustomer(Customer newCustomer){dao.create(newCustomer);}

    public void updateCustomer(Customer changedCustomer){
        try {
            dao.update(changedCustomer);

        }catch (RecordNotFoundException ignored) {
        }
    }

    public void deleteCustomer(Customer oldCustomer){
        try {
            dao.delete(oldCustomer);
        }catch (RecordNotFoundException ignored){
        }
    }

    public Customer findCustomerById(String customerId) throws CustomerNotFoundException {
        try {
            return dao.getById(customerId);
        }catch (RecordNotFoundException ignored) {
            throw new CustomerNotFoundException();
        }
    }

    public List<Customer> findCustomersByName (String name) {
        return dao.getByName(name);
    }

    public List<Customer> getAllCustomers() {
        return dao.getAllCustomers();
    }

    public Customer getFullCustomerDetail(String customerId) throws CustomerNotFoundException {
        try {
            return dao.getById(customerId);
        }catch (RecordNotFoundException ignored) {
            throw new CustomerNotFoundException();
        }
    }

    public void recordCall(String customerId, Call callDetails) throws CustomerNotFoundException {
        try {
            Customer customer = dao.getById(customerId);
            customer.addCall(callDetails);
        }catch(RecordNotFoundException ignored) {
            throw new CustomerNotFoundException();
        }
    }

}
