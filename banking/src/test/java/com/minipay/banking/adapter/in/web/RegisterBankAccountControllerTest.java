package com.minipay.banking.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minipay.banking.adapter.in.web.request.RegisterBankAccountRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@AutoConfigureMockMvc
class RegisterBankAccountControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void registerBankAccount() throws Exception {
        RegisterBankAccountRequest request = new RegisterBankAccountRequest(
                1L,
                "국민은행",
                "101013311123"
        );

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/bank-accounts")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andDo(
                        MockMvcResultHandlers.print()
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk()
                );
    }
}