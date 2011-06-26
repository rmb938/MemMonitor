/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mineempire.mem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.World;
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
    private boolean ram = true;
    private boolean players = true;
    private boolean world = false;
    String mainDirectory = "plugins/MemMonitor";
    File Mem = new File(mainDirectory + File.separator + "config.txt");
    Properties prop = new Properties();

    public void loadProcedure() {
        try {
            FileInputStream in = null;
            in = new FileInputStream(Mem);
            prop.load(in);
            ram = Boolean.parseBoolean(prop.getProperty("ShowRam"));
            players = Boolean.parseBoolean(prop.getProperty("ShowPlayers"));
            world = Boolean.parseBoolean(prop.getProperty("ShowWorld"));
            in.close();
        } catch (Exception ex) {
            Logger.getLogger(MemMonitor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void onEnable() {
        System.out.println("[MemMonitor] Plugin Loaded");
        final int mb = 1024 * 1024;
        loadProcedure();
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {

            @Override
            public void run() {
                Runtime runtime = Runtime.getRuntime();
                System.gc();
                //Print Maximum available memory
                if (ram == true) {
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
                if (players == true) {
                    System.out.println("[MemMonitor]Players Online " + getServer().getOnlinePlayers().length);
                }
                if (world == true) {
                    Iterator<World> W = getServer().getWorlds().iterator();
                    while (W.hasNext()) {
                        System.out.println("[MemMonitor]World: " + W.next().getName() + " Loaded Entities: " + W.next().getEntities().size());
                    }
                }
            }
        }, 0L, 60 * 20L);
    }
}
