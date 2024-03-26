import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.PublicKey;
import java.sql.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Signin extends JDialog {

    public Signin(JFrame parent){
        super(parent);
        setTitle("Create New Account");
        setContentPane(signInPanel);
        setLocationRelativeTo(parent);
        setModal(true);
        setSize(500,500);
        newAccountBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openNewAccountForm();
            }
        });


        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("success");
                String email = tfaccountNumber.getText();
                String password = String.valueOf(tfPassword.getPassword());

                user = getAuthUser(password,email);

                if (user != null){
                    System.out.println("success");
                    writeFile();
                    BankApplication bankApplication = new BankApplication(null);
                    dispose();
                    bankApplication.setVisible(true);



                }
                else{
                    JOptionPane.showMessageDialog(Signin.this,"Invalid Email or Password", "Try Again", JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        setVisible(true);

    }

    public static User user;

    private User getAuthUser(String password, String email) {
        User user = null;

        final String DB_URL = "jdbc:mysql://localhost:4301/users";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM users WHERE Email = ? AND Password = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1,email);
            preparedStatement.setString(2,password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                user = new User();
                user.FirstName = resultSet.getString("FirstName");
                user.LastName = resultSet.getString("LastName");
                user.Address = resultSet.getString("Address");
                user.StateOfOrigin =resultSet.getString("StateOfOrigin");
                user.Email = resultSet.getString("Email");
                user.PhoneNumber = resultSet.getString("PhoneNumber");
                user.Gender = resultSet.getString("Gender");
                user.DateOfBirth =resultSet.getString("DateOfBirth");
                user.Password = resultSet.getString("Password");
                user.Balance = resultSet.getInt("Balance");
                user.AccountNumber = resultSet.getInt("AccountNumber");
                user.BVN = resultSet.getInt("BVN");
                user.AtmPin = resultSet.getString("AtmPin");
            }


            stmt.close();
            conn.close();

        }
        catch (Exception e){
            e.printStackTrace();
        }


        return user;
    }

    private void openNewAccountForm() {
        login loginForm = new login(null);
        loginForm.setVisible(true);
        dispose();


    }


    public void writeFile(){
        String data = user.FirstName + "," +
                user.LastName + "," +
                user.Address + "," +
                user.StateOfOrigin + "," +
                user.Email + "," +
                user.PhoneNumber + "," +
                user.Gender + "," +
                user.DateOfBirth + "," +
                user.Password + "," +
                user.Balance + "," +
                user.AccountNumber + "," +
                user.BVN + "," +
                user.AtmPin;

        try {
            // Specify the file path
            String filePath = "StoreUserData.txt";

            // Create a new FileWriter object
            FileWriter writer = new FileWriter(filePath);

            // Wrap the FileWriter in a BufferedWriter for better performance
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            // Write the data to the file
            bufferedWriter.write(data);

            // Close the BufferedWriter
            bufferedWriter.close();

            System.out.println("Data saved to " + filePath);
        } catch (IOException e) {
            // Handle IOException
            e.printStackTrace();
        }
    }
    public static void main(String[] args){
        Signin signinForm = new Signin( null);
        User user = Signin.user;
        System.out.println("success" +" "+ user.AccountNumber + user.Balance);
    }




    private JTextField tfaccountNumber;
    private JPasswordField tfPassword;
    private JButton signInButton;
    private JButton newAccountBtn;
    private JPanel signInPanel;
}
