package com.kaishengit.hibernate;

import com.kaishengit.pojo.Post;
import com.kaishengit.pojo.PostContent;
import org.junit.Test;

public class OneToOne2Test extends BaseTestCase {

    @Test
    public void save() {
        Post post = new Post();
        post.setTitle("小说ABC");

        PostContent content = new PostContent();
        content.setContent("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");

        content.setPost(post);
        post.setPostContent(content);

        session.save(post);
        session.save(content);

    }

    @Test
    public void find() {
        Post post = (Post) session.get(Post.class,3);
        System.out.println(post.getTitle());
        //延迟加载
        System.out.println(post.getPostContent().getContent());
    }

}
