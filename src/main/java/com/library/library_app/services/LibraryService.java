package com.library.library_app.services;

import com.library.library_app.models.BooksBorrowed;
import com.library.library_app.repositories.BooksBorrowedRepository;
import com.library.library_app.repositories.MemberRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class LibraryService {

    private final BooksBorrowedRepository booksBorrowedRepository;
    private final MemberRepository memberRepository;

    //These constants can be changed if needed to increase fine amount or return deadline
    private static final int LOAN_DAYS = 14;
    private static final double DAILY_FINE = 0.50;

    public LibraryService(BooksBorrowedRepository booksBorrowedRepository,
                          MemberRepository memberRepository) {
        this.booksBorrowedRepository = booksBorrowedRepository;
        this.memberRepository = memberRepository;
    }

    /**
     * Marks a book as returned, calculates any fine, and applies it to the member.
     * @param borrowedId ID from books_borrowed table
     * @return fine amount that was added (0 if no fine)
     */
    public double returnBookAndApplyFine(int borrowedId) {
        //Get the borrow record
        BooksBorrowed record = booksBorrowedRepository.getBorrowedById(borrowedId);

        LocalDate today = LocalDate.now();
        LocalDate borrowDate = record.getBorrowDate();

        //Compute due date: borrowDate + LOAN_DAYS
        LocalDate dueDate = borrowDate.plusDays(LOAN_DAYS);

        //Mark book as returned in DB (sets return_date = today)
        booksBorrowedRepository.returnBook(borrowedId);

        //Check if late
        long daysLate = ChronoUnit.DAYS.between(dueDate, today);
        if (daysLate <= 0) {
            // Returned on time or early → no fine
            return 0.0;
        }

        double fine = daysLate * DAILY_FINE;

        //Add fine to member
        memberRepository.addFine(record.getMemberId(), fine);

        return fine;
    }
}
