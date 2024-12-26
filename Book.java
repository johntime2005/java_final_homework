import javafx.beans.property.*;

public class Book {
    private final IntegerProperty id;
    private final StringProperty bookName;
    private final StringProperty author;
    private final StringProperty publisher;
    private final StringProperty publishDate;
    private final StringProperty isbn;
    private final IntegerProperty quantity;

    public Book(int id, String bookName, String author, String publisher, 
                String publishDate, String isbn, int quantity) {
        this.id = new SimpleIntegerProperty(id);
        this.bookName = new SimpleStringProperty(bookName);
        this.author = new SimpleStringProperty(author);
        this.publisher = new SimpleStringProperty(publisher);
        this.publishDate = new SimpleStringProperty(publishDate);
        this.isbn = new SimpleStringProperty(isbn);
        this.quantity = new SimpleIntegerProperty(quantity);
    }

    // Getters for properties
    public IntegerProperty idProperty() { return id; }
    public StringProperty bookNameProperty() { return bookName; }
    public StringProperty authorProperty() { return author; }
    public StringProperty publisherProperty() { return publisher; }
    public StringProperty publishDateProperty() { return publishDate; }
    public StringProperty isbnProperty() { return isbn; }
    public IntegerProperty quantityProperty() { return quantity; }

    // Standard getters
    public int getId() { return id.get(); }
    public String getBookName() { return bookName.get(); }
    public String getAuthor() { return author.get(); }
    public String getPublisher() { return publisher.get(); }
    public String getPublishDate() { return publishDate.get(); }
    public String getIsbn() { return isbn.get(); }
    public int getQuantity() { return quantity.get(); }

    @Override
    public String toString() {
        return String.format("Book{id=%d, name='%s', author='%s', publisher='%s', date='%s', ISBN='%s', quantity=%d}",
                getId(), getBookName(), getAuthor(), getPublisher(), getPublishDate(), getIsbn(), getQuantity());
    }
}
