package zjc.devicemanage.model;
public class Device {
    private String DeviceID;
    private String DeviceClassId;
    private String DeviceName;
    private String DevicePrice;
    public String getDeviceID() {
        return DeviceID;
    }
    public void setDeviceID(String deviceID) {
        DeviceID = deviceID;
    }
    public String getDeviceClassId() {
        return DeviceClassId;
    }
    public void setDeviceClassId(String deviceClassId) {
        DeviceClassId = deviceClassId;
    }
    public String getDeviceName() {
        return DeviceName;
    }
    public void setDeviceName(String deviceName) {
        DeviceName = deviceName;
    }
    public String getDevicePrice() {
        return DevicePrice;
    }
    public void setDevicePrice(String devicePrice) {
        DevicePrice = devicePrice;
    }
}
