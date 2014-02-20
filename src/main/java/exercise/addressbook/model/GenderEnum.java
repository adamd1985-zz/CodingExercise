package exercise.addressbook.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Gender constants.
 * 
 * @author adam
 * @version 1
 */
public enum GenderEnum {
	MALE("Male"), FEMALE("Female");

	/** For fast string to enum conversion. */
	static Map<String, GenderEnum> genderMap = new HashMap<String, GenderEnum>();

	static {
		for (GenderEnum gender : GenderEnum.values()) {
			genderMap.put(gender.getGenderStr(), gender);
		}
	}

	private String genderStr;

	// ========================================================================

	GenderEnum(String genderStr) {
		this.genderStr = genderStr;
	}

	public String getGenderStr() {
		return genderStr;
	}

	/**
	 * Retrieves the correct enum from string.
	 * 
	 * @param genderStr
	 * @return {@link GenderEnum}
	 */
	public static GenderEnum getGenderForString(String genderStr) {
		return genderMap.get(genderStr);
	}
}
