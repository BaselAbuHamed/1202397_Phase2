package com.example.Phase;

public abstract class Media {

    protected String code;
    protected String title;
    protected int number_of_copies;

    public Media(String code,String title, int number_of_copies){
        this.title=title;
        this.number_of_copies=number_of_copies;
        this.code=code;
    }

    public int getNumber_of_copies() {
        return number_of_copies;
    }

    public void setNumber_of_copies(int number_of_copies) {
        this.number_of_copies = number_of_copies;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void CopiesAvailable(boolean add) {

        if (add) {
            this.number_of_copies++;
        } else {
            this.number_of_copies--;
        }

    }
    public abstract String info();
    public abstract String type();
    public abstract String getRating();
    public abstract double  getWeight();
    public abstract String getArtist();
    public abstract String getSong();

   /* @Override
    public int compareTo(Media o) {

        return this.getTitle().compareTo(o.getTitle());
    }*/

    @Override
    public String toString() {
        return "Media{" +
                "title='" + title + '\'' +
                ", number_of_copies=" + number_of_copies +
                '}';
    }
}
