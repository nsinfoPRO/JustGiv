/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package give.core.services;

import give.base.model.BaseModel;
import give.base.services.BaseService;
import give.core.model.UserModel;
import java.util.ArrayList;

/**
 *
 * @author Administrator
 */
public class UserService extends BaseService {
    
    public UserService() {
        super();
    }
    
    /**
     * 
     * @param strParamEmail
     * @param strParamPwd
     * @return 
     */
    public UserModel authenticateUser(String strParamEmail, String strParamPwd) {
        String sql = "SELECT * FROM user WHERE email=? and password=md5(?)";
        
        this.m_dbHelper.openDB();
        
        ArrayList<BaseModel> users = this.m_dbHelper.executeSQLAndReturnArray(sql, new Object[]{strParamEmail, strParamPwd}, UserModel.class.getSimpleName());
        
        if(users.isEmpty()) {
            return null;
        }
        
        return (UserModel)users.get(0);
    }
    
    /**
     * 
     * @param strParamEmail
     * @return 
     */
    public UserModel findUserByEmail(String strParamEmail) {
        String sql = "SELECT * FROM user WHERE email=?";
        
        ArrayList<BaseModel> users = this.m_dbHelper.executeSQLAndReturnArray(sql, new Object[]{strParamEmail}, UserModel.class.getSimpleName());
        
        if(users.isEmpty()) {
            return null;
        }
        
        return (UserModel)users.get(0);
    }
    
    
    /**
     * 
     * @param newUser
     * @return 
     */
    public boolean registerUser(UserModel newUser) {
        if(newUser == null) return false;
        
        String sql = "INSERT INTO user(fullname, email, password, type) values(?,?,md5(?),?)";
        
        long insertedID = this.m_dbHelper.insertRowByPreparedStatement(sql, newUser.toArrayForInsert());
        
        return insertedID > -1;
    }
    
    /**
     * 
     * @param newUser
     * @return 
     */
    public boolean updateUser(UserModel newUser) {
        if(newUser == null) return true;
        
        String sql = "UPDATE user SET email=?, password=md5(?) WHERE id=?";
        
        return this.m_dbHelper.updateRowByPreparedStatement(sql, new Object[]{newUser.getUserEmail(), newUser.getPassword(), newUser.getID()});        
    }
}
