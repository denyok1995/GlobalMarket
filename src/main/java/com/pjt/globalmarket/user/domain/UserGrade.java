package com.pjt.globalmarket.user.domain;

public enum UserGrade {
    BRONZE("bronze"), SILVER("silver"), GOLD("gold"), DIAMOND("diamond");

    private String grade;
    UserGrade(String grade) {
        this.grade = grade;
    }

    public String getGrade() {
        return this.grade;
    }
}
