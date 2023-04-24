package controller;

import model.dao.DepKeysDAO;
import model.entity.DB;
import model.service.DBService;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class KeyUIController {

    private DBService dbService;

    public KeyUIController() {
        this.dbService = new DBService();
    }

    public Set<String> initKeyUI() throws Exception {
        dbService.initDB();
        return this.dbService.collectDepart();
    }

    public List<String> displayDepKeys(String dep) {
        return dbService.DepKeyToStr(dep);
    }

    public void exitKeyUI() throws IOException {
        this.dbService.WriteBackDB();
    }
}
