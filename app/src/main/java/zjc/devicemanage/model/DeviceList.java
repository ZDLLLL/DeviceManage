package zjc.devicemanage.model;
import java.util.ArrayList;
import java.util.List;

public class DeviceList {
    private List<Device> result = new ArrayList<>();
    public List<Device> getResult() {
        return result;
    }
    public void setResult(List<Device> result) {
        this.result = result;
    }
}
