package model.service;

import model.dao.DepSafesDAO;
import model.dao.SupSafesDAO;
import model.entity.DB;
import model.entity.DepSafe;
import model.entity.Safe;
import model.entity.SupSafe;

import java.io.IOException;
import java.util.*;

public class DBService {

    private final DB db;
    private final DepSafesDAO depSafesDAO;
    private final SupSafesDAO supSafesDAO;

    public DBService() {
        this.db = new DB();
        this.depSafesDAO = new DepSafesDAO();
        this.supSafesDAO = new SupSafesDAO();
    }

    /**
     * 加载数据库
     * @throws Exception 数据文件找不到异常
     */
    public void loadDB() throws Exception {
        db.setDepSafes(this.depSafesDAO.findAll());
        db.setSupSafes(this.supSafesDAO.findAll());
    }

    /**
     * 收集部门
     * @return 数据库中所有保险柜所属部门
     */
    public Set<String> collectDepart() {
        Set<String> depSet = new LinkedHashSet<>();
        for (DepSafe key : db.getDepSafes()) {
            depSet.add(key.getDepartment());
        }
        for (SupSafe key : db.getSupSafes()) {
            depSet.add(key.getDepartment());
        }
        // 排序
        List<String> depList = new LinkedList<>(depSet);
        Collections.sort(depList);
        depSet.clear();
        depSet.addAll(depList);
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
    public void writeBackDB() throws Exception {
        List<DepSafe> depSafes = this.db.getDepSafes();
        List<SupSafe> supSafes = this.db.getSupSafes();
        // 保险柜需按序号顺序排列后写入
        Collections.sort(depSafes);
        Collections.sort(supSafes);
        try {
            this.depSafesDAO.writeBack(depSafes);
            this.supSafesDAO.writeBack(supSafes);
        } catch (IOException e) {
            throw new Exception("文件写入发生异常！");
        }
    }

    /**
     * 添加部门钥匙的保险柜
     * @param dep 保险柜所属部门
     * @param keyStr 添加保险桂的详细信息
     * @throws Exception 输入字符串不符格式异常
     */
    public void addDepSafe(String dep, String keyStr) throws Exception {
        List<DepSafe> depSafes = this.db.getDepSafes();
        int lastest_Id = this.getDepSafeMembers(dep).size();
//        int size = depSafes.size();
//        if (size > 0) {
//            lastest_Id = depSafes.get(size - 1).getId();
//        }
//        else lastest_Id = 0;
        // int lastestId = depSafes.get(depSafes.size() - 1).getId();
        // 检查输入的字符串是否合法
        keyStr = dep + '\t' + (lastest_Id + 1) + '\t' + keyStr;
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
     * 根据序号查找全部门保险柜
     * @param id 保险柜ID
     * @return 找到则返回对应的保险柜，否则返回null
     */
    public DepSafe findDepSafe(int id) {
        for (DepSafe depSafe : this.db.getDepSafes()) {
            if (depSafe.getId() == id) return depSafe;
        }
        return null;
    }

    /**
     * 根据序号和部门查找保险柜
     * @param dep 保险柜所属部门
     * @param id 保险柜ID
     * @return 找到则返回对应的保险柜，否则返回null
     */
    public String findDepSafe(String dep, int id) throws Exception {
        for (DepSafe depSafe : this.db.getDepSafes()) {
            if (depSafe.getId() == id && depSafe.getDepartment().equals(dep))
                return depSafe.toString();
        }
        throw new Exception(dep + "下无序号为" + id + "的保险柜！");
    }

    /**
     * 删除部门和序号均符合用户输入的保险柜
     * @param dep 保险柜所属部门
     * @param id 保险柜id
     * @return 删除是否成功
     */
    public void delDepSafe(String dep, int id) throws Exception {
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
            return;
        }
        throw new Exception(dep + "并不存在序号为" + id + "保险柜！");
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
                            fetch_person += "（备）";
                        }
                    }break;
                    case 'j': {
                        int emer_num = depSafe.getEmergency() - 1;
                        if (emer_num < 0) {
                            throw new Exception("紧急钥匙数量不足！");
                        }
                        else {
                            depSafe.setEmergency(emer_num);
                            fetch_person += "（急）";
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
     * 取出后勤部的钥匙
     * @param id 保险柜序号
     * @param fetch_person 出库人
     * @param note 备注
     * @throws Exception 找不到保险柜异常
     */
    public void fetchSupKey(int id, String fetch_person, String note) throws Exception {
        for (SupSafe supSafe : this.db.getSupSafes()) {
            // 找到对应部门下的保险柜
            if (supSafe.getId() == id) {
                int num_key = supSafe.getNum_key() - 1;
                if (num_key < 0) {
                    throw new Exception("备用钥匙数量不足！");
                }
                else {
                    supSafe.setNum_key(num_key);
                }
                supSafe.setLast_fetch(fetch_person);
                if (!note.equals("")) {
                    supSafe.setNote(note);
                }
                return;
            }
        }
        throw new Exception(Safe.supportDep + "没有序号为" + id + "的保险柜！");
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
                        ret_person += "（备）";
                    }break;
                    case 'j': {
                        depSafe.setEmergency(depSafe.getEmergency() + 1);
                        ret_person += "（急）";
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

    /**
     * 添加后勤部保险柜
     * @param keyStr 后勤部保险柜信息字符串
     * @throws Exception 字符串解析异常
     */
    public void addSupSafe(String keyStr) throws Exception {
        List<SupSafe> supSafes = this.db.getSupSafes();
        int lastest_Id = supSafes.size();
//        int size = supSafes.size();
//        if (size > 0) {
//            lastest_Id = supSafes.get(size - 1).getId();
//        }
//        else lastest_Id = 0;
        // 检查输入的字符串是否合法
        keyStr = Safe.supportDep + "\t" + (lastest_Id + 1) + "\t" +
                keyStr;
        SupSafe supSafe = new SupSafe(keyStr);
        supSafes.add(supSafe);
    }

    /**
     * 查找后勤部对应序号的保险柜
     * @param id 保险柜序号
     * @return 保险柜信息字符串
     * @throws Exception 查找保险柜异常字符串
     */
    public String findSupSafe(int id) throws Exception {
        String supportDep = this.db.getSupSafes().get(0).getDepartment();
        for (SupSafe supSafe : this.db.getSupSafes()) {
            if (supSafe.getId() == id) {
                return supSafe.toString();
            }
        }
        throw new Exception(supportDep + "无序号为" + id + "的保险柜！");
    }

    /**
     * 删除后勤部保险柜
     * @param id 保险柜序号
     * @throws Exception 找不到对应序号保险柜或者数据库未进行改变异常
     */
    public void delSupSafe(int id) throws Exception {
        // 删除id符合的保险柜
        if (this.db.getSupSafes().removeIf(
                supSafe -> supSafe.getId() == id)) {
            // 若删除成功，则刷新所有后勤部保险柜的id
            List<SupSafe> supSafes = this.db.getSupSafes();
            int new_id = 1;
            for (SupSafe supSafe : supSafes) {
                supSafe.setId(new_id++);
            }
            return;
        }
        throw new Exception(Safe.supportDep + "并不存在序号为" + id + "保险柜！");
    }

    /**
     * 归还后勤部钥匙
     * @param id 保险柜序号
     * @param ret_person 入库人
     * @param note 备注
     * @throws Exception 找不到保险柜异常
     */
    public void retSupKey(int id, String ret_person, String note) throws Exception {
        for (SupSafe supSafe : this.db.getSupSafes()) {
            // 找到对应部门下的保险柜
            if (supSafe.getId() == id) {
                supSafe.setNum_key(supSafe.getNum_key() + 1);
                supSafe.setLast_return(ret_person);
                if (!note.equals("")) {
                    supSafe.setNote(note);
                }
                return;
            }
        }
        throw new Exception(Safe.supportDep + "没有序号为" + id + "的保险柜！");
    }

    /**
     * 获取部门保险柜的成员对象
     * @param dep 部门保险柜所属部门
     * @return 部门保险柜成员对象列表
     * @throws Exception 找不到部门保险柜异常
     */
    public List<Object[]> getDepSafeMembers(String dep) throws Exception {
        List<Object[]> ret = new LinkedList<>();
        for (DepSafe depSafe : this.depSafesDAO.findAll()) {
            if (depSafe.getDepartment().equals(dep)) {
                ret.add(depSafe.getMembers());
            }
        }
        return ret;
    }

    /**
     * 获取后勤部保险柜成员
     * @return 保险柜成员列表
     * @throws Exception 后勤部保险柜数据文件查找异常
     */
    public List<Object[]> getSupSafeMembers() throws Exception {
        List<Object[]> ret = new LinkedList<>();
        for (SupSafe supSafe : this.supSafesDAO.findAll()) {
            ret.add(supSafe.getMembers());
        }
        return ret;
    }
}
