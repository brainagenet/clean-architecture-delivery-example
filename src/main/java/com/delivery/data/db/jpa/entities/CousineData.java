package com.delivery.data.db.jpa.entities;

import com.delivery.core.domain.Cousine;
import com.delivery.core.domain.Identity;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

import static com.delivery.data.db.jpa.entities.IdConverter.convertId;

@AllArgsConstructor
@Entity(name = "cousine")
@EqualsAndHashCode(of = {"name"})
@Getter
@NoArgsConstructor
@Setter
@Table(name = "cousine")
@ToString(of = {"name"})
public class CousineData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotNull
    private String name;

    @OneToMany(
            mappedBy = "cousine",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<StoreData> stores;

    public void addStore(StoreData store) {
        if (this.stores == null) {
            this.stores = new HashSet<>();
        }

        store.setCousine(this);
        this.stores.add(store);
    }

    public static CousineData withName(String name) {
        return new CousineData(null, name, new HashSet<>());
    }

    // TODO: test toDomain
    public static Cousine toDomain(CousineData cousineData) {
        return new Cousine(
                new Identity(cousineData.getId()),
                cousineData.getName());
    }

    // TODO: test fromDomain
    public static CousineData fromDomain(Cousine cousine) {
        return new CousineData(
                convertId(cousine.getId()),
                cousine.getName(),
                new HashSet<>());
    }
}
