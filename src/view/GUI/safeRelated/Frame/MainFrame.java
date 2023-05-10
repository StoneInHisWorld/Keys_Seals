package view.GUI.safeRelated.Frame;

import view.GUI.BasicMethods;
import view.GUI.safeRelated.MouseListener.KeyBtnMouseListener;
import view.GUI.safeRelated.MouseListener.SealBtnMouseListener;
import view.UI;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;

public class MainFrame extends UI {

    /**
     * 界面组件
     */
    private JPanel topPanel;
    private JLabel welcomeLabel;
    private JLabel warningLabel;
    private JPanel btPanel;
    private JButton keyBt;
    private JButton sealBt;
    private JPanel mainFramePanel;
    private JPanel keyBtPanel;
    private JPanel sealBtPanel;

    public MainFrame(JFrame frame) throws Exception {
        this.welcome();
        keyBt.addMouseListener(new KeyBtnMouseListener(
                keyBt, sealBt, frame, sealBtPanel, super.getSupportDep())
        );
        sealBt.addMouseListener(new SealBtnMouseListener());
    }

    public static void main(String[] args) {
//        Object[] options = {BasicMethods.getBoldFont(20)};
//        JOptionPane.showOptionDialog(null, "test", "test", JOptionPane.DEFAULT_OPTION,
//                JOptionPane.INFORMATION_MESSAGE, null, options, null);
        // 设置按钮显示效果
        UIManager.put("OptionPane.buttonFont", new FontUIResource(BasicMethods.getPlainFont(20)));
        // 设置文本显示效果
        UIManager.put("OptionPane.messageFont", new FontUIResource(BasicMethods.getPlainFont(20)));
//        JOptionPane.showMessageDialog(null, "我是用UIManager设置过的Dialog");
        try {
            JFrame frame = new JFrame();
            MainFrame mainFrame = new MainFrame(frame);
//            JFrame frame = new JFrame("MainFrame");
//            JFrame frame = new JFrame(mainFrame.getCompanyName() + "钥匙印章管理系统v" + mainFrame.getVersion());
            frame.setContentPane(mainFrame.mainFramePanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setTitle(mainFrame.getCompanyName() + "钥匙印章管理系统v" + mainFrame.getVersion());
            frame.pack();
            frame.setVisible(true);
            BasicMethods.moveToCenter(frame);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void welcome() {
        this.welcomeLabel.setText("欢迎来到" + super.companyName + "钥匙印章管理系统！");
        this.warningLabel.setText("注意：" + this.warning);
    }
}
