package com.faridroid.Joblisting.data;

import com.faridroid.Joblisting.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostRepository extends MongoRepository<Post, String> {



}
