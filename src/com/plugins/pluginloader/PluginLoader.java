/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plugins.pluginloader;

import com.plugins.exception.PluginErrorLoadingException;
import com.plugins.exception.PluginNotFoundException;
import com.plugins.exception.PluginProperiesNotFound;
import com.plugins.plugin.Plugin;
/**
 * <h1>load the plugin from a file</h1>
 * <p> 
 * use this interface to load plugins from file and create instance using the loadPlugin() method 
 * </p>
 * @version 1.0
 * 
 * @author Mohamed khaled(icraus)
 */
public interface PluginLoader {
    
    /**
     *
     * @param filePath the path to the plugin 
     * @return an instance of the plugin
     * @throws com.plugins.exception.PluginNotFoundException
     * @throws com.plugins.exception.PluginProperiesNotFound
     * @throws com.plugins.exception.PluginErrorLoadingException
     */
    public Plugin loadPlugin(String filePath)throws PluginNotFoundException, PluginProperiesNotFound, PluginErrorLoadingException;
    
}
