/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package give.core.model;

import give.base.model.BaseModel;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Administrator
 */
public class UserModel extends BaseModel {
    
    public static final int USER_TYPE_ADMIN  = 1;
    public static final int USER_TYPE_COMMON = 2;
    
    private String m_strUserName;
    private static String m_strUserEmail;
    private int m_nUserType;
    private String m_strPassword;
    
    private String m_strActCode;
    
    public UserModel() {
        super();
        this.m_nUserType = USER_TYPE_COMMON;
    }
    
    public UserModel(ResultSet rs) {
        super(rs);
        
        this.m_strUserName = (String)this.getValueFrom(rs, "fullname", "");
        this.m_strUserEmail= (String)this.getValueFrom(rs, "email", "");
        this.m_strPassword = (String)this.getValueFrom(rs, "password", "");
        this.m_nUserType   = (Integer)this.getValueFrom(rs, "type", USER_TYPE_COMMON);
    }
    
    public UserModel(String strParamName, String strParamEmail,  String strParamPwd) {
        this();
        
        this.m_strUserName = strParamName;
        this.m_strUserEmail= strParamEmail;
        this.m_strPassword = strParamPwd;
    }
    
    public UserModel(String strParamName, String strParamEmail,  String strParamPwd, int nParamType) {
        this();
        
        this.m_strUserName = strParamName;
        this.m_strUserEmail= strParamEmail;
        this.m_strPassword = strParamPwd;
        this.m_nUserType   = nParamType;
    }
    
    public String getUserName() { return this.m_strUserName; }
    public void setUserName(String strParamVal) { this.m_strUserName = strParamVal; }
    
    public static String getUserEmail() { return UserModel.m_strUserEmail; }
    public void setUserEmail(String strParamVal) { this.m_strUserEmail = strParamVal; }
    
    public String getPassword() { return this.m_strPassword; }
    public void setPassword(String strParamVal) { this.m_strPassword = strParamVal; }
    
    public int getUserType() { return this.m_nUserType; }
    public void setUserType(int nParamVal) { this.m_nUserType = nParamVal; }

    @Override
    public ArrayList<Object> toArrayForInsert() {
        ArrayList<Object> arr = new ArrayList<>();
        
        arr.add(this.m_strUserName);
        arr.add(this.m_strUserEmail);
        arr.add(this.m_strPassword);
        arr.add(this.m_nUserType);
        
        return arr;
    }

    @Override
    public ArrayList<Object> toArrayForUpdate() {
        ArrayList<Object> arr = new ArrayList<>();
        
        arr.add(this.m_strUserName);
        arr.add(this.m_strUserEmail);
        arr.add(this.m_strPassword);
        arr.add(this.m_nUserType);
        
        arr.add(this.m_nID);
        
        return arr;
    }
}

