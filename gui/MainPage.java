package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// PAGE 1: landing page with 3 buttons
public class MainPage extends JFrame implements ActionListener {

    private JPanel panel;
    private JLabel titleLabel;
    private JButton enrollButton;
    private JButton searchButton;
    private JButton exitButton;

    public MainPage() {
        setTitle("Phuket Tour Booking Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(520, 430);
        setLocationRelativeTo(null);

        titleLabel = GuiStyle.title("Phuket Tour Booking");

        enrollButton = new JButton("Add Tour Booking");
        enrollButton.addActionListener(this);
        searchButton = new JButton("Search Bookings");
        searchButton.addActionListener(this);
        exitButton = new JButton("Exit");
        exitButton.addActionListener(this);

        panel = GuiStyle.card();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(12, 55, 28, 55),
                BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(226, 232, 240)),
                        BorderFactory.createEmptyBorder(24, 30, 24, 30))));
        JLabel subtitle = GuiStyle.subtitle("Your Phuket tour bookings, all in one place");
        panel.add(subtitle);
        panel.add(Box.createVerticalStrut(24));
        for (JButton button : new JButton[]{enrollButton, searchButton, exitButton}) {
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setMaximumSize(new Dimension(320, 48));
        }
        panel.add(enrollButton);
        panel.add(Box.createVerticalStrut(14));
        panel.add(searchButton);
        panel.add(Box.createVerticalStrut(14));
        panel.add(exitButton);

        add(titleLabel, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
        GuiStyle.apply(this);
        enrollButton.setForeground(Color.BLACK);
        searchButton.setForeground(Color.BLACK);
        exitButton.setForeground(Color.BLACK);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();

        if (actionCommand.equals("Add Tour Booking")) {
            new EnrollPage();
        } else if (actionCommand.equals("Search Bookings")) {
            new SearchPage();
        } else if (actionCommand.equals("Exit")) {
            int chosenOption = JOptionPane.showConfirmDialog(this,
                    "Do you want to exit the program?",
                    "Phuket Tour Booking Manager", JOptionPane.OK_CANCEL_OPTION);
            if (chosenOption == JOptionPane.OK_OPTION) {
                System.exit(0);
            }
        }
    }

    public static void main(String[] args) {
        // use the look of the computer (Mac / Windows) instead of old Java style
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // if it fails, Java keeps its default look
        }
        // bigger, easier to read font for the whole program
        Font f = new Font("SansSerif", Font.PLAIN, 16);
        UIManager.put("Label.font", f);
        UIManager.put("Button.font", f);
        UIManager.put("TextField.font", f);
        UIManager.put("ComboBox.font", f);
        UIManager.put("TextArea.font", f);
        UIManager.put("CheckBox.font", f);

        new MainPage();
    }
}
