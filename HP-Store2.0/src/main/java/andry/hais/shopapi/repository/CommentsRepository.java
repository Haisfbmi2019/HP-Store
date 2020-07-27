package andry.hais.shopapi.repository;

import andry.hais.shopapi.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentsRepository extends JpaRepository<Comment, String> {
    //List<Comment> findAllByUser(User user);
}
