package exercise.addressbook.datarepositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import exercise.addressbook.model.Contact;

/**
 * JPA repo for accessing contacts.
 * 
 * @author adam
 * @version 1
 */
@Component
public interface ContactRepository extends JpaRepository<Contact, Long> {
	/**
	 * Custom to search by gender.
	 * 
	 * @param genderStr
	 * @return
	 */
	public List<Contact> findByGender(String genderStr);

	/**
	 * Custom to search all names within collection.
	 * @param names
	 * @return
	 */
	public List<Contact> findByNameIn(List<String> names);
}
