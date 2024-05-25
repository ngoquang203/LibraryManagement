package com.example.librarymanagement.Datamanagement;

public class BookCatalogings {
    private String IdBookCataloging;
    private String Heading;
    private String Author;
    private String ISBN;
    private String Publishing;
    private String Genre;

    public BookCatalogings(String idBookCataloging, String heading, String author, String ISBN, String publishing, String genre) {
        IdBookCataloging = idBookCataloging;
        Heading = heading;
        Author = author;
        this.ISBN = ISBN;
        Publishing = publishing;
        Genre = genre;
    }


    public String getIdBookCataloging() {
        return IdBookCataloging;
    }

    public void setIdBookCataloging(String idBookCataloging) {
        IdBookCataloging = idBookCataloging;
    }

    public String getHeading() {
        return Heading;
    }

    public void setHeading(String heading) {
        Heading = heading;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getPublishing() {
        return Publishing;
    }

    public void setPublishing(String publishing) {
        Publishing = publishing;
    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }
}
