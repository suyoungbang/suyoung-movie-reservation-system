package com.ohgiraffers.moviereservationsystem.view;

import com.ohgiraffers.moviereservationsystem.model.Reservation;

import java.util.List;

public class ReservationView { // 사용자가 직접 보는 화면(view)

    public void displayError(String m) {
        System.out.println("[오류] " + m);
    }

    public void displaySuccess(String m) {
        System.out.println("[완료] " + m);
    }

    public void displayMessage(String m) {
        System.out.println(m);
    }

    public void displayReservationList(List<Reservation> reservations) {

        if (reservations.isEmpty()) {
            System.out.println("등록된 예매 내역이 없습니다.");
            return;
        }

        reservations.forEach(System.out::println);
    } // displayReservationList()

    public void displayReservation(Reservation reservation) {
        System.out.println(reservation); // 일단 이렇게 해놓고 이따가 변경
    }


}
