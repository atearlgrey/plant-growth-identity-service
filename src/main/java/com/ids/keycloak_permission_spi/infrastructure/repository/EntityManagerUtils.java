package com.ids.keycloak_permission_spi.infrastructure.repository;

import jakarta.persistence.EntityManager;
import org.keycloak.models.KeycloakSession;
import org.keycloak.provider.Provider;

import java.lang.reflect.Method;

public class EntityManagerUtils {

    @SuppressWarnings("unchecked")
    public static EntityManager getEntityManager(KeycloakSession session) {
        try {
            // ✅ Tải class org.keycloak.connections.jpa.JpaConnectionProvider qua reflection
            Class<?> providerClass = Class.forName("org.keycloak.connections.jpa.JpaConnectionProvider");

            // ✅ Ép kiểu "thủ công" để qua generic constraint
            Provider jpaProvider = (Provider) session.getProvider((Class<? extends Provider>) providerClass);

            if (jpaProvider == null) {
                throw new IllegalStateException("JPA Provider not found — Keycloak may not be using JPA storage.");
            }

            // ✅ Gọi phương thức getEntityManager() bằng reflection
            Method getEm = providerClass.getMethod("getEntityManager");
            return (EntityManager) getEm.invoke(jpaProvider);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException("JpaConnectionProvider class not found in Keycloak runtime", e);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Cannot access EntityManager from KeycloakSession", e);
        }
    }
}
