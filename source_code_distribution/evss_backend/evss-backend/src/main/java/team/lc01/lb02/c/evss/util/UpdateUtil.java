package team.lc01.lb02.c.evss.util;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * update util (ignore field which is null)
 */
public class UpdateUtil {
    /**
     * find all source's null properties and ignore them when copy
     *
     * @param source
     * @param target
     */
    public static void copyNotNullProperties(Object source, Object target) {
        BeanUtils.copyProperties(source, target, getNullField(source));
    }

    /**
     * Gets the empty field in the property
     *
     * @param target
     * @return
     */
    private static String[] getNullField(Object target) {
        BeanWrapper beanWrapper = new BeanWrapperImpl(target);
        PropertyDescriptor[] propertyDescriptors = beanWrapper.getPropertyDescriptors();
        Set<String> nullFieldSet = new HashSet<>();
        if (propertyDescriptors.length > 0) {
            for (PropertyDescriptor p : propertyDescriptors) {
                String name = p.getName();
                Object value = beanWrapper.getPropertyValue(name);
                if (Objects.isNull(value)) {
                    nullFieldSet.add(name);
                }
            }
        }
        //ignore id by adding it to nullSet
        nullFieldSet.add("id");
        //to array
        String[] notNullField = new String[nullFieldSet.size()];
        return nullFieldSet.toArray(notNullField);
    }
}
