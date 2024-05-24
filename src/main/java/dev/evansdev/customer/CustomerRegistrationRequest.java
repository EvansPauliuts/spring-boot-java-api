package dev.evansdev.customer;

public record CustomerRegistrationRequest(
        String name,
        String email,
        Integer age
) {
}
