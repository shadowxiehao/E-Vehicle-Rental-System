package team.lc01.lb02.c.evss.domain;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;
import java.time.ZoneId;
import java.util.Objects;
import java.io.Serializable;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.CreationTimestamp;
import team.lc01.lb02.c.evss.exception.NotFoundException;

@Getter
@Setter
@MappedSuperclass
public class BaseDomain<T extends BaseDomain> implements Serializable {

    private static final long serialVersionUID = 1l;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date updatedAt;


    public boolean isNew() {
        return getId() == 0;
    }

    public String getFormattedDate(DateTimeFormatter format) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(getCreatedAt().toInstant(), ZoneId.systemDefault());
        return localDateTime.format(format);
    }

    public void validate(T t) {
        if (Objects.isNull(t)) {
            throw new NotFoundException(t.getClass().getSimpleName());
        }
    }

}
