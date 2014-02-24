/**
 * 
 */
package exercise.addressbook.tos;

import java.io.Serializable;

/**
 * A transfer object to be read by the browser
 * 
 * @author adam
 * @version 1
 */
public class QueryResponse implements Serializable {

	private static final long serialVersionUID = -2055692855032440302L;

	private Boolean error;
	private String msg;
	private Object value;

	public Boolean getError() {
		return error;
	}

	public void setError(Boolean error) {
		this.error = error;
	}

	public String getMsg() {
		return msg;
	}

	public void setErrorMsg(String msg) {
		this.msg = msg;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

}
