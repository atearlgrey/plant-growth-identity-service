package com.ids.keycloak_permission_spi.domain.model;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SystemMenu {
    private String systemMenuCode;
    private String functionCode;
    private String title;
    private String path;
    private String icon;
    private String parentCode;
    private Short menuLevel;

    private String permissionId;
    private LocalDateTime expiredDate;
    private Boolean permissionEnabled;
}
