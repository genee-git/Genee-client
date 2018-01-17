package com.genee.utils;

import java.util.HashMap;
import java.util.Map;

import com.genee.event.EventBus;
import com.genee.service.Service;
import com.genee.utils.logging.Logger;
import com.genee.utils.logging.Logger.TYPE;

public class ServiceExecutor {

	private static Map<Class<?>, Service> serviceMap = new HashMap<>();

	@SafeVarargs
	public static void registerServices(EventBus eventBus, Class<?>... services) {
		for (Class<?> entry : services) {
			Service service;
			try {
				service = (Service) entry.newInstance();
				service.setEventBus(eventBus);
				service.register(eventBus);
				eventBus.addService(service);
				serviceMap.put(entry, service);
			} catch (InstantiationException | IllegalAccessException e) {
				Logger.log(TYPE.SERVERE, "ServiceExecutor falied to register one of the service :" + e.getMessage());
			}
		}
	}

	@SafeVarargs
	public static void startupServices(Class<?>... services) {
		for (Class<?> entry : services) {
			if (serviceMap.containsKey(entry)) {
				Service service = serviceMap.get(entry);
				try {
					service.start();
				} catch (Exception e) {
					Logger.log(TYPE.SERVERE, "ServiceExecutor while startupServices, Exception in " + entry.getSimpleName()
							+ " startup, " + e.getMessage());
				}
			} else {
				Logger.log(TYPE.SERVERE,
						"ServiceExecutor while startupServices, no such service" + entry.getSimpleName() + " is registerd");
			}
		}
	}

}
