package andry.hais.shopapi.service;

import andry.hais.shopapi.dto.CommentDTO;
import andry.hais.shopapi.entity.Comment;
import andry.hais.shopapi.entity.ProductInfo;
import andry.hais.shopapi.entity.User;

public interface CommentsService {
    //List<Comment> findAllByUserEmail(String email);

    void save(User user, ProductInfo productInfo, CommentDTO commentDTO);

    Comment update(Comment comment);
}
