package andry.hais.shopapi.service.impl;

import andry.hais.shopapi.dto.CommentDTO;
import andry.hais.shopapi.entity.Comment;
import andry.hais.shopapi.entity.ProductInfo;
import andry.hais.shopapi.entity.User;
import andry.hais.shopapi.repository.CommentsRepository;
import andry.hais.shopapi.repository.ProductInfoRepository;
import andry.hais.shopapi.repository.UserRepository;
import andry.hais.shopapi.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentsServiceImpl implements CommentsService {
    @Autowired
    CommentsRepository commentsRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductInfoRepository productInfoRepository;

   /* @Override
    public List<Comment> findAllByUserEmail(String email) {
        User user = userRepository.findByEmail(email);
        return commentsRepository.findAllByUser(user);
    }*/

    @Override
    @Transactional
    public void save(User user, ProductInfo productInfo, CommentDTO commentDTO) {
        Comment comment = Comment.of(user, productInfo, commentDTO);
        commentsRepository.save(comment);

        //user.getComments().add(comment);
        productInfo.getComments().add(comment);
        productInfoRepository.save(productInfo);
        //userRepository.save(user);
    }

    @Override
    public Comment update(Comment comment) {
        return null;
    }
}
