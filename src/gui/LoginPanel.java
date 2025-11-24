package gui;

import logic.Autenticacion;
import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {
    private final Autenticacion autenticacion;
    private final MainFrame mainFrame;

    private JTextField dniField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel errorLabel;

    public LoginPanel(Autenticacion autenticacion, MainFrame mainFrame) {
        this.autenticacion = autenticacion;
        this.mainFrame = mainFrame;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Inicio de Sesión", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        add(new JLabel("DNI:"), gbc);
        
        gbc.gridx = 1;
        dniField = new JTextField(15);
        add(dniField, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        add(new JLabel("Contraseña:"), gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        add(passwordField, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginButton = new JButton("Iniciar Sesión");
        add(loginButton, gbc);

        gbc.gridy = 4;
        errorLabel = new JLabel("", SwingConstants.CENTER);
        errorLabel.setForeground(Color.RED);
        add(errorLabel, gbc);

        loginButton.addActionListener(e -> {
            try {
                int dni = Integer.parseInt(dniField.getText());
                String password = new String(passwordField.getPassword());

                if (autenticacion.login(dni, password)) {
                    mainFrame.showPanel("MAIN_MENU");
                    dniField.setText("");
                    passwordField.setText("");
                    errorLabel.setText("");
                } else {
                    errorLabel.setText("DNI o contraseña incorrectos.");
                }
            } catch (NumberFormatException ex) {
                errorLabel.setText("El DNI debe ser un número.");
            }
        });
    }
}
