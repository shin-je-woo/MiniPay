package com.minipay.membership.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minipay.membership.adapter.in.web.request.RegisterMembershipRequest;
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
class RegisterMembershipControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void registerMembership() throws Exception {
        RegisterMembershipRequest request = new RegisterMembershipRequest("name", "email@email.com", "address", true);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/membership")
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