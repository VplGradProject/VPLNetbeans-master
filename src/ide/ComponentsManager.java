/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ide;

import com.plugins.exception.PluginErrorLoadingException;
import com.plugins.exception.PluginNotFoundException;
import com.plugins.exception.PluginProperiesNotFound;
import com.plugins.plugin.PluginList;
import com.plugins.pluginloader.JarPluginLoader;
import com.sun.javafx.collections.ObservableListWrapper;
import icraus.Components.ClassComponent;
import icraus.Components.ComponentPlugin;
import icraus.Components.ComponentPluginFactories;
import icraus.Components.MainClassComponent;
import icraus.Components.SimpleComponentPlugin;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;

/**
 *
 * @author Shoka
 */
public class ComponentsManager {

    public static final String COMPONENT_MANAGER_PROPERTIES = "./component_manager.properties";
    public static final String PLUGIN_PATH_PROPERTY = "pluginsPath";

    private static ComponentsManager instance = new ComponentsManager();
    private ObservableList<ComponentPlugin> pluginList = new ObservableListWrapper<>(new ArrayList<>());

    public static ComponentsManager getInstance() {
        return instance;
    }
    private Properties managerProperties;

    public void loadPluginsDir(String dirName) {
        Path get = Paths.get(dirName);
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(get, "*.{jar}")) {
            for (Path entry : stream) {
                JarPluginLoader l = new JarPluginLoader();
                try {
                    PluginList p = null;
                    p = (PluginList) l.loadPlugin(entry.toString());
                    for (Object t : p) {
                        addComponent((ComponentPlugin) t);
                    }

                } catch (PluginNotFoundException ex) {
                    Logger.getLogger(ComponentsManager.class.getName()).log(Level.SEVERE, null, ex);
                } catch (PluginErrorLoadingException ex) {
                    Logger.getLogger(ComponentsManager.class.getName()).log(Level.SEVERE, null, ex);
                } catch (PluginProperiesNotFound ex) {
                    Logger.getLogger(ComponentsManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (DirectoryIteratorException ex) {
            // I/O error encounted during the iteration, the cause is an IOException
//            throw ex.getCause();
        } catch (IOException ex) {
            Logger.getLogger(ComponentsManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private ComponentsManager() {
        loadProperties();
        loadDefaultComponents();
        String str = managerProperties.getProperty(PLUGIN_PATH_PROPERTY);
        if (str != null) {
            loadPluginsDir(str);
        }

    }

    private void loadDefaultComponents() {
        ComponentPlugin plugin = new SimpleComponentPlugin("Class", "Containers", new ClassComponent(), null);
        addComponent(plugin);
        plugin = ComponentPluginFactories.createIfComponentPlugin("If", "inner blocks");
        addComponent(plugin);
        plugin = ComponentPluginFactories.createForComponentPlugin("For", "inner blocks");
        addComponent(plugin);
        plugin = ComponentPluginFactories.createInputValue("Input", "IO Operations");
        addComponent(plugin);
        addComponent(ComponentPluginFactories.createComment("Comment", "Others"));
        addComponent(ComponentPluginFactories.createCallMethod("Call Method", "Others"));
        addComponent(ComponentPluginFactories.createOutput("Output", "IO Operations"));
        addComponent(ComponentPluginFactories.createWhileComponentPlugin("While", "inner blocks"));
        addComponent(ComponentPluginFactories.createDeclareVarPlugin("Declare Var", "Others"));
        addComponent(ComponentPluginFactories.createAssignVarPlugin("Assign Value", "Others"));
        plugin = new SimpleComponentPlugin("MainClass", "Containers", new MainClassComponent(), null);
        addComponent(plugin);
    }

    public void addComponent(ComponentPlugin plugin) {
        pluginList.add(plugin);
    }

    public ObservableList<ComponentPlugin> getPluginList() {
        return pluginList;
    }

    private void loadProperties() {
        managerProperties = new Properties();
        try (FileInputStream f = new FileInputStream(COMPONENT_MANAGER_PROPERTIES)) {
            managerProperties.load(f);
            return;
        } catch (FileNotFoundException ex) {
//            return;
//            Logger.getLogger(ComponentsManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
//            Logger.getLogger(CompodnentsManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Properties getManagerProperties() {
        return managerProperties;
    }

}
