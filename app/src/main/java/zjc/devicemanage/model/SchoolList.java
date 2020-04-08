package zjc.devicemanage.model;
import java.util.List;

public class SchoolList {
    // 注意：变量名schoolList必须和JSON字符串中的根键名一致
    private List<School> schoolList;
    public List<School> getSchoolList() {
        return schoolList;
    }
    public void setSchoolList(List<School> schoolList) {
        this.schoolList = schoolList;
    }
}
