package com.pjt.globalmarket.config.auth.oauth;

import java.util.Map;

public class GoogleUserInfo implements OAuth2UserInfo {

    Map<String, Object> attributes;

    public GoogleUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getEmail() {
        return String.valueOf(attributes.get("email"));
    }

    @Override
    public String getProvider() {
        return "google";
    }

    @Override
    public String getName() {
        return String.valueOf(attributes.get("name"));
    }

    @Override
    public String getProviderId() {
        return String.valueOf(attributes.get("sub"));
    }
}
