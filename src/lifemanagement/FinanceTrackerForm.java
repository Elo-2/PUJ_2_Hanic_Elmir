package lifemanagement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.io.FileWriter;

public class FinanceTrackerForm {

    private JPanel mainPanel;

    private User currentUser;

    private JTextField PRAĆENJELIČNIHFINANSIJATextField;
    private JTextField unesiteIznosVašegPrihodaTextField;
    private JTextField opišiteOvajIzvorPrihodaTextField;

    private JTextField amountField;
    private JTextField descriptionField;

    private JComboBox<String> typeCombo;
    private JComboBox<String> categoryCombo;

    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton exportButton;

    private JTable transactionTable;

    private JLabel incomeLabel;
    private JLabel expenseLabel;
    private JLabel balanceLabel;


    private TransactionManager manager;

    private String selectedId = null;


    public FinanceTrackerForm(User user) {

        currentUser = user;

        manager = new TransactionManager();


        loadDataIntoTable();
        updateSummary();


        // ADD

        addButton.addActionListener(e -> {

            try {

                Transaction t = new Transaction(
                        null,
                        currentUser.getUserId(),
                        (String) typeCombo.getSelectedItem(),
                        (String) categoryCombo.getSelectedItem(),
                        Double.parseDouble(amountField.getText()),
                        descriptionField.getText()
                );


                manager.addTransaction(t);

                loadDataIntoTable();
                updateSummary();


            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        null,
                        "Greška pri unosu!"
                );

            }

        });



        // UPDATE

        updateButton.addActionListener(e -> {


            if(selectedId == null){

                JOptionPane.showMessageDialog(
                        null,
                        "Odaberi transakciju!"
                );

                return;
            }



            Transaction t = new Transaction(

                    selectedId,
                    currentUser.getUserId(),
                    (String) typeCombo.getSelectedItem(),
                    (String) categoryCombo.getSelectedItem(),
                    Double.parseDouble(amountField.getText()),
                    descriptionField.getText()

            );


            manager.updateTransaction(selectedId,t);


            loadDataIntoTable();
            updateSummary();

            selectedId=null;


        });



        // DELETE

        deleteButton.addActionListener(e -> {


            if(selectedId == null){

                JOptionPane.showMessageDialog(
                        null,
                        "Odaberi transakciju!"
                );

                return;
            }



            manager.deleteTransaction(selectedId);


            loadDataIntoTable();
            updateSummary();

            selectedId=null;


        });



        // EXPORT

        exportButton.addActionListener(e -> {


            try {


                FileWriter writer =
                        new FileWriter("finance_export.txt");


                double income =
                        manager.getTotalIncome(
                                currentUser.getUserId()
                        );


                double expense =
                        manager.getTotalExpense(
                                currentUser.getUserId()
                        );



                writer.write(
                        "Ukupan prihod: "
                                + income
                                + "\n"
                );


                writer.write(
                        "Ukupan rashod: "
                                + expense
                                + "\n"
                );


                writer.write(
                        "Saldo: "
                                + (income-expense)
                                + "\n\n"
                );



                writer.write("Transakcije:\n");



                for(Transaction t :
                        manager.getAllTransactions(
                                currentUser.getUserId()
                        )){


                    writer.write(
                            t.getType()
                                    +" | "
                                    +t.getCategory()
                                    +" | "
                                    +t.getAmount()
                                    +" | "
                                    +t.getDescription()
                                    +"\n"
                    );

                }


                writer.close();


                JOptionPane.showMessageDialog(
                        null,
                        "Export završen!"
                );



            }catch(Exception ex){

                JOptionPane.showMessageDialog(
                        null,
                        "Greška kod exporta!"
                );

            }


        });


    }



    private void loadDataIntoTable(){


        ArrayList<Transaction> list =
                manager.getAllTransactions(
                        currentUser.getUserId()
                );



        DefaultTableModel model =
                new DefaultTableModel();



        model.addColumn("ID");
        model.addColumn("Vrsta");
        model.addColumn("Kategorija");
        model.addColumn("Iznos");
        model.addColumn("Opis");



        for(Transaction t:list){


            model.addRow(
                    new Object[]{

                            t.getId(),
                            t.getType(),
                            t.getCategory(),
                            t.getAmount(),
                            t.getDescription()

                    }
            );

        }



        transactionTable.setModel(model);



        transactionTable
                .getSelectionModel()
                .addListSelectionListener(e -> {


                    int row =
                            transactionTable.getSelectedRow();



                    if(row>=0){


                        selectedId =
                                transactionTable
                                        .getValueAt(row,0)
                                        .toString();



                        typeCombo.setSelectedItem(
                                transactionTable.getValueAt(row,1)
                        );


                        categoryCombo.setSelectedItem(
                                transactionTable.getValueAt(row,2)
                        );


                        amountField.setText(
                                transactionTable.getValueAt(row,3)
                                        .toString()
                        );


                        descriptionField.setText(
                                transactionTable.getValueAt(row,4)
                                        .toString()
                        );


                    }


                });


    }



    private void updateSummary(){


        double income =
                manager.getTotalIncome(
                        currentUser.getUserId()
                );



        double expense =
                manager.getTotalExpense(
                        currentUser.getUserId()
                );



        incomeLabel.setText(
                "Prihod: "
                        +income
        );

        expenseLabel.setText(
                "Rashod: "
                        +expense
        );


        balanceLabel.setText(
                "Saldo: "
                        +(income-expense)
        );


    }



    public JPanel getMainPanel(){

        return mainPanel;

    }

}