package com.codingchosun.backend.repository.templaterepository;


import com.codingchosun.backend.domain.Template;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TemplateRepository extends JpaRepository<Template, Long> {
    Optional<Template> findTemplateByContent(String content);

    List<Template> findByValidates_ToUser_UserId(Long id);
}
