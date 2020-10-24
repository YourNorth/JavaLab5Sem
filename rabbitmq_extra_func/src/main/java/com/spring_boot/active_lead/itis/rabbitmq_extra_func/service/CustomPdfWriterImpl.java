package com.spring_boot.active_lead.itis.rabbitmq_extra_func.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.spring_boot.active_lead.itis.rabbitmq_extra_func.model.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

@Service
@AllArgsConstructor
@Data
public class CustomPdfWriterImpl implements CustomPdfWriter{

    @Override
    public void createPdfs(Person person) {
        createDocumentOne("firstDoc", person);
        createDocumentTwo("secondDoc", person);
        createDocumentThree("thirdDoc", person);
    }

    @Override
    public void createDocumentOne(String fileName, Person person) {
        StringBuffer fileContent = new StringBuffer();
        fileContent.append("\t\tFIRST DOC\nHello, User!\nIt is document for ")
                .append(getPersonInfo(person));
        createDocument(fileName, String.valueOf(fileContent));
    }

    @Override
    public void createDocumentTwo(String fileName, Person person) {
        StringBuffer fileContent = new StringBuffer();
        fileContent.append("\t\tSECOND DOC\nHello, User!\nIt is document for ")
                .append(getPersonInfo(person));
        createDocument(fileName, String.valueOf(fileContent));
    }

    @Override
    public void createDocumentThree(String fileName, Person person) {
        StringBuffer fileContent = new StringBuffer();
        fileContent.append("    THIRD DOC\nHello, User!\nIt is document for ")
                .append(getPersonInfo(person));
        createDocument(fileName, String.valueOf(fileContent));
    }
    private void createDocument(String fileName, String fileContent) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(fileName + ".pdf"));

            document.open();
            Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
            //Chunk chunk = new Chunk(fileContent, font);
            //document.add(chunk);
            Paragraph paragraph = new Paragraph(fileContent, font);
            document.add(paragraph);
            document.close();
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private StringBuffer getPersonInfo(Person person){
        return new StringBuffer()
                .append("\nFirst name: ")
                .append(person.getFirstName())
                .append("\nSecond name: ")
                .append(person.getSecondName())
                .append("\nAge: ")
                .append(person.getAge())
                .append("\nPassword issue: ")
                .append(person.getPasswordIssue())
                .append("\nPassword Number: ")
                .append(person.getPasswordNumber());
    }
}
