/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smeup.examples.javatojarikods;

import com.smeup.rpgparser.interpreter.DataStructValue;
import com.smeup.rpgparser.interpreter.FieldDefinition;
import com.smeup.rpgparser.interpreter.Type;
import com.smeup.rpgparser.interpreter.Value;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author marco.lanari
 */
public class DataStructValueFactory {
    
    private final DataStructValue dataStructValue;
    private final Map<String, FieldDefinition> fieldDefinitions = new HashMap<>();

    private DataStructValueFactory(DataStructValue dataStructValue) {
        this.dataStructValue = dataStructValue;
    }
    
    private FieldDefinition createFieldDefinition(String name, Type value, int startOffSet, int endOffSet) {
        return new FieldDefinition(name, value, startOffSet, endOffSet, null, null, null, null, false, null, null);
    }
    
    public static DataStructValueFactory createFactory(int len) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < len; i++) {
            sb.append(" ");
        }
        return new DataStructValueFactory(new DataStructValue(sb.toString(), len));
    }
    
    public FieldDefinition addField(String name, Type type, Value value, int startOffSet, int endOffSet) {
        FieldDefinition fieldDefinition = createFieldDefinition(name, type, startOffSet, endOffSet);
        if (fieldDefinitions.get(name) == null) {
            fieldDefinitions.put(name, fieldDefinition);
            dataStructValue.set(fieldDefinition, value);
            return fieldDefinition;
        }
        else {
            throw new IllegalArgumentException("Field: " + name + " already added");
        }
    }
    
    public Value getValue(String name) {
        if (fieldDefinitions.get(name) != null) {
            return dataStructValue.get(fieldDefinitions.get(name));
        }
        else {
            return null;
        }
    }
    
    
    public DataStructValue getDataStructValue() {
        return dataStructValue;
    }
}
