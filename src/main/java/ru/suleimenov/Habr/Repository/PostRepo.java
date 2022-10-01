package ru.suleimenov.Habr.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.suleimenov.Habr.entity.Post;
import ru.suleimenov.Habr.entity.Tag;

import java.util.List;

@Repository
public interface PostRepo extends JpaRepository<Post,Long> {
  //  List<Post> findAll(Pageable pageable);
//    @Query(value = "select p from p posts order by ")
    public List findAll();

  @Query(value = "SELECT * FROM posts order by likes desc",nativeQuery = true)
  List<Post> findAllByNativeQuery();
  @Query(value = "select * from posts, post_tags where post_id=posts.id and tag_id=:id order by likes desc", nativeQuery = true)
  List<Post> findAllByTagOrderByLikeCount(Long id);

}
