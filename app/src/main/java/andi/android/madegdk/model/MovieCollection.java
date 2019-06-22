package andi.android.madegdk.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MovieCollection implements Parcelable {

    @SerializedName("movies")
    private ArrayList<Movie> movies;

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.movies);
    }

    public MovieCollection() {
    }

    protected MovieCollection(Parcel in) {
        this.movies = in.createTypedArrayList(Movie.CREATOR);
    }

    public static final Parcelable.Creator<MovieCollection> CREATOR = new Parcelable.Creator<MovieCollection>() {
        @Override
        public MovieCollection createFromParcel(Parcel source) {
            return new MovieCollection(source);
        }

        @Override
        public MovieCollection[] newArray(int size) {
            return new MovieCollection[size];
        }
    };
}
