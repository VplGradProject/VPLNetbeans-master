/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plugins.pluginloader;

import com.plugins.exception.PluginErrorLoadingException;
import com.plugins.plugin.JarPluginBase;
import com.plugins.exception.PluginNotFoundException;
import com.plugins.exception.PluginProperiesNotFound;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Properties;
import com.plugins.plugin.Plugin;
import com.plugins.plugin.PluginList;
//TODO 1.add Plugin Manager

/**
 * <h2>Used to load Plugins from jar files</h2>
 * it uses a class loader and read class From jar files specified in a property called className
 * and found in JarPluginLoader.PLUGIN_METADATA_FILE
 * clients of this loader should implements the PluginIface 
 * @author Mohamed Khaled(icraus)
 * @version 1.1
 */
public class JarPluginLoader implements PluginLoader {
    
    /**
     * <h2>The Default Plugin Meta data file which must exists in all plugins Jar and of type <b>.properties</b></h2>
     */
    public static String PLUGIN_METADATA_FILE = "PluginMetadata.properties";
    public static String PLUGIN_PACKAGE_PROPERTY = "PLUGIN_META_INF";
    public static String PLUGIN_PROPERTIES_PATH = PLUGIN_PACKAGE_PROPERTY + "/" + PLUGIN_METADATA_FILE;
    private JarPluginBase metaPlugin;

    public JarPluginBase getMetaPlugin() {
        return metaPlugin;
    }

    protected void setMetaPlugin(JarPluginBase metaPlugin) {
        this.metaPlugin = metaPlugin;
    }
    private String filePath;
    private ClassLoader loader;
    private Properties prop;
    public JarPluginLoader() {
    }

    /**
     * <h2>used to init class Loader</h2>
     *  the default is java.net.URLClassLoader
     * @return
     * @throws PluginNotFoundException
     */
    protected ClassLoader initClassLoader() throws PluginNotFoundException{
        try {
            File file = new File(getFilePath());
            if(!file.exists())
                throw new MalformedURLException();
//TODO add resource from web support 
            URL jarPath = file.toURI().toURL();
//            URL jarPath = new URL(getFilePath());
            ClassLoader _load =  new URLClassLoader(new URL[]{jarPath});
            return _load;
        } catch (MalformedURLException ex) {
            throw new PluginNotFoundException("No such File");
        }
        
    }
    /**
     * used to load properties from jar files
     * @return
     * @throws PluginProperiesNotFound 
     */
    protected Properties loadProperties() throws PluginProperiesNotFound{
        prop = new Properties();
        try (InputStream stream = ((URLClassLoader)getLoader()).findResource(PLUGIN_PROPERTIES_PATH).openStream()){
            prop.load(stream);
            String str = prop.getProperty("className");
            if(str == null || str.isEmpty())
                throw new NullPointerException();
            return prop;     
        } catch (IOException | NullPointerException ex) {
            throw new PluginProperiesNotFound("Error Loading Properties");
        }
    }
    
    /**
     * Factory method for loading class from jar files
     * @param className
     * @return an instance of the plugin class
     * @throws PluginNotFoundException
     * @throws PluginErrorLoadingException
     */
    protected Plugin loadPluginHelper(String className) throws PluginNotFoundException, PluginErrorLoadingException { 
        try {
            if(className.startsWith("[") && className.endsWith("]")){
                PluginList lst = new PluginList();
                String[] split = className.split(",");
                for(String s : split){
                    String trimd = s.trim();
                    Plugin plugin = loadSinglePlugin(trimd);
                    lst.add(plugin);
                }
                return lst;
            }else {
                
                return loadSinglePlugin(className);
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            throw new PluginNotFoundException("Can't Find Plugin, Error Class Name" + ex.getMessage());
        }catch(ClassCastException ex){
            throw new PluginErrorLoadingException(); 
        }
    }

    protected Plugin loadSinglePlugin(String className) throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        Plugin plugin = (Plugin) Class.forName(className, true, getLoader()).newInstance();
        return plugin;
    }
    protected void initPluginLoader(String _filePath) throws PluginNotFoundException, PluginProperiesNotFound{
        setFilePath(_filePath);
        ClassLoader _load = initClassLoader();
        setLoader(_load);
        loadProperties();
        
        
    }
    /**
     * 
     * @param _filePath
     * @return an instance of com.plugins.plugin.JarPluginBase
     * @throws PluginNotFoundException
     * @throws PluginErrorLoadingException
     * @throws PluginProperiesNotFound 
     */
    
    @Override
    public Plugin loadPlugin(String _filePath) throws PluginNotFoundException, PluginErrorLoadingException, PluginProperiesNotFound {
        initPluginLoader(_filePath);
        String className = prop.getProperty(JarPluginBase.CLASSNAME_PROPERTY);
        Plugin plugin = loadPluginHelper(className);
        setMetaPlugin(new JarPluginBase(plugin));
        getMetaPlugin().setPluginMetaData(prop);
        return plugin;
    }
    
     public String getFilePath() {
        return filePath;
    }

    protected void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public ClassLoader getLoader() {
        return loader;
    }

    protected void setLoader(ClassLoader loader) {
        this.loader = loader;
    }

    

    
    
}
