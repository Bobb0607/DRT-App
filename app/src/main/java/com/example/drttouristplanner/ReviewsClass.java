package com.example.drttouristplanner;

public class ReviewsClass {
    String review_id, review, ratings,place_id, date_time_rating,user_id;

    public ReviewsClass(String review_id, String review, String ratings, String place_id, String date_time_rating, String user_id) {
        this.review = review;
        this.review_id = review_id;
        this.ratings = ratings;
        this.place_id = place_id;
        this.date_time_rating = date_time_rating;
        this.user_id = user_id;
    }

    public String getReview_id() {
        return review_id;
    }

    public void setReview_id(String review_id) {
        this.review_id = review_id;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public String getDate_time_rating() {
        return date_time_rating;
    }

    public void setDate_time_rating(String date_time_rating) {
        this.date_time_rating = date_time_rating;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
