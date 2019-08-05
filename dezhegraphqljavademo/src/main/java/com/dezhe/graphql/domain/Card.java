package com.dezhe.graphql.domain;

/**
 * @Author dezhe
 * @Date 2019/8/3 12:11
 */
public class Card {

    private String cardNumber;

    private Long userId;

    public Card() {
    }

    public Card(String cardNumber, Long userId) {
        this.cardNumber = cardNumber;
        this.userId = userId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
