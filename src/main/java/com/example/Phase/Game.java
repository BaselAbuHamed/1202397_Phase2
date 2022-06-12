package com.example.Phase;

public class Game extends Media implements Comparable<Game>{
    protected double weight;

    public Game(String code,String title, int number_of_copies, double weight) {
        super(code,title, number_of_copies);
        this.weight=weight;
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
        return "Game" +";"+code+ ";" + title + ";" + number_of_copies + ";" + weight ;
    }

    @Override
    public String type() {
        return "Game";
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public String getArtist() {
        return null;
    }

    @Override
    public String getSong() {
        return null;
    }

    @Override
    public String getRating() {
        return null;
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
    public String toString() {
        return "Game{" +"code='" + code +
                ", title='" + title + '\'' +
                ", number_of_copies=" + number_of_copies +
                ",weight=" + weight +'\'' +
                '}'+"\n";
    }

    @Override
    public int compareTo(Game o) {
        return this.getTitle().compareTo(o.getTitle());
    }
}
