package model.entity;

import java.util.List;

public class SupSafe extends Safe {

    private String loc;
    private int num_key;

    public String getDepartment() {
        return department;
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
