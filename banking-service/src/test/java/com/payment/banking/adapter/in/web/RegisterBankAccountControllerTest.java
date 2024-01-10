package com.payment.banking.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payment.banking.domain.RegisteredBankAccount;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RegisterBankAccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void testRegisterBankAccount() throws Exception {

        RegisterBankAccountRequest request = new RegisterBankAccountRequest("1", "bank", "123456", true);

        RegisteredBankAccount expect = RegisteredBankAccount.generateRegisteredBankAccount(
                new RegisteredBankAccount.RegisteredBankAccountId("1"),
                new RegisteredBankAccount.MembershipId("1"),
                new RegisteredBankAccount.BankName("bank"),
                new RegisteredBankAccount.BankAccountNumber("123456"),
                new RegisteredBankAccount.LinkedStatusIsValid(true)
        );

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/banking/account/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(mapper.writeValueAsString(expect)));

    }
}
