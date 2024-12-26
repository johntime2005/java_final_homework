import java.sql.SQLException;
import java.util.List;

public class BookService {
    private bookMegementDao bookDao;

    public BookService(bookMegementDao bookDao) {
        this.bookDao = bookDao;
    }

    public List<Book> getBookByTitle(String title) throws SQLException {
        return bookDao.getBooksByTitle(title);
    }

    public void addBook(Book book) throws SQLException {
        bookDao.addBook(book);
    }
}
