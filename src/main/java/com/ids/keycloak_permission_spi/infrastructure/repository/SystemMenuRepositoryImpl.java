package com.ids.keycloak_permission_spi.infrastructure.repository;

import com.ids.keycloak_permission_spi.domain.model.SystemMenu;
import com.ids.keycloak_permission_spi.domain.repository.SystemMenuRepository;
import org.keycloak.models.KeycloakSession;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SystemMenuRepositoryImpl implements SystemMenuRepository {

    private final EntityManager em;

    public SystemMenuRepositoryImpl(KeycloakSession session) {
        this.em = EntityManagerUtils.getEntityManager(session);
    }

    @Override
    public List<SystemMenu> findByUserId(String userId) {
        Query q = em.createNativeQuery("""
            SELECT 
                user_id,
                role_id,
                role_name,
                client_id,
                function_code,
                function_name,
                system_menu_code,
                menu_title,
                menu_path,
                menu_icon,
                menu_parent_code,
                menu_level,
                permission_id,
                expired_date,
                permission_enabled
            FROM view_ids_user_menus
            WHERE user_id = ?1
        """);
        q.setParameter(1, userId);

        @SuppressWarnings("unchecked")
        List<Object[]> rows = q.getResultList();

        List<SystemMenu> menus = new ArrayList<>();
        for (Object[] r : rows) {
            SystemMenu menu = new SystemMenu();
            menu.setSystemMenuCode((String) r[6]);
            menu.setFunctionCode((String) r[4]);
            menu.setTitle((String) r[7]);
            menu.setPath((String) r[8]);
            menu.setIcon((String) r[9]);
            menu.setParentCode((String) r[10]);
            menu.setMenuLevel(r[11] != null ? ((Number) r[11]).shortValue() : null);
            menu.setPermissionId((String) r[12]);
            menu.setExpiredDate((LocalDateTime) r[13]);
            menu.setPermissionEnabled(r[14] != null && (Boolean) r[14]);
            menus.add(menu);
        }

        return menus;
    }
}
