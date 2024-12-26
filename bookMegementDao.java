import java.sql.SQLException;
import java.util.List;

public interface bookMegementDao {
    void create() throws SQLException;
    void addBook(Book book) throws SQLException;
    void deleteBook(int id) throws SQLException;
    void updateBook(Book book) throws SQLException;
    List<Book> queryBook(String title) throws SQLException;
}
