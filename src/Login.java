//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//import javax.swing.*;
//
///**
// * @Auther: wangqitong
// * @Date: 04-12-2019 11:48
// * @Description: User needs to inout username and password to enter the system.
// */
//public class Login extends JFrame implements ActionListener {
//    protected JButton loginButton, resetButton;
//    protected JPanel brandPanel, userNamePanel, pswPanel, buttonPanel;
//    protected JTextField userNameTextField;
//    protected JLabel loginLabel, userNameLabel, pswLabel;
//    protected JPasswordField pswTextField;
//
//    public Login() {
//        // TODO Auto-generated constructor stub
//        loginButton = new JButton("Login");
//        loginButton.addActionListener(this);
//        resetButton = new JButton("Reset");
//        resetButton.addActionListener(this);
//
//        loginButton.addActionListener(e -> {
//            this.setVisible(false);
//            new MainFrame();
//        });
//
//        brandPanel = new JPanel();
////        ImageIcon img = new ImageIcon("LoginImg.jpg");
////        brandPanel.setLayout(null);
////        brandPanel.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());
////        JLabel a = new JLabel(img);
////        a.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());
//
//        userNamePanel = new JPanel();
//        pswPanel = new JPanel();
//        buttonPanel = new JPanel();
//
//        loginLabel = new JLabel("Login");
//        loginLabel.setFont(new java.awt.Font("Dialog", 1, 20));
//        userNameLabel = new JLabel("Username");
//        pswLabel = new JLabel("Psssword");
//
//        userNameTextField = new JTextField(10);
//        pswTextField = new JPasswordField(10);
//
//        brandPanel.add(loginLabel);
//        userNamePanel.add(userNameLabel);
//        userNamePanel.add(userNameTextField);
//        pswPanel.add(pswLabel);
//        pswPanel.add(pswTextField);
//        buttonPanel.add(loginButton);
//        buttonPanel.add(resetButton);
//
//        brandPanel.setSize(350, 200);
//        userNamePanel.setSize(350, 10);
//        pswPanel.setSize(350, 10);
//        buttonPanel.setSize(350, 30);
//
//        this.add(brandPanel);
//        this.add(userNamePanel);
//        this.add(pswPanel);
//        this.add(buttonPanel);
//
//        this.setTitle("Login");
//        this.setLayout(new GridLayout(4, 1));
//        this.setSize(350, 250);
//        this.setLocationRelativeTo(null);
//        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//
//        this.setVisible(true);
//        this.setResizable(false);
//
//    }
//
//    public void actionPerformed(ActionEvent e) {
//        if (e.getActionCommand()=="Login") {
//            System.out.println("Come in!!!!!");
//        }
//        else if (e.getActionCommand()=="Reset") {
//            userNameTextField.setText("");
//            pswTextField.setText("");
//        }
//    }
//}
