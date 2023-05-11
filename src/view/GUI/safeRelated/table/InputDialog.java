package view.GUI.safeRelated.table;

import view.GUI.BasicMethods;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.List;

public class InputDialog extends JDialog {

    private final Dimension textFieldSize;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPanel bottomPanel;
    private JPanel TopPanel;
    private JPanel btnPanel1;
    private JPanel inputPanel;
    private JPanel messaagePanel;
    private JLabel promptLabel;
    private Object[] toBeInput;
    private List<JTextField> fieldsHaveData;

    public InputDialog(Object[] toBeInput) throws Exception {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        // 自定义部分
        GridLayout inputPanelLayout = new GridLayout();
        inputPanelLayout.setHgap(5);
        this.inputPanel.setLayout(inputPanelLayout);
        this.promptLabel.setFont(BasicMethods.getFont(Font.PLAIN, BasicMethods.BIG));
        this.toBeInput = toBeInput;
        this.fieldsHaveData = new LinkedList<>();
        this.textFieldSize = new Dimension(200, 50);

        for (Object s : toBeInput) {
            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());
            JLabel label = new JLabel((String) s);
            label.setFont(BasicMethods.getFont(Font.PLAIN, BasicMethods.NORMAL));
            panel.add(label, BorderLayout.NORTH);
            JTextField dataField = new JTextField();
            dataField.setSize(this.textFieldSize);
            panel.add(dataField, BorderLayout.SOUTH);
            this.fieldsHaveData.add(dataField);
            this.inputPanel.add(panel);
        }
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) throws Exception {
        InputDialog dialog = new InputDialog(new String[]{"1", "2", "3", "4", "5"});
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
