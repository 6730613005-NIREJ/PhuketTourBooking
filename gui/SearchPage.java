package gui;

import javax.swing.*;

import database.TourDatabase;
import interfaces.TourInterface;
import tours.LandTour;
import tours.SeaTour;
import tours.ShowTicket;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

// PAGE 3: search by ONE criterion and ONE value
public class SearchPage extends JFrame implements ActionListener {

    private JComboBox<String> criteriaBox;
    private JTextField valueTxt;
    private JTextArea searchResults;
    private JButton searchBtn, backBtn;

    public SearchPage() {
        super("Phuket Tour Booking Manager - Search");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        JLabel titleLabel = new JLabel("Search Bookings", SwingConstants.CENTER);
        titleLabel.setForeground(Color.BLUE);
        titleLabel.setFont(new Font("", Font.BOLD, 20));

        String[] criteria = {"Customer name contains", "Price per person >=",
                "Total price >=", "Number of guests >=", "Number of islands >=",
                "Activity type contains", "Show time contains"};
        criteriaBox = new JComboBox<>(criteria);
        valueTxt = new JTextField(20);

        searchBtn = new JButton("Search");
        searchBtn.addActionListener(this);
        backBtn = new JButton("Back to Main Page");
        backBtn.addActionListener(this);

        JPanel topPanel = new JPanel();
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topPanel.add(new JLabel("Search by"));
        topPanel.add(criteriaBox);
        topPanel.add(new JLabel("Value"));
        topPanel.add(valueTxt);
        topPanel.add(searchBtn);
        topPanel.add(backBtn);

        searchResults = new JTextArea();
        searchResults.setEditable(false);
        searchResults.append("Results:\n");
        JScrollPane scrollPane = new JScrollPane(searchResults);
        scrollPane.setPreferredSize(new Dimension(900, 450));

        add(titleLabel, BorderLayout.NORTH);
        add(topPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();

        if (actionCommand.equals("Back to Main Page")) {
            dispose();
        } else if (actionCommand.equals("Search")) {
            doSearch();
        }
    }

    private void doSearch() {
        String criteria = (String) criteriaBox.getSelectedItem();
        String value = valueTxt.getText().trim();

        if (value.equals("")) {
            JOptionPane.showMessageDialog(this, "Please enter a search value.");
            return;
        }

        try {
            List<TourInterface> all = TourDatabase.getTours();
            List<TourInterface> results = new ArrayList<>();

            if (criteria.equals("Customer name contains")) {
                results = searchByCustomerName(all, value);
            } else if (criteria.equals("Price per person >=")) {
                results = searchByPrice(all, Double.parseDouble(value));
            } else if (criteria.equals("Total price >=")) {
                results = searchByTotal(all, Double.parseDouble(value));
            } else if (criteria.equals("Number of guests >=")) {
                results = searchByGuests(all, Integer.parseInt(value));
            } else if (criteria.equals("Number of islands >=")) {
                results = searchByIslands(all, Integer.parseInt(value));
            } else if (criteria.equals("Activity type contains")) {
                results = searchByActivity(all, value);
            } else if (criteria.equals("Show time contains")) {
                results = searchByShowTime(all, value);
            }

            searchResults.setText("Results:\n");
            if (results.isEmpty()) {
                searchResults.append("Not Found\n");
            } else {
                for (TourInterface t : results) {
                    searchResults.append(t.toString() + "\n");
                }
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a number for this search.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Some error occurred. Please contact the admin.");
        }
    }

    // ===== search methods: take a list, return a NEW list with the matches =====

    // --- common criteria: work for every booking (interface methods) ---
    private List<TourInterface> searchByCustomerName(List<TourInterface> list, String text) {
        List<TourInterface> results = new ArrayList<>();
        for (TourInterface t : list) {
            if (t.getCustomerName().toLowerCase().contains(text.toLowerCase())) {
                results.add(t);
            }
        }
        return results;
    }

    private List<TourInterface> searchByPrice(List<TourInterface> list, double min) {
        List<TourInterface> results = new ArrayList<>();
        for (TourInterface t : list) {
            if (t.getPricePerPerson() >= min) {
                results.add(t);
            }
        }
        return results;
    }

    private List<TourInterface> searchByTotal(List<TourInterface> list, double min) {
        List<TourInterface> results = new ArrayList<>();
        for (TourInterface t : list) {
            if (t.calculateTotalPrice() >= min) {   // POLYMORPHISM: each category its own rule
                results.add(t);
            }
        }
        return results;
    }

    private List<TourInterface> searchByGuests(List<TourInterface> list, int min) {
        List<TourInterface> results = new ArrayList<>();
        for (TourInterface t : list) {
            if (t.getNumGuests() >= min) {
                results.add(t);
            }
        }
        return results;
    }

    // --- unique criteria: check the type with instanceof, then cast ---
    private List<TourInterface> searchByIslands(List<TourInterface> list, int min) {
        List<TourInterface> results = new ArrayList<>();
        for (TourInterface t : list) {
            if (t instanceof SeaTour) {
                SeaTour s = (SeaTour) t;
                if (s.getNumIslands() >= min) {
                    results.add(t);
                }
            }
        }
        return results;
    }

    private List<TourInterface> searchByActivity(List<TourInterface> list, String text) {
        List<TourInterface> results = new ArrayList<>();
        for (TourInterface t : list) {
            if (t instanceof LandTour) {
                LandTour l = (LandTour) t;
                if (l.getActivityType().toLowerCase().contains(text.toLowerCase())) {
                    results.add(t);
                }
            }
        }
        return results;
    }

    private List<TourInterface> searchByShowTime(List<TourInterface> list, String text) {
        List<TourInterface> results = new ArrayList<>();
        for (TourInterface t : list) {
            if (t instanceof ShowTicket) {
                ShowTicket s = (ShowTicket) t;
                if (s.getShowTime().toLowerCase().contains(text.toLowerCase())) {
                    results.add(t);
                }
            }
        }
        return results;
 
    }

    public static void main(String[] args) {
        new SearchPage();
    }
}
