package com.softwaresecuritytesting.softwaresecuritytesting.controller;

import com.softwaresecuritytesting.softwaresecuritytesting.model.FileInfo;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Set;

@Component
public class FileValidator implements Validator {

    private static final Set<String> types = Set.of(
            "AB","BC","CD","AE"
    );

    @Override
    public boolean supports(Class<?> aClass) {
        return FileInfo.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        FileInfo fileInfo = (FileInfo) o;

        if (!types.contains(fileInfo.getType())) {
            throw new RuntimeException("Could not read the file. Error: ");
        }
    }
}
