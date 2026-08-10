package lifemanagement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;


public class HabitForm {


    private JPanel mainPanel;
    private JTable habitTable;

    private JTextField nameField;
    private JTextField descriptionField;

    private JComboBox<String> categoryCombo;

    private JButton addButton;
    private JButton completeButton;
    private JButton deleteButton;


    private JLabel analyticsLabel;


    private HabitManager manager;

    private User user;


    public HabitForm(User user){

        this.user=user;

        manager=new HabitManager();


        addButton.addActionListener(e -> {


            Habit habit = new Habit(
                    user.getUserId(),
                    nameField.getText(),
                    descriptionField.getText(),
                    categoryCombo.getSelectedItem().toString()
            );


            manager.addHabit(habit);
            nameField.setText("");
            descriptionField.setText("");
            categoryCombo.setSelectedIndex(0);

            loadTable();

            updateAnalytics();


        });



        completeButton.addActionListener(e -> {


            int row=habitTable.getSelectedRow();


            if(row>=0){

                String id=
                        habitTable.getValueAt(row,0).toString();


                manager.markHabitAsCompleted(id);


                loadTable();
                updateAnalytics();

            }


        });



        deleteButton.addActionListener(e -> {


            int row=habitTable.getSelectedRow();


            if(row>=0){

                String id=
                        habitTable.getValueAt(row,0).toString();


                manager.deleteHabit(id);


                loadTable();
                updateAnalytics();

            }


        });



        loadTable();
        updateAnalytics();

    }



    private void loadTable(){

        ArrayList<Habit> list=
                manager.getHabitsByUser(user.getUserId());


        DefaultTableModel model =
                new DefaultTableModel();


        model.addColumn("ID");
        model.addColumn("Naziv");
        model.addColumn("Kategorija");
        model.addColumn("Dana");


        for(Habit h:list){

            model.addRow(new Object[]{

                    h.getHabitId(),
                    h.getName(),
                    h.getCategory(),
                    h.getConsecutiveDays()

            });

        }


        habitTable.setModel(model);

    }



    private void updateAnalytics(){

        double avg=
                manager.getAverageCompletionPercentage(
                        user.getUserId()
                );


        analyticsLabel.setText(
                "Prosjek završetka: "
                        + avg +"%"
        );

    }



    public JPanel getMainPanel(){

        return mainPanel;

    }

}