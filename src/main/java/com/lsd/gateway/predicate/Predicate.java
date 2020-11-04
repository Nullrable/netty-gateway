package com.lsd.gateway.predicate;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author: nhsoft.lsd
 * @Description:
 * @Date:Create：in 2020-11-02 22:40
 * @Modified By：
 */
public class Predicate {

    private String name;

    private Map<String, String> args = new LinkedHashMap<>();

    public Predicate(String name, Map<String, String> args) {
        this.name = name;
        this.args = args;
    }

    public String getName() {
        return name;
    }


    public Map<String, String> getArgs() {
        return args;
    }

    public boolean match(String pr){

        String host = args.get("HOST");

        if(host.equals(pr)){

            return true;

        }

        return false;

    }
}
