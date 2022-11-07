package com.credable.service;

import io.credable.cbs.customer.*;
import io.credable.cbs.transaction.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import javax.xml.bind.JAXBElement;
import java.util.List;


@Component
public class BankClient extends WebServiceGatewaySupport {

    @Autowired
    private WebServiceTemplate webServiceTemplate;


    @SuppressWarnings("unchecked")
    public List<TransactionData> getTransactions(String customerNumber) {
        io.credable.cbs.transaction.ObjectFactory factory = new io.credable.cbs.transaction.ObjectFactory();
        TransactionsRequest request = factory.createTransactionsRequest();
        request.setCustomerNumber(customerNumber);
        TransactionDataPortService transactionDataPortService=new TransactionDataPortService();
        TransactionDataPort trService=transactionDataPortService.getTransactionDataPortSoap11();
        TransactionsResponse response =trService.transactions(request);
        return response.getTransactions();
    }

    @SuppressWarnings("unchecked")
    public Customer getCustomer(String customerNumber) {
        io.credable.cbs.customer.ObjectFactory factory = new io.credable.cbs.customer.ObjectFactory();
        CustomerRequest request = factory.createCustomerRequest();
        request.setCustomerNumber(customerNumber);
        CustomerPortService customerPortService=new CustomerPortService();
        CustomerPort csService=customerPortService.getCustomerPortSoap11();
        CustomerResponse response=csService.customer(request);
        return response.getCustomer();
    }

     /* CustomerPort csService= customerRequest -> {
            JAXBElement<CustomerResponse> response = (JAXBElement<CustomerResponse>) webServiceTemplate.marshalSendAndReceive(customerRequest);
            return response.getValue();
        };*/
}

