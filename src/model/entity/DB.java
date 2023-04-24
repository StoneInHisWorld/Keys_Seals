package model.entity;

import java.util.ArrayList;
import java.util.List;

public class DB {

    private List<DepKey> depKeys;
    private List<SupKey> supKeys;

    public DB() {
        this.depKeys = new ArrayList<>();
        this.supKeys = new ArrayList<>();
    }

    public List<DepKey> getDepKeys() {
        return depKeys;
    }

    public void setDepKeys(List<DepKey> depKeys) {
        this.depKeys = depKeys;
    }

    public List<SupKey> getSupKeys() {
        return supKeys;
    }

    public void setSupKeys(List<SupKey> supKeys) {
        this.supKeys = supKeys;
    }
}
