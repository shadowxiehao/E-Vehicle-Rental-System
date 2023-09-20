package team.lc01.lb02.c.evss.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "balance",
        uniqueConstraints = @UniqueConstraint(columnNames = {"email"}))
@NamedQueries({
        @NamedQuery(name = "Balance.findBalanceByEmail", query = "SELECT b FROM Balance as b " +
                "WHERE b.email = :email"),
})
public class Balance extends BaseDomain{
    @NotNull
    @Size(min = 1, max = 255)
    @Column(nullable = false, unique = true)
    @Pattern(regexp = ".+@.+\\.[a-z]+", message = "invalid email format")
    private String email;

    @NotNull
    @Column(name = "balance", nullable = false)
    private double balance;

    public Balance(String email, double balance){
        this.email = email;
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Balance{" +
                "email='" + email + '\'' +
                ", balance=" + balance +
                '}';
    }
}
