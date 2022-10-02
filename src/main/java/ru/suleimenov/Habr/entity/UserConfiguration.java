package ru.suleimenov.Habr.entity;

import lombok.Data;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.*;

@Entity
@Data
@Table(name = "configuration")
public class UserConfiguration {
    @Id
    @Column(name = "user_id")
    private Long id;
    private boolean nameHiding;
    private boolean favHiding;
    @Column(name = "sub_hiding")
    private boolean subHiding;
    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    public boolean isFavHiding() {
        return favHiding;
    }

    public boolean isNameHiding() {
        return nameHiding;
    }

    public boolean isSubHiding() {
        return subHiding;
    }
}
