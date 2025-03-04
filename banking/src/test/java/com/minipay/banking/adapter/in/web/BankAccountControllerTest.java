package com.minipay.banking.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minipay.banking.adapter.in.web.request.RegisterBankAccountRequest;
import com.minipay.banking.application.port.out.MembershipServicePort;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;


@SpringBootTest
@AutoConfigureMockMvc
class BankAccountControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    MembershipServicePort membershipServicePort;

    @Test
    void registerBankAccount() throws Exception {
        RegisterBankAccountRequest request = new RegisterBankAccountRequest(
                UUID.randomUUID(),
                "국민은행",
                "101013311123"
        );
        Mockito.when(membershipServicePort.isValidMembership(Mockito.any(UUID.class)))
                .thenReturn(true);


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