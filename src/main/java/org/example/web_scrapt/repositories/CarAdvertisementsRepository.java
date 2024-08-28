package org.example.web_scrapt.repositories;


import org.example.web_scrapt.entity.CarAdvertisementEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface CarAdvertisementsRepository extends JpaRepository<CarAdvertisementEntity, Long> {

    Optional<CarAdvertisementEntity> getByUniqueIdentifierAndStatus(String identifier, Integer status);

}
