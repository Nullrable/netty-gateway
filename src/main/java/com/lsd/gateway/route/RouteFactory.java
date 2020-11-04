package com.lsd.gateway.route;

import com.lsd.gateway.config.GatewayProperties;
import com.lsd.gateway.filter.GatewayFilter;
import com.lsd.gateway.filter.GatewayFilterFactory;
import com.lsd.gateway.filter.SimpleGatewayFilterFactory;
import com.lsd.gateway.predicate.FilterDefinition;
import com.lsd.gateway.predicate.Predicate;
import com.lsd.gateway.predicate.PredicateDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author: nhsoft.lsd
 * @Description:
 * @Date:Create：in 2020-11-03 21:51
 * @Modified By：
 */
public class RouteFactory {

    private static final Logger LOG = LoggerFactory.getLogger(RouteFactory.class);

    private final GatewayProperties properties;

    private final GatewayFilterFactory gatewayFilterFactory = new SimpleGatewayFilterFactory();

    public RouteFactory(GatewayProperties properties) {
        this.properties = properties;
    }


    public List<Route> getRoutes(){

        List<RouteDefinition>  routeDefinitionList = properties.getRoutes();

        if( CollectionUtils.isEmpty(routeDefinitionList) ){
            LOG.info("not define any routes");

            return Collections.emptyList();
        }

        List<Route> routes = new ArrayList<>();
        for(RouteDefinition routeDefinition : routeDefinitionList){

            List<GatewayFilter> filters = new ArrayList<>();

            if (!routeDefinition.getFilters().isEmpty()) {
                filters.addAll(loadGatewayFilters(routeDefinition.getId(),
                        new ArrayList<>(routeDefinition.getFilters())));
            }

            List<Predicate> predicates = new ArrayList<>();

            if (!routeDefinition.getPredicates().isEmpty()) {

                List<Predicate> predicateList = loadPredicates(routeDefinition.getId(), new ArrayList<>(routeDefinition.getPredicates()));

                predicates.addAll(predicateList);
            }


            Route route = new Route(routeDefinition.getId(),
                    routeDefinition.getUri(), filters, predicates, routeDefinition.getMetadata());

            routes.add(route);

        }

        return routes;

    }


    @SuppressWarnings("unchecked")
    private List<GatewayFilter> loadGatewayFilters(String routeId,
                                           List<FilterDefinition> filterDefinitions) {
        ArrayList<GatewayFilter> gatewayFilters = new ArrayList<>(filterDefinitions.size());

        for(FilterDefinition filterDefinition : filterDefinitions ){
            GatewayFilter gatewayFilter = gatewayFilterFactory.apply(routeId, filterDefinition);
            gatewayFilters.add(gatewayFilter);
        }

        return gatewayFilters;
    }

    private List<Predicate> loadPredicates(String routeId,
                                           List<PredicateDefinition> predicateDefinitions) {
        List<Predicate> predicates = new ArrayList<>();

        for (PredicateDefinition predicateDefinition : predicateDefinitions) {
            Predicate predicate = new Predicate(predicateDefinition.getName(), predicateDefinition.getArgs());
            predicates.add(predicate);
        }

        return predicates;
    }
}
