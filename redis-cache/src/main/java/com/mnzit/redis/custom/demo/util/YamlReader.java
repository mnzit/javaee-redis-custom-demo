package com.mnzit.redis.custom.demo.util;

import lombok.extern.slf4j.Slf4j;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.representer.Representer;

/**
 *
 * @author Manjit Shakya <manjit.shakya@f1soft.com>
 */
@Slf4j
public class YamlReader {

    public <T> List<T> getAll(String fileName, Class<T> clazz) {
        Representer representer = new Representer();
        Yaml yaml = new Yaml(new Constructor(clazz), representer);
        List<T> list = new ArrayList<>();
        try {
            InputStream in = new FileInputStream(new File(fileName));

            Iterable<Object> loadAll = yaml.loadAll(in);

            for (Object load : loadAll) {
                T object = clazz.cast(load);
                list.add(object);
            }
        } catch (Exception e) {
            log.error("Exception ", e);
        }
        return list;
    }

    public <T> T load(String fileName, Class<T> clazz) {
        Representer representer = new Representer();
        Yaml yaml = new Yaml(new Constructor(clazz), representer);
        try {
            return yaml.loadAs(new FileInputStream(fileName), clazz);
        } catch (Exception e) {
            log.error("Exception : ", e);
            log.error("Configuration is invalid in " + fileName + " !!!!!!!!!!!!!!!!!!!!!!!!!");
            throw new RuntimeException("Error in configuration file");
        }
    }
}
