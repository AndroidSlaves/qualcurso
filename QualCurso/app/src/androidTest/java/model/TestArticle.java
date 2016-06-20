package model;

import android.database.SQLException;
import android.test.AndroidTestCase;

import java.util.ArrayList;

import libraries.DataBaseStructures;
import models.Article;
import unb.mdsgpp.qualcurso.QualCurso;

public class TestArticle extends AndroidTestCase {

    @Override
    public void testAndroidTestCaseSetupProperly() {
        super.testAndroidTestCaseSetupProperly();
        QualCurso.getInstance().setDatabaseName("database_test.sqlite3.db");
        DataBaseStructures db = new DataBaseStructures();
        db.dropDB();
        db.initDB();
        Article article = new Article();
        article.setPublishedJournals(Integer.parseInt("1"));
        article.setPublishedConferenceProceedings(Integer.parseInt("2"));
        article.save();

        article = new Article();
        article.setPublishedJournals(Integer.parseInt("2"));
        article.setPublishedConferenceProceedings(Integer.parseInt("8"));
        article.save();
    }

    @Override
    protected void tearDown() throws Exception{
        super.tearDown();
    }

    public void testShouldCreateNewArticleOnDataBase() throws ClassNotFoundException, SQLException {
        int initialCount = Article.count();

        Article article = new Article();
        article.setPublishedJournals(Integer.parseInt("0"));
        article.setPublishedConferenceProceedings(Integer.parseInt("0"));

        assertTrue(article.save());
        assertEquals(initialCount, Article.count() - 1);
        article.delete();
    }

    public void testShouldCountArticleOnDataBase() throws ClassNotFoundException, SQLException {
        int initialCount = Article.count();
        Article article = new Article();

        article.setPublishedJournals(Integer.parseInt("0"));
        article.setPublishedConferenceProceedings(Integer.parseInt("0"));
        article.save();

        assertEquals(initialCount+1, Article.count());
        assertEquals(Article.getAll().size(), Article.count());
        article.delete();
    }

    public void testShouldGetArticleOnDataBase() throws ClassNotFoundException, SQLException {
        Article articleOne = new Article();

        articleOne.setPublishedJournals(Integer.parseInt("0"));
        articleOne.setPublishedConferenceProceedings(Integer.parseInt("0"));
        articleOne.save();

        Article articleTwo = Article.get(Article.last().getId());

        assertEquals(articleOne.getPublishedJournals(), articleTwo.getPublishedJournals());
        assertEquals(articleOne.getPublishedConferenceProceedings(),
                articleTwo.getPublishedConferenceProceedings());

        articleOne.delete();
        articleTwo.delete();
    }

    public void testShouldGetAllArticleOnDataBase() throws ClassNotFoundException, SQLException {
        int totalArticles = Article.count();

        assertEquals(totalArticles, Article.getAll().size());
        assertEquals("", Article.first().get("test"));
    }

    public void testShouldGetTheFirstArticleOnDataBase() throws ClassNotFoundException, SQLException {
        Article firstArticle = Article.first();

        assertEquals(firstArticle.getPublishedJournals(),
                Article.getAll().get(0).getPublishedJournals());
        assertEquals(firstArticle.getPublishedConferenceProceedings(),
                Article.getAll().get(0).getPublishedConferenceProceedings());
    }

    public void testShouldGetTheLastArticleOnDataBase() throws ClassNotFoundException, SQLException {
        Article lastArticle = Article.last();
        ArrayList<Article> articleList = Article.getAll();

        assertEquals(lastArticle.getPublishedJournals(),
                articleList.get(articleList.size()-1).getPublishedJournals());
        assertEquals(lastArticle.getPublishedConferenceProceedings(),
                articleList.get(articleList.size()-1).getPublishedConferenceProceedings());
    }

    
}
