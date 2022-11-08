package com.credable.service;

import io.credable.cbs.customer.*;
import io.credable.cbs.transaction.*;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import javax.xml.bind.JAXBElement;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class BankClient {


    public List<TransactionData> getTransactions(String customerNumber) {
        //provider.getRequestContext().put("ws-security.username", "admin");
        //provider.getRequestContext().put("ws-security.password", "pwd123");

        io.credable.cbs.transaction.ObjectFactory factory = new io.credable.cbs.transaction.ObjectFactory();
        TransactionsRequest request = factory.createTransactionsRequest();
        request.setCustomerNumber(customerNumber);
        TransactionDataPortService transactionDataPortService=new TransactionDataPortService();
        System.out.println(transactionDataPortService.getWSDLDocumentLocation());
        TransactionDataPort transactionPort=transactionDataPortService.getTransactionDataPortSoap11();

        BindingProvider provider = (BindingProvider) transactionPort;
        provider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,"http://credable.io/cbs/transaction/");
        provider.getRequestContext().put(BindingProvider.USERNAME_PROPERTY,"admin");
        provider.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY,"pwd123");


        TransactionsResponse response =transactionPort.transactions(request);
        return response.getTransactions();
    }



    public Customer getCustomer(String customerNumber) {
        io.credable.cbs.customer.ObjectFactory factory = new io.credable.cbs.customer.ObjectFactory();
        CustomerRequest request = factory.createCustomerRequest();
        request.setCustomerNumber(customerNumber);
        CustomerPortService customerPortService=new CustomerPortService();
        CustomerPort customerPort=customerPortService.getCustomerPortSoap11();
        BindingProvider provider = (BindingProvider) customerPort;
        provider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,"http://credable.io/cbs/customer");
        provider.getRequestContext().put(BindingProvider.USERNAME_PROPERTY,"admin");
        provider.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY,"pwd123");
        CustomerResponse response=customerPort.customer(request);
        return response.getCustomer();
    }


}

