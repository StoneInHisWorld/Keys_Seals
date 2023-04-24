package view;

import controller.KeyUIController;

import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class KeyUI {

    private final KeyUIController keyUIController;
    private Set<String> departments;

    private final String welcome = "进入钥匙管理系统";
    private final String supChoice = "后勤";
    private final String exitChoice = "exit";
    private final String columnName = "部门名\t" + "序号\t" +
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

    public void mainPage() throws IOException {
        // 初始化界面
        System.out.println(this.welcome);
        String choice = this.supChoice;
        while (!choice.equals(this.exitChoice)) {
            choice = selectDepartment();
            overview(choice);
        }
        exit();
    }

    private String selectDepartment() {
        this.displayDepartments();
        System.out.println("0.退出\n请选择要查看的部门：");
        int choice = this.getChoice(0, this.departments.size());
        if (choice == 0) {
            return this.exitChoice;
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

    public void overview(String choice) {
        if (choice.equals(this.supChoice)) {
            // 展示后勤钥匙
            this.displaySupKey();
        }
        else if (!choice.equals(this.exitChoice)) {
            // 展示部门钥匙
            displayDepKey(choice);
        }
    }

    private void displayDepKey(String dep) {
        System.out.println(this.columnName);
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
