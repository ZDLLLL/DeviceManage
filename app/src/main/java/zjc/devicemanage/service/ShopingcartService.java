package zjc.devicemanage.service;

public interface ShopingcartService {
    // 获取所有购物车
    public void findAllShopingcart ();
    // 获取所有指定用户的购物车
    public void findAllShopingcartByUserId (String userId);
    // 添加指定用户的购物车
    public void addShopingcart(String addDeviceID,String addBuyNum, String addUserID);
}
