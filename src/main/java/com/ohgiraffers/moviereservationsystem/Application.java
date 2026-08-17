package com.ohgiraffers.moviereservationsystem;

import com.ohgiraffers.moviereservationsystem.controller.ReservationController;
import com.ohgiraffers.moviereservationsystem.model.Genre;
import com.ohgiraffers.moviereservationsystem.repository.ReservationRepository;
import com.ohgiraffers.moviereservationsystem.view.ReservationView;

import java.time.LocalDate;
import java.time.LocalTime;

public class Application {
    public static void main(String[] args) {

        ReservationRepository repository = new ReservationRepository();
        ReservationView view = new ReservationView();
        ReservationController controller = new ReservationController(repository, view);

        view.displayMessage("영화 예매 관리 시스템에 오신 것을 환영합니다!");

        while (true) {

            view.displayMainMenu();
            int choice = view.readInt("원하시는 메뉴를 선택해주세요 : ");

            switch (choice) {
                case 1 -> registerReservation(view, controller);
                case 2 -> searchMenu(view, controller);
                case 3 -> updateReservation(view, controller);
                case 4 -> cancelReservation(view, controller);
                case 8 -> {
                    view.displayMessage("영화 예매 관리 시스템을 종료합니다.");
                    view.close();
                    return;
                }
                default -> view.displayError("메뉴에 있는 번호를 선택해주세요.");
            }
        }


    } // main()


    // 영화 예매
    private static void registerReservation(ReservationView view, ReservationController controller) {

            view.displayMessage("");
            view.displayMessage("---------- 영화 예매하기 ----------");

            String movieTitle = view.readLine("영화 제목 : ");
            Genre genre = view.readGenre("영화 장르를 선택해주세요. : ");
            LocalDate screeningDate = view.readDate("상영 날짜를 입력해주세요. (예: 2026-08-20) : ");
            LocalTime screeningTime = view.readTime("상영 시간을 입력해주세요. (예: 14:30) : ");
            String seatNumber = view.readLine("좌석 번호 (예: A10) : ");
            String customerName = view.readLine("예매자 이름 : ");

            controller.registerReservation(movieTitle, genre, screeningTime, screeningDate, seatNumber, customerName);
        } // registerReservation()

    // 예매 조회
    private static void searchMenu(ReservationView view, ReservationController reservationController) {
            while (true) {

                view.displaySearchMenu();
                int choice = view.readInt("메뉴를 선택해주세요 : ");

                switch (choice) {
                    case 1 -> reservationController.showAllReservations();
                    case 2 -> reservationController.showReservationDetails(view.readInt("조회할 예매 번호 : "));
                    case 3 -> reservationController.searchReservationsByMovieTitle(view.readLine("검색할 영화 제목 : "));
                    case 4 -> reservationController.showReservationsByGenre(view.readGenre("조회할 장르 : "));
                    case 8 -> {return;}

                    default -> view.displayError("메뉴에 있는 번호를 선택해주세요.");
                }
            }
        } // searchMenu()

    // 예매 변경
    private static void updateReservation(ReservationView view, ReservationController controller) {
        view.displayMessage("");
        view.displayMessage("---------- 예매 변경 ----------");

        int reservationId = view.readInt("변경할 예매의 기존 예매 번호 : ");
        LocalDate screeningDate = view.readDate("새 상영 날짜 (예: 2026-08-20) : ");
        LocalTime screeningTime = view.readTime("새 상영 시간 (예: 14:30) : ");
        String seatNumber = view.readLine("새 좌석 번호 (예:A10) : ");
        String customerName = view.readLine("새 예매자 이름 : ");

        controller.updateReservation(reservationId, screeningTime, screeningDate, seatNumber, customerName);
    } // updateReservation()

    // 예매 취소
    private static void cancelReservation(ReservationView view, ReservationController controller) {

        view.displayMessage("");
        view.displayMessage("---------- 예매 취소 ----------");

        int reservationId = view.readInt("취소할 예매 번호 : ");
        String confirm = view.readLine("정말 예매를 취소하시겠습니까? (y/n) : ");

        if (!confirm.equalsIgnoreCase("y")) {
            view.displayMessage("예매 취소를 중단했습니다.");
            return;
        }

        controller.cancelReservation(reservationId);
    } //cancelReservation()

} // end of class
