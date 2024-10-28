package com.bptn.pfms._services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BudgetCategoryTest {
    private BudgetManager budgetManager;

    @BeforeEach
    void setUp() {
        budgetManager = new BudgetManager();
    }

    @Test
    void testSetAndGetBudgetByCategory() {
        Budget groceriesBudget = new Budget("Groceries", 500.0);
        budgetManager.setBudget(groceriesBudget);

        Budget retrievedBudget = budgetManager.getBudgetByCategory("Groceries");
        assertNotNull(retrievedBudget);
        assertEquals(500.0, retrievedBudget.getAmount());
        assertEquals("Groceries", retrievedBudget.getCategory());
    }
}
