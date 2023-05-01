package controller;

import model.entity.Safe;
import model.service.DBService;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class KeyUIController {

    public static String supportDep;
    private final DBService dbService;

    public KeyUIController(String supportDep) {
        this.dbService = new DBService();
        KeyUIController.supportDep = supportDep;
        Safe.supportDep = supportDep;
    }

    public Set<String> initKeyUI() throws Exception {
        dbService.loadDB();
        return this.dbService.collectDepart();
    }

    /**
     * 退出钥匙界面需要将所有数据写回文件
     * @throws IOException 文件写回出错
     */
    public void exitKeyUI() throws Exception {
        this.dbService.writeBackDB();
    }

    /**
     * 添加保险柜，同时更新部门选择
     * @param dep 新保险柜所属部门
     * @param keyStr 新保险柜其他数据
     */
    public Set<String> addSafe(String dep, String keyStr) throws Exception {
        if (dep.equals(supportDep)) {
            this.dbService.addSupSafe(keyStr);
        }
        else {
            this.dbService.addDepSafe(dep, keyStr);
        }
        this.refreshDB();
        return this.dbService.collectDepart();
    }

    /**
     * 展示对应部门的所有保险柜信息
     * @param dep 保险柜所属部门
     * @return 所有保险柜的信息字符串
     * @throws Exception 空保险柜信息
     */
    public List<String> displaySafe(String dep) throws Exception {
        List<String> ret = new LinkedList<>();
        ret.add(this.getSafeMemberNames(dep));
        if (dep.equals(supportDep)) {
            // 输出后勤部保险柜信息
            if (ret.addAll(this.dbService.SupKeyToStr())) {
                return ret;
            }
        }
        else {
            // 输出部门保险柜信息
            if (ret.addAll(this.dbService.DepKeyToStr(dep))) {
                return ret;
            }
        }
        throw new Exception(dep + "无保险柜信息！");
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
    public Set<String> delSafe(String dep, int id) throws Exception {
        if (dep.equals(supportDep)) {
            this.dbService.delSupSafe(id);
        }
        else {
            this.dbService.delDepSafe(dep, id);
        }
        this.refreshDB();
        return this.dbService.collectDepart();
//        if (dep.equals(supportDep)) {
//            System.out.println("该功能暂未开放");
//            return false;
//        }
//        else {
//            // 删除部门保险柜
//            String keyStr;
//            // 确认保险柜的存在
//            try {
//                keyStr = this.dbService.findDepKey(dep, id).toString();
//            } catch (NullPointerException e) {
//                System.out.println(dep + "下无id为" + id + "的保险柜！");
//                return false;
//            }
//            // 输出信息，用户确认删除
//            System.out.println("确定要删除" + id + "号保险柜吗？信息如下：");
//            System.out.println(this.dbService.getDepKeyColumnName());
//            System.out.println(keyStr);
//            System.out.println("注意：此操作不可逆！1. 是 0. 否");
//            if (getChoice(scanner, 0, 1) == 0) {
//                return false;
//            }
//            else {
//                if (this.dbService.delDepSafe(dep, id)) {
//                    return true;
//                }
//                else {
//                    System.out.println("部门保险柜删除失败！");
//                    return false;
//                }
//            }
//        }
    }

//    public Set<String> refreshDB() throws Exception {
    public void refreshDB() throws Exception {
        this.dbService.writeBackDB();
//        return this.dbService.collectDepart();
    }

    /**
     * 解析用户输入的同时将出库信息更新到数据库中
     * @param dep 取出钥匙所属保险柜的部门
     * @param input 用户输入
     * @throws Exception 用户输入的错误提示，保险柜钥匙取出错误提示
     * @return 钥匙取出成功信息
     */
    public String fetchKey(String dep, String input) throws Exception {
        // 解析用户输入
        String[] strings = input.split("\t");
        int progress = 0;
        int id = 0;
        char key_select = 0;
        String fetch_person = null;
        String note;
        try {
            id = new Integer(strings[progress++]);
            key_select = strings[progress++].charAt(0);
            if (key_select != 'b' && key_select != 'j') {
                throw new Exception("选择备用钥匙请填写b，选择紧急钥匙请填写j");
            }
            fetch_person = strings[progress++];
            note = strings[progress];
        }
        catch (IndexOutOfBoundsException e) {
            // 备注项可以不填
            int index = new Integer(e.getMessage());
            if (index >= 3) {
                note = "";
            }
            else {
                throw new Exception("序号、钥匙选择、出库人三项必填！");   
            }
        }
        catch (NumberFormatException e) {
            throw new Exception("序号需为非负整数！");
        }
        // 将解析输入录入数据库中
        if (dep.equals(supportDep)) {
            throw new Exception("该功能尚未开放！");
        } 
        else {
            this.dbService.fetchDepKey(dep, id, key_select, fetch_person, note);
            // 刷新本地数据库
            this.refreshDB();
        }
        switch (key_select) {
            case 'b':return fetch_person + "成功取出" + dep + id +
                    "号保险柜的备用钥匙";
            case 'j':return fetch_person + "成功取出" + dep + id +
                    "号保险柜的紧急钥匙";
            default:throw new Exception("未知类型" + key_select +
                    "的钥匙选择！");
        }
    }

    /**
     * 钥匙入库
     * @param dep 钥匙所属部门
     * @param input 用户输入入库信息
     * @return 入库成功输出信息
     * @throws Exception 钥匙输入信息异常
     */
    public String retKey(String dep, String input) throws Exception {
        // 解析用户输入
        String[] strings = input.split("\t");
        int progress = 0;
        int id = 0;
        char key_select = 0;
        String ret_person = null;
        String note;
        try {
            id = new Integer(strings[progress++]);
            key_select = strings[progress++].charAt(0);
            if (key_select != 'b' && key_select != 'j') {
                throw new Exception("选择备用钥匙请填写b，选择紧急钥匙请填写j");
            }
            ret_person = strings[progress++];
            note = strings[progress];
        }
        catch (IndexOutOfBoundsException e) {
            // 备注项可以不填
            int index = new Integer(e.getMessage());
            if (index >= 3) {
                note = "";
            }
            else {
                throw new Exception("序号、钥匙选择、入库人三项必填！");
            }
        }
        catch (NumberFormatException e) {
            throw new Exception("序号需为非负整数！");
        }
        // 将解析输入录入数据库中
        if (dep.equals(supportDep)) {
            throw new Exception("该功能尚未开放！");
        }
        else {
            this.dbService.retDepKey(dep, id, key_select, ret_person, note);
            // 刷新本地数据库
            this.refreshDB();
        }
        switch (key_select) {
            case 'b':return ret_person + "成功放入" + dep + id +
                    "号保险柜的备用钥匙";
            case 'j':return ret_person + "成功放入" + dep + id +
                    "号保险柜的紧急钥匙";
            default:throw new Exception("未知类型" + key_select +
                    "的钥匙选择！");
        }
    }

    public String findSafe(String dep, int id) throws Exception {
        if (dep.equals(supportDep)) {
            return this.dbService.findSupSafe(id);
        }
        else {
            return this.dbService.findDepSafe(dep, id);
        }
    }
}
