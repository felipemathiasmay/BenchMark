/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.systeminfo;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author Felipe
 */
public class BenchMark {
    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws java.io.FileNotFoundException
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws IOException, FileNotFoundException, InterruptedException {
        // TODO code application logic here
        //input file
        SystemaInfo.GetInfo();
        Stages.readCSV();
    }
}