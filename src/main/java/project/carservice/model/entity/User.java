package project.carservice.model.entity;

import jakarta.persistence.*;
import project.carservice.model.entity.enums.UserRoleEnum;

import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false, unique = true)
    private String phone;

    @Column(nullable = false)
    @Enumerated (EnumType.STRING)
    private UserRoleEnum role;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Car> cars;

    @OneToMany(mappedBy = "addedBy")
    private Set<Order> orders;

    @OneToMany(mappedBy = "responsibleMechanic")
    private Set<Order> ordersInProgress;

}
