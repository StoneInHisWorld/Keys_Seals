package model.entity;

import java.util.ArrayList;
import java.util.List;

public class DB {

    private List<DepSafe> depSafes;
    private List<SupSafe> supSafes;

    public DB() {
        this.depSafes = new ArrayList<>();
        this.supSafes = new ArrayList<>();
    }

    public List<DepSafe> getDepSafes() {
        return depSafes;
    }

    public void setDepSafes(List<DepSafe> depSafes) {
        this.depSafes = depSafes;
    }

    public List<SupSafe> getSupSafes() {
        return supSafes;
    }

    public void setSupSafes(List<SupSafe> supSafes) {
        this.supSafes = supSafes;
    }
}
