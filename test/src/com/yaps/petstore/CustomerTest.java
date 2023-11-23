package com.yaps.petstore;

import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * This class tests the Customer class
 */
public final class CustomerTest extends TestCase {

    public CustomerTest(final String s) {
        super(s);
    }

    public static TestSuite suite() {
        return new TestSuite(CustomerTest.class);
    }

    //==================================
    //=            Test cases          =
    //==================================
    /**
     * This test tries to find an object with a invalid identifier.
     */
    public void testFindCustomerWithInvalidValues() throws Exception {

        // Finds an object with a unknown identifier
        final int id = getUniqueId();
        try {
            findCustomer(id);
            fail("Object with unknonw id should not be found");
        } catch (CustomerNotFoundException e) {
        }

        // Finds an object with an empty identifier
        try {
            new Customer().find(new String());
            fail("Object with empty id should not be found");
        } catch (CustomerCheckException e) {
        }

        // Finds an object with a null identifier
        try {
            new Customer().find(null);
            fail("Object with null id should not be found");
        } catch (CustomerCheckException e) {
        }
    }

    /**
     * This method ensures that creating an object works. It first finds the object,
     * makes sure it doesn't exist, creates it and checks it then exists.
     */
    public void testCreateCustomer() throws Exception {
        final int id = getUniqueId();
        Customer customer = null;

        // Ensures that the object doesn't exist
        try {
            customer = findCustomer(id);
            fail("Object has not been created yet it shouldn't be found");
        } catch (CustomerNotFoundException e) {
        }

        // Creates an object
        createCustomer(id);

        // Ensures that the object exists
        try {
            customer = findCustomer(id);
        } catch (CustomerNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Checks that it's the right object
        checkCustomer(customer, id);

        // Creates an object with the same identifier. An exception has to be thrown
        try {
            createCustomer(id);
            fail("An object with the same id has already been created");
        } catch (CustomerDuplicateKeyException e) {
        }

        // Cleans the test environment
        removeCustomer(id);

        try {
            findCustomer(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (CustomerNotFoundException e) {
        }
    }

    /**
     * This test tries to create an object with a invalid values.
     */
    public void testCreateCustomerWithInvalidValues() throws Exception {

        // Creates an object with an empty values
        try {
            final Customer customer = new Customer(new String(), new String(), new String());
            customer.create();
            fail("Object with empty values should not be created");
        } catch (CustomerCheckException e) {
        }

        // Creates an object with an null values
        try {
            final Customer customer = new Customer(null, null, null);
            customer.create();
            fail("Object with null values should not be created");
        } catch (CustomerCheckException e) {
        }
    }

    /**
     * This test tries to update an unknown object.
     */
    public void testUpdateUnknownCustomer() throws Exception {
        // Updates an unknown object
        try {
            new Customer().update();
            fail("Updating a none existing object should break");
        } catch (CustomerCheckException e) {
        }
    }

    /**
     * This test tries to update an object with a invalid values.
     */
    public void testUpdateCustomerWithInvalidValues() throws Exception {

        // Creates an object
        final int id = getUniqueId();
        createCustomer(id);

        // Ensures that the object exists
        Customer customer = null;
        try {
            customer = findCustomer(id);
        } catch (CustomerNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Updates the object with empty values
        try {
            customer.setFirstname(new String());
            customer.setLastname(new String());
            customer.update();
            fail("Updating an object with empty values should break");
        } catch (CustomerCheckException e) {
        }

        // Updates the object with null values
        try {
            customer.setFirstname(null);
            customer.setLastname(null);
            customer.update();
            fail("Updating an object with null values should break");
        } catch (CustomerCheckException e) {
        }

        // Ensures that the object still exists
        try {
            customer = findCustomer(id);
        } catch (CustomerNotFoundException e) {
            fail("Object should be found");
        }

        // Cleans the test environment
        removeCustomer(id);

        try {
            findCustomer(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (CustomerNotFoundException e) {
        }
    }

    /**
     * This test make sure that updating an object success
     */
    public void testUpdateCustomer() throws Exception {
        final int id = getUniqueId();

        // Creates an object
        createCustomer(id);

        // Ensures that the object exists
        Customer customer = null;
        try {
            customer = findCustomer(id);
        } catch (CustomerNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Checks that it's the right object
        checkCustomer(customer, id);

        // Updates the object with new values
        updateCustomer(customer, id + 1);

        // Ensures that the object still exists
        Customer customerUpdated = null;
        try {
            customerUpdated = findCustomer(id);
        } catch (CustomerNotFoundException e) {
            fail("Object should be found");
        }

        // Checks that the object values have been updated
        checkCustomer(customerUpdated, id + 1);

        // Cleans the test environment
        removeCustomer(id);

        try {
            findCustomer(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (CustomerNotFoundException e) {
        }
    }

    /**
     * This test ensures that the system cannont remove an unknown object
     */
    public void testDeleteUnknownCustomer() throws Exception {
        // Removes an unknown object
        try {
            new Customer().remove();
            fail("Deleting an unknown object should break");
        } catch (CustomerCheckException e) {
        }
    }

    //==================================
    //=         Private Methods        =
    //==================================
    private Customer findCustomer(final int id) throws CustomerFinderException, CustomerCheckException {
        final Customer customer = new Customer();
        try {
        customer.find("custo" + id);
        } catch(CustomerNotFoundException e)
		{
        	throw new CustomerNotFoundException();
		}
        return customer;
    }

    private void createCustomer(final int id) throws CustomerCreateException, CustomerCheckException {
        final Customer customer = new Customer("custo" + id, "firstname" + id, "lastname" + id);
        customer.setCity("city" + id);
        customer.setCountry("cnty" + id);
        customer.setState("state" + id);
        customer.setStreet1("street1" + id);
        customer.setStreet2("street2" + id);
        customer.setTelephone("phone" + id);
        customer.setZipcode("zip" + id);
        customer.create();
    }

    private void updateCustomer(final Customer customer, final int id) throws CustomerUpdateException, CustomerCheckException {
        customer.setFirstname("firstname" + id);
        customer.setLastname("lastname" + id);
        customer.setCity("city" + id);
        customer.setCountry("cnty" + id);
        customer.setState("state" + id);
        customer.setStreet1("street1" + id);
        customer.setStreet2("street2" + id);
        customer.setTelephone("phone" + id);
        customer.setZipcode("zip" + id);
        customer.update();
    }

    private void removeCustomer(final int id) throws CustomerCheckException, CustomerRemoveException {
        final Customer customer = new Customer("custo" + id);
        customer.remove();
    }

    private void checkCustomer(final Customer customer, final int id) {
        assertEquals("firstname", "firstname" + id, customer.getFirstname());
        assertEquals("lastname", "lastname" + id, customer.getLastname());
        assertEquals("city", "city" + id, customer.getCity());
        assertEquals("country", "cnty" + id, customer.getCountry());
        assertEquals("state", "state" + id, customer.getState());
        assertEquals("street1", "street1" + id, customer.getStreet1());
        assertEquals("street2", "street2" + id, customer.getStreet2());
        assertEquals("telephone", "phone" + id, customer.getTelephone());
        assertEquals("zipcode", "zip" + id, customer.getZipcode());
    }

    private int getUniqueId() {
        return (int) (Math.random() * 100000);
    }
}
