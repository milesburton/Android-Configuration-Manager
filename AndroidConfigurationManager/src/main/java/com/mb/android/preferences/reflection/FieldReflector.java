package com.mb.android.preferences.reflection;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class FieldReflector {

    private List<Field> fields = new ArrayList<Field>();

    public List<Field> getFields(Class<?> object) {

        for (Field field : object.getDeclaredFields()) {
            field.setAccessible(true);
            fields.add(field);
        }

        if (object.getSuperclass() != null) {
            fields = getFields(object.getSuperclass());
        }

        return fields;
    }
}
