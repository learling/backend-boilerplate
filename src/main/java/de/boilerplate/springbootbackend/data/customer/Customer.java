package de.boilerplate.springbootbackend.data.customer;

import java.time.LocalDateTime;

public class Customer {

    private Long id;
    private String username, email;
    private LocalDateTime updated;

    public Customer(CustomerEntity customerEntity) {
        this.id = customerEntity.getId();
        this.username = customerEntity.getUsername();
        this.email = customerEntity.getEmail();
        this.updated = customerEntity.getUpdated();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

}
