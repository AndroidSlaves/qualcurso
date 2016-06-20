package model;

import android.test.AndroidTestCase;
import models.Book;

public class TestBook extends AndroidTestCase {


    public void testSetIdBook(){
        Book idBook = new Book();
        idBook.setId(10);
        assertEquals(idBook.getId(), 10);
    }

    public void testSetIdBookMustFail(){
        Book idBook = new Book();
        idBook.setId(-1);
        assertNotSame(idBook.getId(), 10);
    }

    public void testGetIdBook(){
        Book idBook = new Book();
        idBook.setId(15);
        assertEquals(15, idBook.getId());
    }

    public void testGetIdBookMustFail(){
        Book idBook = new Book();
        idBook.setId(-121);
        assertNotSame(112, idBook.getId());
    }

    public void testSetIntegralTextBook(){
        Book integralBook = new Book();
        integralBook.setIntegralText(21);
        assertEquals(integralBook.getIntegralText(), 21);
    }

    public void testSetIntegralTextBookMustFail(){
        Book integralBook = new Book();
        integralBook.setIntegralText(-10);
        assertNotSame(integralBook.getIntegralText(), 10);
    }

    public void testGetIntegralTextBook(){
        Book integralBook = new Book();
        integralBook.setIntegralText(21);
        assertEquals(21, integralBook.getIntegralText());
    }

    public void testGetIntegralTextBookMustFail(){
        Book integralBook = new Book();
        integralBook.setIntegralText(-155);
        assertNotSame(integralBook.getIntegralText(), 551);
    }

    public void testSetChaptersBook(){
        Book chaptersBook = new Book();
        chaptersBook.setChapters(25);
        assertEquals(chaptersBook.getChapters(), 25);
    }

    public void testSetChaptersBookMustFail(){
        Book chaptersBook = new Book();
        chaptersBook.setChapters(-11);
        assertNotSame(chaptersBook.getChapters(), 13);
    }

    public void testGetChaptersBook(){
        Book chaptersBook = new Book();
        chaptersBook.setChapters(26);
        assertEquals(26, chaptersBook.getChapters());
    }

    public void testGetChaptersBookMustFail(){
        Book chaptersBook = new Book();
        chaptersBook.setChapters(-175);
        assertNotSame(chaptersBook.getChapters(), 571);
    }

    public void testSetCollectionsBook(){
        Book collectionsBook = new Book();
        collectionsBook.setCollections(21);
        assertEquals(collectionsBook.getCollections(), 21);
    }

    public void testSetCollectionsBookMustFail(){
        Book collectionsBook = new Book();
        collectionsBook.setCollections(-33);
        assertNotSame(collectionsBook.getCollections(), 13);
    }

    public void testGetCollectionsBook(){
        Book collectionsBook = new Book();
        collectionsBook.setCollections(31);
        assertEquals(31, collectionsBook.getCollections());
    }

    public void testGetCollectionsBookMustFail(){
        Book collectionsBook = new Book();
        collectionsBook.setCollections(-185);
        assertNotSame(collectionsBook.getCollections(), 871);
    }

    public void testSetEntriesBook(){
        Book entriesBook = new Book();
        entriesBook.setEntries(12);
        assertEquals(entriesBook.getEntries(), 12);
    }

    public void testSetEntriesBookMustFail(){
        Book entriesBook = new Book();
        entriesBook.setEntries(-42);
        assertNotSame(entriesBook.getEntries(), 28);
    }

    public void testGetEntriesBook(){
        Book entriesBook = new Book();
        entriesBook.setEntries(42);
        assertEquals(42, entriesBook.getEntries());
    }

    public void testGetEntriesBookMustFail(){
        Book entriesBook = new Book();
        entriesBook.setEntries(-223);
        assertNotSame(entriesBook.getEntries(), 441);
    }

    public void testSaveBook(){
        Book entriesBook = new Book();
        boolean result = entriesBook.save();
        assertTrue(result);
    }

}
