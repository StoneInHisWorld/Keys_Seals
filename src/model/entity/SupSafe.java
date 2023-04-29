package model.entity;

import java.util.List;

public class SupSafe extends Safe {

    private String loc;
    private int num_key;

    /**
     * 使用字符串初始化一个后勤部保险柜
     * @param supSafe_str 后勤部保险柜序号以及其后所有信息
     * @throws Exception 字符串解析异常
     */
    public SupSafe(String supSafe_str) throws Exception {
        String[] strs = supSafe_str.split("\t");
        int progress = 0;
        try {
            this.department = supportDep;
            this.id = new Integer(strs[progress++]);
            if (id < 0) {
                throw new Exception("序号为非负整数！");
            }
            this.loc = strs[progress++];
            this.num_key = new Integer(strs[progress++]);
            if (num_key < 0) {
                throw new Exception("钥匙数量为非负整数！");
            }
            this.last_return = strs[progress++];
            this.last_fetch = strs[progress++];
            this.note = strs[progress];
        }
        catch (IndexOutOfBoundsException e) {
            int index = new Integer(e.getMessage());
            if (index <= 1)
                throw new Exception("位置为必填项！");
        }
        catch (NumberFormatException e) {
            throw new Exception("保险柜钥匙数量值为非负整数！");
        }
    }

    public String getDepartment() {
        return department;
    }

    @Override
    public String toString() {
        return super.toString() + loc + '\t' + num_key + '\t' +
                last_return + '\t' + last_fetch  + '\t'+ note;
    }

    public static List<String> memberToStr() {
        List<String> stringList = Safe.memberToStr();
        stringList.add("地点");
        stringList.add("钥匙数量");
        stringList.add("入库人");
        stringList.add("出库人");
        stringList.add("备注");
        return stringList;
    }
}
