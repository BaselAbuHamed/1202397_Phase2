package com.example.Phase;

import java.util.ArrayList;
import java.util.Collections;

public class MediaRental implements MediaRentalInt {

    protected int limitedPlanMax=2;
    protected ArrayList <Customer> customer=new ArrayList<>();
    protected ArrayList <Media> media=new ArrayList<>();

    @Override
    public void addCustomer(String ID,String name, String address,String mobile, String plan) {
        customer.add(new Customer(ID, name, address,mobile, plan));
    }

    @Override
    public void addMovie(String code,String title, int copiesAvailable, String rating) {
       media.add(new Movie(code,title,copiesAvailable,rating));
    }

    @Override
    public void addGame(String code,String title, int copiesAvailable, double weight) {
        media.add(new Game(code,title,copiesAvailable,weight));
    }

    @Override
    public void addAlbum(String code,String title, int copiesAvailable, String artist, String songs) {
        media.add(new Album(code,title,copiesAvailable,artist,songs));
    }

    @Override
    public void setLimitedPlanLimit(int value) {

        this.limitedPlanMax = value;

    }

    @Override
    public String getAllCustomersInfo() {
        StringBuilder customerInfo = new StringBuilder();
        Collections.sort(customer);

        try {
            for (Customer value : customer) {
                customerInfo.append(value.toString()).append("\n");
            }

        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Array is out of Bounds"+e);
        }

        return customerInfo.toString();
    }

    @Override
    public String getAllMediaInfo() {
        StringBuilder mediaInfo = new StringBuilder();

        try {
            for (Media value : media) {
                mediaInfo.append(value.toString()).append("\n");
            }

        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Array is out of Bounds"+e);
        }

        return mediaInfo.toString();
    }

    @Override
    public boolean addToCart(String type,String customerID, String mediaCode) {

        int flagCustomer = -1;
        int flagMedia =-1;

        try {
            for (int i = 0; i <customer.size(); i++) {
                if (customer.get(i).getID().equals(customerID)) {
                    flagCustomer = i;
                }
            }
            if (flagCustomer == -1) {
                System.out.println("check your customer name entered because not found in the list");
                return false;
            }

        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Array is out of Bounds"+e);
        }
        try {
            switch (type) {
                case "Movie":
                    for (int i = 0; i < media.size(); i++) {
                        if (media.get(i).getCode().equals(mediaCode)) {
                            if (media.get(i).getClass().equals(Movie.class))
                                flagMedia = i;
                        }
                    }
                    break;
                case "Game":
                    for (int i = 0; i < media.size(); i++) {
                        if (media.get(i).getCode().equals(mediaCode)) {
                            if (media.get(i).getClass().equals(Game.class))
                                flagMedia = i;
                        }
                    }
                    break;
                case "Album":
                    for (int i = 0; i < media.size(); i++) {
                        if (media.get(i).getCode().equals(mediaCode)) {
                            if (media.get(i).getClass().equals(Album.class))
                                flagMedia = i;
                        }
                    }
                    break;
            }

            if (flagMedia == -1) {
                System.out.println("check your media title entered because not found in the list");
                return false;
            }

        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Array is out of Bounds"+e);
        }
        customer.get(flagCustomer).add_to_cart.add(media.get(flagMedia).getCode());

        /*int flagCustomer = -1;
        int flagMedia =-1;

        try {
            for (int i = 0; i < customer.size(); i++) {
                if (customer.get(i).getName().equals(customerID)) {
                    flagCustomer = i;
                }
            }
            if (flagCustomer == -1) {
                System.out.println("check your customer name entered because not found in the list");
                return false;
            }

        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Array is out of Bounds"+e);
        }
        try {
            for (int i = 0; i < media.size(); i++) {
                if (media.get(i).getTitle().equals(mediaTitle)) {
                    flagMedia =i;
                }
            }
            if (flagMedia == -1) {
                System.out.println("check your media title entered because not found in the list");
                return false;
            }

        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Array is out of Bounds"+e);
        }
        System.out.println("Adding ["+mediaTitle+"] to cart "+customerID+"\n");
        customer.get(flagCustomer).add_to_cart.add(mediaTitle);*/
        return true;
    }

    @Override
    public boolean removeFromCart(String customerName, String mediaTitle) {

        try {
            for (Customer value : customer) {
                if (value.getName().equals(customerName)) {
                    value.add_to_cart.remove(mediaTitle);
                    return true;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Array is out of Bounds"+e);
        }

        return false;
    }

    @Override
    public boolean removeFromRent(String customerID, String mediaCode) {

        for (Customer bufferCustomer : customer) {
            if (bufferCustomer.getID().equals(customerID)) {

                for (int j = 0; j < media.size(); j++) {
                    if (media.get(j).getCode().equals(mediaCode)) {

                        for (int customerCart = 0; customerCart < bufferCustomer.customer_rented_it.size(); customerCart++) {
                            if(mediaCode.equals(bufferCustomer.customer_rented_it.get(customerCart))){

                                bufferCustomer.customer_rented_it.remove(customerCart);
                                media.get(j).CopiesAvailable(true);
                                break;
                            }
                        }
                    }
                }
            }

        }
        return false;
    }
    @Override
    public String processRequests(String customerID) {

        StringBuilder processedRequests = new StringBuilder();
        Collections.sort(customer);

        for (Customer bufferCustomer : customer) {

            int index = 0;
            if(bufferCustomer.getID().equals(customerID)){

                if (bufferCustomer.getPlan().equals("LIMITED")) {

                    for (int customerCart = 0; customerCart < bufferCustomer.add_to_cart.size(); customerCart++) {

                        System.out.println("REQ Ind1 " + index);

                        for (Media bufferMedia : media) {

                            String bufferMediaCode = bufferMedia.getCode();

                            if (bufferMediaCode.equals(bufferCustomer.add_to_cart.get(index))) {

                                if (bufferMedia.getNumber_of_copies() > 0) {

                                    if (bufferCustomer.customer_rented_it.size() < limitedPlanMax) {

                                        bufferCustomer.add_to_cart.remove(index);
                                        bufferCustomer.customer_rented_it.add(bufferMediaCode);
                                        bufferMedia.CopiesAvailable(false);

                                        processedRequests.append("Sending [").append(bufferMediaCode).append("] to ").append(bufferCustomer.getName()).append("\n");
                                        break;
                                    } else {
                                        System.out.println("cant add another item because your plan limited");
                                    }
                                } else {
                                    System.out.println("No copies (" + bufferMediaCode + ") are currently available");
                                }
                            }
                        }
                        index++;
                    }
                }

                else if (bufferCustomer.getPlan().equals("UNLIMITED")) {

                    for (int j = 0; j < bufferCustomer.add_to_cart.size(); j++) {
                        for (Media bufferMedia : media) {

                            String bufferMediaCode = bufferMedia.getCode();

                            if (bufferMediaCode.equals(bufferCustomer.add_to_cart.get(index))) {

                                if (bufferMedia.getNumber_of_copies() > 0) {
                                    bufferCustomer.add_to_cart.remove(index);
                                    bufferCustomer.customer_rented_it.add(bufferMediaCode);
                                    bufferMedia.CopiesAvailable(false);

                                    processedRequests.append("Sending ").append(bufferMediaCode).append(" to ").append(bufferCustomer.getName()).append("\n");
                                    break;
                                } else {
                                    System.out.println("No copies (" + bufferMediaCode + ") are currently available");
                                }
                            }
                        }
                        index++;
                    }
                }
            }

        }
        return processedRequests.toString();
    }

    @Override
    public boolean returnMedia(String customerName, String mediaTitle) {
        int index = -1;

        for (int i = 0; i < customer.size(); i++) {
            if (customer.get(i).getName().equals(customerName)) {
                index = i;
            }
        }
        if (index == -1) {
            return false;
        }
        Customer c = customer.get(index);

        for (int i = 0; i < c.add_to_cart.size(); i++) {
            if (c.add_to_cart.get(i).equals(mediaTitle)) {
                c.add_to_cart.remove(mediaTitle);
                for (Media value : media) {
                    if (value.getTitle().equals(mediaTitle)) {
                        value.CopiesAvailable(true);
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public ArrayList<String> searchMedia(String title, String rating, String artist, String songs) {

        ArrayList<String> sorted = new ArrayList<>();

        boolean TCond , RCond = false, ACond = false, SCond = false;

        for (Media m : media) {

            TCond = (title == null || m.getTitle().equals(title));
            if (m instanceof Movie mov) {
                RCond = (rating == null || mov.getRating().equals(rating));
                ACond = (artist == null);
                SCond = (songs == null);

            } else if (m instanceof Album al) {
                ACond = (artist == null || al.getArtist().equals(artist));
                SCond = (songs == null || al.getSong().contains(songs));
                RCond = (rating == null);
            }
            if (TCond && RCond && ACond && SCond) {
                sorted.add(m.getTitle());
            }
        }
        Collections.sort(sorted);
        return sorted;
    }
}
