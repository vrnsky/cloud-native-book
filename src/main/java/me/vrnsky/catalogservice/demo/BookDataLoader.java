package me.vrnsky.catalogservice.demo;

import me.vrnsky.catalogservice.entity.Book;
import me.vrnsky.catalogservice.repository.BookRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("testdata")
public class BookDataLoader {
    private final BookRepository bookRepository;

    public BookDataLoader(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void loadBookTestData() {
        var book1 = Book.build("1231231230", "Northern Lights", "Lira Sylvertongue", 9.90);
        var book2 = Book.build("1231231232", "Polar Journey", "Iorek Polarson", 12.90);
        bookRepository.saveAll(List.of(book1, book2));
    }
}
