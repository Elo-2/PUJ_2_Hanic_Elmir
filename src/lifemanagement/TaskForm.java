package lifemanagement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.util.ArrayList;

public class TaskForm extends JFrame {

    private JPanel mainPanel;

    private JLabel analyticsLabel;

    private JTextField nameField;
    private JTextField descriptionField;

    private JComboBox<String> priorityCombo;
    private JComboBox<String> statusCombo;

    private JButton addButton;
    private JButton deleteButton;
    private JButton completeButton;

    private JTable table1;


    private User user;
    private TaskManager manager;


    public TaskForm(User user){

        this.user = user;

        manager = new TaskManager();


        setTitle("Task Planner");
        setContentPane(mainPanel);
        setSize(750,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        loadTable();
        updateAnalytics();



        addButton.addActionListener(e -> {


            Task task = new Task(

                    user.getUserId(),
                    nameField.getText(),
                    descriptionField.getText(),
                    priorityCombo.getSelectedItem().toString(),
                    "Osobno",
                    LocalDate.now()

            );


            manager.addTask(task);


            loadTable();
            updateAnalytics();


            nameField.setText("");
            descriptionField.setText("");

        });



        completeButton.addActionListener(e -> {


            int row = table1.getSelectedRow();


            if(row >= 0){

                String id =
                        table1.getValueAt(row,0).toString();


                manager.updateTaskStatus(
                        id,
                        "Završeno"
                );


                loadTable();
                updateAnalytics();

            }

        });



        deleteButton.addActionListener(e -> {


            int row = table1.getSelectedRow();


            if(row >=0){

                String id =
                        table1.getValueAt(row,0).toString();


                manager.deleteTask(id);


                loadTable();
                updateAnalytics();

            }

        });


        setVisible(true);

    }



    private void loadTable(){


        ArrayList<Task> tasks =
                manager.getTasksByUser(user.getUserId());


        DefaultTableModel model =
                new DefaultTableModel();


        model.addColumn("ID");
        model.addColumn("Naziv");
        model.addColumn("Prioritet");
        model.addColumn("Status");
        model.addColumn("Opis");


        for(Task t: tasks){


            model.addRow(new Object[]{

                    t.getTaskId(),
                    t.getTitle(),
                    t.getPriority(),
                    t.getStatus(),
                    t.getDescription()

            });

        }


        table1.setModel(model);

    }




    private void updateAnalytics(){


        int total =
                manager.getTotalTasks(user.getUserId());


        double percentage =
                manager.getCompletionPercentage(
                        user.getUserId()
                );


        analyticsLabel.setText(
                "Ukupno zadataka: "
                        + total
                        +
                        " | Završeno: "
                        + percentage
                        +"%"
        );

    }



    public JPanel getMainPanel(){

        return mainPanel;

    }

}