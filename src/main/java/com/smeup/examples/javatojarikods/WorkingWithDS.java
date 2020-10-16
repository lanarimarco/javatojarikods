/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smeup.examples.javatojarikods;

import com.smeup.examples.calljariko.CallJariko;
import com.smeup.rpgparser.execution.CommandLineProgram;
import com.smeup.rpgparser.execution.Configuration;
import com.smeup.rpgparser.execution.RunnerKt;
import com.smeup.rpgparser.interpreter.DataStructValue;
import com.smeup.rpgparser.interpreter.DecimalValue;
import com.smeup.rpgparser.interpreter.FieldDefinition;
import com.smeup.rpgparser.interpreter.NumberType;
import com.smeup.rpgparser.interpreter.StringType;
import com.smeup.rpgparser.interpreter.StringValue;
import com.smeup.rpgparser.interpreter.SystemInterface;
import com.smeup.rpgparser.jvminterop.JavaSystemInterface;
import com.smeup.rpgparser.parsing.parsetreetoast.RpgType;
import com.smeup.rpgparser.rpginterop.DirRpgProgramFinder;
import com.smeup.rpgparser.rpginterop.RpgProgramFinder;
import java.io.File;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author marco.lanari
 */
public class WorkingWithDS {
   

    
    
    public static void main(String[] args) {
        DataStructValueFactory factory = DataStructValueFactory.createFactory(13);
        
        FieldDefinition name = factory.addField("Name", new StringType(5, false), 
                new StringValue("John", false), 0, 5);
        FieldDefinition surname =factory.addField("Surname", new StringType(5, false), 
                new StringValue("Smith", false), 5, 10);
        FieldDefinition income = factory.addField("Nbr", new NumberType(3, 0, RpgType.ZONED), 
                new DecimalValue(BigDecimal.valueOf(2.00)), 10, 13);
        //al PGM RPG gli passi 
        DataStructValue dataStructIn = factory.getDataStructValue();
        
         //creating a system interface
        SystemInterface systemInterface = new JavaSystemInterface();
        String programName = "DS_CALLED.rpgle";
        
        //assume that rpgle programs are in resource but this dir could be anywhere
        File rpgDir = new File(CallJariko.class.getResource("/rpg/" + programName).getFile()).getParentFile();
        List<RpgProgramFinder> programFinders = Arrays.asList(new DirRpgProgramFinder(rpgDir));
        
        CommandLineProgram jariko = RunnerKt.getProgram(programName, systemInterface, programFinders);

        
        jariko.singleCall(Arrays.asList(dataStructIn.getValue()), new Configuration());
    }
}
