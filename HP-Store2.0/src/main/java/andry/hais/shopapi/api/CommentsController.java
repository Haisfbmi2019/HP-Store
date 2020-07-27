package andry.hais.shopapi.api;


import andry.hais.shopapi.dto.CommentDTO;
import andry.hais.shopapi.entity.Comment;
import andry.hais.shopapi.entity.ProductInfo;
import andry.hais.shopapi.entity.User;
import andry.hais.shopapi.service.CommentsService;
import andry.hais.shopapi.service.ProductService;
import andry.hais.shopapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/comments")
public class CommentsController {
    @Autowired
    ProductService productService;
    @Autowired
    UserService userService;
    @Autowired
    CommentsService commentsService;

    @GetMapping("")
    public List<Comment> showComments() {

        return productService.findAll();
    }

    @PostMapping("/add")
    public boolean add(@RequestBody CommentDTO commentDTO, Principal principal) {
        User user = userService.findOne(principal.getName());
        ProductInfo productInfo = productService.findOne(commentDTO.getProductId());
        try {
            commentsService.save(user, productInfo, commentDTO);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /*@GetMapping("/news/{newsId}")
    public News showOne(@PathVariable("newsId") Long newsId) {
        return newsService.findOne(newsId);
    }

    @PostMapping("/admin/news/new")
    public ResponseEntity create(@Valid @RequestBody News news,
                                 BindingResult bindingResult) {
        News newsIdExists = newsService.findOne(news.getNewsId());
        if (newsIdExists != null) {
            bindingResult
                    .rejectValue("newsId", "error.news",
                            "There is already a news with the code provided");
        }
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult);
        }

        return ResponseEntity.ok(newsService.save(news));
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
    }*/
}
