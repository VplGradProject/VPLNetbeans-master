/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plugins.plugin;

import java.util.Properties;

/**
 * a wrapper class for jar based files plugins
 * 
 * @author Mohamed khaled(icraus)
 * @version 1.1
 */
public class JarPluginBase implements Plugin {
    private Plugin plugin;
    public static String CLASSNAME_PROPERTY = "className";
    
    public static String INTERFACENAME_PROPERTY = "interfaceName";
    
    private Properties pluginMetaData;

    
    private String className;
    private String interfaceName;
    public JarPluginBase(Plugin plug){
        setPlugin(plug);
    }
    public JarPluginBase() {
    }
   private void loadMetaData() {
        setClassName(getPluginMetaData().getProperty(CLASSNAME_PROPERTY));
        setInterfaceName(getPluginMetaData().getProperty(INTERFACENAME_PROPERTY));
   }
/**
     * <h2>provides meta data about the plugin </h2>
     * <p> used to retrieve plugin Meta-data every class that implements that interface must provide at least
     * the plugin a string key called className which represents the class name that implements the interface fully with package name <b>java.lang.Object</b>
     * also provides a string key called interface represents the plugin interface name with package name same as className
     * </p> 
     * @return a string key based property 
     */
    public Properties getPluginMetaData() {
        return pluginMetaData;
    }
    public void setPluginMetaData(Properties pluginMetaData) {
        this.pluginMetaData = pluginMetaData;
        loadMetaData();
    }

 /**
     * <h2> returns The interface used by the plugin </h2>
     * @return 
     */

    public String getClassName() {
        return className;
    }

    protected void setClassName(String className) {
        this.className = className;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    protected void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    protected void setPlugin(Plugin plugin) {
        this.plugin = plugin;
    }
}
