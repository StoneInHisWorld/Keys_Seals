package view;

import java.io.*;
import java.nio.charset.StandardCharsets;

public abstract class UI {

    private static final String configPath = "configuration.txt";
    protected final String companyName;
    protected final String warning;
    protected final String supportDep;
    protected final String version;

    /**
     * 基础UI类。负责加载配置文件。
     * @throws Exception 配置文件读写异常
     */
    public UI() throws Exception {
        // 加载配置文件
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(configPath);
        } catch (FileNotFoundException e) {
            throw new Exception("无法找到配置文件！");
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream, StandardCharsets.UTF_8));
        try {
            this.companyName = bufferedReader.readLine().substring(4);
            this.warning = bufferedReader.readLine().substring(5);
            this.supportDep = bufferedReader.readLine().substring(6);
            this.version = bufferedReader.readLine().substring(4);
            bufferedReader.close();
            fileInputStream.close();
        } catch (IOException e) {
            throw new Exception("配置文件读写异常！");
        }
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getWarning() {
        return warning;
    }

    public String getSupportDep() {
        return supportDep;
    }

    public String getVersion() {
        return version;
    }

    abstract public void welcome();

}
