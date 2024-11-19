package com.yrgo.dataaccess;

import com.yrgo.domain.Call;
import com.yrgo.domain.Customer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class CustomerDaoJdbcTemplateImpl implements CustomerDao {


    private static final String DELETE_SQL = "DELETE FROM CUSTOMER WHERE CUSTOMER_ID=?";
    private static final String UPDATE_SQL = "UPDATE CUSTOMER SET COMPANY_NAME=?, EMAIL=?, TELEPHONE=?, NOTES=? WHERE CUSTOMER-ID=?";
    private static final String INSERT_SQL = "INSERT INTO CUSTOMER (CUSTOMER_ID, COMPANY_NAME, EMAIL, TELEPHONE, NOTES) VALUES (?,?,?,?,?)";

    private static final String GET_CUSTOMER = "SELECT * FROM CUSTOMER WHERE CUSTOMER_ID=?";
    private static final String GET_COMPANY_NAME = "SELECT * FROM CUSTOMER WHERE COMPANY_NAME=?";
    private static final String GET_CUSTOMERS = "SELECT * FROM CUSTOMER";
    private static final String GET_CALLS = "SELECT * FROM TBL_CALL ";

    private static final String CREATE_TBL_CUSTOMER = "CREATE TABLE CUSTOMER(CUSTOMER_ID VARCHAR(50), COMPANY_NAME VARCHAR(255), EMAIL VARCHAR(255),TELEPHONE INTEGER,NOTES VARCHAR(500), PRIMARY KEY (CUSTOMER_ID))";

    private static final String CREATE_TBL_CALL = "CREATE TABLE TBL_CALL(ID INTEGER, TIMEANDDATE TIMESTAMP, NOTES VARCHAR(500), CUSTOMER_ID VARCHAR(50),PRIMARY KEY (ID),FOREIGN KEY (CUSTOMER_ID) REFERENCES CUSTOMER(CUSTOMER_ID))";


    private JdbcTemplate template;

    public CustomerDaoJdbcTemplateImpl(JdbcTemplate template){
        this.template = template;
    }

    private void createTables()	{
        try{
            this.template.update(CREATE_TBL_CUSTOMER);

            this.template.update(CREATE_TBL_CALL);

        }catch (org.springframework.jdbc.BadSqlGrammarException e){
            System.out.println("Assuming the Customer table exists");
        }
    }

    public void create(Customer newCustomer) {
        template.update(INSERT_SQL,newCustomer.getCustomerId(),newCustomer.getCompanyName(), newCustomer.getEmail(),
                newCustomer.getTelephone(), newCustomer.getNotes());
    }

    public Customer getById(String customerId) throws RecordNotFoundException {
        return this.template.queryForObject(GET_CUSTOMER, new CustomerRowMapper(), customerId);
    }

    public List<Customer> getByName(String name) {
        return this.template.query(GET_COMPANY_NAME, new CustomerRowMapper(), name);
    }

    public void update(Customer customerToUpdate) throws RecordNotFoundException {
        template.update(UPDATE_SQL,customerToUpdate.getCompanyName(), customerToUpdate.getEmail(),
                customerToUpdate.getTelephone(), customerToUpdate.getNotes());
    }

    public void delete(Customer oldCustomer) throws RecordNotFoundException {
        this.template.update(DELETE_SQL, oldCustomer.getCustomerId());
    }

    public List<Customer> getAllCustomers() {
        return this.template.query(GET_CUSTOMERS, new CustomerRowMapper());
    }

    public Customer getFullCustomerDetail(String customerId) throws RecordNotFoundException {
        Customer customer = template.queryForObject(GET_CUSTOMER, new CustomerRowMapper(), customerId);
        List<Call> call = template.query(GET_CALLS, new CallRowMapper(), customerId);

        assert customer != null;
        customer.setCalls(call);

        return customer;
    }

    public void addCall (Call newCall, String customerId) throws RecordNotFoundException {
        Customer customer = template.queryForObject(GET_CUSTOMER, new CustomerRowMapper(), customerId);
        Call call = template.queryForObject(GET_CALLS, new CallRowMapper(), customerId);

        assert customer != null;
        customer.addCall(call);
    }


}
class CustomerRowMapper implements RowMapper<Customer> {
    public Customer mapRow(ResultSet rs, int arg1) throws SQLException {
        String customerId= rs.getString(1);
        String companyName = rs.getString(2);
        String email = rs.getString(3);
        String telephone = rs.getString(4);
        String notes = rs.getString(5);

        return new Customer("" + customerId, companyName, email, telephone, notes);
    }
}

class CallRowMapper implements RowMapper<Call> {
    public Call mapRow(ResultSet rs, int arg1) throws SQLException {
        int id= rs.getInt("ID");
        Timestamp timestamp = rs.getTimestamp("TIMEANDDATE");
        Date timeanddate = new Date(timestamp.getTime());
        String notes = rs.getString("NOTES");
        int customerId = rs.getInt("CUSTOMER_ID");

        Call call = new Call(notes, timeanddate);
        call.setId(id);
        call.setCustomerId(customerId);

        return call;
    }
}



