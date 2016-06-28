package model;

import android.test.AndroidTestCase;

import java.util.Calendar;
import java.util.Date;

import helpers.Indicator;
import libraries.DataBaseStructures;
import models.Search;
import unb.mdsgpp.qualcurso.QualCurso;

public class TestSearch extends AndroidTestCase {

    @Override
    public void testAndroidTestCaseSetupProperly() {
        super.testAndroidTestCaseSetupProperly();
        QualCurso.getInstance().setDatabaseName("database_test.sqlite3.db");
        DataBaseStructures database = new DataBaseStructures();
        database.dropDB();
        database.initDB();

        Calendar calendar = Calendar.getInstance();
        Search search = new Search();
        search.setIndicator(Indicator.getIndicatorByValue(Indicator.DEFAULT_INDICATOR));
        search.setMaxValue(10);
        search.setMinValue(5);
        search.setOption(1);
        search.setYear(2014);
        search.setDate(new Date(calendar.getTime().getTime()));
        search.save();

        search = new Search();
        search.setIndicator(Indicator.getIndicatorByValue(Indicator.DEFAULT_INDICATOR));
        search.setMaxValue(15);
        search.setMinValue(10);
        search.setOption(2);
        search.setYear(2014);
        search.setDate(new Date(calendar.getTime().getTime()));
        search.save();
    }

    public void testShouldBeOnlyTenSearchesSavedAtTheDatabase() {
        Calendar calendar = Calendar.getInstance();
        Search search = new Search();
        Indicator indicator = Indicator.getIndicatorByValue(Indicator.DEFAULT_INDICATOR);

        for(int i = 0; i < 12; i++) {
            search.setIndicator(indicator);
            search.setMaxValue(i * 10);
            search.setMinValue(i * 5);
            search.setOption(i);
            search.setYear(2000 + i);
            search.setDate(new Date(calendar.getTime().getTime()));
            search.save();
        }

        int expectedSearchCount = 10;

        assertEquals(expectedSearchCount, Search.count());
        testAndroidTestCaseSetupProperly();
    }

    public void testShouldGetSearch() {
        Search lastSearch = Search.last();

        assertEquals(lastSearch.getId(), Search.get(lastSearch.getId()).getId());
    }

    public void testShouldGetAllSearches() {
        int searchCount = Search.count();

        assertEquals(searchCount, Search.getAll().size());
    }

    public void testShouldGetFirstAndLastSearches() {
        Search firstSearch = Search.first();
        Search lastSearch = Search.last();

        assertEquals(10, firstSearch.getMaxValue());
        assertEquals(15, lastSearch.getMaxValue());
    }

    public void testShouldGetSearchBySomeValue() {
        Search search = Search.getWhere("max_value", "10", false).get(0);
        int searchValue = 10;

        assertEquals(searchValue, search.getMaxValue());
    }

    public void testShouldDeleteSearches() {
        while(Search.count() > 0)
            Search.last().delete();

        assertEquals(0, Search.count());
        testAndroidTestCaseSetupProperly();
    }
}
