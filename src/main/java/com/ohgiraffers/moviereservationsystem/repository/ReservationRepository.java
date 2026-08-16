package com.ohgiraffers.moviereservationsystem.repository;

import com.ohgiraffers.moviereservationsystem.model.Genre;
import com.ohgiraffers.moviereservationsystem.model.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ReservationRepository { // 영화 예매 내역을 저장하고 조회하는 클래스

    // 예매 내역을 저장하는 리스트
    // 해당 arraylist가 다른 리스트를 가리키지 못하게 final로 선언
    private final List<Reservation> reservations = new ArrayList<>();

    // 예매 번호가 중복되지 않게 관리
    // 1번부터 번호표 매겨줌
    private int nextReservationId = 1;

    public ReservationRepository() { initializeReservations();}

    // 기능 확인을 위한 초기 예매 데이터
    // LocalDate.now().plusDays(n) : 오늘로부터 n일 뒤
    private void initializeReservations() {
        save(new Reservation("오디세이", Genre.ACTION, LocalTime.of(10, 30), LocalDate.now().plusDays(1), "F10", "홍길동", 15000));
        save(new Reservation("스파이더맨-브랜드 뉴 데이", Genre.ACTION, LocalTime.of(17, 50), LocalDate.now().plusDays(2), "H09", "김철수", 13000));
        save(new Reservation("오디세이", Genre.ACTION, LocalTime.of(13, 20), LocalDate.now().plusDays(1), "I13", "박영희", 18000));
        save(new Reservation("해피엔드", Genre.DRAMA, LocalTime.of(12, 0), LocalDate.now().plusDays(1), "D05", "김민수", 18000));
        save(new Reservation("사랑의 하츄핑", Genre.ANIMATION, LocalTime.of(15, 45), LocalDate.now().plusDays(2), "K11", "이동민", 21000));
        save(new Reservation("오케이마담2", Genre.COMEDY, LocalTime.of(10, 30), LocalDate.now().plusDays(2), "H09", "최원희", 10000));
        save(new Reservation("마루 밑 아리에티", Genre.ANIMATION, LocalTime.of(21, 00), LocalDate.now().plusDays(3), "F01", "오정원", 15000));
        save(new Reservation("스파이더맨-브랜드 뉴 데이", Genre.ACTION, LocalTime.of(17, 50), LocalDate.now().plusDays(1), "F01", "박소희", 18000));
        save(new Reservation("극한직업", Genre.COMEDY, LocalTime.of(20, 0), LocalDate.now().plusDays(3), "E02", "유지훈", 15000));
        save(new Reservation("겟아웃", Genre.THRILLER, LocalTime.of(22, 15), LocalDate.now().plusDays(1), "C14", "한우진", 15000));
        save(new Reservation("더 드라마", Genre.ROMANCE, LocalTime.of(19, 30), LocalDate.now().plusDays(4), "K09", "박상윤", 15000));
        save(new Reservation("더 드라마", Genre.ROMANCE, LocalTime.of(19, 30), LocalDate.now().plusDays(2), "D06", "송예린", 15000));
        save(new Reservation("애정만세", Genre.DRAMA, LocalTime.of(18, 10), LocalDate.now().plusDays(1), "A14", "황슬기", 15000));
        save(new Reservation("애정만세", Genre.DRAMA, LocalTime.of(18, 10), LocalDate.now().plusDays(1), "F07", "한선우", 15000));
        save(new Reservation("명탐정 코난 - 하이웨이의 타천사", Genre.ANIMATION, LocalTime.of(13, 50), LocalDate.now().plusDays(4), "B07", "양호연", 15000));
    }

    // 예매내역의 목록을 저장
    // 이때 예매번호는 자동으로 붙고, +1씩 늘어남
    public void save(Reservation reservation) {
        reservation.setReservationId(nextReservationId++);
        reservations.add(reservation);
    }

    // 전체 예매 내역 조회
    public List<Reservation> findAll() {
        return new ArrayList<>(reservations);
    }

    // 예매 번호와 일치하는 예매 내역 조회
    public Reservation findById(int id) {
        for (int i =0; i < reservations.size(); i++) {
            Reservation reservation = reservations.get(i);
            if (reservation.getReservationId() == id) {
                return reservation;
            }
        }
        return null; // 일치하는 예매번호가 없을 때
    }

}
