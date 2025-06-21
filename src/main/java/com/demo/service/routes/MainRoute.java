package com.demo.service.routes;

import org.springframework.stereotype.Component;

@Component(value ="mainRoute")
public class MainRoute extends AbstractRoute{

    @Override
    public String getFromTopicName(){
        return getTopicsConfig().getMainTopic();
    }

    public String getFromTopicRouteId() {
        return getTopicsConfig().getTopicRouteId();
    }

}
