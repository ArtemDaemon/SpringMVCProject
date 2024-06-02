package ru.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller class for managing books.
 */
@Controller
public class BookController {

    @Autowired
    BookService bookService;

    /**
     * Handles GET requests to retrieve all books.
     *
     * @param mvcModel the model to hold the list of books
     * @return the view name to display all books
     */
    @GetMapping("/books")
    public String get(Model mvcModel){
        mvcModel.addAttribute("books", bookService.getAllBooks());
        return "books";
    }

    /**
     * Handles GET requests to retrieve a single book by its ID.
     *
     * @param id the ID of the book to retrieve
     * @param mvcModel the model to hold the book details
     * @return the view name to display the book details, or "404" if not found
     */
    @GetMapping("/books/{id}")
    public String getOne(@PathVariable("id") long id, Model mvcModel){
        Book book = bookService.getBookById(id);
        if(book == null) return "404";
        mvcModel.addAttribute("book", book);
        return "book";
    }

    /**
     * Handles GET requests to display the form for creating a new book.
     *
     * @return the view name for the book creation form
     */
    @GetMapping("/books/add")
    public String createBookForm(){
        return "bookForm";
    }

    /**
     * Handles GET requests to display the form for editing an existing book by its ID.
     *
     * @param id the ID of the book to edit
     * @param mvcModel the model to hold the book details
     * @return the view name for the book edit form, or "404" if not found
     */
    @GetMapping("/books/{id}/edit")
    public String editBookForm(@PathVariable("id") long id, Model mvcModel){
        Book book = bookService.getBookById(id);
        if(book == null) return "404";
        mvcModel.addAttribute("book", bookService.getBookById(id));
        return "bookForm";
    }

    /**
     * Handles GET requests to search for books by genre.
     *
     * @param genre the genre to search for
     * @param mvcModel the model to hold the list of books matching the genre
     * @return the view name to display the search results
     */
    @GetMapping("/books/search")
    public String searchBook(@RequestParam("genre") String genre, Model mvcModel) {
        mvcModel.addAttribute("books", bookService.getAllBooksByGenre(genre));
        return "books";
    }

    /**
     * Handles POST requests to add a new book.
     *
     * @param formData the form data submitted by the user
     * @param mvcModel the model to hold any validation errors
     * @return the redirect URL to view the created book, or the book form view if there are validation errors
     */
    @PostMapping("/books")
    public String add(@RequestBody MultiValueMap<String, String> formData, Model mvcModel){
        Book book = new Book();
        String title, author, genre;
        int pages, price;

        title = formData.getFirst("title");
        if(title == null)
            mvcModel.addAttribute("error", "Title shouldn't be empty");
        book.setTitle(title);

        author = formData.getFirst("author");
        if(author == null)
            mvcModel.addAttribute("error", "Author shouldn't be empty");
        book.setAuthor(author);

        genre = formData.getFirst("genre");
        if(genre == null)
            mvcModel.addAttribute("error", "Genre shouldn't be empty");
        book.setGenre(genre);

        String pagesStr = formData.getFirst("pages");
        try{
            pages = Integer.parseInt(pagesStr);
        } catch(Exception ex) {
            pages = 0;
        }
        if(pages < 1)
            mvcModel.addAttribute("error", "Number of pages shouldn't be less or equal 0");
        book.setPages(pages);

        String priceStr = formData.getFirst("price");
        try{
            price = Integer.parseInt(priceStr);
        } catch(Exception ex) {
            price = 0;
        }
        if(price < 1)
            mvcModel.addAttribute("error", "Price shouldn't be less or equal 0");
        book.setPrice(price);

        if(mvcModel.getAttribute("error") != null) return "bookForm";

        bookService.createBook(book);
        mvcModel.addAttribute("book", book);
        return "redirect:/books/" + book.getId();
    }

    /**
     * Handles POST requests to edit an existing book by its ID.
     *
     * @param id the ID of the book to edit
     * @param formData the form data submitted by the user
     * @param mvcModel the model to hold any validation errors
     * @return the redirect URL to view the edited book, or the book form view if there are validation errors
     */
    @PostMapping("/books/{id}")
    public String edit(@PathVariable("id") long id, @RequestBody MultiValueMap<String, String> formData, Model mvcModel) {
        Book book = bookService.getBookById(id);

        if(book == null) return "404";

        String title, author, genre;
        int pages, price;

        title = formData.getFirst("title");
        if(title == null)
            mvcModel.addAttribute("error", "Title shouldn't be empty");
        book.setTitle(title);

        author = formData.getFirst("author");
        if(author == null)
            mvcModel.addAttribute("error", "Author shouldn't be empty");
        book.setAuthor(author);

        genre = formData.getFirst("genre");
        if(genre == null)
            mvcModel.addAttribute("error", "Genre shouldn't be empty");
        book.setGenre(genre);

        String pagesStr = formData.getFirst("pages");
        try{
            pages = Integer.parseInt(pagesStr);
        } catch(Exception ex) {
            pages = 0;
        }
        if(pages < 1)
            mvcModel.addAttribute("error", "Number of pages shouldn't be less or equal 0");
        book.setPages(pages);

        String priceStr = formData.getFirst("price");
        try{
            price = Integer.parseInt(priceStr);
        } catch(Exception ex) {
            price = 0;
        }
        if(price < 1)
            mvcModel.addAttribute("error", "Price shouldn't be less or equal 0");
        book.setPrice(price);

        mvcModel.addAttribute("book", book);
        if(mvcModel.getAttribute("error") != null) return "bookForm";

        bookService.updateBook(id, book);
        return "redirect:/books/" + book.getId();
    }

    /**
     * Handles POST requests to delete a book by its ID.
     *
     * @param id the ID of the book to delete
     * @return the redirect URL to the list of books
     */
    @PostMapping("/books/{id}/delete")
    public String delete(@PathVariable("id") long id){
        Book book = bookService.getBookById(id);
        if(book == null) return "404";
        bookService.deleteBook(id);
        return "redirect:/books";
    }

}
