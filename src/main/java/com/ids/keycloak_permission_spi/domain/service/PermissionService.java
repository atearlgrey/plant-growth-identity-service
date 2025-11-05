package com.ids.keycloak_permission_spi.domain.service;


import com.ids.keycloak_permission_spi.domain.model.Permission;
import com.ids.keycloak_permission_spi.domain.repository.PermissionRepository;
import java.util.List;

public class PermissionService {
    private final PermissionRepository repository;

    public PermissionService(PermissionRepository repository) {
        this.repository = repository;
    }

    public List<Permission> getUserPermissions(String userId) {
        return repository.findByUserId(userId);
    }
}