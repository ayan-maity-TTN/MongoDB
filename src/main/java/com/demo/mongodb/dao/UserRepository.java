package com.demo.mongodb.dao;

import com.demo.mongodb.entity.User;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
  Optional<User> findByEmail(String email);

  Page<User> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
