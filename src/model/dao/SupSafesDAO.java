package model.dao;

import model.entity.SupSafe;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class SupSafesDAO {

    private final String supSafe_filePath = "./supsafes.data";

    /**
     * 查找所有supSafe
     * @return 所有supSafe的字符串格式
     * @throws Exception 文件不存在异常
     */
    public List<SupSafe> findAll() throws Exception {
        List<SupSafe> supSafes = new ArrayList<>();
        FileInputStream file = new FileInputStream(this.supSafe_filePath);
        // 读取部门钥匙
        BufferedReader br = new BufferedReader(new InputStreamReader(file, StandardCharsets.UTF_8));
        String line;
        // 将每一行转化为部门钥匙对象
        while ((line = br.readLine()) != null) {
            supSafes.add(new SupSafe(line));
        }
        br.close();
        return supSafes;
    }

    /**
     * 将所有supSafes转化为字符串，写回
     * @param safes supSafe对象列表
     * @throws IOException 文件写入异常
     */
    public void writeBack(List<SupSafe> safes) throws IOException {
//        FileWriter fileWriter = new FileWriter(new File(this.supSafe_filePath));
        FileOutputStream file = new FileOutputStream(this.supSafe_filePath);
        // 读取部门保险柜
        BufferedWriter br = new BufferedWriter(new OutputStreamWriter(file, StandardCharsets.UTF_8));
        for (SupSafe safe : safes) {
            br.write(safe.toString() + "\n");
        }
        br.close();
    }
}
