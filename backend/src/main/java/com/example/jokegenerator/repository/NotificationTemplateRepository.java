package com.example.jokegenerator.repository;

import com.example.jokegenerator.entity.NotificationTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationTemplateRepository extends JpaRepository<NotificationTemplate, Long> {
    
    @Query("SELECT nt FROM NotificationTemplate nt WHERE nt.isActive = true")
    List<NotificationTemplate> findActiveTemplates();
    
    List<NotificationTemplate> findByTemplateTypeAndIsActiveTrue(NotificationTemplate.TemplateType templateType);
}