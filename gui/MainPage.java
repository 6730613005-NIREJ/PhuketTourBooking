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
        setSize(400, 350);
        setLocationRelativeTo(null);

        titleLabel = new JLabel("Phuket Tour Booking Manager", SwingConstants.CENTER);
        titleLabel.setForeground(Color.BLUE);
        titleLabel.setFont(new Font("", Font.BOLD, 18));

        enrollButton = new JButton("Add Tour Booking");
        enrollButton.addActionListener(this);
        searchButton = new JButton("Search Bookings");
        searchButton.addActionListener(this);
        exitButton = new JButton("Exit");
        exitButton.addActionListener(this);

        enrollButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        searchButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // same size for all 3 buttons
        enrollButton.setPreferredSize(new Dimension(220, 50));
        searchButton.setPreferredSize(new Dimension(220, 50));
        exitButton.setPreferredSize(new Dimension(220, 50));

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(enrollButton);
        panel.add(Box.createVerticalStrut(15));   // empty space between buttons
        panel.add(searchButton);
        panel.add(Box.createVerticalStrut(15));
        panel.add(exitButton);

        add(titleLabel, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
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