package controller;

import model.service.DBService;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class KeyUIController {

    private final DBService dbService;

    public KeyUIController() {
        this.dbService = new DBService();
    }

    public Set<String> initKeyUI() throws Exception {
        dbService.initDB();
        return this.dbService.collectDepart();
    }

    public List<String> displayDepKeys(String dep) {
        return dbService.DepKeyToStr(dep);
    }

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
}
