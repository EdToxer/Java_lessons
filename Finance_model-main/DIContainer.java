package com.finance.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class DIContainer {
    private Map<Class<?>, Supplier<?>> registrations = new HashMap<>();
    private Map<Class<?>, Object> singletons = new HashMap<>();

    public <T> void registerSingleton(Class<T> type, Supplier<T> supplier) {
        registrations.put(type, () -> {
            if (!singletons.containsKey(type)) {
                singletons.put(type, supplier.get());
            }
            return singletons.get(type);
        });
    }

    public <T> T resolve(Class<T> type) {
        Supplier<?> supplier = registrations.get(type);
        if (supplier == null) {
            throw new RuntimeException("No registration for type: " + type.getName());
        }
        return type.cast(supplier.get());
    }
}
