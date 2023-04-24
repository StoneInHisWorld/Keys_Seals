package model.entity;

import java.util.Comparator;

public class DepKey implements Comparable<DepKey> {
    private String department = "";
    private int id = -1;
    private String store = "";
    private String safe_id = "null";
    private boolean back_up = false;
    private int emergency = 0;
    private String note = "";
    private String last_return = "";
    private String last_fetch = "";

    public DepKey(String depKey_str) throws Exception {
        String[] strs = depKey_str.split("\t");
        int progress = 0;
        try {
            this.department = strs[progress++];
            if (department.equals("后勤")) {
                throw new Exception("不可使用后勤钥匙字符串初始化部门钥匙！");
            }
            this.id = new Integer(strs[progress++]);
            this.store = strs[progress++];
            this.safe_id = strs[progress++];
            switch (strs[progress++]) {
                case "1":this.back_up = true;break;
                case "0":this.back_up = false;break;
                default:throw new Exception("错误的部门钥匙字符串输入！");
            }
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

    public boolean isBack_up() {
        return back_up;
    }

    public void setBack_up(boolean back_up) {
        this.back_up = back_up;
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
        return department + '\t' + id + '\t' +
                store + '\t' + safe_id + '\t' +
                (back_up ? 1 : 0) + '\t' + emergency + '\t' +
                last_return + '\t' + last_fetch  + '\t'+ note;
    }


    @Override
    public int compareTo(DepKey o) {
        return this.id <= o.id ? 1 : 0;
    }
}
