package zjc.devicemanage.service;

public interface DeviceService {
    // 获取所有设备
    public void findAllDevice();
    // 获得指定设备分类编号的所有设备对象
    public void findDeviceByDeviceClassId(int deviceClassId);
}
