package lifemanagement;

import javax.swing.*;

public class MainMenuForm extends JFrame {


    private User user;
    private JButton sleepButton;
    private JButton analyticsButton;
    private JButton taskButton;
    private JButton financeButton;
    private JButton moodButton;
    private JButton habitButton;
    private JPanel mainPanel;
    private JLabel welcomeLabel;


    public MainMenuForm(User user) {

        this.user = user;

        setTitle("Life Management System");
        setContentPane(mainPanel);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        welcomeLabel.setText("Dobrodošao, " + user.getFirstName());

        financeButton.addActionListener(e -> {
            JFrame frame = new JFrame("Finance Tracker");
            frame.setContentPane(new FinanceTrackerForm(user).getMainPanel());
            frame.setSize(700, 550);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });

        habitButton.addActionListener(e -> {
            JFrame frame = new JFrame("Habit Tracker");
            frame.setContentPane(new HabitForm(user).getMainPanel());
            frame.setSize(700, 500);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });

        sleepButton.addActionListener(e -> {

            JFrame frame = new JFrame("Sleep Tracker");

            frame.setContentPane(
                    new SleepForm(user).getMainPanel()
            );

            frame.setSize(700,500);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

        });

        taskButton.addActionListener(e -> {

            JFrame frame = new JFrame("Task Planner");

            frame.setContentPane(
                    new TaskForm(user).getMainPanel()
            );

            frame.setSize(750,550);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

        });

        moodButton.addActionListener(e -> {

            JFrame frame=new JFrame("Mood Tracker");

            frame.setContentPane(
                    new MoodForm(user).getMainPanel()
            );

            frame.setSize(650,500);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

        });

        analyticsButton.addActionListener(e -> {

            JFrame frame = new JFrame("Analytics");

            frame.setContentPane(
                    new AnalyticsForm(user).getMainPanel()
            );

            frame.setSize(700,600);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

        });

        setVisible(true);
    }
}