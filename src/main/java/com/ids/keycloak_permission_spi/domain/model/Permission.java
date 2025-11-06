package com.ids.keycloak_permission_spi.domain.model;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Permission {
    private String userId;
    private String roleId;
    private String roleName;
    private String clientId;
    private String functionCode;
    private String functionTitle;
    private String resourceCode;
    private String resourceTitle;
    private String resourcePath;
    private String resourceMethod;
    private String permissionId;
    private LocalDateTime permissionExpiredDate;
    private Boolean permissionEnabled;
}
