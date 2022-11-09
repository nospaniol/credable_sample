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
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class BankClient {


    public List<TransactionData> getTransactions(String customerNumber) {
        io.credable.cbs.transaction.ObjectFactory factory = new io.credable.cbs.transaction.ObjectFactory();
        TransactionsRequest request = factory.createTransactionsRequest();
        request.setCustomerNumber(customerNumber);
        Authenticator.setDefault(new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("admin", "pwd123".toCharArray());
            }
        });
        TransactionDataPortService transactionDataPortService = new TransactionDataPortService();
        TransactionDataPort transactionPort = transactionDataPortService.getTransactionDataPortSoap11();
        BindingProvider provider = (BindingProvider) transactionPort;
        provider.getRequestContext().put(BindingProvider.USERNAME_PROPERTY,"admin");
        provider.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY,"pwd123");
        List<TransactionData> response = transactionPort.transactions(request).getTransactions();
        return response;
    }


    public Customer getCustomer(String customerNumber) {
        io.credable.cbs.customer.ObjectFactory factory = new io.credable.cbs.customer.ObjectFactory();
        CustomerRequest request = factory.createCustomerRequest();
        request.setCustomerNumber(customerNumber);
        CustomerPortService customerPortService = new CustomerPortService();
        CustomerPort customerPort = customerPortService.getCustomerPortSoap11();

        Authenticator.setDefault(new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("admin", "pwd123".toCharArray());
            }
        });
        BindingProvider provider = (BindingProvider) customerPort;
        provider.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, "admin");
        provider.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "pwd123");
        CustomerResponse response = customerPort.customer(request);
        return response.getCustomer();
    }


}

