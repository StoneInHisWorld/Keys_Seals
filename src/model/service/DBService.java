package model.service;

import model.dao.DepKeysDAO;
import model.entity.DB;
import model.entity.DepKey;
import model.entity.SupKey;

import java.io.IOException;
import java.util.*;

public class DBService {

    private final DB db;
    private final DepKeysDAO depKeysDAO;

    public DBService() {
        this.db = new DB();
        this.depKeysDAO = new DepKeysDAO();
    }

    public void initDB() throws Exception {
        db.setDepKeys(this.depKeysDAO.findAll());
    }

    public Set<String> collectDepart() {
        Set<String> depSet = new LinkedHashSet<>();
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
     * 所有的SupKey转化为输出字符串，返回
     * @return 部门为后勤的SupKey的字符串形式
     */
    public List<String> SupKeyToStr() {
        List<String> ret = new LinkedList<>();
        for (SupKey supKey : this.db.getSupKeys()) {
            ret.add(supKey.toString());
        }
        return ret;
    }

    /**
     * 将数据库对象中的内容全部按序号顺序写回文件
     * @throws IOException 文件写入异常
     */
    public void WriteBackDB() throws IOException {
        List<DepKey> depKeys = this.db.getDepKeys();
        // depKey需按序号顺序排列后写入
        Collections.sort(depKeys);
        this.depKeysDAO.writeBack(depKeys);
    }

    /**
     * 添加部门钥匙的保险柜
     * @param dep 保险柜所属部门
     * @param keyStr 添加保险桂的详细信息
     * @throws Exception 输入字符串不符格式异常
     */
    public void addSafe_DepKey(String dep, String keyStr) throws Exception {
        List<DepKey> depKeys = this.db.getDepKeys();
        int lastestId = depKeys.get(depKeys.size() - 1).getId();
        // 检查输入的字符串是否合法
        keyStr = dep + '\t' + (lastestId + 1) + '\t' + keyStr;
        DepKey depKey = new DepKey(keyStr);
        depKeys.add(depKey);
    }

    public String getSupKeyColumnName() {
        StringBuilder columnNames = new StringBuilder();
        // 成员名间添加tab键
        for (String s : SupKey.memberToStr()) {
            columnNames.append(s).append('\t');
        }
        // 去掉最后一个tab
        return columnNames.substring(0, columnNames.length() - 1);
    }

    public String getDepKeyColumnName() {
        StringBuilder columnNames = new StringBuilder();
        // 成员名间添加tab键
        for (String s : DepKey.memberToStr()) {
            columnNames.append(s).append('\t');
        }
        // 去掉最后一个tab
        return columnNames.substring(0, columnNames.length() - 1);
    }

    /**
     * 根据id查找部门保险柜
     * @param id 保险柜ID
     * @return 找到则返回对应的保险柜，否则返回null
     */
    public DepKey findDepKey(int id) {
        for (DepKey depKey : this.db.getDepKeys()) {
            if (depKey.getId() == id) return depKey;
        }
        return null;
    }

    public boolean delDepSafe(String dep, int id) {
        // 删除部门符合且id符合的保险柜
        return this.db.getDepKeys().removeIf(
                depKey -> depKey.getDepartment().equals(dep) &&
                        depKey.getId() == id);
    }
}
