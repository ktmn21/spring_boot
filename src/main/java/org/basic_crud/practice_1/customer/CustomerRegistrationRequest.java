package org.basic_crud.practice_1.customer;

public record CustomerRegistrationRequest (
        String name,
        String email,
        Integer age
){}
