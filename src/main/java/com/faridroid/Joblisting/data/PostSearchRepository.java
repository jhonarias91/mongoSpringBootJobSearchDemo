package com.faridroid.Joblisting.data;

import com.faridroid.Joblisting.model.Post;
import org.springframework.stereotype.Service;

import java.util.List;

public interface PostSearchRepository {

    List<Post>  findByText(String query, int limit);
}
