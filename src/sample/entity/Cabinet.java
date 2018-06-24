package sample.entity;

public class Cabinet {
    private String cabinet_id;
    private String cabinet_num;
    private String cabinet_type;
    private String cabinet_level;

    public String getCabinet_id() {
        return cabinet_id;
    }

    public void setCabinet_id(String cabinet_id) {
        this.cabinet_id = cabinet_id;
    }

    public String getCabinet_num() {
        return cabinet_num;
    }

    public void setCabinet_num(String cabinet_num) {
        this.cabinet_num = cabinet_num;
    }

    public String getCabinet_type() {
        return cabinet_type;
    }

    public void setCabinet_type(String cabinet_type) {
        this.cabinet_type = cabinet_type;
    }

    public String getCabinet_level() {
        return cabinet_level;
    }

    public void setCabinet_level(String cabinet_level) {
        this.cabinet_level = cabinet_level;
    }

    public Cabinet(String cabinet_id, String cabinet_num, String cabinet_type, String cabinet_level) {
        this.cabinet_id = cabinet_id;
        this.cabinet_num = cabinet_num;
        this.cabinet_type = cabinet_type;
        this.cabinet_level = cabinet_level;
    }
}
