package com.example.Phase;

public class Album extends Media implements Comparable<Album>{
    protected String artist;
    protected String song;

    public Album(String code,String title, int number_of_copies, String artist, String song) {
        super(code,title, number_of_copies);
        this.artist=artist;
        this.song=song;
    }

    @Override
    public String getTitle() {
        return super.getTitle();
    }

    @Override
    public void setTitle(String title) {
        super.setTitle(title);
    }

    @Override
    public String info() {
        return "Album"+";"+code +";" + title+ ";" + number_of_copies + ";" + artist+ ";" + song;
    }

    @Override
    public String type() {
        return "Album";
    }

    @Override
    public String getRating() {
        return null;
    }

    @Override
    public double getWeight() {
        return 0;
    }

    @Override
    public int getNumber_of_copies() {
        return super.getNumber_of_copies();
    }

    @Override
    public void setNumber_of_copies(int number_of_copies) {
        super.setNumber_of_copies(number_of_copies);
    }

    public String getArtist() {
        return artist;
    }


    public String getSong() {
        return song;
    }

    @Override
    public String toString() {
        return "Album{"
                +"code='" + code +
                ",title='" + title + '\'' +
                ",number_of_copies=" + number_of_copies +
                ",artist='" + artist + '\'' +
                ",song='" + song + '\'' +
                '}'+"\n";
    }

    @Override
    public int compareTo(Album o) {
        return this.getTitle().compareTo(o.getTitle());
    }
}
