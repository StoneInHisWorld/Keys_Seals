package view.GUI;

import jdk.nashorn.internal.scripts.JO;
import view.GUI.safeRelated.Frame.InputDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;

public class BasicMethods {

    public static final String UIFontString = "Microsoft YaHei UI";
    public static final int SMALL = -1, NORMAL = -2, BIG = -3, ULTRA = -4;

    public static void moveToCenter(JFrame frame) {
        // 设置框架位置为屏幕中央
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) (screenSize.getWidth() / 2 - frame.getWidth() / 2);
        int y = (int) (screenSize.getHeight() / 2 - frame.getHeight() / 2);
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
//        switch (size) {
//            case SMALL: dimension = new Dimension(20, 10); break;
//            case NORMAL: dimension = new Dimension(75, 30); break;
//            case BIG: dimension = new Dimension(200, 50); break;
//            case ULTRA: dimension = new Dimension(400, 100); break;
//            default: dimension = new Dimension(-1, -1);break;
//        }
        switch (size) {
            case SMALL: dimension = new Dimension(20, 10); break;
            case NORMAL: dimension = new Dimension(70, 25); break;
            case BIG: dimension = new Dimension(100, 30); break;
            case ULTRA: dimension = new Dimension(400, 100); break;
            default: dimension = new Dimension(-1, -1);break;
        }
        btn.setPreferredSize(dimension);
        btn.setHorizontalAlignment(SwingConstants.CENTER);
        btn.setVerticalAlignment(SwingConstants.CENTER);
        if (font == null) btn.setFont(getPlainFont(15));
        else btn.setFont(font);
        // 设置点击事件
        if (listener != null)
            btn.addMouseListener(listener);
        return btn;
    }

    /**
     * 获取字体
     * @param style 字体风格
     * @param size 字体大小
     * @return 预设字体
     * @throws Exception 字体大小参数错误，字体风格参数错误
     */
    public static Font getFont(int style, int size) throws Exception {
//        switch (size) {
//            case SMALL:size = 11;break;
//            case NORMAL:size = 15;break;
//            case BIG:size = 20;break;
//            case ULTRA:size = 30;break;
//            default:throw new Exception("错误的字体大小！");
//        }
        switch (size) {
            case SMALL:size = 10;break;
            case NORMAL:size = 13;break;
            case BIG:size = 20;break;
            case ULTRA:size = 25;break;
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

    public static void dealException(Exception e) {
        JOptionPane.showMessageDialog(null, e.getMessage(), "出错", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }

    public static void prompt(final String msg) {
        JOptionPane.showMessageDialog(null, msg,
                "提示", JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean yesOrNo(final String msg) {
        int op = JOptionPane.showConfirmDialog(null, msg,
                "选择", JOptionPane.YES_NO_OPTION);
        System.out.println(op);
        return op == 0;
    }

    public static String getSinLineInput(final String msg,
                                         final String blankPrompt) {
        while (true) {
            String input = JOptionPane.showInputDialog(msg);
            if (input == null) return null;
            if (input.equals("")) JOptionPane.showMessageDialog(
                    null, blankPrompt
            );
            else return input;
        }
    }

    public static void setFrameSize(Frame toBeEdited, int size) throws Exception {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screen_width = screenSize.width;
        int screen_height = screenSize.height;
        Dimension d;
        // 界面占整个屏幕的百分比
        double[] width_portion = {0.4, 0.5, 0.9, 1};
        double[] height_portion = {0.35, 0.4, 0.6, 0.9};
        switch (size) {
            case SMALL:
                d = new Dimension(
                        (int)(screen_width * width_portion[0]), (int)(screen_height * height_portion[0])
                );break;
            case NORMAL:
                d = new Dimension(
                        (int)(screen_width * width_portion[1]), (int)(screen_height * height_portion[1])
                );break;
            case BIG:
                d = new Dimension(
                        (int)(screen_width * width_portion[2]), (int)(screen_height * height_portion[2])
                );break;
            case ULTRA:
                d = new Dimension(
                        (int)(screen_width * width_portion[3]), (int)(screen_height * height_portion[3])
                );break;
            default:throw new Exception("不支持的界面大小" + size);
        }
        toBeEdited.setPreferredSize(d);
        toBeEdited.setSize(d);
    }

    public static void setDialogSize(JDialog toBeEdited, int width_size, int height_size) throws Exception {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screen_width = screenSize.width;
        int screen_height = screenSize.height;
        Dimension d;
        // 界面占整个屏幕的百分比
        double[] width_portion = {0.1, 0.2, 0.4, 0.5};
        double[] height_portion = {0.1, 0.2, 0.2, 0.3};
        int width, height;
        switch (width_size) {
            case SMALL: width = (int) (screen_width * width_portion[0]); break;
            case NORMAL: width = (int) (screen_width * width_portion[1]); break;
            case BIG: width = (int) (screen_width * width_portion[2]); break;
            case ULTRA: width = (int) (screen_width * width_portion[3]); break;
            default: throw new Exception("不支持的对话框宽度参数" + width_size);
        }
        switch (height_size) {
            case SMALL: height = (int) (screen_height * height_portion[0]); break;
            case NORMAL: height = (int) (screen_height * height_portion[1]); break;
            case BIG: height = (int) (screen_height * height_portion[2]); break;
            case ULTRA: height = (int) (screen_height * height_portion[3]); break;
            default: throw new Exception("不支持的对话框宽度参数" + height_size);
        }
        d = new Dimension(width, height);
        toBeEdited.setPreferredSize(d);
        toBeEdited.setSize(d);
    }
}
