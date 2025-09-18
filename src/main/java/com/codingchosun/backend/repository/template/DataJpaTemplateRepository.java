package com.codingchosun.backend.repository.template;

import com.codingchosun.backend.domain.Template;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DataJpaTemplateRepository extends JpaRepository<Template, Long> {

    Optional<Template> findTemplateByContent(String content);

    List<Template> findByEvaluations_ToUser_UserId(Long evaluationsToUserUserId);

}
