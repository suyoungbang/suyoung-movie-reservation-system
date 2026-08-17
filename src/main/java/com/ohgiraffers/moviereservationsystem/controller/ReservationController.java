package com.ohgiraffers.moviereservationsystem.controller;

import com.ohgiraffers.moviereservationsystem.model.Genre;
import com.ohgiraffers.moviereservationsystem.model.Reservation;
import com.ohgiraffers.moviereservationsystem.repository.ReservationRepository;
import com.ohgiraffers.moviereservationsystem.view.ReservationView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ReservationController { // 입력이 들어왔을 때 문제 유무를 판단하여 처리하는 중간 관리자

    /* [규칙]
    *  1. 과거 날짜의 영화는 예매할 수 없다
    *  2. 과거 시간의 영화는 예매할 수 없다
    *  3. 할인이나 기타 가격 정책 구현 전까지는 기본 가격을 책정하여 진행한다
    *  4. 같은 영화의 일정·좌석 변경 시, 예매 변경을 이용한다
    *  5. 다른 영화로 변경 시, 기존 예매 취소 후 새 예매하도록 한다
    */

    private final ReservationRepository repository;
    private final ReservationView view;

    private static final int TICKET_PRICE = 15000; // 기본 가격

    public ReservationController(ReservationRepository repository, ReservationView view) {

        this.repository = repository;
        this.view = view;
    }

    // 새로운 예매 등록
    public void registerReservation(String movieTitle, Genre genre, LocalTime screeningTime, LocalDate screeningDate,
                                    String seatNumber, String customerName) {

        // 입력받은 데이터에 대한 예외처리는 view에서 진행한다
        // 예매 시간 기준 영화 상영 날짜, 시간이 지났는지 판단한다
        if (screeningDate.isBefore(LocalDate.now())) {
            view.displayError("지난 날짜의 영화는 예매할 수 없습니다.");
            return;
        }

        if (screeningDate.isEqual(LocalDate.now())
                && screeningTime.isBefore(LocalTime.now())) {
            view.displayError("이미 지난 상영 시간은 선택할 수 없습니다.");
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

        List<Reservation> reservations = repository.findByMovieTitle(movieTitle);
        view.displayMessage("'" + movieTitle.trim() + "'의 예매 내역 결과입니다.");
        view.displayReservationList(reservations);

    }

    // 장르별 예매 내역 조회
    public void showReservationsByGenre(Genre genre) {

        view.displayMessage(genre.getDescription() + " 장르의 예매 내역 결과입니다.");
        view.displayReservationList(repository.findByGenre(genre));

    }

    // 예매 변경
    /* 날짜, 시간, 좌석, 예매자 이름만 변경 가능하게 한다
    *  따라서 예매 번호, 영화 제목, 장르, 가격, 최초 예매 날짜는 건드리지 않는다
    *  만약, 할인으로 인한 가격 변동이나, 영화 자체를 바꾸고 싶으면 기존 예매를 취소하고 새로 예매한다
    */
    public void updateReservation(int reservationId, LocalTime screeningTime, LocalDate screeningDate,
                                  String seatNumber, String customerName) {

        Reservation reservation = repository.findById(reservationId);

        if (reservation == null) {
            view.displayError("변경할 예매 내역을 찾을 수 없습니다.");
            return;
        }

        if (screeningDate.isBefore(LocalDate.now())) {
            view.displayError("지난 날짜로 예매를 변경할 수 없습니다.");
            return;
        }

        if (screeningDate.isEqual(LocalDate.now())
                && screeningTime.isBefore(LocalTime.now())) {
            view.displayError("이미 지난 상영 시간으로 변경할 수 없습니다.");
            return;
        }

        // 변경 가능한 예매 정보만 수정
        reservation.setScreeningTime(screeningTime);
        reservation.setScreeningDate(screeningDate);
        reservation.setSeatNumber(seatNumber.trim().toUpperCase());
        reservation.setCustomerName(customerName.trim());

        view.displaySuccess("예매 내역이 변경되었습니다.");

    }

    // 예매 취소
    public void cancelReservation(int reservationId) {

        // 예매 번호 존재 확인
        Reservation reservation = repository.findById(reservationId);

        if (reservation == null) {
            view.displayError("취소할 예매 내역을 찾을 수 없습니다.");
            return;
        }

        /* 1. 상영날짜가 오늘보다 이전이거나(이미 지났거나)
        *  2. 상영날짜가 오늘이 맞는데 상영시작시간이 지났을 때
        *  예매 취소는 불가능하다
        * */
        if (reservation.getScreeningDate().isBefore(LocalDate.now())
                || (reservation.getScreeningDate().isEqual(LocalDate.now())
                && reservation.getScreeningTime().isBefore(LocalTime.now()))) {

            view.displayError("예매 취소는 상영 시작 전까지만 가능합니다.");
            return;
        }

        repository.deleteById(reservationId);

        view.displaySuccess(
                "'" + reservation.getMovieTitle() + "' 예매가 취소되었습니다."
        );

    }
}
