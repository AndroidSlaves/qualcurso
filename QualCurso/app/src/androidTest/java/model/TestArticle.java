package model;

import android.test.AndroidTestCase;

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

    
}
