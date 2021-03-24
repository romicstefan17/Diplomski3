/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.Serializable;
import java.sql.PreparedStatement;


public interface GenericEntity extends Serializable {
    String getTableName();
    String getColumnNamesForInsert();
    void getInsertValues(PreparedStatement statement) throws Exception;
    int getColumnCount();
    void setId(Long id);
    String getColumnNamesForUpdate();
    String getConditionForUpdate();
    String getConditionForDelete();
    String getColumnNamesForSelect();
    String getTableForSelect();
    String getConditionForSelect();
    String getConditionForSelectSpecific();
}
