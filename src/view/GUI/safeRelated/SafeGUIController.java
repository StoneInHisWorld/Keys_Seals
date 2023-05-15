package view.GUI.safeRelated;

import controller.SafeUIController;
import view.GUI.BasicMethods;

import javax.swing.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * GUI消息分发器，负责处理GUI层的消息，转换为适配SafeUIController接口的输入。
 * 负责从Controller获取数据库信息转发给GUI层。
 */
public class SafeGUIController {

    private final SafeUIController controller;

    /**
     * GUI消息分发器，负责处理GUI层的消息，转换为适配SafeUIController接口的输入。
     * 负责从Controller获取数据库信息转发给GUI层。
     * @param controller UI控制器
     */
    public SafeGUIController(SafeUIController controller) {
        this.controller = controller;
    }

    public String addSafe(String dep, Object[] inputData)
            throws Exception {
        StringBuilder safeStr = new StringBuilder();
        for (Object inputDatum : inputData) {
            safeStr.append(inputDatum.toString());
            safeStr.append("\t");
        }
        this.controller.addSafe(dep, safeStr.toString());
        return "添加" + dep + "保险柜成功！";
    }

    /**
     * 获取表格列名
     * @return 可用表格列名
     */
    public String[] getTableColumnNames(String dep) {
        String memberStr = this.controller.getSafeMemberNames(dep);
        List<String> strings = new LinkedList<>(Arrays.asList(memberStr.split("\t")));
        // 去掉部门名称表头
        strings.remove(0);
        strings.add("操作");
        String[] ret = new String[strings.size()];
        for (int i = 0; i < strings.size(); i++) {
            ret[i] = strings.get(i);
        }
        return ret;
    }

    public String retKey(String dep, Object[] inputData,
                         String selectedSafeId)
                            throws Exception {
        StringBuilder input = new StringBuilder(
                selectedSafeId + "\t");
        for (Object inputDatum : inputData) {
            input.append(inputDatum);
            input.append("\t");
        }
        this.controller.retKey(dep, input.toString());
        String successMsg;
        if (dep.equals(SafeUIController.supportDep)) {
            successMsg = "成功归还" + dep + "保险柜的一把钥匙！";
        }
        else {
            char keySelected = inputData[0].toString().charAt(0);
            switch (keySelected) {
                case 'b':successMsg = "成功归还" + dep + "保险柜的一把备用钥匙！";break;
                case 'j':successMsg = "成功归还" + dep + "保险柜的一把紧急钥匙！";break;
                default:throw new Exception("错误的钥匙选择！");
            }
        }
        return successMsg;
    }

    public List<Object[]> getSafeData(String dep) throws Exception {
        return this.controller.getSafeMembers(dep);
    }

    public int[] getColumnSize(String dep) {
        int[] columnSize;
        if (dep.equals(SafeUIController.supportDep)) {
            columnSize = new int[]{
                    BasicMethods.SMALL, BasicMethods.NORMAL,
                    BasicMethods.SMALL, BasicMethods.BIG,
                    BasicMethods.BIG, BasicMethods.ULTRA
            };
        }
        else {
            columnSize = new int[]{
                    BasicMethods.SMALL, BasicMethods.NORMAL,
                    BasicMethods.SMALL, BasicMethods.SMALL,
                    BasicMethods.SMALL, BasicMethods.BIG,
                    BasicMethods.BIG, BasicMethods.ULTRA
            };
        }
        return columnSize;
    }

    public Object[] getAddingSafeToBeInput(String dep) {
        List<String> toBeInput = new LinkedList<>(Arrays.asList(
                controller.getSafeMemberNames(dep).split("\t")));
        toBeInput.remove(0);
        toBeInput.remove(0);
        // 添加必填项
        int index = 0;
        if (dep.equals(SafeUIController.supportDep)) {
            toBeInput.set(index, "*" + toBeInput.get(index));
        }
        else {
            toBeInput.set(index, "*" + toBeInput.get(index));
            index++;
            toBeInput.set(index, "*" + toBeInput.get(index));
        }
        return toBeInput.toArray();
    }

    public String delSafe(String dep, int id) throws Exception {
        this.controller.delSafe(dep, id);
        return "成功删除" + dep + "序号为" + id + "的保险柜！";
    }

    public String fetchKey(String dep, Object[] inputData,
                           String selectedSafeId) throws Exception {
        StringBuilder input = new StringBuilder(
                selectedSafeId + "\t");
        for (Object inputDatum : inputData) {
            input.append(inputDatum);
            input.append("\t");
        }
        this.controller.fetchKey(dep, input.toString());
        String successMsg;
        if (dep.equals(SafeUIController.supportDep)) {
            successMsg = "成功取出" + dep + "保险柜的一把钥匙！";
        }
        else {
            char keySelected = inputData[0].toString().charAt(0);
            switch (keySelected) {
                case 'b':successMsg = "成功取出" + dep + "保险柜的一把备用钥匙！";break;
                case 'j':successMsg = "成功取出" + dep + "保险柜的一把紧急钥匙！";break;
                default:throw new Exception("错误的钥匙选择！");
            }
        }
        return successMsg;
    }

    public Object[] getFetKeyBtnToBeInput(String dep) {
        String[] toBeInput;
        if (dep.equals(SafeUIController.supportDep)) {
            toBeInput = new String[]{"*出库人", "备注"};
        }
        else {
            toBeInput = new String[]{"*取备用或应急钥匙（b或j）", "*出库人", "备注"};
        }
        return toBeInput;
    }

    public Object[] getRetKeyBtnToBeInput(String dep) {
        String[] toBeInput;
        if (dep.equals(SafeUIController.supportDep)) {
            toBeInput = new String[]{"*入库人", "备注"};
        }
        else {
            toBeInput = new String[]{"*还备用或应急钥匙（b或j）", "*入库人", "备注"};
        }
        return toBeInput;
    }
}