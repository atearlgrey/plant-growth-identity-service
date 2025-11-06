package com.ids.keycloak_permission_spi.domain.service;

import com.ids.keycloak_permission_spi.domain.model.SystemMenu;
import com.ids.keycloak_permission_spi.domain.repository.SystemMenuRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class SystemMenuService {

    private final SystemMenuRepository menuRepository;

    public List<SystemMenu> getMenusForUser(String userId) {
        return menuRepository.findByUserId(userId);
    }
}
