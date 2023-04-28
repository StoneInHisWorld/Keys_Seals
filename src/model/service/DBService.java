package model.service;

import model.dao.DepKeysDAO;
import model.entity.DB;
import model.entity.DepSafe;
import model.entity.SupSafe;

import java.io.IOException;
import java.util.*;

public class DBService {

    private final DB db;
    private final DepKeysDAO depKeysDAO;

    public DBService() {
        this.db = new DB();
        this.depKeysDAO = new DepKeysDAO();
    }

    public void loadDB() throws Exception {
        db.setDepSafes(this.depKeysDAO.findAll());
    }

    public Set<String> collectDepart() {
        Set<String> depSet = new LinkedHashSet<>();
        for (DepSafe key : db.getDepSafes()) {
            depSet.add(key.getDepartment());
        }
        for (SupSafe key : db.getSupSafes()) {
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
        for (DepSafe depSafe : this.db.getDepSafes()) {
            ret.add(depSafe.toString());
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
        for (DepSafe depSafe : this.db.getDepSafes()) {
            if (depSafe.getDepartment().equals(dep))
                ret.add(depSafe.toString());
        }
        return ret;
    }

    /**
     * 所有的SupKey转化为输出字符串，返回
     * @return 部门为后勤的SupKey的字符串形式
     */
    public List<String> SupKeyToStr() {
        List<String> ret = new LinkedList<>();
        for (SupSafe supSafe : this.db.getSupSafes()) {
            ret.add(supSafe.toString());
        }
        return ret;
    }

    /**
     * 将数据库对象中的内容全部按序号顺序写回文件
     * @throws IOException 文件写入异常
     */
    public void writeBackDB() throws IOException {
        List<DepSafe> depSafes = this.db.getDepSafes();
        // depKey需按序号顺序排列后写入
        Collections.sort(depSafes);
        this.depKeysDAO.writeBack(depSafes);
    }

    /**
     * 添加部门钥匙的保险柜
     * @param dep 保险柜所属部门
     * @param keyStr 添加保险桂的详细信息
     * @throws Exception 输入字符串不符格式异常
     */
    public void addSafe_DepKey(String dep, String keyStr) throws Exception {
        List<DepSafe> depSafes = this.db.getDepSafes();
        int lastestId = depSafes.get(depSafes.size() - 1).getId();
        // 检查输入的字符串是否合法
        keyStr = dep + '\t' + (lastestId + 1) + '\t' + keyStr;
        DepSafe depSafe = new DepSafe(keyStr);
        depSafes.add(depSafe);
    }

    public String getSupKeyColumnName() {
        StringBuilder columnNames = new StringBuilder();
        // 成员名间添加tab键
        for (String s : SupSafe.memberToStr()) {
            columnNames.append(s).append('\t');
        }
        // 去掉最后一个tab
        return columnNames.substring(0, columnNames.length() - 1);
    }

    public String getDepKeyColumnName() {
        StringBuilder columnNames = new StringBuilder();
        // 成员名间添加tab键
        for (String s : DepSafe.memberToStr()) {
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
    public DepSafe findDepKey(int id) {
        for (DepSafe depSafe : this.db.getDepSafes()) {
            if (depSafe.getId() == id) return depSafe;
        }
        return null;
    }

    /**
     * 根据id和部门查找保险柜
     * @param dep 保险柜所属部门
     * @param id 保险柜ID
     * @return 找到则返回对应的保险柜，否则返回null
     */
    public DepSafe findDepKey(String dep, int id) {
        for (DepSafe depSafe : this.db.getDepSafes()) {
            if (depSafe.getId() == id && depSafe.getDepartment().equals(dep))
                return depSafe;
        }
        return null;
    }

    /**
     * 删除部门和序号均符合用户输入的保险柜
     * @param dep 保险柜所属部门
     * @param id 保险柜id
     * @return 删除是否成功
     */
    public boolean delDepSafe(String dep, int id) {
        // 删除部门符合且id符合的保险柜
        if (this.db.getDepSafes().removeIf(
                depSafe -> depSafe.getDepartment().equals(dep) &&
                        depSafe.getId() == id)) {
            // 若删除成功，则刷新所有key的id
            List<DepSafe> depSafes = this.db.getDepSafes();
            int new_id = 1;
            for (DepSafe depSafe : depSafes) {
                depSafe.setId(new_id++);
            }
            return true;
        }
        return false;
    }

    /**
     * 取出部门保险柜的钥匙
     * @param dep 保险柜所属部门
     * @param id 保险柜id
     * @param key_select 选择的钥匙
     * @param fetch_person 出库人
     * @param note 备注
     * @throws Exception 取出钥匙时出现的异常
     */
    public void fetchDepKey(String dep, int id, char key_select,
                            String fetch_person, String note) throws Exception {
        for (DepSafe depSafe : this.db.getDepSafes()) {
            // 找到对应部门下的保险柜
            if (depSafe.getDepartment().equals(dep) &&
                    depSafe.getId() == id) {
                switch (key_select) {
                    case 'b':{
                        int back_up_num = depSafe.getBack_up() - 1;
                        if (back_up_num < 0) {
                            throw new Exception("备用钥匙数量不足！");
                        }
                        else {
                            depSafe.setBack_up(back_up_num);
                            fetch_person += "（备用）";
                        }
                    }break;
                    case 'j': {
                        int emer_num = depSafe.getEmergency() - 1;
                        if (emer_num < 0) {
                            throw new Exception("紧急钥匙数量不足！");
                        }
                        else {
                            depSafe.setEmergency(emer_num);
                            fetch_person += "（紧急）";
                        }
                    }break;
                    default: throw new Exception("未知类型钥匙" + key_select + "！");
                }
                depSafe.setLast_fetch(fetch_person);
                if (!note.equals("")) {
                    depSafe.setNote(note);
                }
                return;
            }
        }
        throw new Exception(dep + "没有序号为" + id + "的保险柜！");
    }

    /**
     * 归还钥匙
     * @param dep 钥匙入库保险柜所属部门
     * @param id 保险柜id
     * @param key_select 选择的钥匙钥匙类型
     * @param ret_person 入库人
     * @param note 备注
     * @throws Exception 钥匙归还出错信息
     */
    public void retDepKey(String dep, int id, char key_select, String ret_person, String note) throws Exception {
        for (DepSafe depSafe : this.db.getDepSafes()) {
            // 找到对应部门下的保险柜
            if (depSafe.getDepartment().equals(dep) &&
                    depSafe.getId() == id) {
                switch (key_select) {
                    case 'b':{
                        depSafe.setBack_up(depSafe.getBack_up() + 1);
                        ret_person += "（备用）";
                    }break;
                    case 'j': {
                        depSafe.setEmergency(depSafe.getEmergency() + 1);
                        ret_person += "（紧急）";
                    }break;
                    default: throw new Exception("未知类型钥匙" + key_select + "！");
                }
                depSafe.setLast_return(ret_person);
                if (!note.equals("")) {
                    depSafe.setNote(note);
                }
                return;
            }
        }
        throw new Exception(dep + "没有序号为" + id + "的保险柜！");
    }
}
