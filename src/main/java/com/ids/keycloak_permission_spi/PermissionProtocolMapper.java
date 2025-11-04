package com.ids.keycloak_permission_spi;

import org.jboss.logging.Logger;
import org.keycloak.models.*;
import org.keycloak.protocol.oidc.OIDCLoginProtocol;
import org.keycloak.protocol.oidc.mappers.*;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.representations.IDToken;

import java.util.*;

public class PermissionProtocolMapper extends AbstractOIDCProtocolMapper
        implements OIDCAccessTokenMapper, OIDCIDTokenMapper, UserInfoTokenMapper {

    public static final String PROVIDER_ID = "ids-permission-mapper";
    private static final Logger LOG = Logger.getLogger(PermissionProtocolMapper.class);

    @Override
    public String getDisplayCategory() {
        return "Token Mapper";
    }

    @Override
    public String getDisplayType() {
        return "IDS Permission Mapper";
    }

    @Override
    public String getHelpText() {
        return "Add permissions from custom tables into JWT token.";
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        List<ProviderConfigProperty> configProperties = new ArrayList<>();

        // Add standard token claim name, json type, and include-in-token checkboxes
        OIDCAttributeMapperHelper.addTokenClaimNameConfig(configProperties);
        OIDCAttributeMapperHelper.addJsonTypeConfig(configProperties);
        OIDCAttributeMapperHelper.addIncludeInTokensConfig(configProperties, PermissionProtocolMapper.class);

        // Set default value for claim name
        configProperties.stream()
                .filter(p -> p.getName().equals(OIDCAttributeMapperHelper.TOKEN_CLAIM_NAME))
                .findFirst()
                .ifPresent(prop -> prop.setDefaultValue("permissions"));

        return configProperties;
    }

    @Override
    protected void setClaim(IDToken token,
                            ProtocolMapperModel mappingModel,
                            UserSessionModel userSession,
                            KeycloakSession keycloakSession,
                            ClientSessionContext clientSessionCtx) {

        // Get claim name from mapper configuration
        String claimName = mappingModel.getConfig().get(OIDCAttributeMapperHelper.TOKEN_CLAIM_NAME);

        if (claimName == null || claimName.isEmpty()) {
            LOG.warn("[IDS] Claim name is missing — skipping mapping.");
            return;
        }

        // You can later replace this with database query logic
        List<String> permissions = List.of("READ", "WRITE", "DELETE");

        LOG.infof("[IDS] setClaim called for user %s, claim=%s, permissions=%s",
                userSession.getUser().getUsername(), claimName, permissions);

        token.getOtherClaims().put(claimName, permissions);
    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    @Override
    public String getProtocol() {
        return OIDCLoginProtocol.LOGIN_PROTOCOL;
    }
}
