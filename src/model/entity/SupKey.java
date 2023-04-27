package model.entity;

import java.util.List;

public class SupKey extends Key{
    private String department;
    private int id;
    private String loc;
    private int num_key;
    private String last_return;
    private String last_fetch;
    private String note;

    public String getDepartment() {
        return department;
    }

    public static List<String> memberToStr() {
        List<String> stringList = Key.memberToStr();
        stringList.add("地点");
        stringList.add("钥匙数量");
        stringList.add("入库人");
        stringList.add("出库人");
        stringList.add("备注");
        return stringList;
    }
}
