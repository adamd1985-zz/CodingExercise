package exercise.addressbook.datarepositories;

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

}
