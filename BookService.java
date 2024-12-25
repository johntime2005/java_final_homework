import java.sql.SQLException;
import java.util.List;
public class BookService {
    private final bookMegementDao bookdao;

    public BookService(bookMegementDao bookdao) {
        this.bookdao = bookdao;
    }

    public void addBook(String bookName, String author, String publisher, String publishDate, String ISBN) {
        try {
            bookdao.addBook(bookName, author, publisher, publishDate, ISBN);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
        public void deleteBook (String bookName){
            try {
                bookdao.deleteBook(bookName);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        public void updateBook (String bookName, String author, String publisher, String publishDate, String ISBN){
            try {
                bookdao.updateBook(bookName, author, publisher, publishDate, ISBN);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        public List<Book> queryBook (String bookName){
            try {
                return bookdao.queryBook(bookName);
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }

    public List<Book> queryAllBooks() {
        try {
            return bookdao.queryAllBooks();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public InventoryStats getInventoryStats() {
        try {
            return bookdao.getInventoryStats();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}