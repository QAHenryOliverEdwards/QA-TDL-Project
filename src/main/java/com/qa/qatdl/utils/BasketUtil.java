package com.qa.qatdl.utils;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.beans.BeanUtils.copyProperties;

public class BasketUtil {

    private static String[] getNullPropName(Object src) {
        final BeanWrapper wrapper = new BeanWrapperImpl(src);
        Set<String> propName = new HashSet<>();

        for (PropertyDescriptor descriptor : wrapper.getPropertyDescriptors()) {
            if (wrapper.getPropertyValue(descriptor.getName()) == null) {
                propName.add(descriptor.getName());
            }
        }
        return propName.toArray(new String[propName.size()]);
    }

    public static void mergeNotNull(Object source, Object target) {
        copyProperties(source, target, getNullPropName(source));
    }

    private BasketUtil() {
        throw new IllegalStateException("Utility class");
    }
}
