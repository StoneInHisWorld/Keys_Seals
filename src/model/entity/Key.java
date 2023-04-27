package model.entity;

import java.util.LinkedList;
import java.util.List;

public class Key implements Comparable<Key> {

    protected String department = "";
    protected int id = -1;
    protected String last_return = "";
    protected String last_fetch = "";
    protected String note = "";

    /**
     * 比较函数
     * @param o 右操作数
     * @return id顺序比较结果
     */
    @Override
    public int compareTo(Key o) {
        return this.id <= o.id ? 1 : 0;
    }

    @Override
    public String toString() {
        return department + '\t' + id + '\t';
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * 钥匙的成员字符串前两个输出必为部门和序号
     * @return 钥匙输出字符串
     */
    public static List<String> memberToStr() {
        List<String> stringList = new LinkedList<>();
        stringList.add("部门名称");
        stringList.add("序号");
        return stringList;
    }
}
