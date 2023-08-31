/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.personalaccountant;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Jihun
 */
public class DataAccess {
    public static List readLLOYDSCSV(String filepath)
    {
        List<Transaction> transactions = new ArrayList<>();
        SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
        String data;
        try{
            BufferedReader br = new BufferedReader(new FileReader(filepath));
            br.readLine();
            while((data=br.readLine())!=null)
            {   
                String[] transaction = data.split(",");
                String transactiondate = transaction[0];
                String transactiontype = transaction[1];
                String transactiondescription = transaction[4];
                double amount=0;
                boolean moneyIn=false;
                if(transaction[5].isEmpty())
                {
                    amount = Double.parseDouble(transaction[6]);
                    moneyIn = true;
                }
                else if(transaction[6].isEmpty())
                {
                    amount = Double.parseDouble(transaction[5]);
                    moneyIn = false;
                }
               
                Transaction t = new Transaction(transactiondate,transactiontype,transactiondescription, amount, moneyIn);
                transactions.add(t);          
            }
            
        }
        catch(IOException ioe)
        {
            System.out.println("Error occured reading your file. Error code: "+ioe);
        }
       return transactions;
    }
    public static void writeToCSV(String filepath, String csv)
    {
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(filepath));
            bw.write("");//empties previous
            bw.write(csv);
            bw.close();
        }   
        catch(IOException ioe)
        {
            
        }
    }    
}
