package com.ids.keycloak_permission_spi.application;

import com.ids.keycloak_permission_spi.domain.model.Permission;
import com.ids.keycloak_permission_spi.domain.model.SystemMenu;
import com.ids.keycloak_permission_spi.domain.service.PermissionService;
import com.ids.keycloak_permission_spi.domain.service.SystemMenuService;
import com.ids.keycloak_permission_spi.infrastructure.repository.PermissionRepositoryImpl;
import com.ids.keycloak_permission_spi.infrastructure.repository.SystemMenuRepositoryImpl;
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

        // Load permission services
        PermissionService permissionService = new PermissionService(new PermissionRepositoryImpl(keycloakSession));
        SystemMenuService systemMenuService = new SystemMenuService(new SystemMenuRepositoryImpl(keycloakSession));

        // Get list permissions
        List<String> permissions = permissionService.getUserPermissions(userSession.getUser().getId())
                .stream()
                .map(Permission::getResourceCode)
                .toList();
        LOG.infof("[IDS] setClaim called for user %s, claim=%s, permissions=%s",
                userSession.getUser().getUsername(), claimName, permissions);

        token.getOtherClaims().put(claimName, permissions);

        // Get list system menus
        List<SystemMenu> menus = systemMenuService.getMenusForUser(userSession.getUser().getId());
        LOG.infof("[IDS] setClaim called for user %s, claim=%s, menus=%s",
                userSession.getUser().getUsername(), "menus", menus);

        token.getOtherClaims().put("menus", menus);
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
