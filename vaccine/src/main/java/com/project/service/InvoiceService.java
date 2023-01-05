package com.project.service;

public interface InvoiceService {

    /**
     * @return
     */
    void createInvoice(String expired, int price, int quantity,String transactionDate, int provideId, int vaccineId);

}
