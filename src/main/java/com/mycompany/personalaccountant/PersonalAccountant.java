/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.personalaccountant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.Iterator;
import java.util.Set;
/**
 *
 * @author Jihun
 */
public class PersonalAccountant {
    //Reads bank CSV file, model them as Transaction objects and is added to a list
    private static final Scanner s = new Scanner(System.in);
    
    private static final List<Transaction> transactions = DataAccess.readLLOYDSCSV("transactions.csv");
    
    private static final Map<String, List<Transaction>> outTransactionsMap = new HashMap<>();
    private static final Map<String, List<Transaction>> inTransactionsMap = new HashMap<>();
    
    public static void runMonthlyAccountPrep(List<Transaction> transactionss) { 
        
        
        printTransactionsDateRange(transactions);
        //from the list take the first and last item to get the date range of the bank csv
       
        addCatagoryFeatureToTransaction(transactions);
        //Use stream and then filter and collect into an arraylist of transaction in and out    
        aggregateTotalPerCatagory(transactions);
        
        DataAccess.writeToCSV("output.csv", getTransactionWithCatagoryFeatureToCSV(transactions)); 
        DataAccess.writeToCSV("TotalAmountPerCatagory.csv", getTotalPerCatagoryCSV());
    } 
    private static void printTransactionsDateRange(List<Transaction> transactions)
    {
        String date_range = "from "+transactions.get(transactions.size()-1).getTransactiondate()+" to "+ transactions.get(0).getTransactiondate();
        System.out.format("For the date range %s\n", date_range);
    }
    private static void addCatagoryFeatureToTransaction(List<Transaction> transactions)
    {
        //NEED EXCEPTION HANDLING
        //NEED OPTION TO GO TO PREVIOUS TRANSACTION IN CASE OF MISTAKE
        //replace first part of lambda to for loop with index for e
        
        for(int i=0; i<transactions.size(); i++){   
                
                if(i!=0)
                {
                    System.out.println("\nEnter Y to edit previous transaction, or anything else to continue");
                    String checkPrev = s.nextLine();
                    if(checkPrev.equals("y")){
                        i-=1;                   
                    }
                    else{
                        //Can catch exception in this case
                    }
                }
                Transaction t=transactions.get(i);
                System.out.println("---------------\nWhat catagory is the below transaction?");
                System.out.println(t);
                if(t.isMoneyIn()){
                    System.out.println("------------");
                    Transaction.inCatagories.stream()
                            .map(c->Transaction.inCatagories.indexOf(c)+" "+c)
                            .forEach(c->System.out.println(c));
                    System.out.println("------------");
                    int index=0;
                    do{
                        try{
                            String input =s.nextLine();
                            index = Integer.parseInt(input);
                            if(index>Transaction.inCatagories.size()-1||index<0)
                            {
                                throw(new IllegalArgumentException());
                            }
                            break;
                        }
                        catch(NumberFormatException nfe)
                        {
                            System.out.println("Incorrect input format. Please enter a number between 0 and "+(Transaction.outCatagories.size()-1));;
                        }
                        catch(IllegalArgumentException iae)
                        {
                            System.out.println("Incorrect input. Please enter a number between 0 and "+(Transaction.inCatagories.size()-1));
                        }
                    }while(true);
                    System.out.println("You have picked " + Transaction.inCatagories.get(index));
                    t.setCatagory(Transaction.inCatagories.get(index));
                }
                else{
                    System.out.println("------------");
                    Transaction.outCatagories.stream()
                            .map(c->Transaction.outCatagories.indexOf(c)+" "+c)
                            .forEach(c->System.out.println(c));
                    System.out.println("------------");
                    int index=0;
                    do{
                        try{
                            String input =s.nextLine();
                            index = Integer.parseInt(input);
                            if(index>Transaction.outCatagories.size()-1||index<0)
                            {
                                throw(new IllegalArgumentException());
                            }
                            break;
                        }
                        catch(NumberFormatException nfe)
                        {
                            System.out.println("Incorrect input format. Please enter a number between 0 and "+(Transaction.outCatagories.size()-1));;
                        }
                        catch(IllegalArgumentException iae)
                        {
                            System.out.println("Incorrect input. Please enter a number between 0 and "+(Transaction.outCatagories.size()-1));
                        }
                    }while(true);

                    System.out.println("You have picked " + Transaction.outCatagories.get(index));
                    t.setCatagory(Transaction.outCatagories.get(index));
                }
            }
            
            System.out.println("All transaction Catagorised, creating. Putting transactions into catagories now \n");
        
        s.close();
    }
    private static void aggregateTotalPerCatagory(List<Transaction> transactions)
    {
        List<Transaction> outTransactions=transactions.stream().filter(t->t.isMoneyIn()==false).collect(Collectors.toList());
        List<Transaction> inTransactions =transactions.stream().filter(t->t.isMoneyIn()==true).collect(Collectors.toList());
        
        Transaction.inCatagories.stream()
                .forEach(c->inTransactionsMap.put(c,inTransactions.stream().filter(t->t.getCatagory().equals(c)).collect(Collectors.toList())));
        Transaction.outCatagories.stream()
                .forEach(c->outTransactionsMap.put(c,outTransactions.stream().filter(t->t.getCatagory().equals(c)).collect(Collectors.toList()) ));
        
    }

   
    private static String getTransactionWithCatagoryFeatureToCSV(List<Transaction> transactions)
    {
        String transactionsCSV="date,type,account,amount,in/out,catagory\n";
        Iterator<Transaction> iTransaction = transactions.iterator();
        while(iTransaction.hasNext())
        {
            transactionsCSV = transactionsCSV + iTransaction.next()+"\n";
        }
        return transactionsCSV;
        
    }
    private static String getTotalPerCatagoryCSV()
    {
      String csv=
                "Out Transactions,\n"
                +getTotalSpendingPerCatagoryCSV(outTransactionsMap)
                +"\n\nIn Transaction\n"
                +getTotalSpendingPerCatagoryCSV(inTransactionsMap);
           return csv;  
    }
    private static String getTotalSpendingPerCatagoryCSV(Map<String, List<Transaction>> transactions)
    {
        Set<String> catagoryKeys = transactions.keySet();
        String transactionCatagoryCSV = "Catagory,Spending";
        for(String key: catagoryKeys)
        {
            transactionCatagoryCSV=transactionCatagoryCSV+"\n"+key+","+transactions.get(key).stream().mapToDouble(t->t.getAmount()).sum();
        }
        return transactionCatagoryCSV;
    }
    
}
