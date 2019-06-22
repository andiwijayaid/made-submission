package andi.android.madegdk.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Movie implements Parcelable {

    @SerializedName("poster")
    private String poster;
    @SerializedName("title")
    private String title;
    @SerializedName("date")
    private String date;
    @SerializedName("rating")
    private Integer rating;
    @SerializedName("overview")
    private String overview;

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.poster);
        dest.writeString(this.title);
        dest.writeString(this.date);
        dest.writeValue(this.rating);
        dest.writeString(this.overview);
    }

    public Movie(){}

    public Movie(String poster, String title, String date, Integer rating, String overview) {
        this.poster = poster;
        this.title = title;
        this.date = date;
        this.rating = rating;
        this.overview = overview;
    }

    protected Movie(Parcel in) {
        this.poster = in.readString();
        this.title = in.readString();
        this.date = in.readString();
        this.rating = (Integer) in.readValue(Integer.class.getClassLoader());
        this.overview = in.readString();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
