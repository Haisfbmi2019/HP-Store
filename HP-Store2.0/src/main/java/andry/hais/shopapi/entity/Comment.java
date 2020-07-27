package andry.hais.shopapi.entity;

import andry.hais.shopapi.dto.CommentDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue
    private Long commentId;

    @ManyToOne
    // @JsonIgnore
    private ProductInfo productInfo;

    @ManyToOne
    // @JsonIgnore
    private User user;

    @Min(0)
    @Max(5)
    private BigDecimal productStars;

    @Column(length = 1000)
    private String comment;

    @Column(length = 1000)
    private String merits;

    @Column(length = 1000)
    private String faults;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @CreationTimestamp
    private LocalDateTime createTime;

    public Comment(BigDecimal productStars, String comment, String merits, String faults) {
        this.productStars = productStars;
        this.comment = comment;
        this.merits = merits;
        this.faults = faults;
    }

    public static Comment of(User user, ProductInfo productInfo, CommentDTO commentDTO) {
        return new Comment(null, productInfo, user, commentDTO.getProductStars(), commentDTO.getComment(),
                commentDTO.getMerits(), commentDTO.getFaults(), null);
    }

    /*public static Comment of(CommentDTO commentDTO) {
        return new Comment(commentDTO.getProductStars(), commentDTO.getComment(),
                commentDTO.getMerits(), commentDTO.getFaults());
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment1 = (Comment) o;
        return Objects.equals(commentId, comment1.commentId) &&
                Objects.equals(productStars, comment1.productStars) &&
                Objects.equals(comment, comment1.comment) &&
                Objects.equals(merits, comment1.merits) &&
                Objects.equals(faults, comment1.faults) &&
                Objects.equals(createTime, comment1.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commentId, productStars, comment, merits, faults, createTime);
    }
}
