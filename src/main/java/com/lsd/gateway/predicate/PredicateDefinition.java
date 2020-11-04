package com.lsd.gateway.predicate;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author: nhsoft.lsd
 * @Description:
 * @Date:Create：in 2020-11-03 21:58
 * @Modified By：
 */
public class PredicateDefinition {

    private String name;

    private Map<String, String> args = new LinkedHashMap<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getArgs() {
        return args;
    }

    public void setArgs(Map<String, String> args) {
        this.args = args;
    }
}
