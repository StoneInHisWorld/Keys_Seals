package view.GUI.safeRelated;

import controller.SafeUIController;
import view.GUI.BasicMethods;

import java.util.*;

/**
 * GUI消息分发器，负责处理GUI层的消息，转换为适配SafeUIController接口的输入。
 * 负责从Controller获取数据库信息转发给GUI层。
 */
public class SafeGUIController {

    private final SafeUIController controller;

    /**
     * GUI消息分发器，负责处理GUI层的消息，转换为适配SafeUIController接口的输入。
     * 负责从Controller获取数据库信息转发给GUI层。
     */
    public SafeGUIController(String supDep) throws Exception {
        this.controller = new SafeUIController(supDep);
        this.controller.initKeyUI();
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

    /**
     * 获取完整的保险柜数据
     * @param dep 保险柜所在部门
     * @return dep部门下所有保险柜的信息序列
     * @throws Exception 找不到保险柜异常
     */
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

    public Object[] getToBeInputOfAddSafe(String dep) {
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
//    public Object[] getToBeInputOfAddAnyDepSafe() {
//        List<String> toBeInput = new LinkedList<>(Arrays.asList(
//                controller.getSafeMemberNames(dep).split("\t")));
//        // 去掉序号项
//        toBeInput.remove(1);
//        // 添加必填项
//        int index = 0;
//        if (dep.equals(SafeUIController.supportDep)) {
//            toBeInput.set(index, "*" + toBeInput.get(index));
//        }
//        else {
//            toBeInput.set(index, "*" + toBeInput.get(index));
//            index++;
//            toBeInput.set(index, "*" + toBeInput.get(index));
//        }
//        return toBeInput.toArray();
//    }

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

    public Set<String> collectDepartments() {
        List<String> departments = new LinkedList<>(this.controller.getDepartments());
//        Collections.sort(departments);
        return new LinkedHashSet<>(departments);
    }

    public String getSupDep() {
        return SafeUIController.supportDep;
    }

//    public List<Object[]> getAllSafeData() throws Exception {
//        List<Object[]> ret = new LinkedList<>();
//        for (String department : this.collectDepartments()) {
//            ret.addAll(this.getSafeData(department));
//        }
//        return ret;
//    }

    public int[] getAllSafeColumnSize(String dep) {
        int[] columnSize;
        if (dep.equals(SafeUIController.supportDep)) {
            columnSize = new int[]{
                    BasicMethods.BIG, BasicMethods.SMALL,
                    BasicMethods.NORMAL, BasicMethods.SMALL,
                    BasicMethods.BIG, BasicMethods.BIG,
                    BasicMethods.ULTRA
            };
        }
        else {
            columnSize = new int[]{
                    BasicMethods.BIG,
                    BasicMethods.SMALL, BasicMethods.NORMAL,
                    BasicMethods.SMALL, BasicMethods.SMALL,
                    BasicMethods.SMALL, BasicMethods.BIG,
                    BasicMethods.BIG, BasicMethods.ULTRA
            };
        }
        return columnSize;
    }

    public String[] getAllSafeTableColumnNames(String dep) {
        String memberStr = this.controller.getSafeMemberNames(dep);
        List<String> strings = new LinkedList<>(Arrays.asList(memberStr.split("\t")));
        strings.add("操作");
        String[] ret = new String[strings.size()];
        for (int i = 0; i < strings.size(); i++) {
            ret[i] = strings.get(i);
        }
        return ret;
    }

    /**
     * 将更新的数据写回数据文件
     * @throws Exception 文件写入异常
     */
    public void refreshDB() throws Exception {
        this.controller.refreshDB();
    }
}
