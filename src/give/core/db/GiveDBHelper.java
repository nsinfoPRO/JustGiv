/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package give.core.db;

import give.base.db.DBConfig;
import give.base.db.DBHelper;

/**
 *
 * @author Administrator
 */
public class GiveDBHelper extends DBHelper {
    
    public GiveDBHelper(DBConfig dbInf) {
        super(dbInf);
        
        this.openDB();
    }
}
