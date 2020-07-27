package andry.hais.shopapi.dto;

        import lombok.AllArgsConstructor;
        import lombok.Data;

        import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CommentDTO {
    //  @JsonAlias
    private String productId;

    //  @JsonAlias
    private String userEmail;

    //   @JsonAlias
    private BigDecimal productStars;

    //   @JsonAlias
    private String comment;

    //    @JsonAlias
    private String merits;

    //   @JsonAlias
    private String faults;
}
