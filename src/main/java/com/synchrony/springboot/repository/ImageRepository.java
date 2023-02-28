package com.synchrony.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.synchrony.springboot.model.Image;
import com.synchrony.springboot.model.User;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer>  {
    // User getUserFromToken(String token);
}
