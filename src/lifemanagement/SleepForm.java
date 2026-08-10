package lifemanagement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.util.ArrayList;

public class SleepForm extends JFrame {

    private JPanel mainPanel;
    private JTable sleepTable;

    private JTextField hoursField;
    private JTextField notesField;

    private JComboBox<String> qualityCombo;

    private JButton addButton;
    private JButton deleteButton;
    private JButton analyticsButton;

    private User user;
    private SleepManager manager;

    public SleepForm(User user) {

        this.user = user;
        manager = new SleepManager();

        setTitle("Sleep Tracker");
        setContentPane(mainPanel);
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        loadTable();

        addButton.addActionListener(e -> {

            try {

                Sleep sleep = new Sleep(
                        user.getUserId(),
                        LocalDate.now(),
                        Double.parseDouble(hoursField.getText()),
                        qualityCombo.getSelectedItem().toString(),
                        notesField.getText()
                );

                manager.addSleepRecord(sleep);

                loadTable();

                hoursField.setText("");
                notesField.setText("");

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Neispravan unos!"
                );

            }

        });

        deleteButton.addActionListener(e -> {

            int row = sleepTable.getSelectedRow();

            if (row >= 0) {

                String id = sleepTable.getValueAt(row, 0).toString();

                manager.deleteSleepRecord(id);

                loadTable();

            }

        });

        analyticsButton.addActionListener(e -> {

            double avg = manager.getAverageSleepHours(user.getUserId());

            double week = manager.getLastWeekAverageSleep(user.getUserId());

            String quality = manager.getMostCommonQuality(user.getUserId());

            int total = manager.getTotalSleepRecords(user.getUserId());

            int good = manager.getGoodSleepNights(user.getUserId());

            JOptionPane.showMessageDialog(
                    this,
                    "Prosjek sati: " + avg +
                            "\nProsjek zadnjih 7 dana: " + week +
                            "\nNajčešća kvaliteta: " + quality +
                            "\nUkupno zapisa: " + total +
                            "\nDobrih noći: " + good
            );

        });

        setVisible(true);
    }

    private void loadTable() {

        ArrayList<Sleep> list =
                manager.getSleepByUser(user.getUserId());

        DefaultTableModel model =
                new DefaultTableModel();

        model.addColumn("ID");
        model.addColumn("Datum");
        model.addColumn("Sati");
        model.addColumn("Kvalitet");
        model.addColumn("Bilješke");

        for (Sleep s : list) {

            model.addRow(new Object[]{
                    s.getSleepId(),
                    s.getDate(),
                    s.getHours(),
                    s.getQuality(),
                    s.getNotes()
            });

        }

        sleepTable.setModel(model);

    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}