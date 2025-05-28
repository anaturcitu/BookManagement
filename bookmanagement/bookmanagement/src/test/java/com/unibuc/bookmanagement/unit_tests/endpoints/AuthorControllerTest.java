// package com.unibuc.bookmanagement.unit_tests.endpoints;

// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.unibuc.bookmanagement.controllers.AuthorController;
// import com.unibuc.bookmanagement.models.Author;
// import com.unibuc.bookmanagement.services.AuthorService;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.http.MediaType;
// import org.springframework.test.web.servlet.MockMvc;

// import java.util.List;
// import java.util.Optional;

// import static org.mockito.Mockito.*;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// @WebMvcTest(AuthorController.class)
// @AutoConfigureMockMvc(addFilters = false)
// public class AuthorControllerTest {

//     @Autowired
//     private MockMvc mockMvc;

//     @MockBean
//     private AuthorService authorService;

//     @Autowired
//     private ObjectMapper objectMapper;

//     @Test
//     void testCreateAuthor() throws Exception {
//         Author author = new Author();
//         author.setId(1L);
//         author.setName("Ion Creangă");

//         when(authorService.createAuthor(any(Author.class))).thenReturn(author);

//         mockMvc.perform(post("/api/authors")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(objectMapper.writeValueAsString(author)))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.id").value(1L))
//                 .andExpect(jsonPath("$.name").value("Ion Creangă"));
//     }

//     @Test
//     void testGetAllAuthors() throws Exception {
//         Author a1 = new Author(); a1.setId(1L); a1.setName("Mihai Eminescu");
//         Author a2 = new Author(); a2.setId(2L); a2.setName("Mircea Eliade");

//         when(authorService.getAllAuthors()).thenReturn(List.of(a1, a2));

//         mockMvc.perform(get("/api/authors"))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.length()").value(2))
//                 .andExpect(jsonPath("$[0].name").value("Mihai Eminescu"))
//                 .andExpect(jsonPath("$[1].name").value("Mircea Eliade"));
//     }

//     @Test
//     void testGetAuthorById_found() throws Exception {
//         Author author = new Author(); author.setId(1L); author.setName("George Coșbuc");

//         when(authorService.getAuthorById(1L)).thenReturn(Optional.of(author));

//         mockMvc.perform(get("/api/authors/1"))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.id").value(1L))
//                 .andExpect(jsonPath("$.name").value("George Coșbuc"));
//     }

//     @Test
//     void testGetAuthorById_notFound() throws Exception {
//         when(authorService.getAuthorById(999L)).thenReturn(Optional.empty());

//         mockMvc.perform(get("/api/authors/999"))
//                 .andExpect(status().isNotFound());
//     }

//     @Test
//     void testDeleteAuthor() throws Exception {
//         doNothing().when(authorService).deleteAuthor(1L);

//         mockMvc.perform(delete("/api/authors/1"))
//                 .andExpect(status().isOk())
//                 .andExpect(content().string("Author deleted successfully"));
//     }
// }
