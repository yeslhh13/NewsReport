package com.example.android.newsreport;

/**
 * Created by gowon on 2017-02-26.
 */

public class News {
    private String mTitle;
    private String mPublishDate;
    private String mSectionName;
    private String mWebURL;

    public News(String title, String publishDate, String sectionName, String webUrl) {
        this.mTitle = title;
        this.mPublishDate = publishDate;
        this.mSectionName = sectionName;
        this.mWebURL = webUrl;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getPublishDate() {
        return mPublishDate;
    }

    public String getSectionName() {
        return mSectionName;
    }

    public String getWebURL() {
        return mWebURL;
    }

    @Override
    public String toString() {
        return "News{" +
                "mTitle='" + mTitle + '\'' +
                ", mPublishDate='" + mPublishDate + '\'' +
                ", mSectionName='" + mSectionName + '\'' +
                ", mWebURL='" + mWebURL + '\'' +
                '}';
    }
}
