package model.entity;

import java.util.List;

public class DepKey extends Key{

    private String store = "";
    private String safe_id = "null";
    private int back_up = 0;
    private int emergency = 0;
    private static final String supportDep = "后勤";

    public DepKey() {    }

    public DepKey(String depKey_str) throws Exception {
        String[] strs = depKey_str.split("\t");
        int progress = 0;
        try {
            this.department = strs[progress++];
            if (department.equals(supportDep)) {
                throw new Exception("不可使用后勤钥匙字符串初始化部门钥匙！");
            }
            this.id = new Integer(strs[progress++]);
            this.store = strs[progress++];
            this.safe_id = strs[progress++];
            this.back_up = new Integer(strs[progress++]);
            this.emergency = new Integer(strs[progress++]);
            this.note = strs[progress++];
            this.last_return = strs[progress++];
            this.last_fetch = strs[progress];
        } catch (IndexOutOfBoundsException e) {  }
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

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getSafe_id() {
        return safe_id;
    }

    public void setSafe_id(String safe_id) {
        this.safe_id = safe_id;
    }

    public int getEmergency() {
        return emergency;
    }

    public void setEmergency(int emergency) {
        this.emergency = emergency;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getLast_return() {
        return last_return;
    }

    public void setLast_return(String last_return) {
        this.last_return = last_return;
    }

    public String getLast_fetch() {
        return last_fetch;
    }

    public void setLast_fetch(String last_fetch) {
        this.last_fetch = last_fetch;
    }

    @Override
    public String toString() {
        return super.toString() + store + '\t' + safe_id + '\t' +
                back_up + '\t' + emergency + '\t' +
                last_return + '\t' + last_fetch  + '\t'+ note;
    }

    public static List<String> memberToStr() {
        List<String> stringList = Key.memberToStr();
        stringList.add("商铺");
        stringList.add("保险柜序号");
        stringList.add("备用钥匙");
        stringList.add("应急钥匙");
        stringList.add("入库人");
        stringList.add("出库人");
        stringList.add("备注");
        return stringList;
    }
}
