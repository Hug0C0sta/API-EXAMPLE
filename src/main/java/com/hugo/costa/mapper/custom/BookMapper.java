package com.hugo.costa.mapper.custom;

import com.hugo.costa.data.vo.v1.BookVO;
import com.hugo.costa.model.Book;
import org.springframework.stereotype.Service;

@Service
public class BookMapper {
    public Book convertVoToEntity(BookVO book){
        Book entity = new Book();
        entity.setId(book.getKey());
        entity.setAuthor(book.getAuthor());
        entity.setLaunchDate(book.getLaunchDate());
        entity.setPrice(book.getPrice());
        entity.setTitle(book.getTitle());

        return entity;
    }
}
