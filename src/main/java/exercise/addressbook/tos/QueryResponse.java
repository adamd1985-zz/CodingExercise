/**
 * 
 */
package exercise.addressbook.tos;

import java.io.Serializable;
import java.util.List;

import exercise.addressbook.model.Contact;

/**
 * A transfer object to be read by the browser
 * 
 * @author adam
 * @version 1
 */
public class QueryResponse implements Serializable{

	private static final long serialVersionUID = -2055692855032440302L;
	
	private String type;
	private String msg;
	private List<Contact> contacts;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public List<Contact> getContacts() {
		return contacts;
	}
	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

	
}
