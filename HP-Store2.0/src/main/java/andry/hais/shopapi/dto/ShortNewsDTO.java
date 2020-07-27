package andry.hais.shopapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShortNewsDTO {
    private Long newsId;

    private String pictureUrl;

    private String title;

    private String subtitle;

    public static ShortNewsDTO of(Long newsId, String pictureUrl, String title, String subtitle) {
        return new ShortNewsDTO(
                newsId, pictureUrl, title, subtitle);
    }
}
