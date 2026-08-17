package com.ohgiraffers.moviereservationsystem.view;

import com.ohgiraffers.moviereservationsystem.model.Genre;
import com.ohgiraffers.moviereservationsystem.model.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class ReservationView { // 사용자가 직접 보는 화면(view)

    private final Scanner scanner = new Scanner(System.in);

    public void displayError(String m) {
        System.out.println("[오류] " + m);
    }

    public void displaySuccess(String m) {
        System.out.println("[완료] " + m);
    }

    public void displayMessage(String m) {
        System.out.println(m);
    }


    // 메인 메뉴 출력
    public void displayMainMenu() {

        System.out.println();
        System.out.println("===== 영화 예매 관리 시스템 =====");
        System.out.println("1. 예매하기");
        System.out.println("2. 예매 내역 조회");
        System.out.println("3. 예매 변경");
        System.out.println("4. 예매 취소");
        System.out.println("8. 프로그램 종료");
    }

    // 예매 내역 조회 메뉴 출력
    public void displaySearchMenu() {

        System.out.println();
        System.out.println("---------- 예매 내역 조회 ----------");
        System.out.println("1. 전체 예매 내역 조회");
        System.out.println("2. 예매 번호로 상세 조회");
        System.out.println("3. 영화 제목으로 예매 검색");
        System.out.println("4. 장르별 예매 내역 조회");
        System.out.println("8. 이전 메뉴로");
    }

    // 전체 예매 내역 출력
    public void displayReservationList(List<Reservation> reservations) {

        if (reservations.isEmpty()) {
            System.out.println("조회된 예매 내역이 없습니다.");
            return;
        }

        System.out.println();
        System.out.println("---------- 예매 내역 ----------");

        for (int i = 0; i < reservations.size(); i++) {

            Reservation reservation = reservations.get(i);

            System.out.printf(
                    "[%d] %s | %s | %s %s | 좌석 %s | 예매자 %s | %,d원%n",
                    reservation.getReservationId(),
                    reservation.getMovieTitle(),
                    reservation.getGenre().getDescription(),
                    reservation.getScreeningDate(),
                    reservation.getScreeningTime(),
                    reservation.getSeatNumber(),
                    reservation.getCustomerName(),
                    reservation.getPrice()
            );
        }

        System.out.println("--------------------------------");
        System.out.println("총 " + reservations.size() + "건");
    } // displayReservationList()

    // 예매 상세내역 출력
    public void displayReservation(Reservation reservation) {
        System.out.println();
        System.out.println("---------- 예매 내역 정보 ----------");
        System.out.println("예매 번호   : " + reservation.getReservationId());
        System.out.println("영화 제목   : " + reservation.getMovieTitle());
        System.out.println("장르        : " + reservation.getGenre().getDescription());
        System.out.println("상영 날짜   : " + reservation.getScreeningDate());
        System.out.println("상영 시간   : " + reservation.getScreeningTime());
        System.out.println("좌석 번호   : " + reservation.getSeatNumber());
        System.out.println("예매자 이름 : " + reservation.getCustomerName());
        System.out.printf("가격         : %,d원%n", reservation.getPrice());
        System.out.println("예매 날짜   : " + reservation.getReservationDate());
    }

    // 입력을 받는 메소드

    // 1. 문자열 입력
    // 빈 값이 아닌 문자열을 입력받는다.
    public String readLine(String prompt) {

        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (!input.isEmpty()) {
                return input;
            }
            displayError("한 글자 이상 입력해주세요.");
        } //while
    } // readLine()

    // 2. 정수 입력
    // 일단 문자열로 받고, 숫자로 변환한다
    // 숫자가 아닌 값을 입력해도 프로그램이 종료되지 않고 다시 입력받는다 (catch (NumberFormatException e))
    public int readInt(String prompt) {

        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                displayError("숫자로 입력해주세요.");
            }
        } // while
    } // readInt()

    // 3. 장르 목록 출력 및 알맞은 장르 번호 입력
    public Genre readGenre(String prompt) {

        System.out.println();
        System.out.println(prompt);

        Genre[] genres = Genre.values();

        for (int i = 0; i < genres.length; i++) {
            System.out.println(genres[i].getGenreNumber() + ". " + genres[i].getDescription());
        }

        while (true) {
            int genreNumber = readInt("선택 : ");

            try {
                return Genre.fromGenreNumber(genreNumber); // 입력받은 번호를 genre로 변환
            } catch (IllegalArgumentException e) {
                displayError(e.getMessage());
            }
        } // while
    } // readGenre()

    // 4. 날짜 입력
    // 날짜는 연도-월-일 형식으로 입력받는다
    // 여기서는 날짜 형식만 보고 영화 상영 날짜보다 과거인지 아닌지는 controller에서 판단
    public LocalDate readDate(String prompt) {

        while (true) {
            String input = readLine(prompt);

            try {
                return LocalDate.parse(input);
            } catch (DateTimeParseException e) {
                displayError("날짜는 연도-월-일 형식으로 입력해주세요. (예: 2026-08-20)" );
            }
        } // while
    } // readDate()

    // 5. 시간 입력
    // 시간을 시:분 형식으로 입력받는다
    // 24시간제 사용 (오전 오후 따로 입력받지 않음)
    // 여기서는 시간 형식만 보고 영화 상영 시간보다 과거인지 아닌지는 controller에서 판단
    public LocalTime readTime(String prompt) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        while (true) {
            String input = readLine(prompt);

            try {
                return LocalTime.parse(input, formatter);
            } catch (DateTimeParseException e) {
                displayError(
                        "시간은 시:분 형식으로 입력해주세요. (예: 14:30) "
                );
            }
        }
    } // readTime()


    public void close() {scanner.close();}
}
