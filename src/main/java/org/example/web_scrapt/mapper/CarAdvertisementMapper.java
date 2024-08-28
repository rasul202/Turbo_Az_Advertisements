package org.example.web_scrapt.mapper;

import jakarta.persistence.MappedSuperclass;
import org.example.web_scrapt.entity.CarAdvertisementEntity;
import org.example.web_scrapt.model.request.AddCarAdvertisementToDbRequest;
import org.example.web_scrapt.model.response.GetAllCarAdvertisementsResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring" , nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CarAdvertisementMapper {

    CarAdvertisementEntity toCarAdvertisementEntity(AddCarAdvertisementToDbRequest request);
    CarAdvertisementEntity toCarAdvertisementEntity(GetAllCarAdvertisementsResponse request);
    GetAllCarAdvertisementsResponse toGetAllCarsAdvertisementsResponse(CarAdvertisementEntity entity);
    void updateCarAdvertisementEntity(@MappingTarget CarAdvertisementEntity targetEntity , CarAdvertisementEntity entity);

}
