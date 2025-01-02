package utils;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import model.UserBook;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.io.IOException;

public class PDFUtils {

    public static void exportUserBook(List<UserBook> userBooks, File file) throws FileNotFoundException, IOException {
        PdfWriter writer = new PdfWriter(file);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        // 添加标题
        document.add(new Paragraph("Annualreport"));

        // 创建表格
        float[] columnWidths = { 15, 15, 15, 15 }; // 列宽
        Table table = new Table(columnWidths);
        table.addCell(new Cell().add(new Paragraph("user_id")));
        table.addCell(new Cell().add(new Paragraph("book_id")));
        table.addCell(new Cell().add(new Paragraph("borrow_date")));
        table.addCell(new Cell().add(new Paragraph("return_date")));

        // 添加用户借书记录到表格
        for (UserBook userBook : userBooks) {
            table.addCell(new Cell().add(new Paragraph(String.valueOf(userBook.getUserId()))));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(userBook.getBookId()))));
            table.addCell(new Cell().add(new Paragraph(userBook.getBorrowDate())));
            table.addCell(new Cell().add(new Paragraph(userBook.getReturnDate())));
        }

        // 将表格添加到文档中
        document.add(table);

        // 关闭文档
        document.close();
    }
}