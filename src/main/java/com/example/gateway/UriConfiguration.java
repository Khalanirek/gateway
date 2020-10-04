package com.example.gateway;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("custom")
class UriConfiguration {

    private String httpHost = "";

    public String getHttpHost() {
        return this.httpHost;
    }

    public void setHttpHost(String httpHost) {
        this.httpHost = httpHost;
    }
}
