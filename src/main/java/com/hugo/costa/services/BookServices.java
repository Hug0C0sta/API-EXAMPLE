package com.hugo.costa.services;

import com.hugo.costa.controllers.BookController;
import com.hugo.costa.data.vo.v1.BookVO;
import com.hugo.costa.exceptions.RequiredObjectsIsNullException;
import com.hugo.costa.exceptions.ResourceNotFoundException;
import com.hugo.costa.mapper.DozerMapper;
import com.hugo.costa.mapper.custom.BookMapper;
import com.hugo.costa.model.Book;
import com.hugo.costa.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookServices {

    private final Logger logger = Logger.getLogger(BookServices.class.getName());

    @Autowired
    BookRepository repository;

    @Autowired
    BookMapper mapper;

    public BookVO findById(Long id) throws Exception {
        logger.info("Finding one book!");
        var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this id!"));;
        var vo = DozerMapper.parseObject(entity, BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
        return vo;
    }

    public List<BookVO> findAll() throws Exception {
        logger.info("Finding all books!");
        var books = DozerMapper.parseListObjects(repository.findAll(), BookVO.class);
        for (BookVO b : books) {
            b.add(linkTo(methodOn(BookController.class).findById(b.getKey())).withSelfRel());
        }
        return books;
    }

    public BookVO create(BookVO book) throws Exception {
        if (book == null) throw new RequiredObjectsIsNullException();
        logger.info("Creating one book!");
        var entity = DozerMapper.parseObject(book, Book.class);
        var vo =  DozerMapper.parseObject(repository.save(entity), BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public BookVO update(BookVO book) throws Exception {
        if (book == null) throw new RequiredObjectsIsNullException();
        logger.info("Creating one book!");
        Book entity = repository.findById(book.getKey()).orElseThrow(() -> new ResourceNotFoundException("No records found for this id!"));
        entity.setTitle(book.getTitle());
        entity.setPrice(book.getPrice());
        entity.setAuthor(book.getAuthor());
        entity.setLaunchDate(book.getLaunchDate());
        var vo =  DozerMapper.parseObject(repository.save(entity), BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());

        return vo;
    }

    public void delete(Long id){
        logger.info("Deleting one book!");
        Book entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this id!"));
        repository.delete(entity);
    }
}
