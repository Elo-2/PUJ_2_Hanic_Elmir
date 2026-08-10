package lifemanagement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.util.ArrayList;

public class MoodForm extends JFrame {

    private JPanel mainPanel;

    private JLabel analyticsLabel;

    private JComboBox<String> moodCombo;

    private JTextField noteField;

    private JButton addButton;
    private JButton deleteButton;

    private JTable table;


    private User user;
    private MoodManager manager;


    public MoodForm(User user) {

        this.user = user;

        manager = new MoodManager();


        setTitle("Mood Tracker");
        setContentPane(mainPanel);
        setSize(700,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        loadTable();
        updateAnalytics();



        addButton.addActionListener(e -> {


            Mood mood = new Mood(

                    user.getUserId(),
                    LocalDate.now(),
                    moodCombo.getSelectedItem().toString(),
                    noteField.getText()

            );


            manager.addMood(mood);


            loadTable();
            updateAnalytics();


            noteField.setText("");

        });



        deleteButton.addActionListener(e -> {


            int row = table.getSelectedRow();


            if(row >= 0){


                String id =
                        table.getValueAt(row,0).toString();


                manager.deleteMood(id);


                loadTable();
                updateAnalytics();

            }

        });



        setVisible(true);

    }



    private void loadTable(){


        ArrayList<Mood> moods =
                manager.getMoodsByUser(
                        user.getUserId()
                );


        DefaultTableModel model =
                new DefaultTableModel();



        model.addColumn("ID");
        model.addColumn("Datum");
        model.addColumn("Raspoloženje");
        model.addColumn("Bilješka");



        for(Mood m : moods){


            model.addRow(new Object[]{

                    m.getMoodId(),
                    m.getDate(),
                    m.getMood(),
                    m.getNote()

            });

        }


        table.setModel(model);

    }




    private void updateAnalytics(){


        String average =
                manager.getAverageMood(
                        user.getUserId()
                );


        analyticsLabel.setText(
                "Prosječno raspoloženje: "
                        + average
        );

    }



    public JPanel getMainPanel(){

        return mainPanel;

    }

}