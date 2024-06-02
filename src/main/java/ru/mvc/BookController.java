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

@Controller
public class BookController {

    @Autowired
    BookService bookService;

    @GetMapping("/books")
    public String get(Model mvcModel){
        mvcModel.addAttribute("books", bookService.getAllBooks());
        return "books";
    }
    @GetMapping("/books/{id}")
    public String getOne(@PathVariable("id") long id, Model mvcModel){
        Book book = bookService.getBookById(id);
        if(book == null) return "404";
        mvcModel.addAttribute("book", book);
        return "book";
    }
    @GetMapping("/books/add")
    public String createBookForm(){
        return "bookForm";
    }
    @GetMapping("/books/{id}/edit")
    public String editBookForm(@PathVariable("id") long id, Model mvcModel){
        Book book = bookService.getBookById(id);
        if(book == null) return "404";
        mvcModel.addAttribute("book", bookService.getBookById(id));
        return "bookForm";
    }
    @GetMapping("/books/search")
    public String searchBook(@RequestParam("genre") String genre, Model mvcModel) {
        mvcModel.addAttribute("books", bookService.getAllBooksByGenre(genre));
        return "books";
    }
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

    @PostMapping("/books/{id}/delete")
    public String delete(@PathVariable("id") long id){
        Book book = bookService.getBookById(id);
        if(book == null) return "404";
        bookService.deleteBook(id);
        return "redirect:/books";
    }

}
