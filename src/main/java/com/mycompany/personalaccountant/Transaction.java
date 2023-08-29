/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.personalaccountant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Jihun
 */
public class Transaction {
    protected static List<String> outCatagories 
            = Arrays.asList("Food-Eatout", "Food-Cooked", "Travel-Daniela", "Travel-Self", "Mobile", "Entertainment", "SelfLearning", "Stuff", "Coffee", "Laundry", "Rent", "Gym", "Other", "Accomodation","Self Transfer");
    protected static List<String> inCatagories = Arrays.asList("Allowance", "Other", "Job", "Refunds", "Self-Transfers");
    private String transactiondate;
    private String transactiontype;
    private String description;
    private Double amount;
    private String catagory;
    private boolean moneyIn;
    
    public Transaction(String transactiondate, String transactiontype, String description, Double amount, Boolean moneyIn)
    {
        this.transactiondate = transactiondate;
        this.transactiontype=transactiontype;
        this.description = description;
        this.amount = amount;
        this.moneyIn = moneyIn;

    }   
    public String getTransactiondate() {
        return transactiondate;
    }

    public String getTransactiontype() {
        return transactiontype;
    }

    public String getDescription() {
        return description;
    }

    public Double getAmount() {
        return amount;
    }

    public boolean isMoneyIn() {
        return moneyIn;
    }

    @Override
    public String toString() {
        String creditOrDebit = this.isMoneyIn()? "In": "Out";
        String catagory=this.catagory==null?"":","+this.catagory;
        return this.getTransactiondate()+","+this.getTransactiontype()+","+this.getDescription() + ","+this.getAmount()+","+creditOrDebit+catagory;
    }

    public String getCatagory() {
        return catagory;
    }

    public void setCatagory(String catagory) {
        this.catagory = catagory;
    }

    
    
    
}
