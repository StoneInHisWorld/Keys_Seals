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
    private static String supportDep;
    private static final String exitChoice = "exit";
    // 数字命令
    private static final int retCmd = 1;
    private static final int addCmd = 2;
    private static final int fetchCmd = 3;
    private static final int delCmd = 4;
    private static final int exitCmd = 0;

    public KeyUI(Scanner scanner, String supportDep) {
        this.scanner = scanner;
        KeyUI.supportDep = supportDep;
        this.keyUIController = new KeyUIController(supportDep);
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

    /**
     * 选择部门进行展示
     * @return 用户选择的部门
     */
    private String selectDepartment() {
        this.displayDepartments();
        System.out.println("0.退出\n请选择要查看的部门：");
        int choice = getChoice(scanner, 0, this.departments.size());
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

    /**
     * 展示数据库中的所有部门名
     */
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
    private void overviewPage(String depChoice) {
        if (depChoice.equals(exitChoice)) return;
        this.displaySafe(depChoice);
        this.overviewOptionPage(depChoice);
    }

    /**
     * 总览界面功能选择
     * @param dep 总览界面选择的部门
     */
    private void overviewOptionPage(String dep) {
        // 对钥匙进行操作
        while(true) {
            // 部门列表没有对应部门，这种情况只会发生在删除dep内最后一个保险柜
            // 之后。
            if (!this.departments.contains(dep)) {
                System.out.println("只有" + addCmd + "添加保险柜功能有效！");
            }
            System.out.println("请选择功能：" + retCmd + ". 入库 " +
                    addCmd + ". 添加保险柜 " + fetchCmd + ". 出库 " +
                    delCmd + ". 删除保险柜 " + exitCmd + ". 退出");
            int cmd = getChoice(scanner, exitCmd, delCmd);
            if (cmd == exitCmd) {
                break;
            }
            else if (cmd == retCmd) {
                this.retKeyUI(dep);
            }
            else if (cmd == addCmd) {
                String old_dep = dep;
                // 当创建新部门时，会切换到新部门的选择页
                dep = this.addSafeUI(dep);
                if (!old_dep.equals(dep)) {
                    System.out.println("切换到" + dep + "的功能页，" +
                            dep + "的保险柜信息如下：");
                    this.displaySafe(dep);
                }
            }
            else if (cmd == fetchCmd) {
                this.fetchKeyUI(dep);
            }
            else if (cmd == delCmd) {
                this.delSafeUI(dep);
            }
            else {
                System.out.println("命令输入错误！");
            }
        }
    }

    private void retKeyUI(String dep) {
        System.out.println("（输入0则退出）");
        System.out.print("放入钥匙");
        String input = getInput("序号\t备用/紧急钥匙（b/j）\t入库人\t备注");
        if (input.equals("0")) return;
        String success_msg;
        try {
            success_msg = this.keyUIController.retKey(dep, input);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            retKeyUI(dep);
            return;
        }
        System.out.println(success_msg);
    }

    /**
     * 取出钥匙的界面
     * @param dep 钥匙所属的保险柜所属的部门
     */
    private void fetchKeyUI(String dep) {
        while (true) {
            try {
                String display;
                if (!dep.equals(supportDep)) {
                    display = "序号\t备用/紧急钥匙（b/j）\t出库人\t备注";
                }
                else {
                    display = "序号\t出库人\t备注";
                }
                System.out.println("（输入0则退出）");
                System.out.print("取出钥匙");
                String input = getInput(display);
                if (input.equals("0")) return;
                String success_msg = this.keyUIController.fetchKey(dep, input);
                System.out.println(success_msg);
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * 添加保险柜界面，并更新部门选择。可以添加新部门。
     * @param dep 目前界面展示的钥匙所属部门
     */
    private String addSafeUI(String dep) {
        while (true) {
            // 是否添加新部门
            System.out.println("是否继续在" + dep + "添加保险柜？1. 是 " +
                    "2. 否，添加一个新部门 0. 退出");
            int choice = getChoice(scanner, 0, 2);
            if (choice == 0) {
                return dep;
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
                    dep = new_dep;
                    break;
                }
                else if (new_dep.contains("后勤")) {
                    System.out.println("有关后勤部门的信息，部门栏请填“" + supportDep + "”！");
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
        try {
            String input = inputSafe(dep);
            // 更新部门选择
            this.departments = this.keyUIController.addSafe(dep, input);
        }
        catch (Exception e) {
            // 更新时出错则提示用户，并继续询问是否添加保险柜
            System.out.println(e.getMessage());
            return addSafeUI(dep);
        }
        System.out.println("添加保险柜成功！");
        return dep;
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

    /**
     * 删除保险柜界面
     * @param dep 保险柜所属部门
     */
    private void delSafeUI(String dep) {
        while (true) {
            System.out.println("请输入删除的保险柜序号（数字序号，输入0返回）：");
            try {
                // 输入保险柜序号
                int id = new Integer(scanner.nextLine());
                if (id == 0) return;
                String keyStr;
                // 确认保险柜的存在
                keyStr = this.keyUIController.findSafe(dep, id);
                // 输出信息，用户确认删除
                System.out.println("确定要删除" + id + "号保险柜吗？信息如下：");
                System.out.println(this.keyUIController.getSafeMemberNames(dep));
                System.out.println(keyStr);
                System.out.println("注意：此操作不可逆！1. 是 0. 否");
                if (getChoice(scanner, 0, 1) == 1) {
                    this.departments = this.keyUIController.delSafe(dep, id);
                    System.out.println("删除" + id + "号保险柜成功！" +
                            dep + "的保险柜信息变更如下：");
                    this.displaySafe(dep);
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("请输入正确的保险柜序号！");
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
//                // 输出信息，用户确认删除
//                System.out.println("确定要删除" + id + "号保险柜吗？信息如下：");
//                System.out.println(this.keyUIController.getSafeMemberNames(dep));
//                System.out.println(keyStr);
//                System.out.println("注意：此操作不可逆！1. 是 0. 否");
//                if (getChoice(scanner, 0, 1) == 0) {
//                    // 撤销删除则重新选择保险柜
//                    delSafeUI(dep);
//                    return;
//                }
//                else {
//                    try {
//                        this.departments = this.keyUIController.delSafe(dep, id);
//                        System.out.println("删除" + id + "号保险柜成功！"+ dep + "的保险柜信息变更如下：");
//                        this.displaySafe(dep);
//                        // this.departments = this.keyUIController.refreshDB();
//                        return;
//                    }
//                    catch (Exception e) {
//                        System.out.println(e.getMessage());
//                    }
//                }
//            }
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

    /**
     * 获取用户的选择，只能使用数字命令
     * @param lowRan 最小数字命令
     * @param highRan 最大数字命令
     * @return 选择结果
     */
    public static int getChoice(final Scanner scanner, final int lowRan,
                                final int highRan) {
        int choice;
        while (true) {
            try {
                choice = new Integer(scanner.nextLine());
            }
            catch (NumberFormatException e) {
                System.out.println("请输入正确的选择命令！");
                continue;
            }
            if (choice < lowRan || choice > highRan) {
                System.out.println("请输入正确的命令！");
            }
            else break;
        }
        return choice;
    }

    private void exit() throws Exception {
        this.keyUIController.exitKeyUI();
    }
}
