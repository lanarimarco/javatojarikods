/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smeup.examples.javatojarikods;

import com.smeup.rpgparser.interpreter.DataStructValue;
import com.smeup.rpgparser.interpreter.DecimalValue;
import com.smeup.rpgparser.interpreter.FieldDefinition;
import com.smeup.rpgparser.interpreter.NumberType;
import com.smeup.rpgparser.interpreter.StringType;
import com.smeup.rpgparser.interpreter.StringValue;
import com.smeup.rpgparser.interpreter.Value;
import com.smeup.rpgparser.parsing.parsetreetoast.RpgType;
import java.math.BigDecimal;

/**
 *
 * @author marco.lanari
 */
public class WorkingWithDS {
   

    
    
    public static void main(String[] args) {
        DataStructValueFactory factory = DataStructValueFactory.createFactory(50);
        FieldDefinition name = factory.addField("name", new StringType(20, false), 
                new StringValue("John", false), 0, 20);
        FieldDefinition surname =factory.addField("surname", new StringType(20, false), 
                new StringValue("Smith", false), 20, 40);
        FieldDefinition income = factory.addField("income", new NumberType(5, 2, RpgType.PACKED), 
                new DecimalValue(BigDecimal.valueOf(60000.00)), 40, 50);
        //al PGM RPG gli passi 
        DataStructValue dataStructIn = factory.getDataStructValue();
        
        //questa è la DS che ti ritorna jariko
        //sarà strutturata come dataStructIn
        //per ipotesi la imposto come la dataStructIn
        DataStructValue dataStructOut = dataStructIn;
        
        //per leggerti i valori
        Value nameValue = dataStructOut.get(name);
        System.out.println(nameValue);
        Value surnameValue = dataStructOut.get(surname);
        System.out.println(surnameValue);
        Value incomeValue = dataStructOut.get(income);
        System.out.println(incomeValue);
    }
}
