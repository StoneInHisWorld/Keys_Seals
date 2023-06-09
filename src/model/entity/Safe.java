package model.entity;

import java.util.LinkedList;
import java.util.List;

//public class Safe implements Comparable<Safe> {
public class Safe{

    protected String department = "";
    protected int id = -1;
    protected String last_return = "初始生成";
    protected String last_fetch = "初始生成";
    protected String note = "";
    public static String supportDep;

    /**
     * 比较函数
     * @param o 右操作数
     * @return id顺序比较结果
     */
//    @Override
    public int compareTo(Safe o) { return this.id <= o.id ? 1 : 0; }

    @Override
    public String toString() {
        return department + '\t' + id + '\t';
    }

    public String getDepartment() {
        return department;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLast_return(String last_return) {
        this.last_return = last_return;
    }

    public void setLast_fetch(String last_fetch) {
        this.last_fetch = last_fetch;
    }

    public void setNote(String note) {
        this.note = note;
    }


    public Object[] getMembers() {
        Object[] members = new Object[2];
        members[0] = this.department;
        members[1] = this.id;
        return members;
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
