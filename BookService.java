import java.sql.SQLException;
import java.util.List;

public class BookService {
    private bookMegementDao bookDao;

    public BookService(bookMegement bookDao) {
        this.bookDao = bookDao;
    }

    // 创建新图书
    public void createBook(Book book) throws SQLException {
        bookDao.addBook(book);
    }

    // 删除图书
    public void deleteBook(int id) throws SQLException {
        bookDao.deleteBook(id);
    }

    // 更新图书信息
    public void updateBook(Book book) throws SQLException {
        bookDao.updateBook(book);
    }

    // 查询图书（根据标题查询）
    public List<Book> getBookByTitle(String title) throws SQLException {
        return bookDao.queryBook(title);
    }
}
