package com.infina.lifotrade.services.common;

import org.modelmapper.ModelMapper;

public class ModelMapperSingleton {
	private static ModelMapper instance;

	private ModelMapperSingleton() {
		// Singleton sınıfın dışından oluşturulmasını engellemek için
	}

	public static ModelMapper getInstance() {
		if (instance == null) {
			synchronized (ModelMapper.class) {
				if (instance == null) {
					ModelMapper modelMapper = new ModelMapper();
					instance = modelMapper;
				}
			}

		}
		return instance;
	}
}
