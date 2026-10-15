package gui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.JTextComponent;
import java.awt.*;

/** Shared Swing styling for the application; uses only the Java standard library. */
final class GuiStyle {
    static final Color BACKGROUND = new Color(244, 247, 251);
    static final Color SURFACE = Color.WHITE;
    static final Color NAVY = new Color(24, 55, 91);
    static final Color BLUE = new Color(39, 112, 180);
    static final Color TEXT = new Color(42, 54, 69);
    static final Color MUTED = new Color(105, 119, 136);
    private static final Font BODY = new Font("SansSerif", Font.PLAIN, 14);

    private GuiStyle() { }

    static void apply(JFrame frame) {
        frame.getContentPane().setBackground(BACKGROUND);
        styleTree(frame.getContentPane());
    }

    static void useBlackFormText(JFrame frame) {
        setBlackText(frame.getContentPane());
    }

    private static void setBlackText(Component component) {
        if (component instanceof JLabel || component instanceof JTextComponent
                || component instanceof JComboBox<?> || component instanceof JCheckBox) {
            component.setForeground(Color.BLACK);
        }
        if (component instanceof Container) {
            for (Component child : ((Container) component).getComponents()) setBlackText(child);
        }
    }

    static JPanel card() {
        JPanel panel = new JPanel();
        panel.setBackground(SURFACE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240)),
                BorderFactory.createEmptyBorder(18, 20, 18, 20)));
        return panel;
    }

    static JLabel title(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("SansSerif", Font.BOLD, 24));
        label.setForeground(NAVY);
        label.setBorder(BorderFactory.createEmptyBorder(22, 16, 8, 16));
        return label;
    }

    static JLabel subtitle(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("SansSerif", Font.PLAIN, 14));
        label.setForeground(MUTED);
        return label;
    }

    private static void styleTree(Component component) {
        if (component instanceof JPanel) {
            JPanel panel = (JPanel) component;
            if (panel.getBackground() == null || panel.getBackground().equals(UIManager.getColor("Panel.background"))) {
                panel.setBackground(BACKGROUND);
            }
        } else if (component instanceof JLabel) {
            JLabel label = (JLabel) component;
            if (label.getForeground() == null || label.getForeground().equals(UIManager.getColor("Label.foreground"))) {
                label.setForeground(TEXT);
            }
        } else if (component instanceof JTextField || component instanceof JComboBox<?>) {
            component.setFont(BODY);
            component.setBackground(SURFACE);
            component.setForeground(TEXT);
            Border line = BorderFactory.createLineBorder(new Color(203, 213, 225));
            component.setPreferredSize(new Dimension(180, 36));
            if (component instanceof JComponent) {
                ((JComponent) component).setBorder(BorderFactory.createCompoundBorder(
                        line, BorderFactory.createEmptyBorder(4, 8, 4, 8)));
            }
        } else if (component instanceof JCheckBox) {
            component.setBackground(SURFACE);
            component.setForeground(TEXT);
        } else if (component instanceof JTextArea) {
            component.setFont(new Font("Monospaced", Font.PLAIN, 13));
            component.setBackground(SURFACE);
            component.setForeground(TEXT);
            ((JTextArea) component).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        } else if (component instanceof JButton) {
            JButton button = (JButton) component;
            button.setFont(new Font("SansSerif", Font.BOLD, 14));
            boolean secondary = button.getText().startsWith("Back") || button.getText().equals("Clear");
            button.setOpaque(true);
            button.setFocusPainted(false);
            button.setForeground(secondary ? NAVY : Color.WHITE);
            button.setBackground(secondary ? new Color(229, 237, 246) : BLUE);
            button.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));
            button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if (component instanceof Container) {
            for (Component child : ((Container) component).getComponents()) styleTree(child);
        }
    }
}
