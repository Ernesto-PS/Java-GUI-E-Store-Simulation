# Java-GUI-E-Store-Simulation

**Overview**

This standalone Java application allows users to add in-stock items to a shopping cart, calculate the total cost (including tax and discounts), generate an invoice, and log transactions to an external file.

**Features**

**User Interface**
  Input fields for adding items, displaying descriptions, and entering quantities.  
  A shopping cart display area to show selected items.  
  Six interactive buttons for managing cart operations and checkout.  
  
**File Handling**
  
  Inventory Input File (inventory.csv): Contains product details such as ID, description, availability, stock count, and unit price.  
  Transaction Log (transactions.csv): Records each purchase with a unique transaction ID generated from a timestamp.  

**Constraints**

  Shopping cart can hold a maximum of five unique items per transaction.  
  A fixed 6% tax rate and predefined discount rates apply to purchases.  
  Assumes users follow the correct sequence of operations, minimizing error handling requirements.  

**How to Run**

**Compile the Java File**

Compile the file: javac NileDotComFall2024.java  

**Run the Program**

Run the file: java NileDotComFall2024  

**Additional Notes**

Review the documentation provided for examples and demonstration of the different features
