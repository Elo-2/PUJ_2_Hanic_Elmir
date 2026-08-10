package lifemanagement;

import javax.swing.*;

public class AnalyticsForm extends JFrame {


    private JPanel mainPanel;

    private JLabel incomeLabel;
    private JLabel expenseLabel;
    private JLabel balanceLabel;

    private JLabel habitLabel;
    private JLabel taskLabel;
    private JLabel moodLabel;

    private JButton refreshButton;


    private User user;


    private TransactionManager transactionManager;
    private HabitManager habitManager;
    private TaskManager taskManager;
    private MoodManager moodManager;



    public AnalyticsForm(User user) {


        this.user = user;


        transactionManager = new TransactionManager();
        habitManager = new HabitManager();
        taskManager = new TaskManager();
        moodManager = new MoodManager();



        setTitle("Analytics");
        setContentPane(mainPanel);
        setSize(600,450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);



        updateAnalytics();



        refreshButton.addActionListener(e -> {

            updateAnalytics();

        });



        setVisible(true);

    }



    private void updateAnalytics(){


        // FINANCE


        double income =
                transactionManager.getTotalIncome(
                        user.getUserId()
                );


        double expense =
                transactionManager.getTotalExpense(
                        user.getUserId()
                );



        incomeLabel.setText(
                "Ukupan prihod: " + income
        );


        expenseLabel.setText(
                "Ukupan rashod: " + expense
        );


        balanceLabel.setText(
                "Saldo: " + (income - expense)
        );




        // HABITS


        double habit =
                habitManager.getAverageCompletionPercentage(
                        user.getUserId()
                );


        habitLabel.setText(
                "Navike završene: "
                        + habit
                        + "%"
        );




        // TASKS


        double tasks =
                taskManager.getCompletionPercentage(
                        user.getUserId()
                );


        taskLabel.setText(
                "Zadaci završeni: "
                        + tasks
                        + "%"
        );




        // MOOD


        String mood =
                moodManager.getAverageMood(
                        user.getUserId()
                );


        moodLabel.setText(
                "Prosječno raspoloženje: "
                        + mood
        );


    }



    public JPanel getMainPanel(){

        return mainPanel;

    }

}