package com.library.library_app.controllers;

import com.library.library_app.Services.LibraryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BorrowController {

    private final LibraryService libraryService;

    public BorrowController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    // Example: PUT /borrowed/5/return
    @PutMapping("/borrowed/{id}/return")
    public ResponseEntity<String> returnBook(@PathVariable("id") int borrowedId) {
        double fine = libraryService.returnBookAndApplyFine(borrowedId);

        if (fine > 0) {
            return ResponseEntity.ok("Book returned. Late fine applied: $" + fine);
        } else {
            return ResponseEntity.ok("Book returned on time. No fine.");
        }
    }
}
