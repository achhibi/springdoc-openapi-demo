package com.amch.service;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.amch.dto.BookDTO;
import com.amch.model.Book;
import com.amch.repository.BookRepository;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {
    
    private final BookRepository bookRepository;
    private final ModelMapper mapper;


    public BookDTO create(BookDTO book){
        return mapper.map(this.bookRepository.save(mapper.map(book, Book.class)), BookDTO.class);
    }

    public List<BookDTO> all() {
      return  StreamSupport.stream( bookRepository.findAll().spliterator(), false)
                        .map(b -> mapper.map(b, BookDTO.class))
                        .collect(Collectors.toList());

    }

    public BookDTO bookById(long id){
        return mapper.map(bookRepository.findById(id), BookDTO.class);
    }

}
