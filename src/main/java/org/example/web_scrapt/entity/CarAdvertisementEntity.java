package org.example.web_scrapt.entity;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.web_scrapt.constants.StatusConstants;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Table(name = "car_advertisements")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CarAdvertisementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String carName;
    BigDecimal price;
    String currency;
    String details;
    String dateTime;
    String uniqueIdentifier;

    @ManyToOne(cascade = {CascadeType.MERGE} , fetch = FetchType.LAZY)
    @JoinColumn(name = "url_id")
    UrlEntity url;

    LocalDateTime createdAt;
    Integer status;

    @PrePersist
    public void setDefaults(){
        createdAt = LocalDateTime.now();
        status = StatusConstants.ACTIVE.getStatus();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarAdvertisementEntity that = (CarAdvertisementEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
