/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package give.core.model;

import give.base.model.BaseModel;
import static give.core.model.UserModel.USER_TYPE_COMMON;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Administrator
 */
public class CharityModel extends BaseModel {
    
    protected String name;
    protected String webSite;
    protected int category;
    protected String categoryName;
    protected String bio;
    protected String metric;
    protected Double amount;
    protected Integer number;
    protected String imageUrl;

    public CharityModel() {
        super();
    }
    
    public CharityModel(ResultSet rs) {
        super(rs);
        
        this.name       = (String)this.getValueFrom(rs, "name", "");
        this.webSite    = (String)this.getValueFrom(rs, "website", "");
        this.category   = (Integer)this.getValueFrom(rs, "category", 0);
        this.bio        = (String)this.getValueFrom(rs, "bio", "");
        this.metric     = (String)this.getValueFrom(rs, "metric", "");
        this.imageUrl   = (String)this.getValueFrom(rs, "logo", "");
        this.amount     = (Double)this.getValueFrom(rs, "amount", 0.0);
        this.number     = (Integer)this.getValueFrom(rs, "quantity", 0);

        this.categoryName = (String)this.getValueFrom(rs, "categoryName", "");
    }
    
    public String getName() { return name; }
    public void setName(String strValue) { this.name = strValue; }
    
    public String getWebSite() { return webSite; }
    public void setWebSite(String strValue) { this.webSite = strValue; }
    
    public Integer getCategory() { return category; }
    public void setCategory(Integer value) { this.category = value; }
    
    public String getBio() { return bio; }
    public void setBio(String strValue) { this.bio = strValue; }
    
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String strValue) { this.imageUrl = strValue; }
    
    public String getMetric() { return metric; }
    public void setMetric(String strValue) { this.metric = strValue; }
    
    public Double getAmount() { return amount; }
    public void setAmount(Double dblValue) { this.amount = dblValue; }
    
    public Integer getNumber() { return number; }
    public void setNumber(Integer nNumber) { this.number = nNumber; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String strValue) { this.categoryName = strValue; }

    @Override
    public ArrayList<Object> toArrayForInsert() {
        ArrayList<Object> arr = new ArrayList<>();
        
        arr.add(name);
        arr.add(imageUrl);
        arr.add(bio);
        arr.add(webSite);
        arr.add(category);        
        arr.add(metric);
        arr.add(amount);
        arr.add(number);
        
        return arr;
    }

    @Override
    public ArrayList<Object> toArrayForUpdate() {
        ArrayList<Object> arr = new ArrayList<>();
        
        arr.add(name);
        arr.add(imageUrl);
        arr.add(bio);
        arr.add(webSite);
        arr.add(category);        
        arr.add(metric);
        arr.add(amount);
        arr.add(number);
        
        arr.add(m_nID);
        
        return arr;
    }
    
}
