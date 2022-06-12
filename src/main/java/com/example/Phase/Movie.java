package com.example.Phase;

public class Movie extends Media implements Comparable<Movie>{
    protected String rating;


    public Movie(String code,String title, int number_of_copies, String rating) {
        super(code,title, number_of_copies);
        this.rating=rating;
    }

    @Override
    public int getNumber_of_copies() {
        return super.getNumber_of_copies();
    }

    @Override
    public void setNumber_of_copies(int number_of_copies) {
        super.setNumber_of_copies(number_of_copies);
    }

    @Override
    public String getTitle() {
        return super.getTitle();
    }

    @Override
    public void setTitle(String title) {
        super.setTitle(title);
    }

    public String getRating() {
        return rating;
    }

    @Override
    public double getWeight() {
        return 0;
    }

    @Override
    public String getArtist() {
        return null;
    }

    @Override
    public String getSong() {
        return null;
    }

    public String info() {
        return "Movie" +";"+code+ ";" + title + ";" + number_of_copies + ";" + rating;
    }

    @Override
    public String type() {
        return "Movie";
    }

    @Override
    public String toString() {
        return "Movie{"
                +"code='" + code +
                ",title='" + title + '\'' +
                ", number_of_copies=" + number_of_copies +
                ", rating='" + rating + '\'' +
                '}'+"\n";
    }

    @Override
    public int compareTo(Movie o) {
        return this.getTitle().compareTo(o.getTitle());
    }
}
