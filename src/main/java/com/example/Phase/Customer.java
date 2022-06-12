package com.example.Phase;

import java.util.ArrayList;

public class Customer implements Comparable <Customer> {
    protected String ID;
    protected String name;
    protected String address;
    protected String mobile;
    protected String plan;
    protected int limit;

    protected ArrayList <String> customer_rented_it=new ArrayList<>();
    protected ArrayList <String> add_to_cart=new ArrayList<>();

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Customer(String ID, String name, String address, String mobile, String plan){
        this.ID=ID;
        this.mobile=mobile;
        this.address=address;
        this.name=name;
        this.plan=plan;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getLimit() {
        return limit;
    }


    public ArrayList<String> getCustomer_rented_it() {
        return customer_rented_it;
    }

    public void setCustomer_rented_it(String rented_it) {
        customer_rented_it.add(rented_it);
    }

    public ArrayList<String> getAdd_to_cart() {
        return add_to_cart;
    }

    public void setAdd_to_cart(String addToCart) {
        add_to_cart.add(addToCart) ;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", plan='" + plan + '\'' +
                ", customer cart=" + add_to_cart +
                ", customer rented it=" + customer_rented_it +
                '}';
    }

    @Override
    public int compareTo(Customer o) {

        return this.getName().compareTo(o.getName());
    }
}
