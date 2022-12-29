package com.github.nbaravik.springuser.service;

import com.github.nbaravik.springuser.dto.StatusDTO;
import com.github.nbaravik.springuser.dto.UserDTO;
import com.github.nbaravik.springuser.dto.UserIdDTO;
import com.github.nbaravik.springuser.exception.UserNotFoundException;
import com.github.nbaravik.springuser.model.User;
import com.github.nbaravik.springuser.model.StatusMode;
import com.github.nbaravik.springuser.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final MongoOperations operations;

    @Autowired
    public UserServiceImpl(UserRepository repository, MongoOperations operations) {

        this.repository = repository;
        this.operations = operations;
    }

    @Override
    public UserIdDTO createUser(UserDTO userDto) {
        User user = User.builder().
                name(userDto.getName()).
                email(userDto.getEmail()).
                uri(userDto.getUri()).
                status(StatusMode.valueOf(userDto.getStatus())).build();
        User persisted = repository.save(user);
        return UserIdDTO.builder().id(persisted.getId()).build();
    }

    @Override
    public UserDTO getUserById(String id) {
        Optional<User> result = repository.findById(id);
        if (!result.isPresent()) {
            throw new UserNotFoundException(id);
        }
        User found = result.get();
        return UserDTO.builder().
                name(found.getName()).
                email(found.getEmail()).
                uri(found.getUri()).
                status(found.getStatus().toString()).build();
    }

    @Override
    public StatusDTO modifyStatus(String id, String status) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));

        Update update = new Update();
        update.set("status", status);
        User oldUser = operations.findAndModify(query, update,
                new FindAndModifyOptions().returnNew(false), User.class);
        if (oldUser == null) {
            throw new UserNotFoundException(id);
        }
        return StatusDTO.builder().
                id(id).
                status(status).
                previousStatus(oldUser.getStatus().toString()).build();
    }
}
