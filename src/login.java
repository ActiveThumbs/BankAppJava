import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.AbstractQueue;
import java.util.Random;

public class login extends JDialog{

    public login(JFrame parent){
        super(parent);
        setTitle("Create New Account");
        setContentPane(Main);
        setVisible(true);
        setLocationRelativeTo(parent);
        setModal(true);
        setSize(800,900);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void registerUser() {
        Random rand = new Random();
        int AccountNumber = Math.abs(rand.nextInt(1000000000)*100);
        int BVN = Math.abs(rand.nextInt(1000000000)*10);
        int Balance = 0;
        String AtmPin = "0000";
        String FirstName = tfFirstName.getText();
        String LastName = tflastName.getText();
        String Address = tfAddress.getText();
        String StateOfOrigin = tfStateofOrigin.getText();
        String PhoneNumber = tfPhoneNumber.getText();
        String Email = tfEmailAddress.getText();
        String Gender = tfGender.getText();
        String Password = String.valueOf(tfPassword.getPassword());
        String ConfirmPassword = String.valueOf(tfConfirmPassword.getPassword());
        String DateOfBirth = tfDateofBirth.getText();

        if (FirstName.isEmpty() ||
        LastName.isEmpty() ||
        Address.isEmpty() ||
        StateOfOrigin.isEmpty() ||
        PhoneNumber.isEmpty() ||
        Email.isEmpty() ||
        Gender.isEmpty() ||
        Password.isEmpty() ||
        DateOfBirth.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Enter All Fields", "Try Again", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!Password.equals(ConfirmPassword)){
            JOptionPane.showMessageDialog(this,"Passwords Dont Match", "Try Again", JOptionPane.ERROR_MESSAGE);
            return;
        }

       user = addToDatabase(FirstName,
        LastName,
        Address,
        StateOfOrigin,
        PhoneNumber,
        Email,
        Gender,
        Password,
        DateOfBirth,
        AtmPin,
        BVN,
        AccountNumber,
        Balance);

        if (user != null){
            dispose();
        }
        else {
            JOptionPane.showMessageDialog(this, "Failed To Register New User", "Try Again", JOptionPane.ERROR_MESSAGE);
        }
    }

    public User user;
    public User addToDatabase(String FirstName, String LastName, String Address, String StateOfOrigin, String PhoneNumber, String Email, String Gender, String Password, String DateOfBirth,String AtmPin, int BVN, int AccountNumber,int Balance ){
        User user = null;

        final String DB_URL = "jdbc:mysql://localhost:4301/users";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try{
            Connection conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO users (firstname, lastname, address, stateoforigin, phonenumber, email, gender, password, dateOfBirth,BVN,AccountNumber,Balance,AtmPin)" + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, FirstName);
            preparedStatement.setString(2, LastName);
            preparedStatement.setString(3, Address);
            preparedStatement.setString(4, StateOfOrigin);
            preparedStatement.setString(5, PhoneNumber);
            preparedStatement.setString(6, Email);
            preparedStatement.setString(7, Gender);
            preparedStatement.setString(8, Password);
            preparedStatement.setString(9, DateOfBirth);
            preparedStatement.setString(10, String.valueOf(BVN));
            preparedStatement.setString(11, String.valueOf(AccountNumber));
            preparedStatement.setString(12, String.valueOf(Balance));
            preparedStatement.setString(13, AtmPin);

            int addedRows = preparedStatement.executeUpdate();
            if (addedRows > 0){
                user = new User();
                user.FirstName = FirstName;
                user.LastName = LastName;
                user.Address = Address;
                user.StateOfOrigin = StateOfOrigin;
                user.Email = Email;
                user.PhoneNumber = PhoneNumber;
                user.Gender = Gender;
                user.DateOfBirth = DateOfBirth;
                user.Password = Password;
            }
            stmt.close();
            conn.close();
                    }
        catch(Exception e){
            e.printStackTrace();
        }
        return user;
    }


    public static void main(String[] args) {
        login loginForm = new login(null);
        User user = loginForm.user;
        if(user != null){
            System.out.println("Successful registration of " + user.FirstName + " " + user.LastName);
        }
        else{
            System.out.println("Registeration Canceled");
        }
    }


    private JTextField tfFirstName;
    private JPanel Main;
    private JButton registerButton;
    private JButton cancelButton;
    private JTextField tflastName;
    private JTextField tfAddress;
    private JTextField tfStateofOrigin;
    private JTextField tfDateofBirth;
    private JTextField tfEmailAddress;
    private JTextField tfPhoneNumber;
    private JTextField tfGender;
    private JPasswordField tfPassword;
    private JPasswordField tfConfirmPassword;

    public void showForm() {
    }
}

