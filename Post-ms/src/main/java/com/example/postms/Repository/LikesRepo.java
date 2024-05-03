package com.example.postms.Repository;


import com.example.postms.Model.Likes;
import com.example.postms.Model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikesRepo extends JpaRepository<Likes,Long> {
    Likes findByPostAndUserId(Post post, Long id);
}
