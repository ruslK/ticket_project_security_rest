package com.ticketsproject.entities;

import com.ticketsproject.entities.common.UserPrinciple;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@Component
public class BaseEntityListener extends AuditingEntityListener {

    @PrePersist
    private void onPrePersist(BaseEntity baseEntity) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        baseEntity.setInsertDateTime(LocalDateTime.now());
        baseEntity.setLastUpdateDateTime(LocalDateTime.now());

        if (authentication != null && !authentication.getName().equals("anonymousUser")) {
            Object obj = authentication.getPrincipal();
            baseEntity.setInsertUserId(((UserPrinciple) obj).getId());
            baseEntity.setLustUpdateUserId(((UserPrinciple) obj).getId());
        }
    }

    @PreUpdate
    private void onPreUpdate(BaseEntity baseEntity) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        baseEntity.setLastUpdateDateTime(LocalDateTime.now());

        if (authentication != null && !authentication.getName().equals("anonymousUser")) {
            Object obj = authentication.getPrincipal();
            baseEntity.setLustUpdateUserId(((UserPrinciple) obj).getId());
        }
    }

}
