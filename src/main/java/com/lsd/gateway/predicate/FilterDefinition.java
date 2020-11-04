package com.lsd.gateway.predicate;

/**
 * @Author: nhsoft.lsd
 * @Description:
 * @Date:Create：in 2020-11-03 21:58
 * @Modified By：
 */
public class FilterDefinition {

    private final String id;
    private final String name;

    public FilterDefinition(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
