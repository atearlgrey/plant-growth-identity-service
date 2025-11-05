package com.ids.keycloak_permission_spi.domain.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Permission {
    private String userId;
    private String roleId;
    private String roleName;
    private String clientId;
    private String resourceId;
    private String resourceCode;
    private String resourceName;
    private String resourceType;
    private String resourcePath;
    private String resourceIcon;
    private String resourceParentId;
    private Boolean resourceVisible;
    private Integer resourceSortOrder;
    private String permissionAction;
    private Boolean permissionActive;
}
