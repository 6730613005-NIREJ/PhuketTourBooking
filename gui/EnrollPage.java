package gui;

import javax.swing.*;

import database.TourDatabase;
import interfaces.TourInterface;
import tours.LandTour;
import tours.SeaTour;
import tours.ShowTicket;

import java.awt.*;
import java.awt.event.*;

// PAGE 2: add a booking and show all bookings
public class EnrollPage extends JFrame implements ActionListener {

    // common fields
    private JComboBox<String> categoryBox;
    private JTextField idTxt, customerTxt, tourNameTxt, priceTxt, guestsTxt;

    // CardLayout: shows only one of the 3 panels at a time
    private CardLayout cardLayout;
    private JPanel cardPanel;

    // Sea panel
    private JComboBox<String> boatBox;
    private JTextField islandsTxt;
    // Land panel
    private JComboBox<String> activityBox;
    private JCheckBox pickupBox;
    // Show panel
    private JTextField showTimeTxt;
    private JComboBox<String> seatBox;

    private JButton addBtn, clearBtn, backBtn;
    private JTextArea listArea;

    public EnrollPage() {
        super("Phuket Tour Booking Manager - Add Booking");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);

        JLabel titleLabel = new JLabel("Tour Booking Entry", SwingConstants.CENTER);
        titleLabel.setForeground(Color.BLUE);
        titleLabel.setFont(new Font("", Font.BOLD, 20));

        // ----- common part (same for every category) -----
        String[] categories = {"Sea Tour", "Land Tour", "Show Ticket"};
        categoryBox = new JComboBox<>(categories);
        categoryBox.addActionListener(this);
        idTxt = new JTextField();
        customerTxt = new JTextField();
        tourNameTxt = new JTextField();
        priceTxt = new JTextField();
        guestsTxt = new JTextField();

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(6, 2, 10, 10));
        formPanel.add(new JLabel("Tour category"));
        formPanel.add(categoryBox);
        formPanel.add(new JLabel("Tour ID"));
        formPanel.add(idTxt);
        formPanel.add(new JLabel("Customer name"));
        formPanel.add(customerTxt);
        formPanel.add(new JLabel("Tour name"));
        formPanel.add(tourNameTxt);
        formPanel.add(new JLabel("Price per person (THB)"));
        formPanel.add(priceTxt);
        formPanel.add(new JLabel("Number of guests"));
        formPanel.add(guestsTxt);

        // ----- Sea panel -----
        String[] boats = {"Speedboat", "Catamaran", "Yacht", "None"};
        boatBox = new JComboBox<>(boats);
        islandsTxt = new JTextField("0");
        JPanel seaPanel = new JPanel();
        seaPanel.setLayout(new GridLayout(2, 2, 10, 10));
        seaPanel.add(new JLabel("Boat type"));
        seaPanel.add(boatBox);
        seaPanel.add(new JLabel("Number of islands (0 if none)"));
        seaPanel.add(islandsTxt);

        // ----- Land panel -----
        String[] activities = {"Elephant Care", "Elephant Trekking", "ATV",
                "Rafting", "Zipline", "City Tour"};
        activityBox = new JComboBox<>(activities);
        pickupBox = new JCheckBox();
        JPanel landPanel = new JPanel();
        landPanel.setLayout(new GridLayout(2, 2, 10, 10));
        landPanel.add(new JLabel("Activity type"));
        landPanel.add(activityBox);
        landPanel.add(new JLabel("Hotel pickup"));
        landPanel.add(pickupBox);

        // ----- Show panel -----
        showTimeTxt = new JTextField();
        String[] seats = {"Standard", "VIP", "Entry"};
        seatBox = new JComboBox<>(seats);
        JPanel showPanel = new JPanel();
        showPanel.setLayout(new GridLayout(2, 2, 10, 10));
        showPanel.add(new JLabel("Show time (e.g. 18:00 or All day)"));
        showPanel.add(showTimeTxt);
        showPanel.add(new JLabel("Seat type"));
        showPanel.add(seatBox);

        // ----- put the 3 panels into the CardLayout -----
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.add(seaPanel, "Sea Tour");
        cardPanel.add(landPanel, "Land Tour");
        cardPanel.add(showPanel, "Show Ticket");

        // ----- buttons ----->
        addBtn = new JButton("Add Booking");
        addBtn.addActionListener(this);
        clearBtn = new JButton("Clear");
        clearBtn.addActionListener(this);
        backBtn = new JButton("Back to Main Page");
        backBtn.addActionListener(this);
        JPanel btnPanel = new JPanel();
        btnPanel.add(addBtn);
        btnPanel.add(clearBtn);
        btnPanel.add(backBtn);

        // ----- list of all bookings -----
        listArea = new JTextArea();
        listArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(listArea);

        // ----- put everything on the window -----
        // inputPanel = form + category panel + buttons (only as tall as needed)
        JPanel inputPanel = new JPanel(new BorderLayout(10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        inputPanel.add(formPanel, BorderLayout.NORTH);
        inputPanel.add(cardPanel, BorderLayout.CENTER);
        inputPanel.add(btnPanel, BorderLayout.SOUTH);

        // the booking list uses all the remaining space
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(inputPanel, BorderLayout.NORTH);
        bottomPanel.add(scrollPane, BorderLayout.CENTER);

        add(titleLabel, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.CENTER);

        showAllBookings();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // if the category combo box changed, show the matching panel
        if (e.getSource() == categoryBox) {
            String category = (String) categoryBox.getSelectedItem();
            cardLayout.show(cardPanel, category);
            return;
        }

        String actionCommand = e.getActionCommand();
        if (actionCommand.equals("Add Booking")) {
            addBooking();
        } else if (actionCommand.equals("Clear")) {
            clearTextField();
        } else if (actionCommand.equals("Back to Main Page")) {
            dispose();
        }
    }

    private void addBooking() {
        String category = (String) categoryBox.getSelectedItem();
        String id = idTxt.getText().trim();
        String customer = customerTxt.getText().trim();
        String tourName = tourNameTxt.getText().trim();

        // 1. empty required fields
        if (id.equals("") || customer.equals("") || tourName.equals("")) {
            JOptionPane.showMessageDialog(this, "Please fill in Tour ID, Customer name and Tour name.");
            return;
        }
        if (category.equals("Show Ticket") && showTimeTxt.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Please enter the show time.");
            return;
        }

        // 2. duplicate Tour ID
        if (TourDatabase.idExists(id)) {
            JOptionPane.showMessageDialog(this, "This Tour ID already exists.");
            return;
        }

        // 3. create the object (errors are caught below)
        String field = "";   // remembers which field we are reading
        try {
            field = "Price per person";
            double price = Double.parseDouble(priceTxt.getText().trim());
            field = "Number of guests";
            int guests = Integer.parseInt(guestsTxt.getText().trim());

            TourInterface t;
            if (category.equals("Sea Tour")) {
                String boat = (String) boatBox.getSelectedItem();
                field = "Number of islands";
                int islands = Integer.parseInt(islandsTxt.getText().trim());
                t = new SeaTour(id, customer, tourName, price, guests, boat, islands);
            } else if (category.equals("Land Tour")) {
                String activity = (String) activityBox.getSelectedItem();
                boolean pickup = pickupBox.isSelected();
                t = new LandTour(id, customer, tourName, price, guests, activity, pickup);
            } else {
                String showTime = showTimeTxt.getText().trim();
                String seat = (String) seatBox.getSelectedItem();
                t = new ShowTicket(id, customer, tourName, price, guests, showTime, seat);
            }

            TourDatabase.addTour(t);
            showAllBookings();
            clearTextField();
            JOptionPane.showMessageDialog(this, "Add booking successfully. Total = "
                    + t.calculateTotalPrice() + " THB");

        } catch (NumberFormatException ex) {
            // text in a number field (this catch must be first)
            JOptionPane.showMessageDialog(this, field + " must be a number.");
        } catch (IllegalArgumentException ex) {
            // thrown by our setters (negative price, guests < 1 ...)
            JOptionPane.showMessageDialog(this, ex.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Some error occurred. Please contact the admin.");
        }
    }

    // loop over the list; each object prints itself with its own toString()
    private void showAllBookings() {
        listArea.setText("Booking List:\n");
        for (TourInterface t : TourDatabase.getTours()) {
            listArea.append(t.toString() + "\n");
        }
    }

    public void clearTextField() {
        idTxt.setText("");
        customerTxt.setText("");
        tourNameTxt.setText("");
        priceTxt.setText("");
        guestsTxt.setText("");
        islandsTxt.setText("0");
        showTimeTxt.setText("");
        pickupBox.setSelected(false);
    }

    public static void main(String[] args) {
        new EnrollPage();
    }
}