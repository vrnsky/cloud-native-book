package me.vrnsky.catalogservice.service;

import me.vrnsky.catalogservice.exception.BookAlreadyExistsException;
import me.vrnsky.catalogservice.exception.BookNotFoundException;
import me.vrnsky.catalogservice.repository.BookRepository;
import me.vrnsky.catalogservice.entity.Book;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Iterable<Book> viewBookList() {
        return bookRepository.findAll();
    }

    public Book viewBookDetails(String isbn) {
        return bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException(isbn));
    }

    public Book addBookToCatalog(Book book) {
        if (bookRepository.existsByIsbn(book.isbn())) {
            throw new BookAlreadyExistsException(book.isbn());
        }
        return bookRepository.save(book);
    }

    public void removeBookFromCatalog(String isbn) {
        bookRepository.deleteByIsbn(isbn);
    }

    public Book editBookDetails(String isbn, Book book) {
        Optional<Book> existingBook = bookRepository.findByIsbn(isbn);
        if (existingBook.isEmpty()) {
            return addBookToCatalog(book);
        }
        var bookToUpdate = new Book(
                existingBook.get().id(),
                existingBook.get().isbn(),
                book.title(),
                book.author(),
//                book.publisher(),
                book.price(),
                existingBook.get().createdDate(),
                existingBook.get().lastModifiedDate(),
                existingBook.get().version());
        return bookRepository.save(bookToUpdate);
    }
}
