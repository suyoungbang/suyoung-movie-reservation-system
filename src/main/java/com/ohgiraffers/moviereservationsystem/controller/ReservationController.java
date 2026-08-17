package com.ohgiraffers.moviereservationsystem.controller;

import com.ohgiraffers.moviereservationsystem.model.Genre;
import com.ohgiraffers.moviereservationsystem.model.Reservation;
import com.ohgiraffers.moviereservationsystem.repository.ReservationRepository;
import com.ohgiraffers.moviereservationsystem.view.ReservationView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ReservationController { // 입력이 들어왔을 때 문제 유무를 판단하여 처리하는 중간 관리자


    private final ReservationRepository repository;
    private final ReservationView view;

    private static final int TICKET_PRICE = 15000; // 할인이나 기타 가격 정책은 일단 뒤로 하고, 기본 가격을 책정해놓는다.

    public ReservationController(ReservationRepository repository, ReservationView view) {

        this.repository = repository;
        this.view = view;
    }

    // 새로운 예매 등록
    public void registerReservation(String movieTitle, Genre genre, LocalTime screeningTime, LocalDate screeningDate,
                                    String seatNumber, String customerName) {
        if (movieTitle == null || movieTitle.isBlank()) {
            view.displayError("영화 제목을 입력해주세요.");
            return;
        }

        if (genre == null) {
            view.displayError("장르를 선택해주세요.");
            return;
        }

        if (screeningDate == null) {
            view.displayError("상영 날짜를 입력해주세요.");
            return;
        }

        if (screeningTime == null) {
            view.displayError("상영 시간을 입력해주세요.");
            return;
        }

        if (screeningDate.isBefore(LocalDate.now())) {
            view.displayError("지난 날짜의 영화는 예매할 수 없습니다.");
            return;
        }

        if (screeningDate.isEqual(LocalDate.now())
                && screeningTime.isBefore(LocalTime.now())) {
            view.displayError("이미 지난 상영 시간은 선택할 수 없습니다.");
            return;
        }

        if (seatNumber == null || seatNumber.isBlank()) {
            view.displayError("좌석 번호를 입력해주세요.");
            return;
        }

        if (customerName == null || customerName.isBlank()) {
            view.displayError("예매자 이름을 입력해주세요.");
            return;
        }


        Reservation reservation = new Reservation(
                movieTitle.trim(),
                genre,
                screeningTime,
                screeningDate,
                seatNumber.trim().toUpperCase(),
                customerName.trim(),
                TICKET_PRICE
        );

        repository.save(reservation);
        view.displaySuccess("예매가 완료되었습니다.");
    } // registerReservation()


    // 전체 예매 내역 조회
    // findAll()을 이용해 저장된 전체 예매 목록의 복사본을 받아와서 view에 전달한다
    public void showAllReservations() {

        List<Reservation> reservations = repository.findAll();
        view.displayReservationList(reservations);
    }

    // 예매 번호로 상세 조회
    public void showReservationDetails(int reservationId) {
        // 올바른 숫자를 입력하지 않았을 때
        if (reservationId <= 0) {
            view.displayError("예매 번호는 1이상의 정수로 입력해주세요.");
            return;
        }

        // findById()를 이용해 번호가 같은 예매 내역을 찾는다
        Reservation reservation = repository.findById(reservationId);

        // 입력한 번호가 존재하지 않을 때
        if (reservation == null) {
            view.displayError("해당 번호의 예매 내역을 찾을 수 없습니다. 다시 입력해주세요.");
            return;
        }

        view.displayReservation(reservation);
    }

    // 영화 제목으로 예매내역 조회
    public void searchReservationsByMovieTitle(String movieTitle) {

        if (movieTitle == null || movieTitle.isBlank()) {
            view.displayError("검색할 영화 제목을 입력해주세요.");
            return;
        }

        List<Reservation> reservations =
                repository.findByMovieTitle(movieTitle);

        if (reservations.isEmpty()) {
            view.displayMessage(
                    "'" + movieTitle.trim() + "'에 대한 예매 내역 결과가 없습니다."
            );
            return;
        }

        view.displayMessage(
                "'" + movieTitle.trim() + "' 예매 내역 결과입니다."
        );
        view.displayReservationList(reservations);
    }

    // 장르별 예매 내역 조회

    // 예매 변경

    // 예매 취소
}
