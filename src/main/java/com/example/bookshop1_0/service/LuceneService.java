package com.example.bookshop1_0.service;

import com.example.bookshop1_0.Lucene.LuceneBean;
import com.example.bookshop1_0.entity.BooksEntity;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class LuceneService {
    @Autowired
    LuceneBean luceneBean;

    public void update(String bookId, BooksEntity book) {
        IndexWriter writer = null;
        Directory directory = luceneBean.getDirectory();
        Analyzer analyzer = luceneBean.getAnalyzer();
        IndexWriterConfig conf = new IndexWriterConfig(analyzer);
        conf.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        try {
            writer = new IndexWriter(directory, conf);

            Document doc = new Document();
            doc.add(new TextField("id", String.valueOf(book.getId()),
                    Field.Store.YES));
            doc.add(new TextField("author", book.getAuthor(),
                    Field.Store.YES));
            doc.add(new TextField("publisher", book.getPublisher(),
                    Field.Store.YES));
            doc.add(new TextField("bookname", book.getBookname(),
                    Field.Store.YES));
            writer.updateDocument(new Term("id", bookId), doc);
            writer.commit();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void add(BooksEntity book) {
        IndexWriter writer = null;
        Directory directory = luceneBean.getDirectory();
        Analyzer analyzer = luceneBean.getAnalyzer();
        IndexWriterConfig conf = new IndexWriterConfig(analyzer);
        conf.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        try {
            writer = new IndexWriter(directory, conf);
            Document doc = new Document();
            doc.add(new TextField("id", String.valueOf(book.getId()),
                    Field.Store.YES));
            doc.add(new TextField("author", book.getAuthor(),
                    Field.Store.YES));
            doc.add(new TextField("publisher", book.getPublisher(),
                    Field.Store.YES));
            doc.add(new TextField("bookname", book.getBookname(),
                    Field.Store.YES));
            writer.addDocument(doc);
            writer.commit();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void delete(String bookId) {
        IndexWriter writer = null;
        Directory directory = luceneBean.getDirectory();
        Analyzer analyzer = luceneBean.getAnalyzer();
        IndexWriterConfig conf = new IndexWriterConfig(analyzer);
        conf.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        try {
            writer = new IndexWriter(directory, conf);
            Term term = new Term("id", bookId);
            writer.deleteDocuments(term);
            writer.commit();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 清空回收站,也就是不能够恢复数据啦
     */
    public void forceMerge() {
        IndexWriter writer = null;
        Directory directory = luceneBean.getDirectory();
        Analyzer analyzer = luceneBean.getAnalyzer();
        IndexWriterConfig conf = new IndexWriterConfig(analyzer);
        conf.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        try {
            writer = new IndexWriter(directory, conf);
            writer.forceMergeDeletes();
            writer.commit();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteAll() {
        IndexWriter writer = null;
        Directory directory = luceneBean.getDirectory();
        Analyzer analyzer = luceneBean.getAnalyzer();
        IndexWriterConfig conf = new IndexWriterConfig(analyzer);
        conf.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        try {
            writer = new IndexWriter(directory, conf);
            writer.deleteAll();
            writer.commit();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<BooksEntity> query(String text) {
        List<BooksEntity> books = new ArrayList<>();
        String fields[] = {"author", "publisher", "bookname"};
        try {
            IndexWriter writer = null;
            Directory directory = luceneBean.getDirectory();
            Analyzer analyzer = luceneBean.getAnalyzer();
            IndexReader reader = DirectoryReader.open(directory);
            IndexSearcher searcher = new IndexSearcher(reader);
            QueryParser parser = new MultiFieldQueryParser(fields, analyzer);
            Query query = parser.parse(text);
            TopDocs topDocs = searcher.search(query, 50);
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            for (int i = 0; i < scoreDocs.length; i++) {
                int docId = scoreDocs[i].doc;
                Document doc = searcher.doc(docId);
                BooksEntity book = new BooksEntity();
                book.setId(Integer.parseInt(doc.get("id")));
                book.setAuthor(doc.get("author"));
                book.setPublisher(doc.get("publisher"));
                book.setBookname(doc.get("bookname"));
                books.add(book);
            }
            return books;
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return books;
        }
    }
}
