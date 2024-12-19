/*
一个定义图书基本属性的类，包括：
图书编号：10位数字(编号具有唯一性)
图书名称：10位汉字
作者：10位汉字
图书类型：5位汉字
是否被借：0或1
被借阅人：10位汉字，在管内就写“无”
*/

public class BookAccount {
    private String bookId;
    private String bookName;
    private String author;
    private String bookType;
    private boolean isBorrowed;
    private String borrower;
    public BookAccount(String bookId,String bookName,String author,String bookType,boolean isBorrowed,String borrower)
    {
        this.bookId=bookId;
        this.bookName=bookName;
        this.author=author;
        this.bookType=bookType;
        this.isBorrowed=isBorrowed;
        this.borrower=borrower;
    }
    //Setters
    public String getBookId()
    {
        return bookId;
    }
    public String getBookName()
    {
        return bookName;
    }
    public String getAuthor()
    {
        return author;
    }
    public String getBookType(){
        return bookType;
    }
    public boolean getIsBorrowed()
    {
        return isBorrowed;
    }
    public String getBorrower(){
        return borrower;
    }
    //Getters
    public void setBookId(String bookId)
    {
        this.bookId=bookId;
    }
    public void setBookName(String bookName)
    {
        this.bookName=bookName;
    }
    public void setAuthor(String author){
        this.author=author;
    }
    public void setBookType(String bookType){
        this.bookType=bookType;
    }
    public void setIsBorrowed(boolean isBorrowed)
    {
        this.isBorrowed=isBorrowed;
    }
    public void setBorrower(String borrower){
        this.borrower=borrower;
    }
    public String toString(){
        return bookId+" "+bookName+" "+author+" "+bookType+" "+isBorrowed+" "+borrower;
    }
}
