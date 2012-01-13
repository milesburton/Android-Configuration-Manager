package com.mb.android.preferences.manager;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class StringCollectionUtils {
	public static String join(Collection<String> stringList, String joinCharacter) {

		StringBuilder sb = new StringBuilder();
		for (String str : stringList) {
			if (isNullOrEmpty(str))
				continue;
			sb.append(str);
			sb.append(joinCharacter);
		}
		String result = sb.toString();
		if (result.length() > 0) {
			return result.substring(0, result.length() - 1);
		}
		return result;
	}

	public static Set<String> convertToSet(String characterSeperatedList, String character) {

		Set<String> stringSet = new HashSet<String>();
		String[] stringArray = characterSeperatedList.split(character);

		for (String id : stringArray)
			if (!isNullOrEmpty(id))
				stringSet.add(id);

		return stringSet;
	}

	private static boolean isNullOrEmpty(String str) {
		return str == null || "".equals(str);
	}
}
