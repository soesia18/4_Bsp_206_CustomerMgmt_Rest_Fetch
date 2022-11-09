package at.htlkaindorf.custmgmt.db;

import at.htlkaindorf.custmgmt.beans.Customer;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class CustomerDB {
    private static CustomerDB theInstance;
    private final List<Customer> customerList;
    public CustomerDB instance;

    private CustomerDB() {
        customerList = new ArrayList<>();

        customerList.add(new Customer(1, "Josef GmbH", "Tulwitz", 20000000));
        customerList.add(new Customer(2, "Hansi KG", "Feldbach", 700));
        customerList.add(new Customer(3, "Daimler AG", "Stuttgart", 100000000));
        customerList.add(new Customer(4, "Audi GmbH und Co KG", "Ingolstadt", 250000000));
    }

    public synchronized static CustomerDB getInstance() {
        if (theInstance == null) theInstance = new CustomerDB();
        return theInstance;
    }

    public void addCustomer(Customer customer) throws KeyAlreadyExistsException {
        if (findCustomerById(customer.getId()).isPresent()) throw new KeyAlreadyExistsException();
        else customerList.add(customer);
    }

    public Optional<Customer> findCustomerById(int id) {
        return customerList.stream().filter(customer -> customer.getId() == id).findFirst();
    }

    public Customer getCustomer(int id) throws NoSuchElementException {

//        Optional<Customer> optionalCustomer = findCustomerById(id);
//
//        if (optionalCustomer.isPresent()) {
//            return optionalCustomer.get();
//        } else {
//            throw new NoSuchElementException();
//        }

        return findCustomerById(id).get();
    }

    public List<Customer> getAllCustomer () {
        return customerList;
    }

    public Customer removeCustomer(int id) throws NoSuchElementException{
        Customer customer = getCustomer(id);
        customerList.remove(customer);

        return customer;
    }

    public void replaceCustomer(Customer customer) throws NoSuchElementException {
        Customer oldCustomer = getCustomer(customer.getId());

        customerList.remove(oldCustomer);
        customerList.add(customer);
    }

}
