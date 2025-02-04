package com.faridroid.Joblisting.service;

import com.faridroid.Joblisting.data.PostSearchRepository;
import com.faridroid.Joblisting.model.Post;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.LimitOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class PostPostSearchImpl implements PostSearchRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Post> findByText(String query, int limit) {

        final List<Post> post = new ArrayList<>();
        // $search stage (using raw BSON document)
        Document searchStage = new Document("$search",
                new Document("index", "default")
                        .append("text", new Document("query", query)
                                .append("path", Arrays.asList("techs", "desc", "profile"))));

        //Sort stage
        SortOperation sortOperation = Aggregation.sort(Sort.by(Sort.Direction.DESC, "exp"));

        //Limit
        LimitOperation limitOperation = Aggregation.limit(Math.min(limit,1000));

        // Aggregation pipeline
        Aggregation aggregation = Aggregation.newAggregation(
                context -> searchStage,
                sortOperation,
                limitOperation
        );

        return mongoTemplate.aggregate(aggregation, "JobPost", Post.class).getMappedResults();
    }
}
