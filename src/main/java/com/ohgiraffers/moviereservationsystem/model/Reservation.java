package com.ohgiraffers.moviereservationsystem.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {  // 영화 예매에 대한 정보

    private int reservationId;        // 예매번호 (자동으로 부여함)
    private String movieTitle;        // 영화제목
    private Genre genre;              // 영화장르 (enum)
    private LocalTime screeningTime;  // 상영시작시간
    private LocalDate screeningDate;  // 상영날짜
    private String seatNumber;        // 좌석번호
    private String customerName;      // 예매자 이름
    private int price;                // 가격
    private LocalDate reservationDate; // 예매날짜

    // 매개변수 있는 생성자 만들기
    // reservationId는 자동으로 부여할 예정이기 때문에 제외하고 만듦
    // 예매 날짜는 예매할 때의 날짜를 자동 저장하기 때문에 매개변수에서 제외하고 LocalDate.now()로 자동설정
    public Reservation(String movieTitle, Genre genre, LocalTime screeningTime, LocalDate screeningDate,
                       String seatNumber, String customerName, int price) {
        this.movieTitle = movieTitle;
        this.genre = genre;
        this.screeningTime = screeningTime;
        this.screeningDate = screeningDate;
        this.seatNumber = seatNumber;
        this.customerName = customerName;
        this.price = price;
        this.reservationDate = LocalDate.now();
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public LocalTime getScreeningTime() {
        return screeningTime;
    }

    public void setScreeningTime(LocalTime screeningTime) {
        this.screeningTime = screeningTime;
    }

    public LocalDate getScreeningDate() {
        return screeningDate;
    }

    public void setScreeningDate(LocalDate screeningDate) {
        this.screeningDate = screeningDate;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    } // 예매날짜는 조회만 하고 생성 후 변경하지 않기 때문에 setter 없이 getter만 만듦

    @Override
    public String toString() {
        return "Reservation{" +
                "reservationId=" + reservationId +
                ", movieTitle='" + movieTitle + '\'' +
                ", genre=" + genre +
                ", screeningTime=" + screeningTime +
                ", screeningDate=" + screeningDate +
                ", seatNumber='" + seatNumber + '\'' +
                ", customerName='" + customerName + '\'' +
                ", price=" + price +
                ", reservationDate=" + reservationDate +
                '}';
    }
}
