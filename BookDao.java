
import java.util.List;
public interface BookDao {
    public void addBook(String bookName, String author, String publisher, String publishDate, String ISBN);

    public void deleteBook(String bookName);

    public void updateBook(String bookName, String author, String publisher, String publishDate, String ISBN);

    public void queryBook(String bookName);

    public void queryAllBook();

    public void getInventoryStats();
}
