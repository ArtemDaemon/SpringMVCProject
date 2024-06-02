package ru.mvc;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for managing Book entities.
 */
public interface BookRepository extends JpaRepository<Book, Long> {
    /**
     * Finds books by their genre.
     *
     * @param genre the genre of the books to find
     * @return a list of books that match the given genre
     */
    List<Book> findByGenre(String genre);
}
