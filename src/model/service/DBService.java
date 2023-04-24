package model.service;

import model.dao.DepKeysDAO;
import model.entity.DB;
import model.entity.DepKey;
import model.entity.SupKey;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class DBService {

    private DB db;
    private DepKeysDAO depKeysDAO;

    public DBService() {
        this.db = new DB();
        this.depKeysDAO = new DepKeysDAO();
    }

    public void initDB() throws Exception {
        db.setDepKeys(this.depKeysDAO.findAll());
    }

    public Set<String> collectDepart() {
        Set<String> depSet = new HashSet<>();
        for (DepKey key : db.getDepKeys()) {
            depSet.add(key.getDepartment());
        }
        for (SupKey key : db.getSupKeys()) {
            depSet.add(key.getDepartment());
        }
        return depSet;
    }

    /**
     * 将所有的DepKey转化为输出字符串，返回
     * @return 所有DepKey的字符串格式
     */
    public List<String> DepKeyToStr() {
        List<String> ret = new LinkedList<>();
        for (DepKey depKey : this.db.getDepKeys()) {
            ret.add(depKey.toString());
        }
        return ret;
    }

    /**
     * 选择部门为dep的depKey转化为输出字符串，返回
     * @param dep 选择的部门
     * @return 部门为dep的depKey的字符串形式
     */
    public List<String> DepKeyToStr(String dep) {
        List<String> ret = new LinkedList<>();
        for (DepKey depKey : this.db.getDepKeys()) {
            if (depKey.getDepartment().equals(dep))
                ret.add(depKey.toString());
        }
        return ret;
    }

    /**
     * 将数据库对象中的内容全部写回文件
     * @throws IOException 文件写入异常
     */
    public void WriteBackDB() throws IOException {
        this.depKeysDAO.writeBack(this.DepKeyToStr());
    }
}
