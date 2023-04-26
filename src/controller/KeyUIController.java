package controller;

import model.service.DBService;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
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

    public String getSafeMemberNames(String dep) throws Exception {
        if (dep.equals(supportDep)) {
            return this.dbService.getSupKeyColumnName();
        }
        else {
            return this.dbService.getDepKeyColumnName();
        }
    }
}
