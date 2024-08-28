package org.example.web_scrapt.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.web_scrapt.constants.StatusConstants;

import java.lang.annotation.Target;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "urls")
@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
public class UrlEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String url;
    Integer status;

    @OneToMany(mappedBy = "url" , fetch = FetchType.LAZY)
    List<CarAdvertisementEntity> cars;

    @PrePersist
    public void setDefaults(){
        status = StatusConstants.ACTIVE.getStatus();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UrlEntity urlEntity = (UrlEntity) o;
        return Objects.equals(id, urlEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
