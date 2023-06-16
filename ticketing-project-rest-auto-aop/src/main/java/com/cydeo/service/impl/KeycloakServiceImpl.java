package com.cydeo.service.impl;

import com.cydeo.config.KeycloakProperties;
import com.cydeo.dto.UserDTO;
import com.cydeo.service.KeycloakService;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.List;

import static java.util.Arrays.asList;
import static org.keycloak.admin.client.CreatedResponseUtil.getCreatedId;

@Service
public class KeycloakServiceImpl implements KeycloakService {

    //want to use info from properties file, so inject KeycloakProperties
    private final KeycloakProperties keycloakProperties;

    public KeycloakServiceImpl(KeycloakProperties keycloakProperties) {
        this.keycloakProperties = keycloakProperties;
    }

    @Override
    public Response userCreate(UserDTO userDTO) {
        //assigning the password from DTO to keycloak
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setTemporary(false);//no need to reset it everytime as in keycloak
        credential.setValue(userDTO.getPassWord());//capture password from DTO and assign it
        //set the user from the DTO to keycloak
        UserRepresentation keycloakUser = new UserRepresentation();
        keycloakUser.setUsername(userDTO.getUserName());
        keycloakUser.setFirstName(userDTO.getFirstName());
        keycloakUser.setLastName(userDTO.getLastName());
        keycloakUser.setEmail(userDTO.getUserName());
        keycloakUser.setCredentials(asList(credential));
        keycloakUser.setEmailVerified(true);
        keycloakUser.setEnabled(true);

        //creating keycloak instance
        Keycloak keycloak = getKeycloakInstance();
        //getting the realm resource
        RealmResource realmResource = keycloak.realm(keycloakProperties.getRealm());
        UsersResource usersResource = realmResource.users();

        // Create Keycloak user, response returns the keycloak user
        Response result = usersResource.create(keycloakUser);
        //getting the client secret from the keycloak user
        String userId = getCreatedId(result);
        ClientRepresentation appClient = realmResource.clients()
                .findByClientId(keycloakProperties.getClientId()).get(0);
        //getting the client role from the json body, adding it to the keycloak role
        RoleRepresentation userClientRole = realmResource.clients().get(appClient.getId()) //
                .roles().get(userDTO.getRole().getDescription()).toRepresentation();

        realmResource.users().get(userId).roles().clientLevel(appClient.getId())
                .add(List.of(userClientRole));

        keycloak.close();
        return result;
    }

    @Override
    public void delete(String userName) {

        Keycloak keycloak = getKeycloakInstance();

        RealmResource realmResource = keycloak.realm(keycloakProperties.getRealm());
        UsersResource usersResource = realmResource.users();

        List<UserRepresentation> userRepresentations = usersResource.search(userName);
        String uid = userRepresentations.get(0).getId();
        usersResource.delete(uid);

        keycloak.close();
    }

    /**
     * To create a keycloak instance, getInstance() requires 5 parameters
     * that should be retrieved through the KeycloakProperties
     * @return instance of the keycloak
     */
    private Keycloak getKeycloakInstance(){
        return Keycloak.getInstance(keycloakProperties.getAuthServerUrl(),
                keycloakProperties.getMasterRealm(), keycloakProperties.getMasterUser()
                , keycloakProperties.getMasterUserPswd(), keycloakProperties.getMasterClient());
    }
}
