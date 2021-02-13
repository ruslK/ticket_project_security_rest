package com.ticketsproject.entities;

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
        baseEntity.setInsertUserId(1L);
        baseEntity.setLustUpdateUserId(1L);
        if (authentication != null && !authentication.getName().equals("anonymousUser")) {
            long id = Long.parseLong(authentication.getName());
            baseEntity.setInsertUserId(id);
            baseEntity.setLustUpdateUserId(id);
        }
    }

    @PreUpdate
    private void onPreUpdate(BaseEntity baseEntity) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        baseEntity.setLastUpdateDateTime(LocalDateTime.now());
        baseEntity.setLustUpdateUserId(1L);
        if (authentication != null && !authentication.getName().equals("anonymousUser")) {
            long id = Long.parseLong(authentication.getName());
            baseEntity.setLustUpdateUserId(id);
        }
    }

}
