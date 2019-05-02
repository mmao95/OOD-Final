package UI.Comment;

import javax.swing.*;
import java.awt.*;

/**
 * @Auther: Di Zhu
 * @Date: 04-22-2019 17:30
 * @Description:
 */
public class CommentPanel extends JPanel {

    private JTextField valueField;
    private boolean hasComment = false;

    public CommentPanel() {
        super();
        valueField = new JTextField();
        valueField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        this.setBackground(Color.WHITE);
        this.setLayout(new BorderLayout());
        this.add(valueField, BorderLayout.CENTER);
    }

    public CommentPanel(boolean hasComment) {
        super();
        valueField = new JTextField();
        valueField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        this.hasComment = hasComment;
        setBackground();
        this.setLayout(new BorderLayout());
        this.add(valueField, BorderLayout.CENTER);
    }

    public void setValue(String value) {
        valueField.setText(value);
    }

    public String getValue() {
        return valueField.getText();
    }

    public void setBackground() {
        if (hasComment)
            this.setBackground(Color.RED);
        else
            this.setBackground(Color.WHITE);
    }
}
