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

@RestController
@RequestMapping("bank")
@Api(value = "Loan", description = "Loan api")
public class LoanController {

    @Autowired
    private BankClient bankClient;
    @Autowired
    private ScoringClient scoringClient;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private LoanBookRepository loanBookRepository;

    @ApiOperation(value = "get customer transaction data")
    @PostMapping("/transaction/data")
    public ResponseEntity<CredableResponse> getTransactions(
            @RequestBody BankRequest request
    ) {
        CredableResponse credable_response = new CredableResponse();
        try {
            List<TransactionData> response = bankClient.getTransactions(request.getCustomerNumber());
            credable_response.setData(response);
            return new ResponseEntity(credable_response, HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            credable_response.setTitle("error");
            credable_response.setMessage("system busy, please try again later!");
            credable_response.setDetail(e.toString());
            return new ResponseEntity(credable_response, HttpStatus.OK);
        }
    }

    @ApiOperation(value = "get loan status")
    @PostMapping("/get/loan/status")
    public ResponseEntity<CredableResponse> getLoanStatus(
            @RequestBody BankRequest request
    ) {
        CredableResponse credable_response = new CredableResponse();
        try {
            Customer customer=customerRepository.findByCustomerNumber(request.getCustomerNumber());
            if(customer==null){
                credable_response.setTitle("error");
                credable_response.setMessage("sorry, customer number not registered!");
                return new ResponseEntity(credable_response, HttpStatus.OK);
            }
            LoanBook loanBook=loanBookRepository.findByCustomer(customer);
            if(loanBook==null){
                credable_response.setTitle("success");
                credable_response.setMessage("user has no active loan!");
                return new ResponseEntity(credable_response, HttpStatus.OK);
            }
            credable_response.setTitle("success");
            credable_response.setMessage(loanBook.getLoanStatus());
            return new ResponseEntity(credable_response, HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            credable_response.setTitle("error");
            credable_response.setMessage("system busy, please try again later!");
            credable_response.setDetail(e.toString());
            return new ResponseEntity(credable_response, HttpStatus.OK);
        }
    }

    @ApiOperation(value = "apply loan reuest")
    @PostMapping("/loan/request")
    public ResponseEntity<CredableResponse> getLoan(
            @RequestBody LoanRequest request
    ) {
        CredableResponse credable_response = new CredableResponse();
        try {
            String token=scoringClient.getScoringToken(request.getCustomerNumber());
            ScoringResults scoringResults=scoringClient.getScoring(token);
           if(scoringResults.getLimitAmount()<request.getAmount()){
               credable_response.setTitle("error");
               credable_response.setMessage("sorry, the amount is requested than the loan limit set!");
               return new ResponseEntity(credable_response, HttpStatus.OK);
           }
           Customer customer=customerRepository.findByCustomerNumber(request.getCustomerNumber());
            if(customer==null){
                credable_response.setTitle("error");
                credable_response.setMessage("sorry, customer number not registered!");
                return new ResponseEntity(credable_response, HttpStatus.OK);
            }
            LoanBook loanBook=loanBookRepository.findByCustomer(customer);
            if(loanBook!=null&&loanBook.getLoanStatus()=="active"){
                credable_response.setTitle("error");
                credable_response.setMessage("sorry, active loan found!");
                return new ResponseEntity(credable_response, HttpStatus.OK);
            }

            loanBook=new LoanBook();
            loanBook.setCustomer(customer);
            loanBook.setLoanAmount(request.getAmount());
            loanBook.setTotalPaid(0);
            loanBook.setLoanStatus("pending");
            loanBookRepository.save(loanBook);
            credable_response.setTitle("success");
            credable_response.setMessage("loan applied successfully!");
            return new ResponseEntity(credable_response, HttpStatus.OK);

        } catch (Exception e){
            e.printStackTrace();
            credable_response.setTitle("error");
            credable_response.setMessage("system busy, please try again later!");
            credable_response.setDetail(e.toString());
            return new ResponseEntity(credable_response, HttpStatus.OK);
        }
    }

}
