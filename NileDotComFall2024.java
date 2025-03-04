import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
import java.text.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//**************************************************************************************

public class NileDotComFall2024 extends JFrame {
    // Static variables to hold frame dimensions in pixels
    private static final int FRAME_WIDTH = 750;
    private static final int FRAME_HEIGHT = 750;

    // Static variables to hold text fields in pixels
    private static final int TFIELD_WIDTH = 10;
    private static final int TFIELD_HEIGHT = 10;

    // Declare reference variables for panels
    private JPanel northPanel, centerPanel, southPanel;

    // Declare reference variables for grid layouts
    private GridLayout northGrid4by2, centerGrid7by1, southGrid5by2;

    // Declare reference variables for labels
    private JLabel idLabel, qtyLabel, itemLabel, totalLabel, cartLabel, controlsLabel, blankLabel;

    // Declare reference variables for buttons
    private JButton processB, confirmB, viewB, finishB, newB, exitB;

    // Declare reference variables for user's text inputs
    private JTextField idTextField, qtyTextField, itemTextField, totalTextField;

    // Declare reference variables for event handlers - one for each handler required - six in this case
    private ProcessButtonHandler procbHandler;
    private ConfirmButtonHandler confbHandler;
    private ViewButtonHandler viewbHandler;
    private FinishButtonHandler finbHandler;
    private NewButtonHandler newbHandler;
    private ExitButtonHandler exitbHandler;

    // More static variables for formatting currency, percentages, and decimal values
    private NumberFormat nF;

    // Define arrays for holding items in the cart
    private JTextField[] cartArray;
    private String[] cartLineArray;
    private String[] itemDetailsArray = new String[5];

    // Define additional variables as needed
    static String userItemID = "", itemID = "", itemTitle = "", itemStatus = "", itemDetails = "";
    static double itemPrice = 0, itemSubtotal = 0, orderSubtotal = 0, orderTotal = 0, taxAmount = 0;
    static int itemQuantity = 0, itemQuantityInFile = 0, itemCount = 0;
    final static double TAX_RATE = 0.060, DISCOUNT_FOR_00 = .0, DISCOUNT_FOR_05 = .10, DISCOUNT_FOR_10 = .15, DISCOUNT_FOR_15 = .20;
    
//**************************************************************************************

public NileDotComFall2024()
{
    setTitle("Nile.Com - Fall 2024"); // Sets the title of the frame
    setSize(FRAME_WIDTH, FRAME_HEIGHT); // Sets the frame size

    Color LIGHT_BLUE = new Color(51,204,255); // Defines a light_blue color

    // Defines a content pane to hold everything
    Container pane = getContentPane();
    pane.setBackground(Color.DARK_GRAY);

    // Defines grid layouts for each section
    northGrid4by2 = new GridLayout(4,2,8,4);
    centerGrid7by1 = new GridLayout(7,1,8,8);
    southGrid5by2 = new GridLayout(5,2,8,4);

    // *************************************************North Section*****************************************************************

    // *****************************Labels***************************************

    // Defines labels
    idLabel = new JLabel("Enter item ID for Item #" + (itemCount + 1) + ":");
    qtyLabel = new JLabel("Enter quantity for Item #" + (itemCount + 1) + ":");
    itemLabel = new JLabel("Details for Item #" + (itemCount + 1) + ":");
    totalLabel = new JLabel("Current Subtotal for " + itemCount + " item(s):");

    // Styles labels' font color
    idLabel.setForeground(Color.YELLOW);
    qtyLabel.setForeground(Color.YELLOW);
    itemLabel.setForeground(Color.RED);
    totalLabel.setForeground(LIGHT_BLUE);

    // Styles labels' font
    idLabel.setFont(new Font("Calibri", Font.BOLD, 18));
    qtyLabel.setFont(new Font("Calibri", Font.BOLD,18));
    itemLabel.setFont(new Font("Calibri", Font.BOLD, 18));
    totalLabel.setFont(new Font("Calibri", Font.BOLD,18));

    // Styles labels' horizontal alignments
    idLabel.setHorizontalAlignment(JLabel.RIGHT);
    qtyLabel.setHorizontalAlignment(JLabel.RIGHT);
    itemLabel.setHorizontalAlignment(JLabel.RIGHT);
    totalLabel.setHorizontalAlignment(JLabel.RIGHT);

    // *****************************Labels***************************************

    // *****************************TextFields***************************************

    // Defines text fields
    idTextField = new JTextField();
    qtyTextField = new JTextField();
    itemTextField = new JTextField();
    totalTextField = new JTextField();

    // Styles the size of the text fields
    idTextField.setPreferredSize(new Dimension(TFIELD_WIDTH, TFIELD_HEIGHT));
    qtyTextField.setPreferredSize(new Dimension(TFIELD_WIDTH, TFIELD_HEIGHT));
    itemTextField.setPreferredSize(new Dimension(TFIELD_WIDTH, TFIELD_HEIGHT));
    totalTextField.setPreferredSize(new Dimension(TFIELD_WIDTH, TFIELD_HEIGHT));

    // Styles text fields' font
    idTextField.setFont(new Font("Calibri", Font.PLAIN, 18));
    qtyTextField.setFont(new Font("Calibri", Font.PLAIN,18));
    itemTextField.setFont(new Font("Calibri", Font.PLAIN, 15));
    totalTextField.setFont(new Font("Calibri", Font.PLAIN,15));

    // Prevents users from inserting text in these fields
    itemTextField.setEditable(false);
    totalTextField.setEditable(false);

    // *****************************TextFields***************************************

    // *****************************NorthPanel***************************************

    northPanel = new JPanel();
    northPanel.setBackground(Color.DARK_GRAY);
    northPanel.setLayout(northGrid4by2);
    northPanel.setPreferredSize(new Dimension(150,150));

    // Adds labels and text fields in the correct order to north panel
    pane.add(northPanel, BorderLayout.NORTH);
    northPanel.add(idLabel);
    northPanel.add(idTextField);
    northPanel.add(qtyLabel);
    northPanel.add(qtyTextField);
    northPanel.add(itemLabel);
    northPanel.add(itemTextField);
    northPanel.add(totalLabel);
    northPanel.add(totalTextField);

    // *****************************NorthPanel***************************************

    // *************************************************North Section*****************************************************************

    // *************************************************Center Section*****************************************************************

    // *****************************Labels***************************************

    // Defines labels
    cartLabel = new JLabel("Your Shopping Cart is Currently Empty"); // Defines label
    cartLabel.setForeground(Color.RED); // Styles label's color
    cartLabel.setFont(new Font("Calibri", Font.BOLD, 18)); // Styles label's font
    cartLabel.setHorizontalAlignment(JLabel.CENTER);

    // *****************************Labels***************************************

    // *****************************TextFields***************************************

    cartArray = new JTextField[5];
    for (int counter = 0; counter < 5; counter++)
    {
        cartArray[counter] = new JTextField();
        cartArray[counter].setPreferredSize(new Dimension(TFIELD_WIDTH, TFIELD_HEIGHT));
        cartArray[counter].setEditable(false);
    }

    // *****************************TextFields***************************************

    // *****************************CenterPanel***************************************

    centerPanel = new JPanel();
    centerPanel.setBackground(Color.GRAY);
    centerPanel.setLayout(centerGrid7by1);
    centerPanel.setPreferredSize(new Dimension(250,250));

    // Adds labels and text fields in the correct order to north panel
    pane.add(centerPanel, BorderLayout.CENTER);
    centerPanel.add(cartLabel);
    for (int i = 0; i < 5; i++)
    {
        centerPanel.add(cartArray[i]);
    }

    // *****************************CenterPanel***************************************

    // *************************************************Center Section*****************************************************************

    // *************************************************South Section******************************************************************

    // *****************************Labels***************************************

    // Defines labels
    controlsLabel = new JLabel(" USER CONTROLS ");
    blankLabel = new JLabel(" ");

    // Style label's font color
    controlsLabel.setForeground(Color.WHITE);

    // Styles label's font
    controlsLabel.setFont(new Font("Calibri", Font.BOLD, 18));

    // Styles the label's alignment
    controlsLabel.setHorizontalAlignment(JLabel.CENTER);

    // *****************************Labels***************************************

    // *****************************Buttons***************************************

    // Instantiate buttons and register handlers
    processB = new JButton("Search for Item #" + (itemCount + 1));
    procbHandler = new ProcessButtonHandler();
    processB.addActionListener(procbHandler);

    confirmB = new JButton("Add Item #" + (itemCount + 1) + " To Cart");
    confbHandler = new ConfirmButtonHandler();
    confirmB.addActionListener(confbHandler);

    viewB = new JButton("View Cart");
    viewbHandler = new ViewButtonHandler();
    viewB.addActionListener(viewbHandler);

    finishB = new JButton("Check Out");
    finbHandler = new FinishButtonHandler();
    finishB.addActionListener(finbHandler);

    newB = new JButton("Empty Cart - Start a New Order");
    newbHandler = new NewButtonHandler();
    newB.addActionListener(newbHandler);

    exitB = new JButton("Exit (Close App)");
    exitbHandler = new ExitButtonHandler();
    exitB.addActionListener(exitbHandler);

    // Initial settings for buttons, fields
    confirmB.setEnabled     (false); // Disable confirm until calculation complete
    viewB.setEnabled        (false);  
    finishB.setEnabled      (false); // Disable finish until confirm complete

    // *****************************Buttons***************************************

    // *****************************SouthPanel***************************************

    southPanel = new JPanel();
    southPanel.setBackground(Color.BLUE);
    southPanel.setLayout(southGrid5by2);
    southPanel.setPreferredSize(new Dimension(250,250));

    // Adds labels and text fields in the correct order to north panel
    pane.add(southPanel, BorderLayout.SOUTH);
    southPanel.add(controlsLabel);
    southPanel.add(blankLabel);
    southPanel.add(processB);
    southPanel.add(confirmB);
    southPanel.add(viewB);
    southPanel.add(finishB);
    southPanel.add(newB);
    southPanel.add(exitB);

    // *****************************SouthPanel***************************************

    // *************************************************South Section******************************************************************
}

//**************************************************************************************
    private class ProcessButtonHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            // Set class variables
            File inputFile = new File("inventory.csv"); // Reads data input
            FileReader inputFileReader = null;
            BufferedReader inputBuffReader = null;
            Scanner aScanner = null;
            String inventoryLine;
            String itemIDFromFile;
            String csvSplitBy = ", ";
            Boolean found = false;

            try
            {
                // Set max array size
                cartLineArray = new String[5];

                // Get user input from the GUI fields
                userItemID = idTextField.getText();
                itemQuantity = Integer.parseInt(qtyTextField.getText());

                inputFileReader = new FileReader(inputFile);
                inputBuffReader = new BufferedReader(inputFileReader);
                inventoryLine = inputBuffReader.readLine(); // Reads from file

                // Loops through the file unitl itemID is found or EOF reached
                while (inventoryLine != null)
                {
                    aScanner = new Scanner(inventoryLine).useDelimiter("\\s*,\\s*");
                    itemIDFromFile = aScanner.next();

                    if(itemIDFromFile.equals(userItemID)) // Indicates if item is found
                    {
                        cartLineArray = inventoryLine.split(csvSplitBy); // Places item values into the cart string array
                        found = true;
                        break; // Breaks out of loop
                    }
                    else
                    {
                        // Reads next line from file
                        inventoryLine = inputBuffReader.readLine();
                    }
                }

                // Item not found in file condition
                if (found == false)
                {
                    JOptionPane.showMessageDialog(null, "Item ID " + userItemID + " not in file", 
                                            "Nile Dot Com - ERROR", JOptionPane.ERROR_MESSAGE);
                    userItemID = "";
                    itemQuantity = 0;
                    idTextField.setText(null);
                    qtyTextField.setText(null);
                }
                else
                {
                    itemID = cartLineArray[0];
                    itemTitle = cartLineArray[1];
                    itemStatus = cartLineArray[2];
                    itemQuantityInFile = Integer.parseInt(cartLineArray[3]);
                    itemPrice = Double.parseDouble(cartLineArray[4]);

                    // Item not in stock condition
                    if (itemStatus.equals("false"))
                    {
                        JOptionPane.showMessageDialog(null, "Sorry... that item is out of stock, please try another item",
                                                "Nile Dot Com - ERROR", JOptionPane.ERROR_MESSAGE);
                        userItemID = "";
                        itemQuantity = 0;
                        idTextField.setText(null);
                        qtyTextField.setText(null);
                    }
                    // Insufficient stock at hand condition
                    else if (itemQuantity > itemQuantityInFile)
                    {
                        JOptionPane.showMessageDialog(null, "Insufficient stock. Only " + itemQuantityInFile + 
                                                    " on hand. Please reduce the quantity.", "Nile Dot Com - ERROR", 
                                                    JOptionPane.ERROR_MESSAGE);
                        itemQuantity = 0;
                        qtyTextField.setText(null);
                    }
                    // Zero quantity entered condition
                    else if (itemQuantity == 0)
                    {
                        JOptionPane.showMessageDialog(null, "Please enter a valid number of item quantity greater than 0.", 
                                                    "Nile Dot Com - ERROR", JOptionPane.ERROR_MESSAGE);
                        itemQuantity = 0;
                        qtyTextField.setText(null);
                    }
                    // If all else is good, proceed to produce correct output for user
                    else
                    {
                        // Get the currency instance
                        nF = NumberFormat.getCurrencyInstance(Locale.US);

                        if (itemQuantity >= 1 && itemQuantity <= 4)
                        {
                            itemSubtotal = itemPrice * itemQuantity;
                            orderSubtotal = orderSubtotal + itemSubtotal;
                            itemDetails = itemID + " " + itemTitle + " " + nF.format(itemPrice) + " " + itemQuantity + " " + 
                                          (int)(DISCOUNT_FOR_00 * 100) + "% " + nF.format(itemSubtotal);
                            itemDetailsArray[itemCount] = itemDetails;
                        }
                        else if (itemQuantity >= 5 && itemQuantity <= 9)
                        {
                            itemSubtotal = ((itemPrice * itemQuantity) * DISCOUNT_FOR_05) + (itemPrice * itemQuantity);
                            orderSubtotal = orderSubtotal + itemSubtotal;
                            itemDetails = itemID + " " + itemTitle + " " + nF.format(itemPrice) + " " + itemQuantity + " " + 
                                          (int)(DISCOUNT_FOR_05 * 100) + "% " + nF.format(itemSubtotal);
                            itemDetailsArray[itemCount] = itemDetails;
                        }
                        else if (itemQuantity >= 10 && itemQuantity <= 14)
                        {
                            itemSubtotal = ((itemPrice * itemQuantity) * DISCOUNT_FOR_10) + (itemPrice * itemQuantity);
                            orderSubtotal = orderSubtotal + itemSubtotal;
                            itemDetails = itemID + " " + itemTitle + " " + nF.format(itemPrice) + " " + itemQuantity + " " + 
                                          (int)(DISCOUNT_FOR_10 * 100) + "% " + nF.format(itemSubtotal);
                            itemDetailsArray[itemCount] = itemDetails;
                        }
                        else if (itemQuantity >= 15)
                        {
                            itemSubtotal = ((itemPrice * itemQuantity) * DISCOUNT_FOR_15) + (itemPrice * itemQuantity);
                            orderSubtotal = orderSubtotal + itemSubtotal;
                            itemDetails = itemID + " " + itemTitle + " " + nF.format(itemPrice) + " " + itemQuantity + " " + 
                                          (int)(DISCOUNT_FOR_15 * 100) + "% " + nF.format(itemSubtotal);
                            itemDetailsArray[itemCount] = itemDetails;
                        }
                        itemTextField.setText(itemDetails);

                        // Adjusts buttons' status
                        confirmB.setEnabled(true);
                        processB.setEnabled(false);
                        itemCount++;
                    }
                }
            }

            catch(FileNotFoundException fileNotFoundException)
            {
                JOptionPane.showMessageDialog(null, "Error: File not found", "Nile Dot Com - ERROR", 
                                            JOptionPane.ERROR_MESSAGE);
            }
            catch(IOException ioException)
            {
                JOptionPane.showMessageDialog(null, "Error: Problem reading from file", "Nile Dot Com - ERROR", 
                                             JOptionPane.ERROR_MESSAGE);
            }
            catch(NumberFormatException numberFormatException)
            {
                JOptionPane.showMessageDialog(null, "Error: Incorrent use of format", "Nile Dot Com - ERROR", 
                                             JOptionPane.ERROR_MESSAGE);
            }
        }
    }
//**************************************************************************************
    private class ConfirmButtonHandler implements ActionListener // Look at slide 15!
    {
        public void actionPerformed(ActionEvent e)
        {
            // Labels and input fields cleared for next input
            idTextField.setText(null);
            qtyTextField.setText(null);

            // Updates item number in labels
            idLabel.setText("Enter item ID for Item #" + (itemCount + 1) + ":");
            qtyLabel.setText("Enter quantity for Item #" + (itemCount + 1) + ":");
            totalLabel.setText("Current Subtotal for " + itemCount + " item(s):");
            cartLabel.setText("Your Shopping Cart Currently Contains " + itemCount + " Item(s)");
            
            // Updates cart items according to item number confirmed
            switch (itemCount) 
            {
                case 1:
                    cartArray[itemCount - 1].setText("Item " + itemCount + " - SKU: " + itemID + ", Desc: " + itemTitle + 
                                                    ", Price: Ea. " + nF.format(itemPrice) + ", Qty: " + itemQuantity + ", Total: " + 
                                                    nF.format(itemSubtotal));
                    processB.setText("Search for Item #" + (itemCount + 1));
                    confirmB.setText("Add Item #" + (itemCount + 1) + " To Cart");
                    confirmB.setEnabled(false);
                    processB.setEnabled(true);
                    viewB.setEnabled(true);
                    finishB.setEnabled(true);
                    break;
                case 2:
                    cartArray[itemCount - 1].setText("Item " + itemCount + " - SKU: " + itemID + ", Desc: " + itemTitle + 
                                                    ", Price: Ea. " + nF.format(itemPrice) + ", Qty: " + itemQuantity + ", Total: " + 
                                                    nF.format(itemSubtotal));
                    itemLabel.setText("Details for Item #" + itemCount + ":");
                    processB.setText("Search for Item #" + (itemCount + 1));
                    confirmB.setText("Add Item #" + (itemCount + 1) + " To Cart");
                    confirmB.setEnabled(false);
                    processB.setEnabled(true);
                    viewB.setEnabled(true);
                    finishB.setEnabled(true);
                    break;
                case 3:
                    cartArray[itemCount - 1].setText("Item " + itemCount + " - SKU: " + itemID + ", Desc: " + itemTitle + 
                                                    ", Price: Ea. " + nF.format(itemPrice) + ", Qty: " + itemQuantity + ", Total: " + 
                                                    nF.format(itemSubtotal));
                    itemLabel.setText("Details for Item #" + itemCount + ":");
                    processB.setText("Search for Item #" + (itemCount + 1));
                    confirmB.setText("Add Item #" + (itemCount + 1) + " To Cart");
                    confirmB.setEnabled(false);
                    processB.setEnabled(true);
                    viewB.setEnabled(true);
                    finishB.setEnabled(true);
                    break;
                case 4:
                    cartArray[itemCount - 1].setText("Item " + itemCount + " - SKU: " + itemID + ", Desc: " + itemTitle + 
                                                    ", Price: Ea. " + nF.format(itemPrice) + ", Qty: " + itemQuantity + ", Total: " + 
                                                    nF.format(itemSubtotal));
                    itemLabel.setText("Details for Item #" + itemCount + ":");
                    processB.setText("Search for Item #" + (itemCount + 1));
                    confirmB.setText("Add Item #" + (itemCount + 1) + " To Cart");
                    confirmB.setEnabled(false);
                    processB.setEnabled(true);
                    viewB.setEnabled(true);
                    finishB.setEnabled(true);
                    break;
                case 5:
                    cartArray[itemCount - 1].setText("Item " + itemCount + " - SKU: " + itemID + ", Desc: " + itemTitle + 
                                                    ", Price: Ea. " + nF.format(itemPrice) + ", Qty: " + itemQuantity + ", Total: " + 
                                                    nF.format(itemSubtotal));
                    itemLabel.setText("Details for Item #" + itemCount + ":");
                    processB.setText("Search for Item #" + (itemCount + 1));
                    confirmB.setText("Add Item #" + (itemCount + 1) + " To Cart");
                    confirmB.setEnabled(false);
                    processB.setEnabled(false);
                    viewB.setEnabled(true);
                    finishB.setEnabled(true);
                    break;
                default:
                    break;
            }

            // Reflects the subtotal for all confirmed items
            totalTextField.setText(nF.format(orderSubtotal));
        }
    }
//**************************************************************************************
    private class ViewButtonHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            // Prints cart items statements depending on the number of confirmed items
            switch(itemCount)
            {
                case 1:
                    JOptionPane.showMessageDialog(null, "1. " + itemDetailsArray[0], "Nile Dot Com - Current Shopping Status Cart", 
                                                 JOptionPane.INFORMATION_MESSAGE);
                    break;
                case 2:
                    JOptionPane.showMessageDialog(null, "1. " + itemDetailsArray[0] + "\n2. " + itemDetailsArray[1], 
                                            "Nile Dot Com - Current Shopping Status Cart", JOptionPane.INFORMATION_MESSAGE);
                    break;
                case 3:
                    JOptionPane.showMessageDialog(null, "1. " + itemDetailsArray[0] + "\n2. " + itemDetailsArray[1]
                                                + "\n3. " + itemDetailsArray[2], "Nile Dot Com - Current Shopping Status Cart", 
                                                JOptionPane.INFORMATION_MESSAGE);
                    break;
                case 4:
                    JOptionPane.showMessageDialog(null, "1. " + itemDetailsArray[0] + "\n2. " + itemDetailsArray[1]
                                                + "\n3. " + itemDetailsArray[2] + "\n4. " + itemDetailsArray[3], 
                                                "Nile Dot Com - Current Shopping Status Cart", JOptionPane.INFORMATION_MESSAGE);
                    break;
                case 5:
                    JOptionPane.showMessageDialog(null, "1. " + itemDetailsArray[0] + "\n2. " + itemDetailsArray[1]
                                                + "\n3. " + itemDetailsArray[2] + "\n4. " + itemDetailsArray[3] + 
                                                "\n5. " + itemDetailsArray[4], "Nile Dot Com - Current Shopping Status Cart", 
                                                JOptionPane.INFORMATION_MESSAGE);
                    break;
            }
        }
    }
//**************************************************************************************
    private class FinishButtonHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            // Set class variables
            StringBuilder transactionLine = new StringBuilder();
            FileWriter fw = null;
            Date date = new Date();
            LocalDateTime currentDateTime;
            DateTimeFormatter dateTimeFormat;
            String dateTimeStr = "";
            String timeStamp = "";

            try
            {
                fw = new FileWriter("transactions.csv", true);
                currentDateTime = LocalDateTime.now();
                dateTimeFormat = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
                dateTimeStr = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG).format(date); // Sets up
                timeStamp = currentDateTime.format(dateTimeFormat);

                transactionLine.append("Date: " + dateTimeStr + "\n\n");
                transactionLine.append("Number of line items: " + itemCount + "\n\n");
                transactionLine.append("Item# / ID / Title / Price / Qty / Disc % / Subtotal: \n\n");

                for (int itemIndex = 0; itemIndex < itemCount; itemIndex++)
                {
                    transactionLine.append((itemIndex + 1) + ". " + itemDetailsArray[itemIndex] + "\n");
                    fw.write(timeStamp + ", " + itemDetailsArray[itemIndex] + ", " + dateTimeStr + "\n");
                }

                transactionLine.append("\n\n");
                transactionLine.append("Order subtotal: " + nF.format(orderSubtotal) + "\n\n");
                transactionLine.append("Tax rate: " + (int)(TAX_RATE * 100) + "% \n\n");
                transactionLine.append("Tax amount: " + nF.format((TAX_RATE * orderSubtotal)) + "\n\n");
                transactionLine.append("ORDER TOTAL: " + nF.format((orderSubtotal + (TAX_RATE * orderSubtotal))) + "\n\n");
                transactionLine.append("Thanks for shopping at Nile Dot Com!");

                JOptionPane.showMessageDialog(null, transactionLine, "Nile Dot Com - FINAL INVOICE", JOptionPane.INFORMATION_MESSAGE);
                fw.close();

                // Disables text fields and buttons so user starts over
                processB.setEnabled(false);
                confirmB.setEnabled(false);
                viewB.setEnabled(false);
                finishB.setEnabled(false);
                idTextField.setText(null);
                qtyTextField.setText(null);
                idTextField.setEditable(false);
                qtyTextField.setEditable(false);
            }

            catch(FileNotFoundException fileNotFoundException)
            {
                JOptionPane.showMessageDialog(null, "Error: File not found", "Nile Dot Com - ERROR", 
                                            JOptionPane.ERROR_MESSAGE);
            }
            catch(IOException ioException)
            {
                JOptionPane.showMessageDialog(null, "Error: Problem reading from file", "Nile Dot Com - ERROR", 
                                             JOptionPane.ERROR_MESSAGE);
            }
            catch(NumberFormatException numberFormatException)
            {
                JOptionPane.showMessageDialog(null, "Error: Incorrent use of format", "Nile Dot Com - ERROR", 
                                             JOptionPane.ERROR_MESSAGE);
            }
        }
    }
//**************************************************************************************
    private class NewButtonHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            // Resets items holding arrays
            for (int i = 0; i < 5; i++)
            {
                cartLineArray[i] = null;
                itemDetailsArray[i] = null;
                cartArray[i].setText(null);
            }

            // Resets variables
            userItemID = "";
            itemID = "";
            itemTitle = "";
            itemStatus = "";
            itemDetails = "";
            itemPrice = 0; 
            itemSubtotal = 0;
            orderSubtotal = 0;
            orderTotal = 0;
            itemQuantity = 0;
            itemQuantityInFile = 0;
            itemCount = 0;

            // Resets labels, fields, and buttons
            idLabel.setText("Enter item ID for Item #" + (itemCount + 1) + ":");
            qtyLabel.setText("Enter quantity for Item #" + (itemCount + 1) + ":");
            itemLabel.setText("Details for Item #" + (itemCount + 1) + ":");
            totalLabel.setText("Current Subtotal for " + itemCount + " item(s):");
            cartLabel.setText("Your Shopping Cart is Currently Empty");

            idTextField.setText(null);
            qtyTextField.setText(null);
            itemTextField.setText(null);
            totalTextField.setText(null);

            idTextField.setEditable(true);
            qtyTextField.setEditable(true);

            processB.setText("Search for Item #" + (itemCount + 1));
            confirmB.setText("Add Item #" + (itemCount + 1) + " To Cart");

            //Resets Button back to the beginning
            processB.setEnabled(true);
            confirmB.setEnabled(false);
        }
    }
//**************************************************************************************
    private class ExitButtonHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            System.exit(0);     
        }
    }
//**************************************************************************************
    public static void main(String[] args)
    {
        JFrame aNewStore = new NileDotComFall2024(); // Create the frame object
        aNewStore.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        aNewStore.setVisible(true); // Display the frame
    }
}
