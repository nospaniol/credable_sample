package com.credable.repository;

import com.credable.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer,Long> {
    Customer findByCustomerId(Long customerId);
    Customer findByCustomerNumber(String customerId);
}
