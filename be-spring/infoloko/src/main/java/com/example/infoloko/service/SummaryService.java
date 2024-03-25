package com.example.infoloko.service;


import com.example.infoloko.model.Summary;
import com.example.infoloko.model.SummaryDocument;
import com.example.infoloko.repository.MongoLokoRepository;
import com.example.infoloko.repository.SummaryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class SummaryService {

    private  static  final Logger LOGGER = LoggerFactory.getLogger(SummaryService.class);
    private final SummaryRepository summaryRepository;
    private final MongoLokoRepository mongoLokoRepository;
    private final MongoTemplate mongoTemplate;

    @Value("${telegram.bot.token}")
    private String botToken;

    @Value("${telegram.bot.chat-id}")
    private String chatId;

    private final RestTemplate restTemplate;

    @Autowired
    public SummaryService(
            SummaryRepository summaryRepository,
            MongoLokoRepository mongoLokoRepository,
            MongoTemplate mongoTemplate,
            RestTemplate restTemplate
    ) {
        this.summaryRepository = summaryRepository;
        this.mongoLokoRepository = mongoLokoRepository;
        this.mongoTemplate = mongoTemplate;
        this.restTemplate = restTemplate;
    }

    public List<SummaryDocument> getDailyCounts() {

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.group("tanggal", "status")
                        .count().as("jumlah"),
                Aggregation.project("jumlah")
                        .and("_id.tanggal").as("tanggal")
                        .and("_id.status").as("status")
        );

        AggregationResults<SummaryDocument> results = mongoTemplate.aggregate(aggregation, "info-lokos", SummaryDocument.class);

        return results.getMappedResults();
    }

    @Scheduled(fixedRate = 20000)
    public void saveSummary() {
        try{
            List<SummaryDocument> summaries = getDailyCounts();


            for (SummaryDocument summary: summaries) {

                String dateString = summary.getTanggal();
                String[] dateParts = dateString.split("-");
                if (dateParts.length == 3 && dateParts[2].length() == 1) {
                    dateParts[2] = "0" + dateParts[2];
                    dateString = String.join("-", dateParts);
                }
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                Summary sum = new Summary();
                sum.setTanggal(LocalDateTime.now());
                sum.setStatus(summary.getStatus());
                sum.setJumlah(summary.getJumlah());

                //save to postgresql
                summaryRepository.save(sum);

                //send to telegram
                String apiUrl = "https://api.telegram.org/bot" + botToken + "/sendMessage?chat_id=" + chatId + "&text=" + "Summary sudah masuk dari tanggal " + summary.getTanggal();
                ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, null, String.class);

                //logging scheduler
                //LOGGER.info(String.format("Successfully push data to postgresql -> %s", sum));
                //LOGGER.info(String.format("Successfully send data to telegram -> %s", response));
            }
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Summary> getAllSummary() {
        return summaryRepository.findAll();
    }
}
