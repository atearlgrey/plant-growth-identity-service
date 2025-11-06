package com.ids.keycloak_permission_spi.infrastructure.repository;

import com.ids.keycloak_permission_spi.domain.model.Permission;
import com.ids.keycloak_permission_spi.domain.repository.PermissionRepository;
import org.keycloak.models.KeycloakSession;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.time.LocalDateTime;
import java.util.*;

public class PermissionRepositoryImpl implements PermissionRepository {

    private final EntityManager em;

    public PermissionRepositoryImpl(KeycloakSession session) {
        this.em = EntityManagerUtils.getEntityManager(session);
    }

    @Override
    public List<Permission> findByUserId(String userId) {
        Query q = em.createNativeQuery("""
            SELECT *
            FROM view_ids_user_permissions
            WHERE user_id = ?1
        """);
        q.setParameter(1, userId);

        @SuppressWarnings("unchecked")
        List<Object[]> rows = q.getResultList();

        List<Permission> permissions = new ArrayList<>();
        for (Object[] r : rows) {
            permissions.add(
                new Permission(
                    (String) r[0],
                    (String) r[1],
                    (String) r[2],
                    (String) r[3],
                    (String) r[4],
                    (String) r[5],
                    (String) r[6],
                    (String) r[7],
                    (String) r[8],
                    (String) r[9],
                    (String) r[10],
                    (LocalDateTime) r[11],
                    (Boolean) r[12]
            ));
        }

        return permissions;
    }
}