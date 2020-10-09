/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smeup.examples.calljariko;

import com.smeup.rpgparser.execution.CommandLineProgram;
import com.smeup.rpgparser.execution.Configuration;
import com.smeup.rpgparser.execution.JarikoCallback;
import com.smeup.rpgparser.execution.RunnerKt;
import com.smeup.rpgparser.interpreter.IMemorySliceStorage;
import com.smeup.rpgparser.interpreter.MemorySliceId;
import com.smeup.rpgparser.interpreter.SystemInterface;
import com.smeup.rpgparser.interpreter.Value;
import com.smeup.rpgparser.jvminterop.JavaSystemInterface;
import com.smeup.rpgparser.rpginterop.DirRpgProgramFinder;
import com.smeup.rpgparser.rpginterop.RpgProgramFinder;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author marco.lanari
 */
public class CallJariko {
    
    private static class MemoryStorage implements IMemorySliceStorage {
        
        private final Map<MemorySliceId, Map<String, ? extends Value>> memorySliceToVariables = new HashMap<>();

        @Override
        public void open() {
            //for now ignore
        }

        @Override
        public Map<String, Value> load(MemorySliceId msi) {
            //this method can't return null!!!
            return (Map<String, Value>)memorySliceToVariables.getOrDefault(msi, new HashMap<>());
        }

        @Override
        public void beginTrans() {
            //for now ignore
        }

        @Override
        public void store(MemorySliceId msi, Map<String, ? extends Value> map) {
            memorySliceToVariables.put(msi, map);
        }

        @Override
        public void commitTrans() {
            //for now ignore
        }

        @Override
        public void rollbackTrans() {
            //for now ignore
        }

        @Override
        public void close() {
            //for now ignore
        }
        
    }
    
    
    
    public static void main(String[] args) {
        
        //creating a system interface
        SystemInterface systemInterface = new JavaSystemInterface();
        String programName = "ACTGRP.rpgle";
        
        //assume that rpgle program are in resource but this dir could be anywhere
        File rpgDir = new File(CallJariko.class.getResource("/rpg/" + programName).getFile()).getParentFile();
        List<RpgProgramFinder> programFinders = Arrays.asList(new DirRpgProgramFinder(rpgDir));
        
        //init jariko
        CommandLineProgram jariko = RunnerKt.getProgram(programName, systemInterface, programFinders);

        //Creating a Dummy memory storage in order to handle jariko memory persistence
        //For example you can add/get memorystorage in a httpsession attribute
        MemoryStorage storage = new MemoryStorage();
        
        
        //creating jariko configuration
        //storage is a storage
        //JarikoCallback is another story... :-)
        Configuration configuration = new Configuration(storage, new JarikoCallback());
        
        jariko.singleCall(new ArrayList<String>(), configuration);
        
    }
    
}
