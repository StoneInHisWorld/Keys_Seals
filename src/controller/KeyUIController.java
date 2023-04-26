package controller;

import model.service.DBService;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class KeyUIController {

    private static final String supportDep = "后勤";
    private final DBService dbService;

    public KeyUIController() {
        this.dbService = new DBService();
    }

    public Set<String> initKeyUI() throws Exception {
        dbService.initDB();
        return this.dbService.collectDepart();
    }

//    public List<String> displayDepKeys(String dep, List<String> columnNames) {
//        columnNames = this.dbService.getColumnName(dep);
//        return dbService.DepKeyToStr(dep);
//    }

    /**
     * 退出钥匙界面需要将所有数据写回文件
     * @throws IOException 文件写回出错
     */
    public void exitKeyUI() throws IOException {
        this.dbService.WriteBackDB();
    }

    /**
     * 添加保险柜，同时更新部门选择
     * @param dep 新保险柜所属部门
     * @param keyStr 新保险柜其他数据
     * @return 更新的部门选择
     */
    public Set<String> addSafe(String dep, String keyStr) {
        try {
            this.dbService.addSafe_DepKey(dep, keyStr);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return this.dbService.collectDepart();
    }

    public List<String> displaySafe(String dep) throws Exception {
        List<String> ret = new LinkedList<>();
        ret.add(this.getSafeMemberNames(dep));
        if (dep.equals(supportDep)) {
            // 输出后勤部保险柜信息
//            ret.add(dbService.getSupKeyColumnName());
            if (!ret.addAll(this.dbService.SupKeyToStr())) {
                throw new Exception("生成后勤部保险柜信息失败！");
            }
            else return ret;
        }
        else {
            // 输出部门保险柜信息
//            ret.add(this.dbService.getDepKeyColumnName());
            if (!ret.addAll(this.dbService.DepKeyToStr(dep))) {
                throw new Exception("生成部门保险柜信息失败！");
            }
            else return ret;
        }
    }

    public String getSafeMemberNames(String dep) {
        if (dep.equals(supportDep)) {
            return this.dbService.getSupKeyColumnName();
        }
        else {
            return this.dbService.getDepKeyColumnName();
        }
    }

    /**
     * 删除保险柜
     * @param dep 保险柜所属部门
     * @param id 保险柜序号
     * @return 删除是否成功，成功则true，失败则false
     */
    public boolean delSafe(String dep, int id) {
        // 退出则不进行任何操作
        if (id == 0) return true;
        if (dep.equals(supportDep)) {
            System.out.println("该功能暂未开放");
            return false;
        }
        else {
            // 删除部门保险柜
            String keyStr;
            // 确认保险柜的存在
            try {
                keyStr = this.dbService.findDepKey(id).toString();
            } catch (NullPointerException e) {
                System.out.println(dep + "下无id为" + id + "的保险柜！");
                return false;
            }
            // 输出信息，用户确认删除
            System.out.println("确定要删除" + id + "号保险柜吗？信息如下：");
            System.out.println(this.dbService.getDepKeyColumnName());
            System.out.println(keyStr);
            System.out.println("注意：此操作不可逆！1. 是 0. 否");
            if (getChoice(0, 1) == 0) {
                return false;
            }
            else {
                if (this.dbService.delDepSafe(dep, id)) {
                    return true;
                }
                else {
                    System.out.println("部门保险柜删除失败！");
                    return false;
                }
            }
        }
    }

    /**
     * 获取用户的选择，只能使用数字命令
     * @param lowRan 最小数字命令
     * @param highRan 最大数字命令
     * @return 选择结果
     */
    public static int getChoice(final int lowRan, final int highRan) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        while (true) {
            choice = scanner.nextInt();
            if (choice < lowRan || choice > highRan) {
                System.out.println("请输入正确的命令！");
            }
            else break;
        }
        scanner.close();
        return choice;
    }
}
