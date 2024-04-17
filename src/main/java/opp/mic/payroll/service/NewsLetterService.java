package opp.mic.payroll.service;

import opp.mic.payroll.model.NewsLetter;
import opp.mic.payroll.repository.NewsLetterRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class NewsLetterService {

    private NewsLetterRepository newsLetterRepository;

    public NewsLetterService(NewsLetterRepository newsLetterRepository) {
        this.newsLetterRepository = newsLetterRepository;
    }
    public Page<NewsLetter> getAllSubscribers(int page){
        PageRequest pageRequest = PageRequest.of(page,10);
        return newsLetterRepository.findAll(pageRequest);
    }
}
