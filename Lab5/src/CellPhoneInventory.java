import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Paths;
import java.util.*;

public class CellPhoneInventory extends JFrame {
    JLabel labelModel, labelManufacturer, labelRetailPrice, labelBanner;
    JTextField jTextFieldModel, jTextFieldManufacturer, jTextFieldRetailPrice;
    JButton jButtonAdd, jButtonSave, jButtonNext, jButtonShow;
    JPanel newPanel, buttonPanel;
    ArrayList<CellPhone> phoneArrayList = new ArrayList<CellPhone>();

    CellPhoneInventory() {
        setTitle("Cellphone Inventory");
        setLayout(new BorderLayout());
        labelBanner = new JLabel("Cellphone Inventory Management");
        labelBanner.setFont(new Font("Serif", Font.BOLD, 20));
        labelBanner.setForeground(Color.BLUE);
        labelModel = new JLabel("Model");
        labelManufacturer = new JLabel("Manufacturer");
        labelRetailPrice = new JLabel("Retail Price");
        jTextFieldModel = new JTextField(15);
        jTextFieldManufacturer = new JTextField(15);
        jTextFieldRetailPrice = new JTextField(15);
        jButtonAdd = new JButton("Add");
        jButtonSave = new JButton("Save");
        jButtonNext = new JButton("Next");
        jButtonShow = new JButton("Show Inventory");
        newPanel = new JPanel(new GridLayout(3, 2));
        newPanel.add(labelModel);
        newPanel.add(jTextFieldModel);
        newPanel.add(labelManufacturer);
        newPanel.add(jTextFieldManufacturer);
        newPanel.add(labelRetailPrice);
        newPanel.add(jTextFieldRetailPrice);
        buttonPanel = new JPanel(new GridLayout(2, 2));
        buttonPanel.add(jButtonAdd);
        buttonPanel.add(jButtonNext);
        buttonPanel.add(jButtonSave);
        buttonPanel.add(jButtonShow);
        add(labelBanner, BorderLayout.NORTH);
        add(newPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        jButtonAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String model = jTextFieldModel.getText();
                    String manufacturer = jTextFieldManufacturer.getText();
                    double price = Double.parseDouble(jTextFieldRetailPrice.getText());

                    // Validate model
                    if (model.isEmpty()) {
                        throw new InvalidModelException("Model cannot be an empty string.");
                    }

                    // Validate manufacturer
                    if (manufacturer.isEmpty()) {
                        throw new InvalidManufacturerException("Manufacturer cannot be an empty string.");
                    }

                    // Validate retail price
                    if (price < 0 || price > 1500) {
                        throw new InvalidRetailPriceException("Retail price must be between 0 and 1500.");
                    }

                    phoneArrayList.add(new CellPhone(model, manufacturer, price));

                    String inventoryDisplay = "";
                    for (CellPhone p : phoneArrayList) {
                        System.out.println(p);
                        inventoryDisplay += p;
                    }

                    JOptionPane.showMessageDialog(null, inventoryDisplay);
                } catch (InvalidModelException ime) {
                    JOptionPane.showMessageDialog(null, ime.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                } catch (InvalidManufacturerException ime) {
                    JOptionPane.showMessageDialog(null, ime.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                } catch (InvalidRetailPriceException irpe) {
                    JOptionPane.showMessageDialog(null, irpe.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(null, "Invalid Price Format", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        jButtonNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jTextFieldModel.setText("");
                jTextFieldManufacturer.setText("");
                jTextFieldRetailPrice.setText("");
            }
        });

        jButtonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Formatter outCellPhoneList = null;
                try {
                    // file stream for output file
                    outCellPhoneList = new Formatter("cellPhones.txt");

                    for (int i = 0; i < phoneArrayList.size(); i++) {
                        outCellPhoneList.format("%s %s %.2f\n", phoneArrayList.get(i).getModel(),
                                phoneArrayList.get(i).getManufacturer(),
                                phoneArrayList.get(i).getRetailPrice());
                    }
                    System.out.println("cellphone list stored in txt file.");
                } catch (SecurityException securityException) {
                    System.err.println("You do not have write access to this file.");
                    System.exit(1);
                } catch (FileNotFoundException fileNotFoundException) {
                    System.err.println("Error creating file.");
                    System.exit(1);
                } catch (FormatterClosedException closedException) {
                    System.err.println("Error writing to file - file has been closed.");
                    System.exit(1);
                } catch (IllegalFormatException formatException) {
                    System.err.println("Error with output.");
                    System.exit(1);
                } finally {
                    if (outCellPhoneList != null) {
                        outCellPhoneList.close();
                    }
                }
            }
        });

        jButtonShow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String msg = String.format("%-20s%-20s%10s%n", "Model", "Manufacturer",
                        "retail price");
                // open cellPhoneList.txt, read its contents and close the file
                try (Scanner input = new Scanner(Paths.get("cellPhones.txt"))) {

                    // read record from file
                    while (input.hasNext()) { // while there is more to read
                        // display record contents
                        msg += String.format("%-20s%-20s%10.2f%n", input.next(),
                                input.next(), input.nextDouble());
                    }
                } catch (NoSuchElementException | IllegalStateException | IOException exception) {
                    //e.printStackTrace();
                    System.out.println("Error: " + exception.getMessage());
                }
                JOptionPane.showMessageDialog(null, msg);
            }
        });
    }

    public static void main(String[] args) {
        CellPhoneInventory cellPhoneInventory = new CellPhoneInventory();
        cellPhoneInventory.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cellPhoneInventory.setSize(400, 300);
        cellPhoneInventory.setVisible(true);
    }
}


