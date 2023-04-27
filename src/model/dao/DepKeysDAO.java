package model.dao;

import model.entity.DepKey;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class DepKeysDAO {

    private final String depKey_filePath = "./keys.data";

    /**
     * 查找所有depKey
     * @return 所有depKey的字符串格式
     * @throws Exception 文件不存在异常
     */
    public List<DepKey> findAll() throws Exception {
        List<DepKey> depKeys = new ArrayList<>();
        FileInputStream file = new FileInputStream(this.depKey_filePath);
        // 读取部门钥匙
        BufferedReader br = new BufferedReader(new InputStreamReader(file, StandardCharsets.UTF_8));
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
     * @param keys depKeys对象列表
     * @throws IOException 文件写入异常
     */
    public void writeBack(List<DepKey> keys) throws IOException {
        FileWriter fileWriter = new FileWriter(new File(this.depKey_filePath));
        for (DepKey key : keys) {
            fileWriter.write(key.toString() + "\n");
        }
        fileWriter.close();
    }
}
