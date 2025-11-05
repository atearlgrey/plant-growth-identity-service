package com.ids.keycloak_permission_spi.domain.repository;

import com.ids.keycloak_permission_spi.domain.model.Permission;
import java.util.List;

public interface PermissionRepository {
    List<Permission> findByUserId(String userId);
}
