package com.credable.controller;

import com.credable.dto.*;
import com.credable.model.Customer;
import com.credable.model.LoanBook;
import com.credable.repository.CustomerRepository;
import com.credable.repository.LoanBookRepository;
import com.credable.service.BankClient;
import com.credable.service.ScoringClient;
import io.credable.cbs.transaction.TransactionData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class LoanControllerTest {

    @Autowired
    private BankClient bankClient;

    @Autowired
    private ScoringClient scoringClient;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private LoanBookRepository loanBookRepository;

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void applyLoan() {
        String customerNumber="234774784";
        int amount=100;
        mockMvc.perform(get("/loan/request/"+customerNumber"/"+amount))
                .andDo(print())
                .andExpect(jsonPath("$.*", hasSize(500)))
                .andExpect(status().is2xxSuccessful());

    }

    @Test
    public void getLoanStatus(){
        String customerNumber="366585630";
        mockMvc.perform(get("/get/loan/status/"+customerNumber))
                .andDo(print())
                .andExpect(jsonPath("$.*", hasSize(500)))
                .andExpect(status().is2xxSuccessful());
    }



}
