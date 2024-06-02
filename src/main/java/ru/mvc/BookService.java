package ru.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing Book entities.
 */
@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    /**
     * Retrieves all books from the repository.
     *
     * @return a list of all books
     */
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    /**
     * Retrieves a book by its ID.
     *
     * @param id the ID of the book to retrieve
     * @return the book with the given ID, or null if not found
     */
    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    /**
     * Creates a new book in the repository.
     *
     * @param book the book to create
     * @return the created book
     */
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    /**
     * Updates an existing book in the repository.
     *
     * @param id the ID of the book to update
     * @param book the book with updated information
     * @return the updated book, or null if the book with the given ID does not exist
     */
    public Book updateBook(Long id, Book book) {
        if (bookRepository.existsById(id)) {
            book.setId(id);
            return bookRepository.save(book);
        }
        return null;
    }

    /**
     * Deletes a book by its ID.
     *
     * @param id the ID of the book to delete
     */
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    /**
     * Retrieves all books with the given genre from the repository.
     *
     * @param genre the genre of the books to retrieve
     * @return a list of books that match the given genre
     */
    public List<Book> getAllBooksByGenre(String genre) {
        return bookRepository.findByGenre(genre);
    }
}
