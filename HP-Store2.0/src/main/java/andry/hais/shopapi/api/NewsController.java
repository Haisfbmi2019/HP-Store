package andry.hais.shopapi.api;

import andry.hais.shopapi.dto.NewsDTO;
import andry.hais.shopapi.dto.ShortNewsDTO;
import andry.hais.shopapi.entity.News;
import andry.hais.shopapi.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
public class NewsController {
    @Autowired
    NewsService newsService;

    @GetMapping("/news")
    public PageImpl<ShortNewsDTO> findAll(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                          @RequestParam(value = "size", defaultValue = "5") Integer size) {
        PageRequest request = PageRequest.of(page - 1, size);
        return newsService.findAll(request);
    }

    @GetMapping("/news/{newsId}")
    public News showOne(@PathVariable("newsId") Long newsId) {
        return newsService.findOne(newsId);
    }

    @PostMapping("/admin/news/new")
    public ResponseEntity create(@Valid @RequestBody NewsDTO newsDTO,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult);
        }

        return ResponseEntity.ok(newsService.save(newsDTO));
    }

    @PutMapping("/admin/news/{id}/edit")
    public ResponseEntity edit(@PathVariable("id") Long newsId,
                               @Valid @RequestBody News news,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult);
        }
        if (!newsId.equals(news.getNewsId())) {
            return ResponseEntity.badRequest().body("Id Not Matched");
        }

        return ResponseEntity.ok(newsService.update(news));
    }

    @DeleteMapping("/admin/news/{id}/delete")
    public ResponseEntity delete(@PathVariable("id") Long newsId) {
        newsService.delete(newsId);
        return ResponseEntity.ok().build();
    }
}
