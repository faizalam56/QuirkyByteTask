package com.example.faiz.quirkybytetask;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by faiz on 25/6/18.
 */

public class DemoPost implements Serializable{
    public String postId;
    public String postTitle;
    public String postDateTime;
    public String postSource;
    public ArrayList<PostMedia> postMedia;
    public String postLink;
    public String postIsArticle;
    public String postDescription;
    public String postAuthor;
    public String postContent;

    public class PostMedia implements Serializable{
        public String MediaUrl;
        public String MediaType;
    }
}

