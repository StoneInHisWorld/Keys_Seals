package view.CmdUI;

import view.UI;

import java.util.Scanner;

public class CmdUI extends UI {

    private static final int keyCmd = 1;
    private static final int sealCmd = 2;
    private static final int exitCmd = 0;

    public CmdUI() throws Exception {
//        // 加载配置文件
//        FileInputStream fileInputStream = new FileInputStream(configPath);
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream, StandardCharsets.UTF_8));
//        this.companyName = bufferedReader.readLine().substring(4);
//        this.warning = bufferedReader.readLine().substring(5);
//        this.supportDep = bufferedReader.readLine().substring(6);
//        bufferedReader.close();
//        fileInputStream.close();
    }

    public static void main(String[] args) throws Exception {
        CmdUI ui;
        ui = new CmdUI();
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
        System.out.println("欢迎来到" + super.companyName +"钥匙管理系统v" + super.version);
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

    public String getSupportDep() { return supportDep; }
}
