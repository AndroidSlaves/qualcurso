package model;

import android.test.AndroidTestCase;

import libraries.DataBaseStructures;
import models.Institution;
import unb.mdsgpp.qualcurso.QualCurso;

public class TestInstitution extends AndroidTestCase {
    @Override
    public void testAndroidTestCaseSetupProperly() {
        super.testAndroidTestCaseSetupProperly();
        QualCurso.getInstance().setDatabaseName("database_test.sqlite3.db");
        DataBaseStructures db = new DataBaseStructures();
        db.dropDB();
        db.initDB();
        Institution institution = new Institution();
        institution.setAcronym("one");
        institution.save();
        institution = new Institution();
        institution.setAcronym("two");
        institution.save();
    }
    @Override
    protected void setUp() {

    }

    @Override
    protected void tearDown() {
    }

    public void testShouldCreateNewInstitutionOnDataBase() {
        Institution institution = new Institution();
        institution.setAcronym("three");
        institution.save();
        assertEquals("three", Institution.last().getAcronym());
        institution = Institution.last();
        institution.delete();
    }

    public void testShouldCountAllInstitutionsOnDataBase() {
        int initialCount = Institution.count();
        Institution institution = new Institution();
        institution.setAcronym("other");
        institution.save();
        assertEquals(initialCount+1, Institution.count());
        assertEquals(Institution.getAll().size(), Institution.count());
        institution.delete();
    }


    public void testShouldGetAllInstitutionsOnDataBase() {
        assertEquals("one", Institution.getAll().get(0).getAcronym());
        assertEquals("two", Institution.getAll().get(1).getAcronym());
        assertEquals("", Institution.first().get("test"));
    }
}
