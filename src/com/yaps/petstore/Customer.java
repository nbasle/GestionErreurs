package com.yaps.petstore;

import java.io.Serializable;

/**
 * This class represents a customer for the YAPS company.
 */
public final class Customer implements Serializable {

    // ======================================
    // =             Attributes             =
    // ======================================
    private String _id;
    private String _firstname;
    private String _lastname;
    private String _telephone;
    private String _street1;
    private String _street2;
    private String _city;
    private String _state;
    private String _zipcode;
    private String _country;

    private final transient HashmapAccessor _dao = new HashmapAccessor();

    // ======================================
    // =            Constructors            =
    // ======================================
    public Customer() {
    }

    public Customer(final String id) {
        _id = id;
    }

    public Customer(final String id, final String firstname, final String lastname) {
        _id = id;
        _firstname = firstname;
        _lastname = lastname;
    }

    // ======================================
    // =           Business methods         =
    // ======================================

    /**
     * Given a customer id this methods loads all the attributes of a Customer object.
     * The customer id cannot be null or empty.
     *
     * @param id customer identifier
     * @throws CustomerNotFoundException thrown if the customer id is not found
     * @throws CustomerFinderException   thrown if the customer id is invalid or a system failure is occurs
     * @throws CustomerCheckException    is thrown if invalid data is found
     */
    public void find(final String id) throws CustomerFinderException, CustomerCheckException {

        // Checks data integrity
        checkId(id);

        // Uses the DAO to access the persistent layer
        try {
        final Customer temp = _dao.select(id);
        
        // Sets data to current object
        _id = temp.getId();
        _firstname = temp.getFirstname();
        _lastname = temp.getLastname();
        _telephone = temp.getTelephone();
        _street1 = temp.getStreet1();
        _street2 = temp.getStreet2();
        _city = temp.getCity();
        _state = temp.getState();
        _zipcode = temp.getZipcode();
        _country = temp.getCountry();
        }
        catch(CustomerNotFoundException e){
        	throw new CustomerNotFoundException();
        }
    }

    /**
     * When all the Customer attibutes are set, calling this method will save the object
     * into a hashmap. This method checks that the mandatory attributes
     * are set (customer id, first name and last name).
     *
     * @throws CustomerDuplicateKeyException is thrown if a customer with the same id
     *                                       already exists in the system
     * @throws CustomerCreateException       is thrown if a mandatory attribute is not set
     *                                       or a system failure is occurs
     * @throws CustomerCheckException        is thrown if invalid data is found
     */
    public void create() throws CustomerCreateException, CustomerCheckException {

        // Checks data integrity
        checkData();

        // Uses the DAO to access the persistent layer
        _dao.insert(this);
    }

    /**
     * When all the Customer attibutes are set, calling this method will update the object
     * into a hashmap. This method checks that the mandatory attributes
     * are set (customer id, first name and last name).
     *
     * @throws CustomerUpdateException is thrown if a mandatory attribute is not set
     *                                 or a system failure is occurs
     * @throws CustomerCheckException  is thrown if invalid data is found
     */
    public void update() throws CustomerUpdateException, CustomerCheckException {

        // Checks data integrity
        checkData();

        try {

            // Uses the DAO to access the persistent layer
            _dao.update(this);

        } catch (CustomerNotFoundException e) {
            throw new CustomerUpdateException("Cannot update customer. Customer not found");
        } catch (CustomerDuplicateKeyException e) {
            throw new CustomerUpdateException("Cannot update customer. Customer already exists");
        }
    }

    /**
     * Calling this method will remove the object from the hashmap.
     * The customer id cannot be null or empty.
     *
     * @throws CustomerRemoveException thrown if the customer id is invalid or a system failure is occurs
     * @throws CustomerCheckException          is thrown if invalid data is found
     */
    public void remove() throws CustomerRemoveException, CustomerCheckException {

        // Checks data integrity
        checkId(_id);

        try {

            // Uses the DAO to access the persistent layer
            _dao.delete(_id);

        } catch (CustomerNotFoundException e) {
            throw new CustomerRemoveException("Cannot remove the customer. Customer not found");
        }
    }

    // ======================================
    // =           Private methods          =
    // ======================================
    /**
     * This method checks the integrity of the object data.
     *
     * @throws CustomerCheckException if data is invalide
     */
    private void checkData() throws CustomerCheckException {
        checkId(_id);
        if (_firstname == null || "".equals(_firstname))
            throw new CustomerCheckException("Invalid first name");
        if (_lastname == null || "".equals(_lastname))
            throw new CustomerCheckException("Invalid last name");
    }

    /**
     * This method checks that the identifier is valid.
     *
     * @param id identifier to check
     * @throws CustomerCheckException if the id is invalid
     */
    private void checkId(final String id) throws CustomerCheckException {
        if (id == null || "".equals(id))
            throw new CustomerCheckException("Invalid customer id");
    }

    // ======================================
    // =         Getters and Setters        =
    // ======================================
    public String getId() {
        return _id;
    }

    public String getFirstname() {
        return _firstname;
    }

    public void setFirstname(final String firstname) {
        _firstname = firstname;
    }

    public String getLastname() {
        return _lastname;
    }

    public void setLastname(final String lastname) {
        _lastname = lastname;
    }

    public String getTelephone() {
        return _telephone;
    }

    public void setTelephone(final String telephone) {
        _telephone = telephone;
    }

    public String getStreet1() {
        return _street1;
    }

    public void setStreet1(final String street1) {
        _street1 = street1;
    }

    public String getStreet2() {
        return _street2;
    }

    public void setStreet2(final String street2) {
        _street2 = street2;
    }

    public String getCity() {
        return _city;
    }

    public void setCity(final String city) {
        _city = city;
    }

    public String getState() {
        return _state;
    }

    public void setState(final String state) {
        _state = state;
    }

    public String getZipcode() {
        return _zipcode;
    }

    public void setZipcode(final String zipcode) {
        _zipcode = zipcode;
    }

    public String getCountry() {
        return _country;
    }

    public void setCountry(final String country) {
        _country = country;
    }

    public String toString() {
        final StringBuffer buf = new StringBuffer();
        buf.append("\n\tCustomer {");
        buf.append("\n\t\tId=").append(_id);
        buf.append("\n\t\tFirst Name=").append(_firstname);
        buf.append("\n\t\tLast Name=").append(_lastname);
        buf.append("\n\t\tTelephone=").append(_telephone);
        buf.append("\n\t\tStreet 1=").append(_street1);
        buf.append("\n\t\tStreet 2=").append(_street2);
        buf.append("\n\t\tCity=").append(_city);
        buf.append("\n\t\tState=").append(_state);
        buf.append("\n\t\tZipcode=").append(_zipcode);
        buf.append("\n\t\tCountry=").append(_country);
        buf.append("\n\t}");
        return buf.toString();
    }
}
