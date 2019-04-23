package UI.Comment;

import javax.swing.*;
import java.awt.*;

/**
 * @Auther: Di Zhu
 * @Date: 04-22-2019 17:30
 * @Description:
 */
public class CommentPanel extends JPanel {

    private JLabel valueLabel;
    private String comment = null;

    public CommentPanel() {
        super();
        valueLabel = new JLabel();
        this.setBackground(Color.WHITE);
        this.setLayout(new BorderLayout());
        this.add(valueLabel, BorderLayout.CENTER);

    }

    public boolean hasComment() {
        return comment.isEmpty();
    }

    public void setValue(String value) {
        valueLabel.setText(value);
    }

    public void setBackground() {
        if (hasComment())
            this.setBackground(Color.RED);
        else
            this.setBackground(Color.WHITE);
    }

    /*** Setter and Getter ***/
    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }
}
