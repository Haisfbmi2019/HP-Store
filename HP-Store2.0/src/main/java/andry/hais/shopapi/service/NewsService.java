package andry.hais.shopapi.service;

import andry.hais.shopapi.dto.NewsDTO;
import andry.hais.shopapi.dto.ShortNewsDTO;
import andry.hais.shopapi.entity.News;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface NewsService {
    List<ShortNewsDTO> mergeNews(List<News> news);

    void addNews(List<NewsDTO> news);

    PageImpl<ShortNewsDTO> findAll(Pageable pageable);

    News findOne(Long newsId);

    News update(News news);

    News save(NewsDTO newsDTO);

    void delete(Long newsId);
}
