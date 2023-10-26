package com.example.MSSQLConnection.service;

import com.example.MSSQLConnection.annotations.Init;
import com.example.MSSQLConnection.annotations.JsonElement;
import com.example.MSSQLConnection.annotations.JsonSerializable;
import com.example.MSSQLConnection.dto.UserDto;
import com.example.MSSQLConnection.exception.JsonSerializationException;
import com.example.MSSQLConnection.persistence.Person;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PersonService {

    public String getPersonData() {
        Person person = new Person();
        person.setFirstName("Hakan");
        person.setLastName("Keleş");
        person.setAddress("Yenimahalle");
        person.setAge("41");

        return convertToJson(person);
    }

    private void checkIfSerializable(Object object) {
        if (Objects.isNull(object)) {
            throw new JsonSerializationException("The object to serialize is null");
        }

        Class<?> clazz = object.getClass();
        if (!clazz.isAnnotationPresent(JsonSerializable.class)) {
            throw new JsonSerializationException("The class "
                    + clazz.getSimpleName()
                    + " is not annotated with JsonSerializable");
        }
    }

    private void initializeObject(Object object) throws Exception {
        Class<?> clazz = object.getClass();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Init.class)) {
                method.setAccessible(true);
                method.invoke(object);
            }
        }
    }

    private String getJsonString(Object object) throws Exception {
        Class<?> clazz = object.getClass();
        Map<String, String> jsonElementsMap = new HashMap<>();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(JsonElement.class)) {
                jsonElementsMap.put(getKey(field), (String) field.get(object));
            }
        }

        String jsonString = jsonElementsMap.entrySet()
                .stream()
                .map(entry -> "\"" + entry.getKey() + "\":\""
                        + entry.getValue() + "\"")
                .collect(Collectors.joining(","));
        String headerAndBody = "{ \"header\" : [\"personAge\", \"firstName\", \"lastName\"], \"body\" : [";
        return headerAndBody + "{" + jsonString + "}]}";
    }

    public String convertToJson(Object object) throws JsonSerializationException {
        try {
            checkIfSerializable(object);
            initializeObject(object);
            return getJsonString(object);
        } catch (Exception e) {
            throw new JsonSerializationException(e.getMessage());
        }
    }

    private String getKey(Field field) {
        String value = field.getAnnotation(JsonElement.class)
                .key();
        return value.isEmpty() ? field.getName() : value;
    }

    private UserDto mapToPersonDto(Person person){
        UserDto userDto = new UserDto();
        userDto.setFirstName(person.getFirstName());
        userDto.setLastName(person.getLastName());
        userDto.setEmail(person.getEmail());

        return userDto;
    }
}
