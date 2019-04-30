//import javax.swing.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
///**
// * @Auther: wangqitong
// * @Date: 04-30-2019 16:58
// * @Description:
// */
//public class CommentFrame extends JFrame implements ActionListener {
//    protected JButton okButton, deleteButton;
//    protected JTextField commentTextField;
//    private MainFrame mainFrame;
//
//    public CommentFrame(MainFrame inputMainFrame, String inputCommentString) {
//        // TODO Auto-generated constructor stub
//
//        mainFrame = inputMainFrame;
//
//        okButton = new JButton("OK");
//        okButton.addActionListener(this);
//        okButton.setBounds(25, 150, 75, 25);
//        deleteButton = new JButton("Delete");
//        deleteButton.addActionListener(this);
//        deleteButton.setBounds(200, 150, 75, 25);
//
//        commentTextField = new JTextField(inputCommentString);
//        commentTextField.setBounds(25, 25, 250, 100);
//
//        this.add(commentTextField);
//        this.add(okButton);
//        this.add(deleteButton);
//
//        this.setTitle("Comment");
////        this.setLayout(new GridLayout(3, 1));
//        this.setSize(300, 225);
//        this.setLocationRelativeTo(null);
//        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//
//        this.setVisible(true);
//        this.setResizable(false);
//    }
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        if (e.getActionCommand()=="OK") {
//           mainFrame.updateComment(commentTextField.getText());
//        }
//        else if (e.getActionCommand()=="Delete") {
//            commentTextField.setText("");
//        }
//    }
//}
