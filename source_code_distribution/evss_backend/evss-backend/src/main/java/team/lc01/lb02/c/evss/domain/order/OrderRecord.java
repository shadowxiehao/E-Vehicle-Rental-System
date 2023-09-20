package team.lc01.lb02.c.evss.domain.order;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import team.lc01.lb02.c.evss.domain.BaseDomain;
import team.lc01.lb02.c.evss.util.json.JsonUtil;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "orderrecord")
@NamedQueries({
        @NamedQuery(name = "OrderRecord.findAll", query = "SELECT o FROM OrderRecord o "),
        @NamedQuery(name = "Order.findByEmail", query = "SELECT o FROM OrderRecord o " +
                "WHERE o.email = :email"),
        @NamedQuery(name = "Order.findByEmailAndStatus", query = "SELECT o FROM OrderRecord o " +
                "WHERE o.email = :email AND o.status = :status"),
        @NamedQuery(name = "Order.findBySn", query = "SELECT o FROM OrderRecord o " +
                "WHERE o.sn = :sn"),
        @NamedQuery(name = "Order.findByType", query = "SELECT o FROM OrderRecord as o,Vehicle as v " +
                "WHERE v.type = :type AND o.sn = v.sn "),
        @NamedQuery(name = "Order.findByTimeRange", query = "SELECT o FROM OrderRecord o " +
                "WHERE o.endTime >= :moreThanTime AND o.endTime <= :lessThanTime"),
})
public class OrderRecord extends BaseDomain {

    @NotNull
    @Size(min = 1, max = 255)
    @Column(nullable = false)
    @Pattern(regexp = ".+@.+\\.[a-z]+", message = "invalid email format")
    private String email;

    @Column(name = "start_time")
    private long startTime = -1;

    @Column(name = "end_time")
    private long endTime = -1;

    @NotNull
    @Column(name = "locations", columnDefinition = "json")
    private String locations;

    @NotNull
    private String sn;

    @Column(name = "money", columnDefinition = "double")
    private double money = 0.0;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    public OrderRecord(String email, long startTime, List<Double> location, String sn) {
        this.email = email;
        this.startTime = startTime;
        List<List<Double>> locations = new ArrayList<>();
        locations.add(location);
        this.setLocations(locations);
        this.setStatus(Status.Open);
        this.sn = sn;
    }

    public List<List<Double>> getLocations() {
        List<List<Double>> locationsList = JsonUtil.toBean(this.locations, ArrayList.class);
        return locationsList;
    }

    public Boolean setLocations(List<List<Double>> locations) {
        this.locations = JsonUtil.toJsonPretty(locations);
        return true;
    }

    public void checkNull() {
        super.validate(this);
    }

}

