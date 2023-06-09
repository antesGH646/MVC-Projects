package com.cydeo.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *If multiple microservices or projects are the same database
 * and keycloak, they need to same information in their
 * properties file. If something changes e.g. you need to change it
 * everywhere. For this reason, place the common sources in one place
 * but all the projects have to access the place.
 * How do you access the sources of that place/properties file or use the values in your
 * Spring-boot application?
 * Note that usually all the information are kept in GitHub, but not in the application itself.
 */
@Component
@Getter
@Setter
public class KeycloakProperties {
//A class from keycloak-admin-client library requires the following properties
    //getting the keycloak realm value from the properties file located within the project
    @Value("${keycloak.realm}")
    private String realm;
    //getting the keycloak server url value from the properties file located within the project
    @Value("${keycloak.auth-server-url}")
    private String authServerUrl;
    //getting the keycloak client value from the properties file located within the project
    @Value("${keycloak.resource}")
    private String clientId;
    //getting the keycloak client secret key value from the properties file located within the project
    @Value("${keycloak.credentials.secret}")
    private String clientSecret;
    //getting the keycloak master user value from the properties file located within the project
    @Value("${master.user}")
    private String masterUser;
    //getting the keycloak master user password value from the properties file located within the project
    @Value("${master.user.password}")
    private String masterUserPswd;
    //getting the keycloak master realm value from the properties file located within the project
    @Value("${master.realm}")
    private String masterRealm;
    //getting the keycloak master client value from the properties file located within the project
    @Value("${master.client}")
    private String masterClient;
}
