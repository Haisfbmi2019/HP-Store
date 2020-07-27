package andry.hais.shopapi.service.impl;

import andry.hais.shopapi.dto.NewsDTO;
import andry.hais.shopapi.dto.ShortNewsDTO;
import andry.hais.shopapi.entity.News;
import andry.hais.shopapi.enums.ResultEnum;
import andry.hais.shopapi.exception.MyException;
import andry.hais.shopapi.repository.NewsRepository;
import andry.hais.shopapi.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {
    @Autowired
    NewsRepository newsRepository;

    @Override
    public List<ShortNewsDTO> mergeNews(List<News> news) {
        List<ShortNewsDTO> list = new ArrayList<>();
        news.forEach((x) -> {
            ShortNewsDTO newsDTO = News.toShortNewsDTO(x);
            //
            list.add(newsDTO);
        });
        return list;
    }

    @Override
    public void addNews(List<NewsDTO> news) {
        news.forEach((x) -> {
            News newNews = News.fromDTO(x);
            newsRepository.save(newNews);
        });
    }

    @Override
    public PageImpl<ShortNewsDTO> findAll(Pageable pageable) {
        Page<News> page = newsRepository.findAll(pageable);
        return new PageImpl<>(mergeNews(page.getContent()), pageable, page.getTotalElements());
    }

    @Override
    public News findOne(Long newsId) {
        return newsRepository.findByNewsId(newsId);
    }

    @Override
    public News update(News news) {
        News oldNews = newsRepository.findByNewsId(news.getNewsId());
        oldNews.setMainPictureUrl(news.getMainPictureUrl());
        oldNews.setPictureUrl2(news.getPictureUrl2());
        oldNews.setPictureUrl3(news.getPictureUrl3());
        oldNews.setTitle(news.getTitle());
        oldNews.setSubtitle(news.getSubtitle());
        oldNews.setPictureUrl4(news.getPictureUrl2());
        oldNews.setPictureUrl5(news.getPictureUrl3());
        oldNews.setText(news.getText());


        return newsRepository.save(oldNews);
    }

    @Override
    public News save(NewsDTO newsDTO) {
        return newsRepository.save(News.fromDTO(newsDTO));
    }

    @Override
    public void delete(Long newsId) {
        News news = findOne(newsId);
        if (news == null) throw new MyException(ResultEnum.NEWS_NOT_EXIST);
        newsRepository.delete(news);
    }
}
