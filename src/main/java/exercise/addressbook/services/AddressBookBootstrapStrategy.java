package exercise.addressbook.services;

public interface AddressBookBootstrapStrategy {

	/**
	 * Boots data regarding the addressbook
	 * 
	 * @throws Exception
	 */
	public void boot() throws Exception;
}
