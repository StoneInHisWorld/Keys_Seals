package view;

import controller.KeyUIController;

import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class KeyUI {

    private final KeyUIController keyUIController;
    private Set<String> departments;
    private static final String welcome = "进入钥匙管理系统";
    // 搜索命令
    private static final String supChoice = "后勤";
    private static final String exitChoice = "exit";
    // 数字命令
    private static final int takeCmd = 1, addCmd = 2, fetchCmd = 3, exitCmd = 0;
    private static final String columnName = "部门名\t" + "序号\t" +
            "店铺名\t" + "保险柜序号\t" + "备用钥匙\t" + "应急钥匙\t" +
            "入库人\t" + "出库人\t" + "备注\t";

    public KeyUI() {
        this.keyUIController = new KeyUIController();
        try {
            this.departments = keyUIController.initKeyUI();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void mainPage() throws Exception {
        // 初始化界面
        System.out.println(welcome);
        String choice = supChoice;
        while (!choice.equals(exitChoice)) {
            choice = selectDepartment();
            overviewPage(choice);
        }
        exit();
    }

    private String selectDepartment() {
        this.displayDepartments();
        System.out.println("0.退出\n请选择要查看的部门：");
        int choice = this.getChoice(0, this.departments.size());
        if (choice == 0) {
            return exitChoice;
        }
        else {
            // 获取用户选择的部门名
            Iterator<String> it = this.departments.iterator();
            for (int i = 0; i < choice - 1; i++) {
                it.next();
            }
            return it.next();
        }
    }

    private void displayDepartments() {
        int i = 0;
        for (String department : departments) {
            System.out.print(((i++)+1) + "." + department + " ");
        }
    }

    /**
     * 总览界面
     * @param depChoice 选择的部门
     */
    private void overviewPage(String depChoice) throws Exception {
        if (depChoice.equals(supChoice)) {
            // 展示后勤钥匙
            this.displaySupKey();
        }
        else if (!depChoice.equals(exitChoice)) {
            // 展示部门钥匙
            this.displayDepKey(depChoice);
            this.overviewOptionPage(depChoice);
        }
    }

    private void overviewOptionPage(String dep) throws Exception {
        // 对钥匙进行操作
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("请选择功能：" + takeCmd + ". 入库 " +
                    addCmd + ". 添加保险柜 " + fetchCmd + ". 出库 " +
                    exitCmd + ". 退出");
            int cmd = scanner.nextInt();
            if (cmd == exitCmd) {
                break;
            }
            else if (cmd == takeCmd) {
                System.out.println("功能尚未开放！请选择其他功能：");
            }
            else if (cmd == addCmd) {
                this.addSafeUI(dep);
            }
            else if (cmd == fetchCmd) {
                System.out.println("功能尚未开放！请选择其他功能：");
            }
            else {
                System.out.println("命令输入错误！");
            }
        }
    }

    /**
     * 添加保险柜界面，并更新部门选择。可以添加新部门。
     * @param dep 目前界面展示的钥匙所属部门
     * @throws Exception 输入错误提示
     */
    private void addSafeUI(String dep) throws Exception {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            // 是否添加新部门
            System.out.println("是否继续在" + dep + "添加保险柜？1. 是 " +
                    "2. 否，添加一个新部门 0. 退出");
            int choice = getChoice(0, 2);
            if (choice == 0) {
                return;
            }
            else if (choice == 2) {
                System.out.print("请输入新部门名：");
                String new_dep = scanner.nextLine();
                // 部门名查重
                if (this.departments.contains(new_dep)) {
                    System.out.println(new_dep + "已存在，请重新输入新的部门名，或查看"
                            + new_dep + "钥匙以添加新的保险柜！");
                }
                else {
                    dep = new_dep;
                    break;
                }

            }
            else {
                break;
            }
        }
        String input = inputKey(dep, scanner);
        // 更新部门选择
        this.departments = this.keyUIController.addSafe(dep, input);
        System.out.println("添加保险柜成功！");
    }

    /**
     * 输入钥匙除部门和序号外的其他信息。使用tab间隔
     * @param dep 钥匙所属部门
     * @param scanner 输入扫描器
     * @return 输入字符串
     * @throws Exception 输入错误提示
     */
    private String inputKey(String dep, Scanner scanner) throws Exception {
        // 输入新钥匙数据
        System.out.println("请依次输入各项数据，项间使用tab间隔：");
        if (dep.equals(KeyUI.supChoice)) {
            // 添加后勤部的钥匙
            System.out.println();
        }
        else if (dep.equals(KeyUI.exitChoice)) {
            throw new Exception("错误的部门输入！");
        }
        else {
            System.out.println("店铺名\t保险柜序号\t备用钥匙\t应急钥匙" +
                    "\t入库人\t出库人\t备注");
        }
        return scanner.nextLine();
    }

    private void displayDepKey(String dep) {
        System.out.println(columnName);
        for (String key : this.keyUIController.displayDepKeys(dep)) {
            System.out.println(key);
        }
    }

    private void displaySupKey() {

    }

    private int getChoice(final int lowRan, final int highRan) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        while (true) {
            choice = scanner.nextInt();
            if (choice < lowRan || choice > highRan) {
                System.out.println("请输入正确的命令！");
            }
            else break;
        }
        return choice;
    }

    private void exit() throws IOException {
        this.keyUIController.exitKeyUI();
    }
}
