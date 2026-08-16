package com.ohgiraffers.moviereservationsystem.model;

public enum Genre {
    ACTION(1, "액션"),
    COMEDY(2, "코미디"),
    ROMANCE(3, "로맨스"),
    DRAMA(4, "드라마"),
    ANIMATION(5, "애니메이션"),
    THRILLER(6, "스릴러");

    // 화면에 표시할 이름과 숫자는 생성 후 변경하지 않음
    private final int genreNumber;
    private final String description;

    Genre(int genreNumber, String description) {
        this.genreNumber = genreNumber;
        this.description = description;
    }

    public int getGenreNumber() {
        return genreNumber;
    }

    public String getDescription() {
        return description;
    }

    // 사용자가 입력한 장르 번호와 일치하는 값 반환
    public static Genre fromGenreNumber(int genreNumber) {

        Genre[] genres = Genre.values();

        for (int i = 0; i < genres.length; i++) {
            if (genres[i].genreNumber == genreNumber) {
                return genres[i];
            }
        }
        // 일치하는 장르가 없으면 잘못된 번호로 판단
        throw new IllegalArgumentException("목록에 있는 장르 번호를 입력해주세요.");
    } // fromGenreNumber()
}

