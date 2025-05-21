package di;

import annotations.AutoInjectable;

import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.util.Properties;

public class Injector {
    private Properties properties = new Properties();

    public Injector(String configPath) {
        try {
            properties.load(new FileInputStream(configPath));
        } catch (Exception e) {
            throw new RuntimeException("Failed to load properties file", e);
        }
    }

    public <T> T inject(T object) {
        Class<?> clazz = object.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(AutoInjectable.class)) {
                Class<?> fieldType = field.getType();
                String implClassName = properties.getProperty(fieldType.getName());
                if (implClassName != null) {
                    try {
                        Object implInstance = Class.forName(implClassName).getDeclaredConstructor().newInstance();
                        field.setAccessible(true);
                        field.set(object, implInstance);
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to inject dependency: " + implClassName, e);
                    }
                } else {
                    throw new RuntimeException("Implementation not found for: " + fieldType.getName());
                }
            }
        }
        return object;
    }
}