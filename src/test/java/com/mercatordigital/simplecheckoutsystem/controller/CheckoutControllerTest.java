package com.mercatordigital.simplecheckoutsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Stream;

import static com.mercatordigital.simplecheckoutsystem.model.Product.APPLE;
import static com.mercatordigital.simplecheckoutsystem.model.Product.ORANGE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CheckoutControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeAll
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @ParameterizedTest
    @NullAndEmptySource
    void shouldReturnBadRequest_WhenNullOrEmptyProductsProvided(String items) throws Exception {
        mockMvc.perform(post("/api/checkout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"products\":%s}".formatted(items)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnCorrectTotal_WhenValidProductsProvided() throws Exception {

        mockMvc.perform(post("/api/checkout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"products\":%s}".formatted("[\"APPLE\",\"ORANGE\"]")))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"priceString\":\"£%.2f\"}"
                        .formatted(APPLE.getPrice()
                                .add(ORANGE.getPrice())
                                .setScale(2, RoundingMode.HALF_UP)
                                .floatValue())));
    }

    @Test
    void shouldReturnCorrectTotal_WhenImproperCaseProductsProvided() throws Exception {
        mockMvc.perform(post("/api/checkout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"products\":[\"apple\",\"oRangE\"]}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"priceString\":\"£%.2f\"}"
                        .formatted(APPLE.getPrice()
                                .add(ORANGE.getPrice())
                                .setScale(2, RoundingMode.HALF_UP)
                                .floatValue())));
    }

    @ParameterizedTest
    @MethodSource("getInvalidInputs")
    void shouldReturnBadRequest_WhenInvalidProductsProvided(List<String> input) throws Exception {
        mockMvc.perform(post("/api/checkout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"products\":%s}".formatted(new ObjectMapper().writeValueAsString(input))))
                .andExpect(status().isBadRequest());
    }

    public Stream<Arguments> getInvalidInputs() {
        return Stream.of(
                Arguments.of(List.of("appl")),
                Arguments.of(List.of("appel")),
                Arguments.of(List.of("APPLE", "ORANGE", "")),
                Arguments.of(List.of("APPLE", "ORANGE", "APPLE", "BANANA"))
        );
    }
}