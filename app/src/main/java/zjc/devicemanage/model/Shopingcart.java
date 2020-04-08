package zjc.devicemanage.model;

public class Shopingcart {
    private String ShopingcartID;
    private Device Device;
    private String BuyNum;
    private String UserID;

    public String getShopingcartID() {
        return ShopingcartID;
    }

    public void setShopingcartID(String shopingcartID) {
        ShopingcartID = shopingcartID;
    }

    public zjc.devicemanage.model.Device getDevice() {
        return Device;
    }

    public void setDevice(zjc.devicemanage.model.Device device) {
        Device = device;
    }

    public String getBuyNum() {
        return BuyNum;
    }

    public void setBuyNum(String buyNum) {
        BuyNum = buyNum;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }
}
