package com.infina.lifotrade.services.common;

import java.util.Arrays;
import java.util.List;

public class MessagesUtility {
	public static List<String> convertToMessages(String messages) {

		String[] errorArray = messages.substring(1, messages.length() - 1).split(", ");
		List<String> errorList = Arrays.asList(errorArray);
		return errorList;
	}
}
