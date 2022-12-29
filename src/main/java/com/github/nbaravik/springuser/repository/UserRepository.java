package com.github.nbaravik.springuser.repository;

import com.github.nbaravik.springuser.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
