package com.pjt.globalmarket.config.auth.oauth;

public interface OAuth2UserInfo {

    public String getEmail();

    public String getProvider();

    public String getName();

    public String getProviderId();

}
