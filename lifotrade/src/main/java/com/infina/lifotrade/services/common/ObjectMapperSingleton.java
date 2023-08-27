package com.infina.lifotrade.services.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class ObjectMapperSingleton {
	private static ObjectMapper instance;

	private ObjectMapperSingleton() {
		// Singleton sınıfın dışından oluşturulmasını engellemek için
	}

	public static ObjectMapper getInstance() {
		if (instance == null) {
			synchronized (ObjectMapperSingleton.class) {
				if (instance == null) {
					ObjectMapper objectMapper = new ObjectMapper();
			        objectMapper.registerModule(new JavaTimeModule()); // JSR-310 modülü eklendi
					instance = objectMapper;
				}
			}

		}
		return instance;
	}
}
