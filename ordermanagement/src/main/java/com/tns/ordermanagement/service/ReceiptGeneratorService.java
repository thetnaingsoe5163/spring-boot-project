package com.tns.ordermanagement.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;

import org.springframework.stereotype.Service;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.tns.ordermanagement.controller.commondto.Receipt;

import jakarta.servlet.ServletContext;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReceiptGeneratorService {
	
	private final ServletContext context;
	
	public void generateReceipt(Receipt receipt) {
		
		var folder = context.getRealPath("/resources/receipts/");
		
		var path = Path.of(folder);
		path = path.resolve(folder.concat("%s.pdf".formatted(receipt.getTrxId())));
		
		String html = getHtmlVersion(receipt);
		
		try(OutputStream w = new FileOutputStream(path.toFile())) {
			var pdfRenderer = new PdfRendererBuilder();
			pdfRenderer.withHtmlContent(html, null);
			pdfRenderer.toStream(w);
			pdfRenderer.run();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private String getHtmlVersion(Receipt receipt) {
		var html = new StringBuilder();
		var font = Path.of(context.getRealPath("/resources/font/NotoSansMyanmar-Regular.ttf")).toUri().toString();
		
		
	    var fontCss = String.format("""
	            @font-face {
	                font-family: 'MyanmarFont';
	                src: url('%s');
	            }
	            """, font);
	    
	    System.out.println(font);

	        // Append the HTML head and style, injecting the fontCss directly
	        html.append("""
	            <!DOCTYPE html>
	            <html>
	            <head>
	                <meta charset="UTF-8"/>
	                <style>
	        """);
	        html.append(fontCss);  // Append the formatted font-face CSS

	        html.append("""
	                    body {
	                        font-family: 'MyanmarFont', Arial, sans-serif;
	                        margin: 20px;
	                        font-size: 14px;
	                    }
	                    h2 {
	                        text-align: center;
	                        margin-bottom: 10px;
	                    }
	                    table {
	                        width: 100%;
	                        border-collapse: collapse;
	                        margin-top: 20px;
	                    }
	                    th, td {
	                        border: 1px solid #ccc;
	                        padding: 8px;
	                        text-align: left;
	                    }
	                    th {
	                        background-color: #f2f2f2;
	                    }
	                    .total {
	                        font-weight: bold;
	                        text-align: right;
	                    }
	                    .footer {
	                        margin-top: 30px;
	                        text-align: center;
	                        font-size: 12px;
	                        color: #888;
	                    }
	                </style>
	            </head>
	            <body>
	            """);		
		
		html.append("<h2>Receipt</h2>");
		html.append("<p><strong>Transaction ID:</strong> ").append(receipt.getTrxId()).append("</p>");

		html.append("""
				    <table>
				        <thead>
				            <tr>
				                <th>#</th>
				                <th>Item</th>
				                <th>Details</th>
				                <th>Quantity</th>
				                <th>Unit Price</th>
				                <th>Total</th>
				            </tr>
				        </thead>
				        <tbody>
				""");

		int count = 1;
		for (var item : receipt.getItems()) {
			html.append("<tr>");
			html.append("<td>").append(count++).append("</td>");
			html.append("<td>").append(item.englishName()).append("(").append(item.burmeseName()).append(")").append("</td>");
			html.append("<td>").append(item.details() == null ? "-" : item.details()).append("</td>");
			html.append("<td>").append(item.quantity()).append("</td>");
			html.append("<td>").append(item.salePrice()).append("</td>");
			html.append("<td>").append(item.totalPrice()).append("</td>");
			html.append("</tr>");
		}

		html.append("""
				    </tbody>
				    <tfoot>
				        <tr>
				            <td colspan="5" class="total">Total Amount</td>
				            <td><strong>
				""");

		html.append(receipt.allTotalAmount());

		html.append("""
				                </strong></td>
				            </tr>
				        </tfoot>
				    </table>
				    <div class="footer">
				        <p>Thank you for choosing us!</p>
				    </div>
				    </body>
				    </html>
				""");

		return html.toString();
	}
}
