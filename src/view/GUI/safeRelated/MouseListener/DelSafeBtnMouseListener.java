//package view.GUI.safeRelated.MouseListener;
//
//import view.GUI.BasicMethods;
//import view.GUI.safeRelated.Frame.SafeOverviewFrame;
//
//import javax.swing.*;
//import java.awt.event.MouseEvent;
//
//public class DelSafeBtnMouseListener extends BasicMouseListener {
//
//    private final SafeOverviewFrame depOverviewFrame;
//
//    public DelSafeBtnMouseListener(JFrame parentFrame, SafeOverviewFrame depOverviewFrame) {
//        super(parentFrame);
//        this.depOverviewFrame = depOverviewFrame;
//    }
//
//    @Override
//    public void mouseClicked(MouseEvent e) {
//        if (BasicMethods.yesOrNo("确定要删除该保险柜吗？")) {
//            try {
//                this.dealTransaction(null);
//                this.refreshParentFrame();
//            } catch (Exception exception) {
//                BasicMethods.dealException(exception);
//            }
//        }
//    }
//
//    @Override
//    public void refreshParentFrame() {
//        try {
//            this.depOverviewFrame.drawTable();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void dealTransaction(Object[] inputData) throws Exception {
//        String successMsg = this.depOverviewFrame.
//                dealDeletingSafe();
//        BasicMethods.prompt(successMsg);
//    }
//}
