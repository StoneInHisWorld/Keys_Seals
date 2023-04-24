package view;

import java.io.IOException;
import java.util.Scanner;

public class CmdUI {
    private final int keyCmd = 1;
    private final int sealCmd = 2;
    private final int exitCmd = 0;
    private final String companyName = "周大福公司";
    private final String warning = "在使用本系统时，请勿更改.data文件";

    public static void main(String[] args) throws Exception {
        CmdUI ui = new CmdUI();
        Scanner scanner = new Scanner(System.in);
        ui.welcome();
        while(true) {
            System.out.println("请选择功能：" + ui.getKeyCmd() + ". 钥匙 " + ui.getSealCmd() + ". 印章 "
                    + ui.getExitCmd() + ". 退出");
            int cmd = scanner.nextInt();
            if (cmd == ui.getExitCmd()) {
                break;
            }
            else if (cmd == ui.getKeyCmd()) {
                KeyUI keyUI = new KeyUI();
                keyUI.mainPage();
            }
            else if (cmd == ui.getSealCmd()) {
                System.out.println("功能尚未开放！请选择其他功能：");
            }
            else {
                System.out.println("命令输入错误！");
            }
        }
        ui.goodbye();
    }

    public void welcome() {
        System.out.println("欢迎来到" + companyName +"钥匙管理系统");
        System.out.println("注意：" + warning);
    }

    public void goodbye() {
        System.out.println("再见！");
    }

    public int getKeyCmd() {
        return keyCmd;
    }

    public int getSealCmd() {
        return sealCmd;
    }

    public int getExitCmd() {
        return exitCmd;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getWarning() {
        return warning;
    }
}
