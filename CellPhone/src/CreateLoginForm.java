import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

// CreateLoginForm class to create a login form
class CreateLoginForm extends JFrame implements ActionListener {
    JButton jButtonSubmit, jButtonExit, jButtonCreateAccount;
    JPanel newPanel;
    JLabel userLabel, passLabel, infoLabel;
    final JTextField textField1, textField2;

    CreateLoginForm() {
        setTitle("Lab 5 Login Window");
        setLayout(new BorderLayout());
        Icon logo = new ImageIcon(getClass().getResource("user-login-305.png"));
        infoLabel = new JLabel(logo);
        infoLabel.setSize(new Dimension(20, 20));

        userLabel = new JLabel();
        userLabel.setText("Username");
        textField1 = new JTextField(15);

        passLabel = new JLabel();
        passLabel.setText("Password");
        textField2 = new JPasswordField(15);

        jButtonSubmit = new JButton("SUBMIT");
        jButtonExit = new JButton("EXIT");
        jButtonCreateAccount = new JButton("Create an Account");

        newPanel = new JPanel(new GridLayout(4, 1));
        newPanel.add(userLabel);
        newPanel.add(textField1);
        newPanel.add(passLabel);
        newPanel.add(textField2);
        newPanel.add(jButtonSubmit);
        newPanel.add(jButtonExit);

        add(infoLabel, BorderLayout.NORTH);
        add(newPanel, BorderLayout.CENTER);
        add(jButtonCreateAccount, BorderLayout.SOUTH);

        jButtonSubmit.addActionListener(this);
        jButtonExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int r = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirmation window", JOptionPane.YES_NO_OPTION);
                if (r == 0)
                    System.exit(0);
            }
        });

        jButtonCreateAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateAccount createAccountForm = new CreateAccount();
                createAccountForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                createAccountForm.setSize(300, 220);
                createAccountForm.setVisible(true);
            }
        });
    }

    public void actionPerformed(ActionEvent ae) {
        String userValue = textField1.getText();
        String passValue = textField2.getText();
        boolean isSuccess = false;

        try (BufferedReader br = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                String usernameFromFile = parts[0].trim();
                String passwordFromFile = parts[1].trim();

                if (userValue.equals(usernameFromFile) && passValue.equals(passwordFromFile)) {
                    isSuccess = true;
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("IO Exception Error");
        }

        if (isSuccess) {
            CellPhoneInventory page = new CellPhoneInventory();
            page.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            page.setSize(350, 250);
            page.setVisible(true);
            CreateLoginForm.this.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Please enter valid username and password");
            System.out.println("Please enter valid username and password");
        }
    }

    public static void main(String arg[]) {
        CreateLoginForm form = new CreateLoginForm();
        form.setSize(320, 240);
        form.setVisible(true);
    }
}

