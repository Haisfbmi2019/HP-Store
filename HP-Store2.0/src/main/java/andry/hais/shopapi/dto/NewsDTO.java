package andry.hais.shopapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class NewsDTO {

    private String mainPictureUrl;

    private String pictureUrl2;

    private String pictureUrl3;

    private String title;

    private String subtitle;

    private String pictureUrl4;

    private String pictureUrl5;

    private String text;

    public static NewsDTO of(String mainPictureUrl, String pictureUrl2, String pictureUrl3, String title,
                             String subtitle, String pictureUrl4, String pictureUrl5, String text) {
        return new NewsDTO(
                mainPictureUrl, pictureUrl2, pictureUrl3, title, subtitle, pictureUrl4, pictureUrl5, text);
    }

}
