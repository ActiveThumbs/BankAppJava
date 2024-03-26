import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Profiles extends JDialog{
    private JLabel tfFirstName;
    private JLabel TfLastName;
    private JLabel tfBvn;
    private JLabel tfAccountNumber;
    private JPanel Main;
    private JLabel tfDOB;
    private JLabel tfAddress;
    private JLabel tfEmail;
    private JLabel tfGender;
    private JLabel tf;
    private JLabel tfState;
    private JLabel tfPhone;

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

    public Profiles(JFrame parent) {
        super(parent);
        setTitle("Create New Account");
        setContentPane(Main);
        setVisible(true);
        setLocationRelativeTo(parent);
        setModal(true);
        setSize(600, 850);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        readData();
        tfFirstName.setText(FirstName);
        tfAccountNumber.setText(String.valueOf(AccountNumber));
        tfBvn.setText(String.valueOf(BVN));
        TfLastName.setText(LastName);
        tfState.setText(StateOfOrigin);
        tfDOB.setText(DateOfBirth);
        tfAddress.setText(Address);
        tfEmail.setText(Email);
        tfGender.setText(Gender);
        tfPhone.setText(PhoneNumber);


        setVisible(true);
        };

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

    public static void main(String[] args) {
        Profiles profiles = new Profiles(null);

    }


}

