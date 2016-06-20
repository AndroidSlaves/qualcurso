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
        Book idBook = new Book();
        idBook.setId(21);
        assertEquals(idBook.getId(), 21);
    }

    public void testSetIntegralTextBookMustFail(){
        Book idBook = new Book();
        idBook.setId(-10);
        assertNotSame(idBook.getId(), 10);
    }

    public void testGetIntegralTextBook(){
        Book idBook = new Book();
        idBook.setId(21);
        assertEquals(21, idBook.getId());
    }

    public void testGetIntegralTextBookMustFail(){
        Book idBook = new Book();
        idBook.setId(-155);
        assertNotSame(idBook.getId(), 551);
    }

    public void testSetChaptersBook(){
        Book idBook = new Book();
        idBook.setId(25);
        assertEquals(idBook.getId(), 25);
    }

    public void testSetChaptersBookMustFail(){
        Book idBook = new Book();
        idBook.setId(-11);
        assertNotSame(idBook.getId(), 13);
    }

    public void testGetChaptersBook(){
        Book idBook = new Book();
        idBook.setId(26);
        assertEquals(26, idBook.getId());
    }

    public void testGetChaptersBookMustFail(){
        Book idBook = new Book();
        idBook.setId(-175);
        assertNotSame(idBook.getId(), 571);
    }

    public void testSetCollectionsBook(){
        Book idBook = new Book();
        idBook.setId(21);
        assertEquals(idBook.getId(), 21);
    }

    public void testSetCollectionsBookMustFail(){
        Book idBook = new Book();
        idBook.setId(-33);
        assertNotSame(idBook.getId(), 13);
    }

    public void testGetCollectionsBook(){
        Book idBook = new Book();
        idBook.setId(31);
        assertEquals(31, idBook.getId());
    }

    public void testGetCollectionsBookMustFail(){
        Book idBook = new Book();
        idBook.setId(-185);
        assertNotSame(idBook.getId(), 871);
    }

    public void testSetEntriesBook(){
        Book idBook = new Book();
        idBook.setId(12);
        assertEquals(idBook.getId(), 12);
    }

    public void testSetEntriesBookMustFail(){
        Book idBook = new Book();
        idBook.setId(-42);
        assertNotSame(idBook.getId(), 28);
    }

    public void testGetEntriesBook(){
        Book idBook = new Book();
        idBook.setId(42);
        assertEquals(42, idBook.getId());
    }

    public void testGetEntriesBookMustFail(){
        Book idBook = new Book();
        idBook.setId(-223);
        assertNotSame(idBook.getId(), 441);
    }
}
