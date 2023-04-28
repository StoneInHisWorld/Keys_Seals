package view;

import controller.KeyUIController;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class KeyUI {

    private final Scanner scanner;
    private final KeyUIController keyUIController;
    private Set<String> departments;
    private static final String welcome = "进入钥匙管理系统";
    // 搜索命令
    private final String supportDep;
    private static final String exitChoice = "exit";
    // 数字命令
    private static final int takeCmd = 1;
    private static final int addCmd = 2;
    private static final int fetchCmd = 3;
    private static final int delCmd = 4;
    private static final int exitCmd = 0;

    public KeyUI(Scanner scanner, String supportDep) {
        this.scanner = scanner;
        this.supportDep = supportDep;
        this.keyUIController = new KeyUIController(scanner);
        try {
            this.departments = keyUIController.initKeyUI();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void mainPage() throws Exception {
        // 初始化界面
        System.out.println(welcome);
        String choice = supportDep;
        while (!choice.equals(exitChoice)) {
            choice = selectDepartment();
            overviewPage(choice);
        }
        exit();
    }

    private String selectDepartment() {
        this.displayDepartments();
        System.out.println("0.退出\n请选择要查看的部门：");
        int choice = KeyUIController.getChoice(
                scanner, 0, this.departments.size());
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
        if (this.departments.size() == 0) {
            System.out.println("数据库中暂未存有任何保险柜数据！");
            return;
        }
        for (String department : departments) {
            System.out.print(((i++)+1) + "." + department + " ");
        }
    }

    /**
     * 总览界面
     * @param depChoice 选择的部门
     */
    private void overviewPage(String depChoice) throws Exception {
        if (depChoice.equals(exitChoice)) return;
        this.displaySafe(depChoice);
        this.overviewOptionPage(depChoice);
    }

    private void overviewOptionPage(String dep) throws Exception {
        // 对钥匙进行操作
        while(true) {
            System.out.println("请选择功能：" + takeCmd + ". 入库 " +
                    addCmd + ". 添加保险柜 " + fetchCmd + ". 出库 " +
                    delCmd + ". 删除保险柜 " + exitCmd + ". 退出");
            int cmd = scanner.nextInt();
            if (cmd == exitCmd) {
                break;
            }
            else if (cmd == takeCmd) {
                this.takeKeyUI(dep);
            }
            else if (cmd == addCmd) {
                this.addSafeUI(dep);
            }
            else if (cmd == fetchCmd) {
                System.out.println("功能尚未开放！请选择其他功能：");
            }
            else if (cmd == delCmd) {
                this.delSafeUI(dep);
            }
            else {
                System.out.println("命令输入错误！");
            }
        }
    }

    private void takeKeyUI(String dep) {
        System.out.print("取出钥匙");
        String input = getInput("序号\t备用/紧急钥匙（b/j）\t出库人\t备注");
        try {
            this.keyUIController.takeKey(dep, input);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 添加保险柜界面，并更新部门选择。可以添加新部门。
     * @param dep 目前界面展示的钥匙所属部门
     */
    private void addSafeUI(String dep) throws Exception {
        while (true) {
            // 是否添加新部门
            System.out.println("是否继续在" + dep + "添加保险柜？1. 是 " +
                    "2. 否，添加一个新部门 0. 退出");
            int choice = KeyUIController.getChoice(
                    scanner, 0, 2);
            if (choice == 0) {
                return;
            }
            else if (choice == 2) {
                Scanner scanner = new Scanner(System.in);
                System.out.print("请输入新部门名：");
                String new_dep = scanner.nextLine();
                // 部门名查重
                if (this.departments.contains(new_dep)) {
                    System.out.println(new_dep + "已存在，请重新输入新的部门名，或查看"
                            + new_dep + "钥匙以添加新的保险柜！");
                }
                else if (new_dep.equals(supportDep)) {
                    System.out.println("该功能尚未开放！");
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
        // 获取输入
        String input = inputSafe(dep);
        // 更新部门选择
        this.keyUIController.addSafe(dep, input);
        this.departments = this.keyUIController.refreshDB();
        System.out.println("添加保险柜成功！");
    }

    /**
     * 输入保险柜除部门和序号外的其他信息。使用tab间隔
     * @param dep 保险柜所属部门
     * @return 输入字符串
     */
    private String inputSafe(String dep) {
        // 获取新保险柜需要输入的数据
        String columnNames = this.keyUIController.getSafeMemberNames(dep);
        // 取出前两项
        columnNames = columnNames.substring(columnNames.indexOf('\t') + 1);
        columnNames = columnNames.substring(columnNames.indexOf('\t') + 1);
        return this.getInput(columnNames);
    }

    /**
     * 获取默认输入流中的用户输入
     * @param display 需要展示的输入提示
     * @return 输入字符串
     */
    private String getInput(String display) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请依次输入各项数据，项间使用tab间隔：");
        // 展示输入提示
        System.out.println(display);
        return scanner.nextLine();
    }

    private void delSafeUI(String dep) throws Exception {
        while (true) {
            System.out.println("请输入删除的保险柜序号（数字序号，输入0返回）：");
            int id = scanner.nextInt();
            if (id == 0) return;
            if (this.keyUIController.delSafe(dep, id)) {
                System.out.println("删除" + id + "号保险柜成功！"+ dep + "的保险柜信息变更如下：");
                this.displaySafe(dep);
                this.departments = this.keyUIController.refreshDB();
                return;
            }
        }
    }

    private void displaySafe(String dep) {
        try {
            List<String> outputs = this.keyUIController.displaySafe(dep);
            for (String output : outputs) {
                System.out.println(output);
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void exit() throws IOException {
        this.keyUIController.exitKeyUI();
    }
}
