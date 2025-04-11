package org.basic_crud.practice_1.customer;

public record CustomerUpdateRequest(
        String name,
        String email,
        Integer age
) {
}
