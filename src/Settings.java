import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class Settings extends JDialog {
    private JPanel Main;
    private JPasswordField tfOldPin;
    private JPasswordField tfNewPin;
    private JPasswordField tffConfirmPin;
    private JButton changeButton;

    public String FirstName;
    public String LastName;
    public String Address;
    public String StateOfOrigin;
    public String PhoneNumber;
    public String Email;
    public String Gender;
    public String Password;
    public String DateOfBirth;
    public String AtmPin;
    public int BVN;
    public int AccountNumber;
    public int Balance;
    public String line;
    public String currentPin;
    public String NewPin;

    public Settings(JFrame parent) {
        super(parent);
        setTitle("Create New Account");
        setContentPane(Main);
        setVisible(true);
        setLocationRelativeTo(parent);
        setModal(true);
        setSize(800, 900);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        changeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                readData();
                currentPin = String.valueOf(tfOldPin.getPassword());
                NewPin = String.valueOf(tfNewPin.getPassword());
                String ConfirmPin = String.valueOf(tffConfirmPin.getPassword());
                changePassword(currentPin, NewPin, ConfirmPin);
            }
        });
    }

    private void changePassword(String currentPin, String newPin, String confirmPin) {
        if (currentPin.equals(AtmPin)){
            if (newPin.length() == 4 && confirmPin.length() == 4){
                if (newPin.equals(confirmPin)){
                    writeFile();
                    final String DB_URL = "jdbc:mysql://localhost:4301/users";
                    final String USERNAME = "root";
                    final String PASSWORD = "";

                    try {
                        Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

                        Statement stmt = conn.createStatement();
                        String getDepositRow = "UPDATE users SET AtmPin = ? WHERE AccountNumber = ?";
                        PreparedStatement preparedStatement = conn.prepareStatement(getDepositRow);
                        preparedStatement.setInt(1,(Integer.parseInt(newPin)));
                        preparedStatement.setInt(2,AccountNumber);
                        int addedRows = preparedStatement.executeUpdate();

                        System.out.println(newPin + " " + AccountNumber);

                        stmt.close();
                        conn.close();
                        dispose();

                    }
                    catch (Exception e){

                    }
                }
                else{
                    System.out.println("Confirm Pin Doesnt Match");
                }
            }
            else{
                System.out.println("pin should be 4 chars long");
            }
        }
        else{
            System.out.println("wrong pin");
        }
    }

    public void readData(){
        try {
            String filePath = "StoreUserData.txt";
            FileReader reader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(reader);
            line = bufferedReader.readLine();
            bufferedReader.close();

            FirstName  = line.split(",")[0];
            LastName  = line.split(",")[1];
            Address = line.split(",")[2];
            StateOfOrigin  = line.split(",")[3];
            Email   = line.split(",")[5];
            PhoneNumber = line.split(",")[4];
            Gender = line.split(",")[6];
            DateOfBirth  = line.split(",")[8];
            Password = line.split(",")[7];
            Balance  = Integer.parseInt(line.split(",")[9]);
            AccountNumber = Integer.parseInt(line.split(",")[10]);
            BVN = Integer.parseInt(line.split(",")[11]);
            AtmPin = line.split(",")[12];
        } catch (IOException e) {
            // Handle IOException
            e.printStackTrace();
        }
    }

    public void writeFile(){
        String data = line.split(",")[0] + "," +
                line.split(",")[1] + "," +
                line.split(",")[2] + "," +
                line.split(",")[3] + "," +
                line.split(",")[4] + "," +
                line.split(",")[5] + "," +
                line.split(",")[6] + "," +
                line.split(",")[7] + "," +
                line.split(",")[8] + "," +
                line.split(",")[9] + "," +
                line.split(",")[10] + "," +
                line.split(",")[11] + "," +
                NewPin;

        try {
            String filePath = "StoreUserData.txt";

            FileWriter writer = new FileWriter(filePath);

            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            bufferedWriter.write(data);

            bufferedWriter.close();

            System.out.println("Data saved to " + filePath);
        } catch (IOException e) {
            // Handle IOException
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        Settings settings = new Settings(null);

    }

}

