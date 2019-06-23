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
    @SerializedName("runtime")
    private Integer runtime;
    @SerializedName("budget")
    private Integer budget;
    @SerializedName("revenue")
    private Integer revenue;
    @SerializedName("overview")
    private String overview;

    public Integer getBudget() {
        return budget;
    }

    public void setBudget(Integer budget) {
        this.budget = budget;
    }

    public Integer getRevenue() {
        return revenue;
    }

    public void setRevenue(Integer revenue) {
        this.revenue = revenue;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

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

    public Movie() {
    }

    public Movie(String poster, String title, String date, Integer rating, Integer runtime, Integer budget, Integer revenue, String overview) {
        this.poster = poster;
        this.title = title;
        this.date = date;
        this.rating = rating;
        this.runtime = runtime;
        this.budget = budget;
        this.revenue = revenue;
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
        dest.writeValue(this.runtime);
        dest.writeValue(this.budget);
        dest.writeValue(this.revenue);
        dest.writeString(this.overview);
    }

    protected Movie(Parcel in) {
        this.poster = in.readString();
        this.title = in.readString();
        this.date = in.readString();
        this.rating = (Integer) in.readValue(Integer.class.getClassLoader());
        this.runtime = (Integer) in.readValue(Integer.class.getClassLoader());
        this.budget = (Integer) in.readValue(Integer.class.getClassLoader());
        this.revenue = (Integer) in.readValue(Integer.class.getClassLoader());
        this.overview = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
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
