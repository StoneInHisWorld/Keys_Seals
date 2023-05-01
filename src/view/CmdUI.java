package view;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class CmdUI {

    private static final int keyCmd = 1;
    private static final int sealCmd = 2;
    private static final int exitCmd = 0;
    private static final String configPath = "configuration.txt";
    private final String companyName;
    private final String warning;
    private final String supportDep;

    public CmdUI() throws IOException {
        // 加载配置文件
        FileInputStream fileInputStream = new FileInputStream(configPath);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream, StandardCharsets.UTF_8));
        this.companyName = bufferedReader.readLine().substring(4);
        this.warning = bufferedReader.readLine().substring(5);
        this.supportDep = bufferedReader.readLine().substring(6);
        bufferedReader.close();
        fileInputStream.close();
    }

    public static void main(String[] args){
        CmdUI ui;
        try {
            ui = new CmdUI();
        } catch (IOException e) {
            System.out.println("配置文件读取异常！");
            return;
        }
        Scanner scanner = new Scanner(System.in);
        ui.welcome();
        while(true) {
            System.out.println("请选择功能：" + ui.getKeyCmd() + ". 钥匙 " + ui.getSealCmd() + ". 印章 "
                    + ui.getExitCmd() + ". 退出");
            int cmd = KeyUI.getChoice(scanner, ui.getExitCmd(), ui.getSealCmd());
            try {
//                cmd = scanner.nextInt();
                if (cmd == ui.getExitCmd()) {
                    break;
                }
                else if (cmd == ui.getKeyCmd()) {
                    // 钥匙处理界面
                    KeyUI keyUI = new KeyUI(scanner, ui.getSupportDep());
                    try {
                        keyUI.mainPage();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                else if (cmd == ui.getSealCmd()) {
                    System.out.println("功能尚未开放！请选择其他功能：");
                }
                else {
                    throw new Exception();
                }
            } catch (Exception e) {
                System.out.println("命令输入错误！");
            }
        }
        ui.goodbye();
        scanner.close();
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

    public String getSupportDep() { return supportDep;    }
}
