package com.example.pokerratingservice.service;

import com.example.pokerratingservice.repository.CustomerRepository;
import com.example.pokerratingservice.security.CustomerDetails;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Service
public class CustomerDetailsService implements UserDetailsService {
    final CustomerRepository customerRepository;
    final CustomerDetails customerDetails;

    /*@Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Customer> customer = customerRepository.findCustomerByEmail(email);
        if(customer.isEmpty())
            throw new UsernameNotFoundException("username not found");
        return new CustomerDetails(customer.get());
    } */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        return customerDetails;
    }
}
