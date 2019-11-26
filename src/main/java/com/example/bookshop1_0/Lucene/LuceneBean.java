package com.example.bookshop1_0.Lucene;

import com.example.bookshop1_0.entity.BooksEntity;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class LuceneBean {
    private String indexPath;
    private Directory directory;
    private Analyzer analyzer;
    private IndexWriterConfig conf;

    public Analyzer getAnalyzer() {
        return analyzer;
    }

    public void setAnalyzer(Analyzer analyzer) {
        this.analyzer = analyzer;
        this.conf = new IndexWriterConfig(this.analyzer);
        conf.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
    }

    public Directory getDirectory() {
        return directory;
    }

    public String getIndexPath() {
        return indexPath;
    }

    public void setIndexPath(String indexPath) {
        try {
            this.indexPath = indexPath;
            this.directory = FSDirectory.open(new File(this.indexPath).toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createIndex(List<BooksEntity> list) {
        try {
            IndexWriter indexWriter = new IndexWriter(directory, conf);
            for (BooksEntity book : list) {
                Document doc = new Document();
                doc.add(new TextField("id", String.valueOf(book.getId()),
                        Field.Store.YES));
                doc.add(new TextField("author", book.getAuthor(),
                        Field.Store.YES));
                doc.add(new TextField("publisher", book.getPublisher(),
                        Field.Store.YES));
                doc.add(new TextField("bookname", book.getBookname(),
                        Field.Store.YES));
                // 将doc添加到索引中
                indexWriter.addDocument(doc);
            }
            indexWriter.commit();
            indexWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
