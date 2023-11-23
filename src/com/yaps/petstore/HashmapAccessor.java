package com.yaps.petstore;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * This class uses a HashTable to store customer objects in it and serializes the hashmap.
 */
public final class HashmapAccessor {

    // ======================================
    // =             Attributes             =
    // ======================================
    private static Map _hashmap = new HashMap();
    private static final String HASHTABLE_FILE_NAME = "persistentCustomer.ser";

    // ======================================
    // =            Constructors            =
    // ======================================
    public HashmapAccessor() {
    }
    // ======================================
    // =           Business methods         =
    // ======================================
    /**
     * This method gets a Customer object from the HashMap.
     *
     * @param id Customer identifier to be found in the hastable
     * @return Customer the customer object with all its attributs set
     * @throws CustomerNotFoundException is thrown if the customer id not found in the hastable
     * @throws HashmapAccessException       is thrown if there's a persistent problem
     */
    public Customer select(final String id) throws CustomerNotFoundException {

        // Loads the Hastable
        loadHastable();

        // If the given id doesn't exist we throw a CustomerNotFoundException
        if (!_hashmap.containsKey(id)) {
            throw new CustomerNotFoundException();
        }

        return (Customer) _hashmap.get(id);
    }

    /**
     * This method inserts a Customer object into the HashMap and serializes the Hastable on disk.
     *
     * @param customer Customer Object to be inserted into the hastable
     * @throws CustomerDuplicateKeyException is thrown when an identical object is already in the hastable
     * @throws HashmapAccessException           is thrown if there's a persistent problem
     */
    public void insert(final Customer customer) throws CustomerDuplicateKeyException {

        // Loads the Hastable with all the objects
        loadHastable();

        // If the given id already exists we cannot insert the new Customer
        if (_hashmap.containsKey(customer.getId())) {
            throw new CustomerDuplicateKeyException();
        }

        // Puts the object into the hastable
        _hashmap.put(customer.getId(), customer);

        // Saves the hastable with all the objects
        saveHastableOnDisk();
    }

    /**
     * This method updates a Customer object of the HashMap and serializes the Hastable on disk.
     *
     * @param customer Customer to be updated from the hastable
     * @throws CustomerNotFoundException     is thrown if the customer id not found in the hastable
     * @throws CustomerDuplicateKeyException is thrown when an identical object is already in the hastable
     * @throws HashmapAccessException           is thrown if there's a persistent problem
     */
    public void update(final Customer customer) throws CustomerNotFoundException, CustomerDuplicateKeyException {
        remove(customer.getId());
        insert(customer);
    }

    /**
     * This method deletes a Customer object from the HashMap and serializes the Hastable on disk.
     *
     * @param id Customer identifier to be deleted from the hastable
     * @throws CustomerNotFoundException is thrown if there's a persistent problem
     * @throws HashmapAccessException       is thrown if there's a persistent problem
     */
    public void remove(final String id) throws CustomerNotFoundException {

        // Loads the Hastable
        loadHastable();

        // If the given id does'nt exist we cannot remove the Customer from the hashmap
        if (!_hashmap.containsKey(id)) {
            throw new CustomerNotFoundException();
        }

        // The object is removed from the hastable
        _hashmap.remove(id);

        // Saves the hastable
        saveHastableOnDisk();
    }

    // ======================================
    // =            Private methods         =
    // ======================================
    // This method loads the hastable with the file saved on disk
    private static void loadHastable() throws HashmapAccessException {

        try {
            final FileInputStream fin = new FileInputStream(HASHTABLE_FILE_NAME);
            final ObjectInputStream in = new ObjectInputStream(fin);

            // Reads the values from the file
            _hashmap = (HashMap) in.readObject();

            in.close();
        } catch (FileNotFoundException e) {
            // The first call to this method, the file doesn't exist so nothing is done
        } catch (Exception e) {
            throw new HashmapAccessException("Cannot load " + HASHTABLE_FILE_NAME + " !!!", e);
        }
    }

    // This method saves the hastable on disk
    private static void saveHastableOnDisk() throws HashmapAccessException {

        try {
            final FileOutputStream fout = new FileOutputStream(HASHTABLE_FILE_NAME);
            final ObjectOutputStream out = new ObjectOutputStream(fout);

            // Saves the hashmap to disk
            out.writeObject(_hashmap);

            out.close();
        } catch (IOException e) {
            throw new HashmapAccessException("Cannot save " + HASHTABLE_FILE_NAME + " !!!", e);
        }
    }
	/**
	 * @param _id
	 */
	public void delete(String _id) throws CustomerNotFoundException{
		// TODO Auto-generated method stub
		remove(_id);
	}
}
