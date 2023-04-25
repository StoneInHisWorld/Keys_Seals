package model.entity;

public class SupKey {
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

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public int getNum_key() {
        return num_key;
    }

    public void setNum_key(int num_key) {
        this.num_key = num_key;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getLast_return() {
        return last_return;
    }

    public void setLast_return(String last_return) {
        this.last_return = last_return;
    }

    public String getLast_fetch() {
        return last_fetch;
    }

    public void setLast_fetch(String last_fetch) {
        this.last_fetch = last_fetch;
    }
}
