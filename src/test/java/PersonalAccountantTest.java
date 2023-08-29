/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import com.mycompany.personalaccountant.DataAccess;
import com.mycompany.personalaccountant.Transaction;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Jihun
 */
public class PersonalAccountantTest {
    private static Map<String, List<Transaction>> outTransactionsMap = new HashMap<>();
    private static Map<String, List<Transaction>> inTransactionsMap = new HashMap<>();
    
    @BeforeEach
    public void setUp() {
        
    }

    /**
     * Test of main method, of class PersonalAccountant.
     */
    @Test
    @DisplayName("Testing DataAccess reads LLOYDS CSV FILE  CORRECTLY")
    void testReadLLOYDSCSV(){     
        List<Transaction> testTransactionList = DataAccess.readLLOYDSCSV("TestData/transactions.csv"); //test.csv file has 5 lines of data

        assertEquals(testTransactionList.get(0).getTransactiondate(),"21/08/2023");
        assertEquals(testTransactionList.get(0).getTransactiontype(), "DEB");
        assertEquals(testTransactionList.get(0).getDescription(),"SPOTIFY");
        assertEquals(testTransactionList.get(0).getAmount(), 9.99);
        assertEquals(testTransactionList.get(0).isMoneyIn(),false);
        
        assertEquals(testTransactionList.size(), 6);           
    }
    @Test
    @DisplayName("Testing DataAccess reads LLOYDS CSV FILE  CORRECTLY")
    void exampletest(){     
             
    }
    
}
