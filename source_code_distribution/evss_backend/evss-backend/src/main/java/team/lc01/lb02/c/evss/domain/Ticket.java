package team.lc01.lb02.c.evss.domain;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "ticket", uniqueConstraints = @UniqueConstraint(columnNames = {"id"}))

@NamedQueries({
        @NamedQuery(name = "Ticket.viewAll", query = "SELECT t FROM Ticket t"),
        @NamedQuery(name = "Ticket.findByUserEmail", query = "SELECT t FROM Ticket t WHERE t.userEmail =: email")
})

public class Ticket extends BaseDomain {

    /**
     * The user email of the reported user
     *
     */
    @NotNull
    @Column(name = "user_email")
    private String userEmail;

    /**
     * The reported vehicle sn
     *
     */
    @NotNull
    @Column(name = "vehicle_sn")
    private String vehicleSN;

    /**
     * The ticket content
     *
     */
    @Column(name = "content")
    private String content;

}
