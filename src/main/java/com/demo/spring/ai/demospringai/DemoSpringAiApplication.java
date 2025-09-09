package com.demo.spring.ai.demospringai;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@SpringBootApplication
public class DemoSpringAiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoSpringAiApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(VectorStore vectorStore, JdbcTemplate jdbcTemplate,
										@Value("classpath:CDC_AirQual_TEST_AI.pdf") Resource pdfRessource){
		return args -> {
			PdfDocumentReaderConfig pdfDocumentReaderConfig = PdfDocumentReaderConfig.defaultConfig();
			PagePdfDocumentReader pagePdfDocumentReader = new PagePdfDocumentReader(pdfRessource,pdfDocumentReaderConfig);
			List<Document> documentList = pagePdfDocumentReader.get();
			System.out.println(documentList.size());
		};
	}
}
