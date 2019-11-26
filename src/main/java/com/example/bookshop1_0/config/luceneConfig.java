package com.example.bookshop1_0.config;

import com.example.bookshop1_0.Lucene.LuceneBean;
import com.example.bookshop1_0.dao.BookMapper;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class luceneConfig {
    @Autowired
    BookMapper bookMapper;
    @Bean
    public LuceneBean luceneBean(){
        LuceneBean luceneBean = new LuceneBean();
        luceneBean.setIndexPath("D:\\bookshopPro\\bookshop1_0\\src\\main\\resources\\index");
        luceneBean.setAnalyzer(new SmartChineseAnalyzer());
        luceneBean.createIndex(bookMapper.queryAll());
        return luceneBean;
    }
}
