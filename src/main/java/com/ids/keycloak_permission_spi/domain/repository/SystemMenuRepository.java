package com.ids.keycloak_permission_spi.domain.repository;

import com.ids.keycloak_permission_spi.domain.model.SystemMenu;
import java.util.List;

public interface SystemMenuRepository {
    List<SystemMenu> findByUserId(String userId);
}