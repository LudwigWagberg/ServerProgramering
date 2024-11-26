package com.yrgo.dataaccess;


import com.yrgo.domain.Call;
import com.yrgo.domain.Customer;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository
public class CustomerDaoJpaImpl implements CustomerDao{


    @PersistenceContext
    private EntityManager em;


    public void create(Customer customer) {
        em.persist(customer);
    }


    public Customer getById(String customerId) throws RecordNotFoundException {
        return em.find(Customer.class, customerId);
    }


    public List<Customer> getByName(String name) {
        return em.createQuery("select customer from Customer as customer where customer.companyName=:name", Customer.class)
                .setParameter("name", name).getResultList();
    }


    public void update(Customer customerToUpdate) throws RecordNotFoundException {
        em.merge(customerToUpdate);
    }


    public void delete(Customer oldCustomer) throws RecordNotFoundException {
        Customer customer = em.find(Customer.class, oldCustomer.getCustomerId());
        em.remove(customer);
    }


    public List<Customer> getAllCustomers() {
        return em.createQuery("select customer from Customer as customer", Customer.class).getResultList();
    }


    public Customer getFullCustomerDetail(String customerId) throws RecordNotFoundException {


        return em.createQuery("select customer from Customer as customer left join fetch customer.calls where customer.customerId=:customerId", Customer.class)
                .setParameter("customerId", customerId)
                .getSingleResult();
    }


    public void addCall (Call newCall, String customerId) throws RecordNotFoundException {
        Customer customer = em.find(Customer.class, customerId);
        customer.addCall(newCall);
    }
}
