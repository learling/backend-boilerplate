package de.boilerplate.springbootbackend.data.customer.token;

import de.boilerplate.springbootbackend.data.customer.Customer;

public class CustomerToken {

    private Customer customer;
    private String token;

    public CustomerToken(Customer customer, String token) {
        this.customer = customer;
        this.token = token;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
