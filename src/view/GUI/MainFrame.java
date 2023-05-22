package view.GUI;

import view.GUI.safeRelated.MouseListener.KeyBtnMouseListener;
import view.GUI.safeRelated.MouseListener.SealBtnMouseListener;
import view.UI;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;

public class MainFrame extends UI{

    /**
     * 界面组件
     */
    private JPanel topPanel;
    private JLabel welcomeLabel;
    private JLabel warningLabel;
    private JPanel btPanel;
    private JButton keyBtn;
    private JButton sealBtn;
    private JPanel mainPanel;
    private JPanel keyBtnPanel;
    private JPanel sealBtnPanel;

    private final PresentFrame presentFrame;

    public MainFrame() throws Exception {
        this.welcome();
        this.presentFrame = new PresentFrame(null);
    }

    public static void main(String[] args) throws Exception {
        // 设置按钮显示效果
        UIManager.put("OptionPane.buttonFont", new FontUIResource(BasicMethods.getFont(Font.PLAIN, BasicMethods.NORMAL)));
        // 设置文本显示效果
        UIManager.put("OptionPane.messageFont", new FontUIResource(BasicMethods.getFont(Font.PLAIN, BasicMethods.BIG)));
        try {
            new MainFrame().present();
        } catch (Exception e) {
            BasicMethods.dealException(e);
        }
    }

    private void present() throws Exception {
        this.presentFrame.present();
        this.warningLabel.setFont(
                BasicMethods.getFont(Font.PLAIN, BasicMethods.NORMAL)
        );
        this.welcomeLabel.setFont(
                BasicMethods.getFont(Font.BOLD, BasicMethods.ULTRA)
        );
        this.keyBtn.addMouseListener(
                new KeyBtnMouseListener(
                        presentFrame.getOwnerFrame(),
                        super.getSupportDep()
                )
        );
        this.keyBtn.setFont(BasicMethods.getFont(Font.BOLD, BasicMethods.NORMAL));
        sealBtn.addMouseListener(
                new SealBtnMouseListener()
        );
        this.sealBtn.setFont(BasicMethods.getFont(Font.BOLD, BasicMethods.NORMAL));
    }

    @Override
    public void welcome() {
        this.welcomeLabel.setText("欢迎来到" + super.companyName + "钥匙印章管理系统！");
        this.warningLabel.setText("注意：" + this.warning);
    }

    private class PresentFrame extends BasicFrame {

        public PresentFrame(JFrame parentFrame) {
            super(parentFrame);
        }

        @Override
        protected int getSize() {
            return BasicMethods.SMALL;
        }

        @Override
        protected String getTitle() {
            return getCompanyName() + "钥匙印章管理系统v" + getVersion();
        }

        @Override
        protected JPanel getMainPanel() {
            return mainPanel;
        }
    }
}
