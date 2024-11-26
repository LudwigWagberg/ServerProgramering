package com.yrgo.services.customers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.yrgo.domain.Call;
import com.yrgo.domain.Customer;

public class CustomerManagementMockImpl implements CustomerManagementService {
	private HashMap<String,Customer> customerMap;

	public CustomerManagementMockImpl() {
		customerMap = new HashMap<String,Customer>();
		customerMap.put("OB74", new Customer("OB74" ,"Fargo Ltd", "some notes"));
		customerMap.put("NV10", new Customer("NV10" ,"North Ltd", "some other notes"));
		customerMap.put("RM210", new Customer("RM210" ,"River Ltd", "some more notes"));
	}


	@Override
	public void newCustomer(Customer newCustomer) {
		customerMap.put(newCustomer.getCustomerId(), newCustomer);
	}
	@Override
	public void updateCustomer(Customer changedCustomer) {
		Customer customer = customerMap.get(changedCustomer.getCustomerId());

		customer.setCustomerId(customer.getCustomerId());
		customer.setCompanyName(customer.getCompanyName());
		customer.setNotes(customer.getNotes());
	}

	@Override
	public void deleteCustomer(Customer oldCustomer) {
		customerMap.remove(oldCustomer.getCustomerId());

	}

	@Override
	public Customer findCustomerById(String customerId) throws CustomerNotFoundException {
		Customer customer = customerMap.get(customerId);
		if (customer == null) {
			throw new CustomerNotFoundException();
		}
		return customer;
	}

	@Override
	public List<Customer> findCustomersByName(String name) {
		ArrayList <Customer> filteredCustomers = new ArrayList<>();

		for (Customer customers : customerMap.values()) {
			if (customers.getCompanyName().equals(name)) {
				filteredCustomers.add(customers);
			}
		}
		return filteredCustomers;
	}

	@Override
	public List<Customer> getAllCustomers() {
		return new ArrayList<>(customerMap.values());
	}

	@Override
	public Customer getFullCustomerDetail(String customerId) throws CustomerNotFoundException {
		return findCustomerById(customerId);
	}

	@Override
	public void recordCall(String customerId, Call callDetails) throws CustomerNotFoundException {
		//First find the customer
		Customer customer = findCustomerById(customerId);

		//Call the addCall on the customer
		customer.addCall(callDetails);
	}

}
