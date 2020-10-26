/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smeup.examples.calljariko;

import com.smeup.rpgparser.execution.CommandLineProgram;
import com.smeup.rpgparser.execution.Configuration;
import com.smeup.rpgparser.execution.RunnerKt;
import com.smeup.rpgparser.interpreter.DBFile;
import com.smeup.rpgparser.interpreter.DBInterface;
import com.smeup.rpgparser.interpreter.FileMetadata;
import com.smeup.rpgparser.interpreter.SystemInterface;
import com.smeup.rpgparser.jvminterop.JavaSystemInterface;
import com.smeup.rpgparser.logging.LoggingKt;
import com.smeup.rpgparser.rpginterop.DirRpgProgramFinder;
import com.smeup.rpgparser.rpginterop.RpgProgramFinder;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author marco.lanari
 */
public class CallJarikoWithLogging {
    
    private static class MyDBInterface implements DBInterface {

        @Override
        public FileMetadata metadataOf(String string) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public DBFile open(String string) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
    }
    
    
    public static void main(String[] args) {
        
        Properties loggingConfig =  LoggingKt.consoleLoggingConfiguration(LoggingKt.PERFORMANCE_LOGGER, LoggingKt.RESOLUTION_LOGGER);
        //creating a system interface
        SystemInterface systemInterface = new JavaSystemInterface(System.out, null, new MyDBInterface(), loggingConfig);

       
        String programName = "ACTGRP.rpgle";
        
        //assume that rpgle program are in resource but this dir could be anywhere
        File rpgDir = new File(CallJariko.class.getResource("/rpg/" + programName).getFile()).getParentFile();
        List<RpgProgramFinder> programFinders = Arrays.asList(new DirRpgProgramFinder(rpgDir));
        
        //init jariko
        CommandLineProgram jariko = RunnerKt.getProgram(programName, systemInterface, programFinders);
        
        //creating jariko configuration
        //storage is a storage
        //JarikoCallback is another story... :-)
        Configuration configuration = new Configuration();
        
        jariko.singleCall(new ArrayList<String>(), configuration);
        
        
    }
}
