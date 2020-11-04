package com.lsd.gateway.route;

import com.lsd.gateway.filter.GatewayFilter;
import com.lsd.gateway.predicate.Predicate;

import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * @Author: nhsoft.lsd
 * @Description:
 * @Date:Create：in 2020-11-02 22:36
 * @Modified By：
 */
public class Route {

    private final String id;

    private final URI uri;

    private final List<GatewayFilter> gatewayFilters;

    private final List<Predicate> predicates;

    private final Map<String, Object> metadata;

    public Route(String id, URI uri, List<GatewayFilter> gatewayFilters, List<Predicate> predicates, Map<String, Object> metadata) {
        this.id = id;
        this.uri = uri;
        this.gatewayFilters = gatewayFilters;
        this.predicates = predicates;
        this.metadata = metadata;
    }

    public String getId() {
        return id;
    }

    public URI getUri() {
        return uri;
    }

    public List<GatewayFilter> getGatewayFilters() {
        return gatewayFilters;
    }

    public List<Predicate> getPredicates() {
        return predicates;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }
}
