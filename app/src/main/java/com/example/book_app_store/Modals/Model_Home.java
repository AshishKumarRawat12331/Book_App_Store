package com.example.book_app_store.Modals;

public class Model_Home {

    String id, User_id, Product_Title, Product_Desc, Product_Image, Product_Price, Product_Location;

    public Model_Home() {
    }

    public Model_Home(String id, String user_id, String product_Title, String product_Desc, String product_Image, String product_Price, String product_Location) {
        this.id = id;
        User_id = user_id;
        Product_Title = product_Title;
        Product_Desc = product_Desc;
        Product_Image = product_Image;
        Product_Price = product_Price;
        Product_Location = product_Location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return User_id;
    }

    public void setUser_id(String user_id) {
        User_id = user_id;
    }

    public String getProduct_Title() {
        return Product_Title;
    }

    public void setProduct_Title(String product_Title) {
        Product_Title = product_Title;
    }

    public String getProduct_Desc() {
        return Product_Desc;
    }

    public void setProduct_Desc(String product_Desc) {
        Product_Desc = product_Desc;
    }

    public String getProduct_Image() {
        return Product_Image;
    }

    public void setProduct_Image(String product_Image) {
        Product_Image = product_Image;
    }

    public String getProduct_Price() {
        return Product_Price;
    }

    public void setProduct_Price(String product_Price) {
        Product_Price = product_Price;
    }

    public String getProduct_Location() {
        return Product_Location;
    }

    public void setProduct_Location(String product_Location) {
        Product_Location = product_Location;
    }
}
