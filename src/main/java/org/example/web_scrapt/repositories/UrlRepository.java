package org.example.web_scrapt.repositories;

import org.example.web_scrapt.entity.UrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UrlRepository extends JpaRepository<UrlEntity , Long> {

    Optional<UrlEntity> findByUrlAndStatus(String url, Integer status);

    Optional<List<UrlEntity>> findAllByStatus(Integer status);

}
