package zjc.devicemanage.model;
import zjc.devicemanage.util.MyHttpUtil;

public class Information {
    private String InformationID;
    private String InformationContent;
    private String InformationImage;
    private String InformationCreateTime;
    public String getInformationID() {
        return InformationID;
    }
    public void setInformationID(String informationID) {
        InformationID = informationID;
    }
    public String getInformationContent() {
        int start = 3;
        int end = InformationContent.indexOf("</x>");
        String result = InformationContent.substring(start, end);
        return result;
    }
    public void setInformationContent(String informationContent) {
        InformationContent = informationContent;
    }
    public String getInformationImage() {
        return MyHttpUtil.host + InformationImage;
    }
    public void setInformationImage(String informationImage) {
        InformationImage = informationImage;
    }
    public String getInformationCreateTime() {
        return InformationCreateTime;
    }
    public void setInformationCreateTime(String informationCreateTime) {
        InformationCreateTime = informationCreateTime;
    }
}
