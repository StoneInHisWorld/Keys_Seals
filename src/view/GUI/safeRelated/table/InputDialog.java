package view.GUI.safeRelated.table;

import controller.SafeUIController;
import view.GUI.BasicMethods;
import view.GUI.safeRelated.Frame.DepOverviewFrame;
import view.GUI.safeRelated.MouseListener.BasicMouseListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.List;

public class InputDialog extends JDialog {

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPanel bottomPanel;
    private JPanel TopPanel;
    private JPanel btnPanel1;
    private JPanel inputPanel;
    private JPanel messaagePanel;
    private JLabel promptLabel;
    // 自定义部分
    private final BasicMouseListener listener;
    private final Dimension textFieldSize;
    private final Object[] toBeInput;
    private final List<JTextField> fieldsHaveData;

    public InputDialog(Object[] toBeInput, String dialogTtile,
                       BasicMouseListener listener) throws Exception {
        this.listener = listener;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> {
            try {
                onOK();
            } catch (Exception exception) {
                BasicMethods.dealException(exception);
            }
        });

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(
                e -> onCancel(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT
        );
        // 自定义部分
        this.toBeInput = toBeInput;
        this.textFieldSize = new Dimension(200, 50);
        this.fieldsHaveData = new LinkedList<>();
        this.customize();
        this.present(dialogTtile);
    }

    /**
     * 自定义对话框
     * @throws Exception 字体参数异常
     */
    private void customize() throws Exception {
        // UI设置
        GridLayout inputPanelLayout = new GridLayout();
        inputPanelLayout.setHgap(5);
        this.inputPanel.setLayout(inputPanelLayout);
        this.promptLabel.setFont(BasicMethods.getFont(Font.PLAIN, BasicMethods.BIG));
        // 定制输入界面的输入框
        for (Object s : toBeInput) {
            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());
            JLabel label = new JLabel(s.toString());
            label.setFont(BasicMethods.getFont(Font.PLAIN, BasicMethods.NORMAL));
            panel.add(label, BorderLayout.NORTH);
            JTextField dataField = new JTextField();
            dataField.setSize(this.textFieldSize);
            panel.add(dataField, BorderLayout.SOUTH);
            this.fieldsHaveData.add(dataField);
            this.inputPanel.add(panel);
        }
    }

    /**
     * 对于确认键的处理
     * @throws Exception 添加的保险柜信息字符串解析异常
     */
    private void onOK() throws Exception {
        List<Object> data = new LinkedList<>();
        for (JTextField fieldsHaveDatum : fieldsHaveData) {
            data.add(fieldsHaveDatum.getText());
        }
        this.listener.dealTransaction(data.toArray());
        this.dispose();
        listener.refreshParentFrame();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private void present(final String dialogTtile) {
        BasicMethods.moveToCenter(this);
        this.setTitle(dialogTtile);
        this.pack();
        this.setVisible(true);
    }
}
