import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Arrays;

public class TransferFunds extends JDialog{
    private JTextField tfBankName;
    private JPasswordField tfAtmPin;
    private JTextField tfAmount;
    private JButton confirmTransfer;
    private JTextField tfAccountNumber;
    private JPanel Main;

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
    public int CurrentBalance;

    public TransferFunds(JFrame parent){
        super(parent);
        setTitle("Create New Account");
        setContentPane(Main);
        setVisible(true);
        setLocationRelativeTo(parent);
        setModal(true);
        setSize(1000, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setVisible(true);

        confirmTransfer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String accountNumber =  tfAccountNumber.getText();
                int amount = Integer.parseInt(tfAmount.getText());
                String bank = tfBankName.getText();
                String atmPin = String.valueOf(tfAtmPin.getPassword());
                readData();
                CurrentBalance = Balance;
                System.out.println(CurrentBalance);
                if (amount < CurrentBalance && atmPin.equals(AtmPin)){
                    if (!atmPin.equals("0000")) {
                        transferFunds(accountNumber, bank, amount);
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Change the Default Pin 0000 in the Settings ", "Try Again", JOptionPane.ERROR_MESSAGE);
                        dispose();
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "You either entered the wrong pin or Need More Money", "Try Again", JOptionPane.ERROR_MESSAGE);
                    dispose();
                }}
        });
    }

    private void transferFunds(String accountNumber, String bank, int amount) {


        final String DB_URL = "jdbc:mysql://localhost:4301/users";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();
            String getDepositRow = "UPDATE users SET Balance = ? WHERE AccountNumber = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(getDepositRow);
            preparedStatement.setInt(1, (CurrentBalance -  amount));
            preparedStatement.setInt(2,AccountNumber);
            int addedRows = preparedStatement.executeUpdate();

            CurrentBalance = (CurrentBalance  - amount);
            System.out.println(CurrentBalance + " " + AccountNumber);
            writeFile();
            stmt.close();
            conn.close();
            JOptionPane.showMessageDialog(this, "Transfer of" + " " + amount + " " + "to" + " " + accountNumber + " " + bank +" "+ "was successfull", "Success",JOptionPane.PLAIN_MESSAGE);
        }
        catch (Exception e){

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
                CurrentBalance + "," +
                line.split(",")[10] + "," +
                line.split(",")[11] + "," +
                line.split(",")[12];

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

    public static void Main(String[] args){
        TransferFunds transferFunds = new TransferFunds(null);
    }
}
