package team.lc01.lb02.c.evss.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import team.lc01.lb02.c.evss.util.Status;
import org.hibernate.validator.constraints.Range;
import team.lc01.lb02.c.evss.util.json.JsonUtil;

@Getter
@Setter
@Entity
@Table(name = "vehicle", uniqueConstraints = @UniqueConstraint(columnNames = {"id"}))

@NamedQueries({
        @NamedQuery(name = "Vehicle.viewAll", query = "SELECT v FROM Vehicle v"),
        @NamedQuery(name = "Vehicle.findByType", query = "SELECT v FROM Vehicle v WHERE v.type =: type"),
        @NamedQuery(name = "Vehicle.findBySN", query = "SELECT v FROM Vehicle v WHERE v.sn =: sn")
})

public class Vehicle extends BaseDomain {

    /**
     * The unique serial number of the Vehicle
     *
     */
    @NotNull
    @Column(name = "sn", nullable = false, unique = true)
    private String sn;

    /**
     * The type of the vehicle
     * 1 -> electric scooters
     * 2 -> electric bikes
     *
     */
    @NotNull
    @Range(min= 1, max = 2, message = "The vehicle type is not valid.")
    @Column(name = "type", nullable = false)
    private Integer type;

    /**
     * The life of battery
     *
     */
    @NotNull
    @Range(min= 0, max = 100, message = "battery life can not less than 0 and greater than 100")
    @Column(name = "battery", nullable = false)
    private Double battery = 0.0;

    /**
     * The charging cycle of the vehicle
     * default -> 0 - when it never charged
     *
     */
    @NotNull
    @Range(min= 0, message = "The charging cycle can not be negative.")
    @Column(name = "charging_cycle", nullable = false)
    private Double chargingCycle = 0.0;

    /**
     * The charging records of the vehicle
     * 'null' when it never charged
     *
     */
    @Column(name = "charging_records", columnDefinition = "json")
    private String chargingRecords;

    /**
     * The current status of the vehicle
     * default -> 0
     * -1 -> under maintenance
     *  0 -> standby
     *  1 -> in use
     *  2 -> parking
     *  3 -> reported
     *  4 -> defective
     *
     */
    @Range(min= -1, max = 4, message = "Unknown status.")
    @Column(name = "status", nullable = false)
    private Integer status = Status.STANDBY;

    /**
     * The status records of the vehicle
     * 'null' when it never maintained
     *
     */
    @Column(name = "status_records", columnDefinition = "json")
    private String statusRecords;

    /**
     * The geolocation of the vehicle
     * 'null' when it never in use
     *
     */
    @Column(name = "location")
    private String location;

    /**
     * The user ID of the last maintainer
     * 'null' when it never maintained
     *
     */
    @ManyToOne
    @PrimaryKeyJoinColumn(name = "maintainer_id")
    private User maintainer;

    /**
     * The user ID of the current user
     * 'null' when it is not occupied
     *
     */
    @ManyToOne
    @PrimaryKeyJoinColumn(name = "user_id")
    private User user;

    /**
     * The maintenance records of the vehicle
     * 'null' when it never maintained
     *
     */
    @Column(name = "maintain_records", columnDefinition = "json")
    private String maintainRecords;

    /**
     * Set of tickets of the vehicle
     *
     */
    @OneToMany
    @JoinColumn(name = "vehicle")
    private Set<Ticket> tickets;

    /**
     * Set status and keep the records
     *
     * @param status Integer - A status number
     */
    public void setStatus(Integer status) {
        Map<String, Object> result;
        LocalDateTime nowTime = LocalDateTime.now();
        String lastResult = this.getStatusRecords();
        if (lastResult != null) {
            result = JsonUtil.toBean(lastResult, LinkedHashMap.class);
        } else {
            result = new LinkedHashMap<>();
        }
        result.put(nowTime.toString(), status);
        this.setStatusRecords(JsonUtil.toJson(result));
        this.status = status;
    }

    /**
     * The overridden toString method
     *
     * @return String
     */
    @Override
    public String toString() {
        return "Vehicle{" +
                "sn=" + sn +
                ", type=" + type +
                ", chargingCycle=" + chargingCycle +
                ", chargingRecords='" + chargingRecords + '\'' +
                ", status=" + status +
                ", location='" + location + '\'' +
                ", maintainer=" + maintainer +
                ", maintainRecords='" + maintainRecords + '\'' +
                '}';
    }
}
