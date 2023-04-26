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

    public int getId() {
        return id;
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
        stringList.add("保险柜");
        stringList.add("备用钥匙");
        stringList.add("应急钥匙");
        stringList.add("入库人");
        stringList.add("出库人");
        stringList.add("备注");
        return stringList;
    }
}
