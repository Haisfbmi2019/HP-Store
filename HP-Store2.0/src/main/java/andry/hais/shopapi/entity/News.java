package andry.hais.shopapi.entity;

import andry.hais.shopapi.dto.NewsDTO;
import andry.hais.shopapi.dto.ShortNewsDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class News {

    @Id
    @GeneratedValue
    private Long newsId;

    @NotNull
    private String mainPictureUrl;

    @NotNull
    private String pictureUrl2;

    @NotNull
    private String pictureUrl3;

    @NotNull
    private String title;

    @NotNull
    @Column(length = 1000)
    private String subtitle;

    @NotNull
    private String pictureUrl4;

    @NotNull
    private String pictureUrl5;

    @NotNull
    @Column(length = 1000)
    private String text;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @UpdateTimestamp
    private LocalDateTime updateTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @CreationTimestamp
    private LocalDateTime createTime;

    public static News of(String mainPictureUrl, String pictureUrl2, String pictureUrl3, String title,
                          String subtitle, String pictureUrl4, String pictureUrl5, String text) {
        return new News(null, mainPictureUrl, pictureUrl2, pictureUrl3, title, subtitle, pictureUrl4,
                pictureUrl5, text, null, null);
    }

    public static News fromDTO(NewsDTO newsDTO) {
        return News.of(newsDTO.getMainPictureUrl(), newsDTO.getPictureUrl2(), newsDTO.getPictureUrl3(), newsDTO.getTitle(),
                newsDTO.getSubtitle(), newsDTO.getPictureUrl4(), newsDTO.getPictureUrl5(), newsDTO.getText());
    }

    public static ShortNewsDTO toShortNewsDTO(News news) {
        return ShortNewsDTO.of(news.getNewsId(), news.getMainPictureUrl(), news.getTitle(), news.getSubtitle()
        );
    }
}
