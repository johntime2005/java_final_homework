package utils;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import model.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class PDFUtils {

    public static void exportUsers(List<User> users, File file) throws FileNotFoundException {
        PdfWriter writer = new PdfWriter(file);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        // 添加标题
        document.add(new Paragraph("用户数据"));

        // 创建表格
        float[] columnWidths = {2, 2, 3, 2}; // 列宽
        Table table = new Table(columnWidths);
        table.addCell(new Cell().add(new Paragraph("ID")));
        table.addCell(new Cell().add(new Paragraph("姓名")));
        table.addCell(new Cell().add(new Paragraph("邮箱")));
        table.addCell(new Cell().add(new Paragraph("电话")));

        

        // 将表格添加到文档中
        document.add(table);

        // 关闭文档
        document.close();
    }
}
