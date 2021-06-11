/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.systeminfo;

import java.util.List;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HWDiskStore;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.PhysicalMemory;
import oshi.util.FormatUtil;

/**
 *
 * @author Felipe, Geovanna e Mateus
 */

public class SystemaInfo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        GetInfo();
    }
    
    public static void GetInfo(){
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();
        CentralProcessor cpu = hal.getProcessor();
        GlobalMemory globalMemory = hal.getMemory();

        CentralProcessor.ProcessorIdentifier processorIdentifier = cpu.getProcessorIdentifier();

        System.out.println("\n------------------- Processor -------------------");
        System.out.println("* Vendor: " + processorIdentifier.getVendor());
        System.out.println("* Name: " + processorIdentifier.getName());
        System.out.println("* Family: " + processorIdentifier.getFamily());
        System.out.println("* ID: " + processorIdentifier.getProcessorID());
        System.out.println("* Identifier: " + processorIdentifier.getIdentifier());
        System.out.println("* Frequency (Hz): " + processorIdentifier.getVendorFreq());
        System.out.println("* Frequency (GHz): " + processorIdentifier.getVendorFreq() / 1000000000.0);
        System.out.println("* Number of physical packages: " + cpu.getPhysicalPackageCount());
        System.out.println("* Number of physical CPU's: " + cpu.getPhysicalProcessorCount());
        System.out.println("* Number of logical CPU's: " + cpu.getLogicalProcessorCount());

        long usedMemory = globalMemory.getTotal() - globalMemory.getAvailable();

        System.out.println("\n------------------- Memory -------------------");
        System.out.println("* Total memory: " + FormatUtil.formatBytes(globalMemory.getTotal()));
        System.out.println("* Available memory: " + FormatUtil.formatBytes(globalMemory.getAvailable()));
        System.out.println("* Used memory: " + FormatUtil.formatBytes(usedMemory));

        List<PhysicalMemory> physicalMemories = globalMemory.getPhysicalMemory();
        physicalMemories.forEach(physicalMemory -> {
            System.out.println("\n* Manufacturer: " + physicalMemory.getManufacturer());
            System.out.println("* Memory type: " + physicalMemory.getMemoryType());
            System.out.println("* Bank/slot label: " + physicalMemory.getBankLabel());
            System.out.println("* Capacity: " + FormatUtil.formatBytes(physicalMemory.getCapacity()));
            System.out.println("* Clock speed: " + FormatUtil.formatHertz(physicalMemory.getClockSpeed()));
        });

        System.out.println("\n------------------- HardDisk -------------------");
        List<HWDiskStore> hwDiskStores = hal.getDiskStores();
        hwDiskStores.forEach(hwDiskStore -> {
            System.out.println("\n* Name: " + hwDiskStore.getName());
            System.out.println("* Model: " + hwDiskStore.getModel());
            System.out.println("* Serial:" + hwDiskStore.getSerial());
            System.out.println("* Size: " + FormatUtil.formatBytes(hwDiskStore.getSize()));
            System.out.println("* Bytes read: " + FormatUtil.formatBytes(hwDiskStore.getReadBytes()));
            System.out.println("* Bytes written: " + FormatUtil.formatBytes(hwDiskStore.getWriteBytes()));
        });
    }
}
