import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class BankApplication extends JDialog{
    private JLabel userName;
    private JLabel accountBalance;
    private JButton depositMoney;
    private JButton HOMEButton;
    private JButton SETTINGSButton;
    private JButton PROFILEButton;
    private JPanel Main;
    private JButton refreshButton;
    private JButton transferButton;
    public User user;

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

    public BankApplication(JFrame parent) {
        super(parent);
        setTitle("Create New Account");
        setContentPane(Main);
        setVisible(true);
        setLocationRelativeTo(parent);
        setModal(true);
        setSize(1000, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        readData();
        accountBalance.setText("$" + String.valueOf(Balance));

        setVisible(true);
        depositMoney.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DepositForm depositForm = new DepositForm(null);
                depositForm.setVisible(true);

                dispose();
            }
        });
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshBalance();
                System.out.println("refreshed");
            }
        });
        PROFILEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Profiles profiles = new Profiles(null);


            }
        });
        transferButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TransferFunds transferFunds = new TransferFunds(null);
                transferFunds.setVisible(true);
            }
        });
        HOMEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        SETTINGSButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Settings settings = new Settings(null);
            }
        });
    }

    public void readData(){
        try {
            String filePath = "StoreUserData.txt";
            FileReader reader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
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
        } catch (IOException e) {


            // Handle IOException
            e.printStackTrace();
        }
    }

    public void refreshBalance() {
        readData(); // Reload balance from file
        accountBalance.setText("$" + String.valueOf(Balance)); // Update GUI with new balance
    }

    public static void main(String[] args) {
        BankApplication bankApplication = new BankApplication(null);

    }
}

