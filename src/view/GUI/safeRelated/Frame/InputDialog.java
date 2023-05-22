package view.GUI.safeRelated.Frame;

import view.GUI.BasicMethods;
import view.GUI.BasicMouseListener;

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
    private final int width_size;

    /**
     * 生成一个新的输入对话框
     * @param toBeInput 将要输入的信息
     * @param dialogTtile 输入对话框的标题
     * @param listener 处理“确定”键的监听器。用户点击确定后会处理用户输入的数据，并刷新父界面。
     * @param width_size 对话框的宽度大小参数。需为BasicMethods的参数。
     * @throws Exception 字体参数异常
     */
    public InputDialog(Object[] toBeInput, String dialogTtile,
                       BasicMouseListener listener, int width_size) throws Exception {
        this.listener = listener;
        this.width_size = width_size;
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
        this.promptLabel.setFont(BasicMethods.getFont(Font.BOLD, BasicMethods.BIG));
        // 定制输入界面的输入框
        for (Object s : toBeInput) {
            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());
            JLabel label = new JLabel(s.toString());
            label.setFont(BasicMethods.getFont(Font.PLAIN, BasicMethods.BIG));
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
        this.listener.refreshParentFrame();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private void present(final String dialogTtile) throws Exception {
        BasicMethods.setDialogSize(this, this.width_size, BasicMethods.NORMAL);
        BasicMethods.moveToCenter(this);
        this.setTitle(dialogTtile);
        this.pack();
        this.setVisible(true);
    }
}
