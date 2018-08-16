package org.unibl.etf.sni.clientapp.util;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import org.unibl.etf.sni.clientapp.mysql.dto.DrivingLicenceCategoryDto;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
public class PdfDocument {
	private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
	private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.BOLD);

	private static void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}

	public static byte[] makePdf(org.unibl.etf.sni.clientapp.mysql.dto.Document doc) {
		byte[] pdf=null;
		try {
			PdfPTable table = new PdfPTable(2);
			table.setWidthPercentage(100);
			table.setWidths(new int[] { 70, 30 });
			table.addCell(createTextCell(doc));
			table.addCell(createCategoriesCell(doc.getCategories()));
			table.completeRow();
			ByteArrayOutputStream baos=new ByteArrayOutputStream();
			Document document = new Document(new Rectangle(350, 225));
			PdfWriter.getInstance(document, baos);
			document.open();
			document.add(table);
			document.close();
			pdf=baos.toByteArray();
			baos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return pdf;
	}

	public static PdfPCell createCategoriesCell(List<DrivingLicenceCategoryDto> categories)
			throws DocumentException, IOException {
		PdfPCell cell = new PdfPCell();
		if (categories != null) {
			Paragraph p = new Paragraph("Categories", catFont);
			addEmptyLine(p, 1);
			for (DrivingLicenceCategoryDto category : categories) {
				p.add(new Paragraph(category.getCategory().getCategory()+":" +ServiceUtility.sdf.format(category.getValidFrom()), subFont));
			}
			p.setAlignment(Element.ALIGN_JUSTIFIED);
			cell.addElement(p);
		}
		
		cell.setBorder(PdfPCell.NO_BORDER);
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		
		return cell;
	}

	public static PdfPCell createTextCell(org.unibl.etf.sni.clientapp.mysql.dto.Document document)
			throws DocumentException, IOException {
		PdfPCell cell = new PdfPCell();
		Paragraph p = new Paragraph(document.getCategory() + " #" + document.getSerial(), catFont);
		addEmptyLine(p, 1);
		p.add(new Paragraph("Name:" + document.getUser().getName(), subFont));
		p.add(new Paragraph("Surname:" + document.getUser().getSurname(), subFont));
		p.add(new Paragraph("Person ID:" + document.getUser().getPid(), subFont));
		p.add(new Paragraph("Date of birth:" + ServiceUtility.sdf.format(document.getUser().getDateOfBirth()), subFont));
		p.add(new Paragraph("Place of birth:" + document.getUser().getPlaceOfBirth(), subFont));
		p.add(new Paragraph("Sex:" + (document.getUser().getSex() ? "Male" : "Female"), subFont));
		p.add(new Paragraph("Residence:" + document.getUser().getResidence(), subFont));
		p.add(new Paragraph("Date of issue:" + ServiceUtility.sdf.format(document.getValidFrom()), subFont));
		p.add(new Paragraph("Valid until:" + ServiceUtility.sdf.format(document.getValidUntil()), subFont));
		p.setAlignment(Element.ALIGN_JUSTIFIED);
		cell.setBorder(PdfPCell.NO_BORDER);
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		cell.addElement(p);
		return cell;
	}

}
