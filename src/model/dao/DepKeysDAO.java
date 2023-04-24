package model.dao;

import model.entity.DepKey;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DepKeysDAO {

    private final String depKey_filePath = "./keys.data";
//    private final String supKey_filePath = "";
//    private final String seal_filePath = "";

    /**
     * 查找所有depKey
     * @return 所有depKey的字符串格式
     * @throws Exception 文件不存在异常
     */
    public List<DepKey> findAll() throws Exception {
        List<DepKey> depKeys = new ArrayList<>();
        File file = new File(this.depKey_filePath);
        if (!file.exists()) {
            throw new Exception("文件不存在！");
        }
        // 读取部门钥匙
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        // 将每一行转化为部门钥匙对象
        while ((line = br.readLine()) != null) {
            depKeys.add(new DepKey(line));
        }
        br.close();
        return depKeys;
    }

    /**
     * 将所有depKeys转化为字符串，写回
     * @param keys 所有depKey的字符串格式
     * @throws IOException 文件写入异常
     */
    public void writeBack(List<String> keys) throws IOException {
        FileWriter fileWriter = new FileWriter(new File(this.depKey_filePath));
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        for (String key : keys) {
//            bufferedWriter.write(key + "\n");
            fileWriter.write(key + "\n");
        }
        fileWriter.close();
        bufferedWriter.close();
    }
}