package com.todo.utils;

import java.util.Arrays;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("commaSeparatedFieldConverter")
public class CommaSeparatedFieldConverter implements Converter {

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return null;
        }

        List<String> strings = (List<String>) value;
        StringBuilder builder = new StringBuilder();

        for (String string : strings) {
            if (builder.length() > 0) {
                builder.append(",");
            }

            builder.append(string);
        }

        return builder.toString();
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null) {
            return null;
        }

        return Arrays.asList(value.split(","));
    }

}