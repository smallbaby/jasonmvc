package com.jason.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * author: zhangkai
 * date: 2019-08-22
 * description:
 */
public class ConfigLoader {
    private Map<String, Object> configMap;

    public ConfigLoader() {
        this.configMap = new HashMap<>();
    }

    public void load(String conf) {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(new File(conf)));
            toMap(properties);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void toMap(Properties properties) {
        if (null != properties) {
            Set<Object> keys = properties.keySet();
            for (Object key : keys) {
                Object value = properties.get(key);
                configMap.put(key.toString(), value);
            }
        }
    }

    public String getConf(String name) {
        return configMap.get(name) == null ? null : configMap.get(name).toString();
    }

    public void setConf(String name, String value) {
        configMap.put(name, value);
    }

    public Object getObject(String name) {
        return configMap.get(name);
    }

}
