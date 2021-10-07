package ru.bogdanov.orderservice.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Converter
public class ListToStringConverter implements AttributeConverter<List<Long>,String> {

        @Override
        public String convertToDatabaseColumn(List<Long> attribute) {
            if (attribute == null || attribute.isEmpty()) {
                return "";
            }
            String result = "";
            for (Long l:attribute) {
                result +=l.toString()+",";
            }

            result = result.substring(0,result.length()-1);
            return result;
        }

        @Override
        public List<Long> convertToEntityAttribute(String dbData) {
            if (dbData == null || dbData.trim().length() == 0) {
                return new ArrayList<Long>();
            }

            String[] data = dbData.split(",");
            ArrayList<Long> l = new ArrayList<>();
            for (int i = 0; i <data.length ; i++) {
                l.add( Long.parseLong(data[i]));
            }
            return l;
        }

}
