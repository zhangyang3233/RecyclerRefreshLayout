package com.dinuscxj.example.model;

public class OpenProjectModel {
    public static final String MORE_CURSOR = "more_cursor";

    private final String mTitle;
    private final String mContent;
    private final String mAuthor;
    private final String mColor;

    public OpenProjectModel(String mTitle, String mContent, String mAuthor, String mColor) {
        this.mTitle = mTitle;
        this.mContent = mContent;
        this.mAuthor = mAuthor;
        this.mColor = mColor;
    }

    public OpenProjectModel(String mTitle, String mContent, String mColor) {
        this(mTitle, mContent, "dinus", mColor);
    }

    public String getTitle() {
        return mTitle;
    }

    public String getContent() {
        return mContent;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getColor() {
        return mColor;
    }

}
