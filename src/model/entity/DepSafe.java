package model.entity;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class DepSafe extends Safe implements Comparable<DepSafe>{

    private String store = "";
    private String safe_id = "null";
    private int back_up = 0;
    private int emergency = 0;

    /**
     * 通过信息字符串生成一个部门保险柜。
     * @param depKey_str 信息字符串。格式为“部门名称\t序号\t店铺名称\t保险柜名称\t备用钥匙数量\t应急钥匙数量\t入库人\t出库人\t备注\t”
     * @throws Exception 信息字符串解析异常
     */
    public DepSafe(String depKey_str) throws Exception {
        String[] strs = depKey_str.split("\t");
        int progress = 0;
        try {
            this.department = strs[progress++];
            if (department.equals(supportDep)) {
                throw new Exception("不可使用后勤钥匙字符串初始化部门钥匙！");
            }
            this.id = new Integer(strs[progress++]);
            if (id < 0) {
                throw new Exception("序号为非负整数！");
            }
            this.store = strs[progress++];
            this.safe_id = strs[progress++];
            this.back_up = new Integer(strs[progress++]);
            if (back_up < 0) {
                throw new Exception("备用钥匙值为非负整数！");
            }
            this.emergency = new Integer(strs[progress++]);
            if (emergency < 0) {
                throw new Exception("备用钥匙值为非负整数！");
            }
            this.last_return = strs[progress++];
            this.last_fetch = strs[progress++];
            this.note = strs[progress];
        }
        catch (IndexOutOfBoundsException e) {
            int index = new Integer(e.getMessage());
            if (index <= 3)
                throw new Exception("商铺名称、保险柜名称为必填项！");
        }
        catch (NumberFormatException e) {
            throw new Exception("保险柜备用钥匙、紧急钥匙值为非负整数！");
        }
    }

    public int getBack_up() {
        return back_up;
    }

    public void setBack_up(int back_up) {
        this.back_up = back_up;
    }

    public int getEmergency() {
        return emergency;
    }

    public void setEmergency(int emergency) {
        this.emergency = emergency;
    }

    @Override
    public String toString() {
        return super.toString() + store + '\t' + safe_id + '\t' +
                back_up + '\t' + emergency + '\t' + last_return + '\t' +
                last_fetch  + '\t'+ note;
    }

    public Object[] getMembers() {
        List<Object> objectList = new LinkedList<>(Arrays.asList(super.getMembers()));
        objectList.add(this.store);
        objectList.add(this.safe_id);
        objectList.add(this.back_up);
        objectList.add(this.emergency);
        objectList.add(this.last_return);
        objectList.add(this.last_fetch);
        objectList.add(this.note);
        return objectList.toArray();
    }

    public static List<String> memberToStr() {
        List<String> stringList = Safe.memberToStr();
        stringList.add("商铺名称");
        stringList.add("保险柜");
        stringList.add("备用钥匙");
        stringList.add("应急钥匙");
        stringList.add("入库人");
        stringList.add("出库人");
        stringList.add("备注");
        return stringList;
    }

    @Override
    public int compareTo(DepSafe o) {
        if (this.department.equals(o.department))
            if (this.store.equals(o.store))
                if (this.safe_id.equals(o.safe_id))
                    return super.compareTo(o);
                else return this.safe_id.compareTo(o.safe_id);
            else return this.store.compareTo(o.store);
        else return this.department.compareTo(o.department);
    }
}
