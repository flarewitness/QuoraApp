package com.flare.quora.repositories;

import com.flare.quora.models.Like;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface LikeRepository extends ReactiveMongoRepository<Like, String> {

}
