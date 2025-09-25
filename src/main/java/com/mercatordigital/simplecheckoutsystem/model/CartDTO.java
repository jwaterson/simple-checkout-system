package com.mercatordigital.simplecheckoutsystem.model;

import java.util.List;
import java.util.Objects;

public record CartDTO(List<Product> products) {

    public CartDTO {
        if (Objects.isNull(products) || products.isEmpty() || products.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException(
                    "The list of products cannot be null or empty and cannot contain null elements: (list passed: %s)"
                            .formatted(products));
        }
    }
}
