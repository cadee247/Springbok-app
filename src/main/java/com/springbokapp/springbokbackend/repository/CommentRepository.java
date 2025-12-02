package com.springbokapp.springbokbackend.repository;

import com.springbokapp.springbokbackend.model.Comment;
import com.springbokapp.springbokbackend.model.Meme; // ✅ Add this import
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByMeme(Meme meme); // ✅ This is correct
}
