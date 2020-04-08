package zjc.devicemanage.model;

public class School {
    // 注意：变量名name、province和city必须与JSON字符串中的字段名一致
    private String name ;
    private String province;
    private String city;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getProvince() {
        return province;
    }
    public void setProvince(String province) {
        this.province = province;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
}
