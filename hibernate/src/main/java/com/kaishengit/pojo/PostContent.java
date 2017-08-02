package com.kaishengit.pojo;

import javax.persistence.*;

@Entity
@Table(name = "post_content")
public class PostContent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String content;

    @ManyToOne
    @JoinColumn(name = "post_id",unique = true)
    private Post post;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
