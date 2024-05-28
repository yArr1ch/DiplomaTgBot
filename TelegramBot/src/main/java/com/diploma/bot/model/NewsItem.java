package com.diploma.bot.model;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "news_item")
public class NewsItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "url", nullable = false)
    private String url;
}
