package model;
import com.fasterxml.jackson.annotation.JsonProperty;
public class UserBook {
    private int userId;
    private int bookId;
    private String borrowDate;
    private String returnDate;

    // 构造函数
    public UserBook(@JsonProperty("user_id") int userId,
                    @JsonProperty("book_id") int bookId,
                    @JsonProperty("borrow_date") String borrowDate,
                    @JsonProperty("return_date") String returnDate) {
        this.userId = userId;
        this.bookId = bookId;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }

    // Getter 和 Setter 方法
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(String borrowDate) {
        this.borrowDate = borrowDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }
}
