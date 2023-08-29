/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.personalaccountant;

/**
 *
 * @author Jihun
 */
public class Main {
    public static void main(String[] args)
    {
        
        PersonalAccountant.runMonthlyAccountPrep(DataAccess.readLLOYDSCSV("transactions.csv"));
    }
}
