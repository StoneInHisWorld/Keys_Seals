package view.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;

public class BasicMethods {

    public static final String UIFontString = "Microsoft YaHei UI";
    public static final int SMALL = -1, NORMAL = -2, BIG = -3, ULTRA = -4;

    public static void moveToCenter(JFrame frame) {
        // 设置框架位置为屏幕中央
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) screenSize.getWidth() / 2 - frame.getWidth() / 2;
        int y = (int) screenSize.getHeight() / 2 - frame.getHeight() / 2;
        frame.setLocation(x, y);
    }

    public static void moveToCenter(JDialog dialog) {
        // 设置框架位置为屏幕中央
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) screenSize.getWidth() / 2 - dialog.getWidth() / 2;
        int y = (int) screenSize.getHeight() / 2 - dialog.getHeight() / 2;
        dialog.setLocation(x, y);
    }

    public static JButton getVisibleBtn(String text, Font font, int size, MouseListener listener) {
        JButton btn = new JButton(text);
        btn.setVisible(true);
        Dimension dimension;
        switch (size) {
            case SMALL: dimension = new Dimension(20, 10); break;
            case NORMAL: dimension = new Dimension(75, 30); break;
            case BIG: dimension = new Dimension(200, 50); break;
            case ULTRA: dimension = new Dimension(400, 100); break;
            default: dimension = new Dimension(-1, -1);break;
        }
        btn.setPreferredSize(dimension);
        btn.setHorizontalAlignment(SwingConstants.CENTER);
        btn.setVerticalAlignment(SwingConstants.CENTER);
        if (font == null) btn.setFont(getPlainFont(20));
        else btn.setFont(font);
        // 设置点击事件
        if (listener != null)
            btn.addMouseListener(listener);
        return btn;
    }

    public static Font getFont(int style, int size) throws Exception {
        switch (size) {
            case SMALL:size = 11;break;
            case NORMAL:size = 15;break;
            case BIG:size = 20;break;
            case ULTRA:size = 30;break;
            default:throw new Exception("错误的字体大小！");
        }
        switch (style) {
            case Font.BOLD:return getBoldFont(size);
            case Font.PLAIN:return getPlainFont(size);
            default:throw new Exception("不支持的字体风格！");
        }
    }

    private static Font getPlainFont(final int size) {
        return new Font(UIFontString, Font.PLAIN, size);
    }

    private static Font getBoldFont(int size) {
        return new Font(UIFontString, Font.BOLD, size);
    }

    public static void showStandardMessageDialog(String title, String msg, int msgType) {
        JLabel msgLabel = new JLabel(msg);
        msgLabel.setFont(getPlainFont(20));
        JOptionPane.showMessageDialog(null, msgLabel, title, msgType);
    }

    public static Dimension getTableCellSize(int choice) throws Exception {
        final int height = 35;
        int width;
        switch (choice) {
            case SMALL: width = 10; break;
            case NORMAL:width = 50;break;
            case BIG:width = 100;break;
            case ULTRA:width = 150;break;
            default:throw new Exception("不支持的表格单元格大小" + choice);
        }
        return new Dimension(width, height);
    }
}
