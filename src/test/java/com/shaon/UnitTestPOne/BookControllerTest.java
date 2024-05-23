package com.shaon.UnitTestPOne;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.shaon.UnitTestPOne.book.Book;
import com.shaon.UnitTestPOne.book.BookController;
import com.shaon.UnitTestPOne.book.BookRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(MockitoJUnitRunner.class)
public class BookControllerTest {

    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookController bookController;

    Book record_1 = new Book(1L,"Atomic Habits","James Clear",22.2);
    Book record_2 = new Book(2L,"Advanced Java","Rahman",30.5);
    Book record_3 = new Book(3L,"Multi Threading","Rahman",15.0);

    @Before
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }


    @Test
    public void getList() throws Exception{
        List<Book> records = new ArrayList<>(Arrays.asList(record_1,record_2,record_2));

        Mockito.when(bookRepository.findAll()).thenReturn(records);

        mockMvc.perform(MockMvcRequestBuilders.get("/get-list")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", org.hamcrest.Matchers.hasSize(3)))
                .andExpect(jsonPath("$[0].bookName").value("Atomic Habits"));
    }


    @Test
    public void findById() throws Exception {
        Mockito.when(bookRepository.findById(record_1.getId())).thenReturn(Optional.of(record_1));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/find-by/{id}", record_1.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookName").value("Atomic Habits"))
                .andExpect(jsonPath("$.author").value("James Clear"));
    }


    @Test
    public void saveBook() throws Exception {
        Book newRecord = new Book(4L, "New Book", "New Author", 25.5);

        Mockito.when(bookRepository.save(newRecord)).thenReturn(newRecord);

        String content = objectWriter.writeValueAsString(newRecord);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/save/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(4))
                .andExpect(jsonPath("$.bookName").value("New Book"))
                .andExpect(jsonPath("$.author").value("New Author"))
                .andExpect(jsonPath("$.price").value(25.5));
    }


}
