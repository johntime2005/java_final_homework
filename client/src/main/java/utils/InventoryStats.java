package utils;

public class InventoryStats {
    private int uniqueBooks;
    private int totalBooks;

    // Getters and Setters
    public int getUniqueBooks() {
        return uniqueBooks;
    }

    public void setUniqueBooks(int uniqueBooks) {
        this.uniqueBooks = uniqueBooks;
    }

    public int getTotalBooks() {
        return totalBooks;
    }

    public void setTotalBooks(int totalBooks) {
        this.totalBooks = totalBooks;
    }

    @Override
    public String toString() {
        return "InventoryStats{" +
                "uniqueBooks=" + uniqueBooks +
                ", totalBooks=" + totalBooks +
                '}';
    }
}
