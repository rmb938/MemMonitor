/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mineempire.mem;

import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Ryan
 */
public class MemMonitor extends JavaPlugin {

    @Override
    public void onDisable() {
        System.out.println("[MemMonitor] Plugin Shut Down");
    }

    @Override
    public void onEnable() {
        System.out.println("[MemMonitor] Plugin Loaded");
        final int mb = 1024 * 1024;
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {

            @Override
            public void run() {
                Runtime runtime = Runtime.getRuntime();
                System.gc();
                //Print Maximum available memory
                System.out.println("[MemMonitor]Max Memory:" + (runtime.maxMemory() / mb) + " MB");
                //Print total available memory
                System.out.println("[MemMonitor]Total Memory:" + (runtime.totalMemory() / mb) + " MB");
                //Print free memory
                System.out.println("[MemMonitor]Free Memory:"
                        + (runtime.freeMemory() / mb) + " MB");
                //Print used memory
                System.out.println("[MemMonitor]Used Memory:"
                        + ((runtime.totalMemory() - runtime.freeMemory()) / mb) + " MB");
            }
        }, 0L, 60 * 20L);
    }
    
    
    
}
