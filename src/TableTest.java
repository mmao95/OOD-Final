import UI.Comment.CommentEditor;
import UI.Comment.CommentRenderer;

import javax.swing.*;
import java.awt.*;

/**
 * @Auther: Di Zhu
 * @Date: 05-01-2019 15:00
 * @Description:
 */
public class TableTest extends JFrame {

    private JTable jTable;
    private Container container;

    String[] columnNames = {"Integer", "String"};
    Object[][] data = {{1, "BBB"}, {12, "AAA"},
            {2, "DDD"}, {5, "CCC"}};

    private int windowWidth = 1280;
    private int windowHeight = 720;

    public TableTest() {
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();

        container = getContentPane();
        container.setLayout(new BorderLayout());

        jTable = new JTable(data, columnNames);
//        jTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

        jTable.getColumnModel().getColumn(0).setCellRenderer(new CommentRenderer());

        //getColumnModel().getColumn(0).setCellEditor(new CommentEditor());

        this.add(jTable);

        /*** Settings of UI.MainFrame ***/
        setTitle("Grading System");
        setBounds((int)(screenSize.getWidth() - windowWidth)/ 2,
                (int)(screenSize.getHeight() - windowHeight)/ 2,
                windowWidth, windowHeight);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }


}
