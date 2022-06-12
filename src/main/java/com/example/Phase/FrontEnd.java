package com.example.Phase;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class FrontEnd extends Application {

    static MediaRental mediaRental =new MediaRental();

    static  ArrayList <String> Archive=new ArrayList<>();

    private static void writeCustomers (File customerFile) {
        StringBuilder rented =new StringBuilder();
        StringBuilder addCart =new StringBuilder();

        try {
            if (customerFile.exists()) {
                PrintWriter writeCus = new PrintWriter(customerFile); //file writer
                for (Customer customer : mediaRental.customer) {

                    for (int i=0;i <customer.customer_rented_it.size();i++) {
                        rented.append(customer.customer_rented_it.get(i)).append(",");
                    }

                    for (int i=0;i <customer.add_to_cart.size();i++) {
                        addCart.append(customer.add_to_cart.get(i)).append(",");
                    }

                    if(addCart.length() > 0){
                        addCart.deleteCharAt(addCart.length() - 1);
                    }

                    if(rented.length() > 0){
                        rented.deleteCharAt(rented.length() - 1);
                    }
                    writeCus.print(customer.getID()+";"+customer.getName() + ";"+customer.getAddress()+";" +customer.getMobile()+";"+customer.getPlan()+";" + addCart+ ";" + rented + "\n");
                    rented = new StringBuilder();
                    addCart = new StringBuilder();
                }
                writeCus.close();
            }
            else {
                System.out.println("Customer file not found!");
            }
        }
        catch (FileNotFoundException e){
            System.out.println("Customer file error!");
        }

    }
    private static void readCustomers(File customerFile) {
        try {
            if (customerFile.exists()){
                Scanner scanCus = new Scanner (customerFile);
                while (scanCus.hasNext()) {

                    String bufferLine = scanCus.nextLine();
                    String[] bufferSplit = bufferLine.split(";");

                    String ID = bufferSplit[0].trim();
                    String name = bufferSplit[1].trim();
                    String address = bufferSplit[2].trim();
                    String mobile = bufferSplit[3].trim();
                    String plan = bufferSplit[4].trim();

                    Customer tmp = new Customer(ID,name, address,mobile, plan);

                    try {
                        String cart = bufferSplit[5];
                        String [] cartArr = cart.split(",");

                        for (String s : cartArr) {
                            tmp.add_to_cart.add(s);
                        }
                    }
                    catch (Exception e) {
                        System.out.println("No items in this user's cart.");
                    }
                    try {
                        String owned = bufferSplit[6];
                        String [] ownedArr = owned.split(",");

                        for (String s : ownedArr) {
                            tmp.customer_rented_it.add(s);
                        }
                    }
                    catch (Exception e) {
                        System.out.println("No items in this user's owned list.");
                    }
                    mediaRental.customer.add(tmp);
                }
                scanCus.close();
            }
            else {
                System.out.println("Customer file not found!\nCreating new file...");
                try{
                    customerFile.createNewFile();
                }
                catch (Exception e) {
                    System.out.println("Error creating file!");
                }
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("Customer file error!");
        }
    }

    private static void readMedia(File mediaFile) {
        try {
            if (mediaFile.exists()){
                Scanner scanMedia = new Scanner (mediaFile); //file scanner
                while (scanMedia.hasNext()) {
                    String buffer = scanMedia.nextLine();
                    // System.out.println(buffer);
                    String[] bufferArr = buffer.split(";");

                    String code = bufferArr[1].trim();
                    String title = bufferArr[2].trim();
                    int copies = Integer.parseInt(bufferArr[3]);

                    if (bufferArr[0].trim().equalsIgnoreCase("Game")) {

                        double grams = Double.parseDouble(bufferArr[4]);
                        Game tmp = new Game(code,title, copies, grams);
                        mediaRental.media.add(tmp);
                    }

                    else if (bufferArr[0].trim().equalsIgnoreCase("Movie")) {

                        String rating = bufferArr[4].trim();
                        Movie tmp = new Movie(code,title, copies, rating);
                        mediaRental.media.add(tmp);
                    }

                    else if (bufferArr[0].trim().equalsIgnoreCase("Album")) {

                        String artist = bufferArr[4].trim();
                        String songs = bufferArr[5].trim();
                        Album tmp = new Album(code,title, copies, artist, songs);
                        mediaRental.media.add(tmp);
                    }

                }
                scanMedia.close();
            }
            else {
                System.out.println("Media file not found!\nCreating a new file...");
                try{
                    mediaFile.createNewFile();
                }
                catch (IOException e) {
                    System.out.println("Can't create file!");
                }
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("Media file error!");
        }

    }
    private static void writeMedia (File mediaFile) {
        try {
            if (mediaFile.exists()){
                PrintWriter writeMed = new PrintWriter(mediaFile); //file writer
                for (Media media : mediaRental.media) {
                    writeMed.print(media.info()+"\n");
                }
                writeMed.close();
            }
            else {
                System.out.println("Media file not found!");
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("Media file error!");
        }

    }

    private static void readArchive(File archiveFile) {
        try {
            if (archiveFile.exists()){
                Scanner scanArchive = new Scanner (archiveFile); //file scanner
                while (scanArchive.hasNext()) {
                    Archive.add(scanArchive.nextLine());
                }
                scanArchive.close();
            }
            else {
                System.out.println("Archive file not found!\nCreating a new file...");
                try{
                    archiveFile.createNewFile();
                }
                catch (IOException e) {
                    System.out.println("Can't create file!");
                }
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("Media file error!");
        }

    }
    private static void writeArchive(File archiveFile){
        try {
            if (archiveFile.exists()){
                PrintWriter writeArc = new PrintWriter(archiveFile); //file writer

                    writeArc.print(Archive.toString()+"\n");
                writeArc.close();
            }
            else {
                System.out.println("Archive file not found!");
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("Archive file error!");
        }
    }

    private void addBinding(Button submitBtn, TextField... textFields) {
        for (int i = 0; i < textFields.length; i++) {
            TextField tf = textFields[i];
            Node node = i == textFields.length - 1 ? submitBtn : textFields[i + 1];
            node.disableProperty().bind(
                    Bindings.createBooleanBinding(() -> tf.isDisable() || tf.getText() == null || tf.getText().isEmpty(),
                            tf.textProperty(), tf.disableProperty()));
        }
    }
    private void addBinding(RadioButton submitBtn, TextField... textFields) {
        for (int i = 0; i < textFields.length; i++) {
            TextField tf = textFields[i];
            Node node = i == textFields.length - 1 ? submitBtn : textFields[i + 1];
            node.disableProperty().bind(
                    Bindings.createBooleanBinding(() -> tf.isDisable() || tf.getText() == null || tf.getText().isEmpty(),
                            tf.textProperty(), tf.disableProperty()));
        }
    }

                                @Override
    public void start(Stage stage) {

        File customerFile = new File("customers.txt");//Customer File
        File mediaFile = new File("media.txt");//Media File
        File archiveFile = new File("archive.txt");//Media File

        //Check Customer File is existed and create file if not existed
        //Is file exist read data from Customer File and save as in customer array list
        readCustomers(customerFile);

        //Check Media File is existed and create file if not existed
        //Is file exist read data from Media File and save as in media array list
        readMedia(mediaFile);

        readArchive(archiveFile);

        //Create Group and Scene for primary Stage
        //And set stage maximize to seen full window
        stage.setMaximized(true);
        stage.setFullScreen(true);
        Group primaryGroup =new Group();
        Scene primaryScene = new Scene(primaryGroup, 1650, 900);

        //Create object shadow to be created behind the specified node
        //Set shadow color blue ,50 radius , 50 width ,and 50 height
        DropShadow dropShadow=new DropShadow();
        dropShadow.setColor(Color.DARKBLUE);
        dropShadow.setRadius(50);
        dropShadow.setWidth(50);
        dropShadow.setHeight(50);

        Image backGround =new Image("background.jpg");
        ImageView viewBG = new ImageView(backGround);

        Image backGround1 =new Image("background2.jpg");
        ImageView viewBG1 = new ImageView(backGround1);

        Image backGround2 =new Image("background3.jpg");
        ImageView viewBG2 = new ImageView(backGround2);

        //set background for primary Scene
        primaryGroup.getChildren().add(viewBG);

        Font font = new Font(35);
        Font fontMedia = new Font(30);
        Font font1 = new Font(30);

        Image image=new Image("media.png");
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setLayoutX(700);
        imageView.setLayoutY(80);
        primaryGroup.getChildren().add(imageView);

        //text Rental Media System
        Text text = new Text();
        text.setLayoutX(770);
        text.setLayoutY(680);
        text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR,40));
        text.setFill(Color.WHITE);
        text.setStroke(Color.BLACK);
        text.setStrokeWidth(2);
        text.setText("Rental Media System");
        primaryGroup.getChildren().add(text);

        //text Customer
        Text textCus = new Text();
        textCus.setLayoutX(70);
        textCus.setLayoutY(270);
        textCus.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR,35));
        textCus.setFill(Color.WHITE);
        textCus.setStroke(Color.BLACK);
        textCus.setStrokeWidth(2);
        textCus.setText("Customer");
        primaryGroup.getChildren().add(textCus);

        //text Media
        Text textMed = new Text();
        textMed.setLayoutX(100);
        textMed.setLayoutY(410);
        textMed.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR,35));
        textMed.setFill(Color.WHITE);
        textMed.setStroke(Color.BLACK);
        textMed.setStrokeWidth(2);
        textMed.setText("Media");
        primaryGroup.getChildren().add(textMed);

        //text Rent
        Text textRen = new Text();
        textRen.setLayoutX(115);
        textRen.setLayoutY(580);
        textRen.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR,35));
        textRen.setFill(Color.WHITE);
        textRen.setStroke(Color.BLACK);
        textRen.setStrokeWidth(2);
        textRen.setText("Rent");
        primaryGroup.getChildren().add(textRen);

        //Create button for Customer and set image
        Image c=new Image("buttonCustomer.png");
        Button addCustomer =new Button("",new ImageView(c));
        addCustomer.setStyle("-fx-background-color: null");
        addCustomer.setLayoutX(120);
        addCustomer.setLayoutY(150);
        primaryGroup.getChildren().add(addCustomer);

        //Create button for Media and set image
        Image m=new Image("buttonMedia.png");
        Button addMedia =new Button("",new ImageView(m));
        addMedia.setStyle("-fx-background-color: null");
        addMedia.setLayoutX(120);
        addMedia.setLayoutY(300);
        primaryGroup.getChildren().add(addMedia);

        //Create button for rent and set image
        Image r=new Image("buttonRent.png");
        Button rent =new Button("",new ImageView(r));
        rent.setStyle("-fx-background-color: null");
        rent.setLayoutX(120);
        rent.setLayoutY(470);
        primaryGroup.getChildren().add(rent);

        addCustomer.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent-> addCustomer.setEffect(dropShadow)));
        addCustomer.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent-> addCustomer.setEffect(null)));

        addMedia.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent-> addMedia.setEffect(dropShadow)));
        addMedia.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent-> addMedia.setEffect(null)));

        rent.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent-> rent.setEffect(dropShadow)));
        rent.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent-> rent.setEffect(null)));

        //action add customer move to new scene and select the option need
        addCustomer.setOnAction(actionEvent -> {

            //Create Group and Scene for action addCustomer
            Group groupCustomer =new Group();
            Scene Customer = new Scene(groupCustomer, 5000, 5000);

            //set background for customer Scene
            groupCustomer.getChildren().add(viewBG1);

            //create button to add customer
            Image newCustomer=new Image("addCustomer.png");
            Button customerNew =new Button("Add new Customer",new ImageView(newCustomer));
            customerNew.setStyle("-fx-background-color: null");
            customerNew.setFont(font1);
            customerNew.setLayoutX(50);
            customerNew.setLayoutY(40);
            groupCustomer.getChildren().add(customerNew);

            //create button to delete Customer
            Image DelCustomer=new Image("deleteCustomer.png");
            Button customerDel =new Button("Delete Customer",new ImageView(DelCustomer));
            customerDel.setStyle("-fx-background-color: null");
            customerDel.setFont(font1);
            customerDel.setLayoutX(50);
            customerDel.setLayoutY(160);
            groupCustomer.getChildren().add(customerDel);

            //create button to update information customer
            Image updateCustomer=new Image("updateCustomer.png");
            Button customerUpdate =new Button("Update Information about Customer",new ImageView(updateCustomer));
            customerUpdate.setStyle("-fx-background-color: null");
            customerUpdate.setFont(font1);
            customerUpdate.setLayoutX(50);
            customerUpdate.setLayoutY(280);
            groupCustomer.getChildren().add(customerUpdate);

            //create button to search customer
            Image searchCustomer=new Image("searchCustomer.png");
            Button customerSearch =new Button("Search a Customer by id",new ImageView(searchCustomer));
            customerSearch.setStyle("-fx-background-color: null");
            customerSearch.setFont(font1);
            customerSearch.setLayoutX(50);
            customerSearch.setLayoutY(400);
            groupCustomer.getChildren().add(customerSearch);

            //create button to back primary Scene
            Image backMain=new Image("back.png");
            Button back =new Button("",new ImageView(backMain));
            back.setStyle("-fx-background-color: null");
            back.setFont(font);
            back.setLayoutX(1100);
            back.setLayoutY(600);
            groupCustomer.getChildren().add(back);

            customerNew.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent-> customerNew.setEffect(dropShadow)));
            customerNew.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent-> customerNew.setEffect(null)));

            customerDel.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent-> customerDel.setEffect(dropShadow)));
            customerDel.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent-> customerDel.setEffect(null)));

            customerUpdate.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent-> customerUpdate.setEffect(dropShadow)));
            customerUpdate.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent-> customerUpdate.setEffect(null)));

            customerSearch.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent-> customerSearch.setEffect(dropShadow)));
            customerSearch.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent-> customerSearch.setEffect(null)));

            back.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent-> back.setEffect(dropShadow)));
            back.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent-> back.setEffect(null)));

            //action customerNew move to new scene to add new customer to list
            customerNew.setOnAction(actionEvent12 -> {

                //Create Group and Scene for action customerNew
                Group groupNewCustomer =new Group();
                Scene SceneNewCustomer = new Scene(groupNewCustomer, 1650, 900);

                //set background for SceneNewCustomer Scene
                groupNewCustomer.getChildren().add(viewBG2);

                //text Customer ID
                Text textNC = new Text();
                textNC.setLayoutX(200);
                textNC.setLayoutY(110);
                textNC.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR,35));
                textNC.setFill(Color.WHITE);
                textNC.setStroke(Color.BLACK);
                textNC.setStrokeWidth(2);
                textNC.setText("Customer ID:");
                groupNewCustomer.getChildren().add(textNC);

                //text field to input customer id
                TextField textFieldNC=new TextField();
                textFieldNC.setMinSize(350,70);
                textFieldNC.setLayoutX(630);
                textFieldNC.setLayoutY(70);
                groupNewCustomer.getChildren().add(textFieldNC);

                //text Customer Name
                Text textNC1 = new Text();
                textNC1.setLayoutX(200);
                textNC1.setLayoutY(210);
                textNC1.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR,35));
                textNC1.setFill(Color.WHITE);
                textNC1.setStroke(Color.BLACK);
                textNC1.setStrokeWidth(2);
                textNC1.setText("Customer Name:");
                groupNewCustomer.getChildren().add(textNC1);

                //text field to input customer name
                TextField textFieldNC1=new TextField();
                textFieldNC1.setMinSize(350,70);
                textFieldNC1.setLayoutX(630);
                textFieldNC1.setLayoutY(170);
                groupNewCustomer.getChildren().add(textFieldNC1);

                //text Customer Address
                Text textNC2 = new Text();
                textNC2.setLayoutX(200);
                textNC2.setLayoutY(310);
                textNC2.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR,35));
                textNC2.setFill(Color.WHITE);
                textNC2.setStroke(Color.BLACK);
                textNC2.setStrokeWidth(2);
                textNC2.setText("Customer Address:");
                groupNewCustomer.getChildren().add(textNC2);

                //text field to input customer address
                TextField textFieldNC2=new TextField();
                textFieldNC2.setMinSize(350,70);
                textFieldNC2.setLayoutX(630);
                textFieldNC2.setLayoutY(270);
                groupNewCustomer.getChildren().add(textFieldNC2);

                //text Customer Mobile
                Text textNC3 = new Text();
                textNC3.setLayoutX(200);
                textNC3.setLayoutY(410);
                textNC3.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR,35));
                textNC3.setFill(Color.WHITE);
                textNC3.setStroke(Color.BLACK);
                textNC3.setStrokeWidth(2);
                textNC3.setText("Customer Mobile:");
                groupNewCustomer.getChildren().add(textNC3);

                //text field to input customer mobile
                TextField textFieldNC3=new TextField();
                textFieldNC3.setMinSize(350,70);
                textFieldNC3.setLayoutX(630);
                textFieldNC3.setLayoutY(370);
                groupNewCustomer.getChildren().add(textFieldNC3);

                //text Customer Plan
                Text textNC4 = new Text();
                textNC4.setLayoutX(200);
                textNC4.setLayoutY(510);
                textNC4.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR,35));
                textNC4.setFill(Color.WHITE);
                textNC4.setStroke(Color.BLACK);
                textNC4.setStrokeWidth(2);
                textNC4.setText("Customer Plan:");
                groupNewCustomer.getChildren().add(textNC4);

                ToggleGroup toggleGroup = new ToggleGroup();

                //create two radio button to select customer plan
                //first radio button input limited
                //second radio button input unlimited

    /*first*/   RadioButton limited=new RadioButton("LIMITED");
                limited.setFont(fontMedia);
                limited.setLayoutX(630);
                limited.setLayoutY(470);
                limited.setToggleGroup(toggleGroup);
                groupNewCustomer.getChildren().add(limited);

    /*second*/  RadioButton unlimited =new RadioButton("UNLIMITED");
                unlimited.setFont(fontMedia);
                unlimited.setLayoutX(630);
                unlimited.setLayoutY(520);
                unlimited.setToggleGroup(toggleGroup);
                groupNewCustomer.getChildren().add(unlimited);

                Button addCus=new Button("Add Customer",new ImageView("addCus.png"));
                addCus.setStyle("-fx-background-color: null");
                addCus.setFont(font);
                addCus.setLayoutX(250);
                addCus.setLayoutY(600);
                groupNewCustomer.getChildren().add(addCus);

                Button back1 =new Button("",new ImageView("back1.png"));
                back1.setStyle("-fx-background-color: null");
                back1.setLayoutX(850);
                back1.setLayoutY(600);
                groupNewCustomer.getChildren().add(back1);

                addBinding(addCus, textFieldNC, textFieldNC1, textFieldNC2, textFieldNC3);
                addBinding(limited, textFieldNC, textFieldNC1, textFieldNC2, textFieldNC3);
                addBinding(unlimited, textFieldNC, textFieldNC1, textFieldNC2, textFieldNC3);

                addCus.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent-> addCus.setEffect(dropShadow)));
                addCus.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent-> addCus.setEffect(null)));

                back1.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent-> back1.setEffect(dropShadow)));
                back1.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent-> back1.setEffect(null)));

                /*action add cus when click it , there's two steps.The first step is to check the customer's ID
                 if it exists or not.The second step if it doesn't exist, it creates a new customer and takes its
                  statement from the text fields and saves it in array list for customer If it exists, it displays
                  a warning message that the ID is used.*/
                addCus.setOnAction(actionEvent111 -> {

                    //Create a variable of a boolean type and its initial value is false.
                    boolean found = false;

                    /*for loop spin from 0 to array list customer length, if id customer existed makes value the found
                     variable true and break for loop */
                    for(int i=0 ; i <mediaRental.customer.size() ; i++){
                        if(textFieldNC.getText().equals(mediaRental.customer.get(i).getID())){
                            found = true;
                            break;
                        }
                    }

                    /*if statement to check value of found ,If the value is true, it displays a warning message to
                     the user that the ID was used by , If the value is false, it creates a new client and takes
                     its data from the text fields. */
                    if(!found){
                            String ID=textFieldNC.getText();
                            String Name=textFieldNC1.getText();
                            String Address =textFieldNC2.getText();
                            String Mobile =textFieldNC3.getText();
                            String Plan =null;

                            if (limited.isSelected()){
                                Plan="LIMITED";
                            }
                            else if(unlimited.isSelected()){
                                Plan="UNLIMITED";
                            }

                            mediaRental.addCustomer(ID, Name, Address,Mobile, Plan);
                            writeCustomers(customerFile);

                    }
                    else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("The ID entered is already exists.");
                        alert.show();

                    }
                    //After everything is done, the text fields are cleaned.
                    textFieldNC.clear();
                    textFieldNC1.clear();
                    textFieldNC2.clear();
                    textFieldNC3.clear();
                    limited.setSelected(false);
                    unlimited.setSelected(false);
                });

                /*Action back to return customer scene*/
                back1.setOnAction(actionEvent1 -> stage.setScene(Customer));

                stage.setScene(SceneNewCustomer);
            });

            //action customerDel move to new scene to delete customer from list
            customerDel.setOnAction(actionEvent13 -> {

                //Create Group and Scene for action customerDel
                Group groupDelCustomer =new Group();
                Scene SceneDelCustomer = new Scene(groupDelCustomer, 1650, 900);

                //set background for SceneDelCustomer Scene
                groupDelCustomer.getChildren().add(viewBG2);

                //text Customer ID
                Text textNC = new Text();
                textNC.setLayoutX(200);
                textNC.setLayoutY(110);
                textNC.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR,35));
                textNC.setFill(Color.WHITE);
                textNC.setStroke(Color.BLACK);
                textNC.setStrokeWidth(2);
                textNC.setText("Customer ID:");
                groupDelCustomer.getChildren().add(textNC);

                //text field to input customer id
                TextField textFieldNC=new TextField();
                textFieldNC.setMinSize(350,70);
                textFieldNC.setLayoutX(630);
                textFieldNC.setLayoutY(70);
                groupDelCustomer.getChildren().add(textFieldNC);

                //text Customer Name
                Text textNC1 = new Text();
                textNC1.setLayoutX(200);
                textNC1.setLayoutY(210);
                textNC1.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR,35));
                textNC1.setFill(Color.WHITE);
                textNC1.setStroke(Color.BLACK);
                textNC1.setStrokeWidth(2);
                textNC1.setText("Customer Name:");
                groupDelCustomer.getChildren().add(textNC1);

                //text field to show customer name
                TextField textFieldNC1=new TextField();
                textFieldNC1.setMinSize(350,70);
                textFieldNC1.setEditable(false);
                textFieldNC1.setLayoutX(630);
                textFieldNC1.setLayoutY(170);
                groupDelCustomer.getChildren().add(textFieldNC1);

                //text Customer Name
                Text textNC2 = new Text();
                textNC2.setLayoutX(200);
                textNC2.setLayoutY(310);
                textNC2.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR,35));
                textNC2.setFill(Color.WHITE);
                textNC2.setStroke(Color.BLACK);
                textNC2.setStrokeWidth(2);
                textNC2.setText("Customer Address:");
                groupDelCustomer.getChildren().add(textNC2);

                //text field to show customer Address
                TextField textFieldNC2=new TextField();
                textFieldNC2.setMinSize(350,70);
                textFieldNC2.setEditable(false);
                textFieldNC2.setLayoutX(630);
                textFieldNC2.setLayoutY(270);
                groupDelCustomer.getChildren().add(textFieldNC2);

                //text Customer Mobile
                Text textNC3 = new Text();
                textNC3.setLayoutX(200);
                textNC3.setLayoutY(410);
                textNC3.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR,35));
                textNC3.setFill(Color.WHITE);
                textNC3.setStroke(Color.BLACK);
                textNC3.setStrokeWidth(2);
                textNC3.setText("Customer Mobile:");
                groupDelCustomer.getChildren().add(textNC3);

                //text field to show customer Mobile
                TextField textFieldNC3=new TextField();
                textFieldNC3.setMinSize(350,70);
                textFieldNC3.setEditable(false);
                textFieldNC3.setLayoutX(630);
                textFieldNC3.setLayoutY(370);
                groupDelCustomer.getChildren().add(textFieldNC3);

                //text Customer Plan
                Text textNC4 = new Text();
                textNC4.setLayoutX(200);
                textNC4.setLayoutY(510);
                textNC4.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR,35));
                textNC4.setFill(Color.WHITE);
                textNC4.setStroke(Color.BLACK);
                textNC4.setStrokeWidth(2);
                textNC4.setText("Customer Plan:");
                groupDelCustomer.getChildren().add(textNC4);

                //text field to show customer Plan
                TextField textFieldNC4=new TextField();
                textFieldNC4.setMinSize(350,70);
                textFieldNC4.setEditable(false);
                textFieldNC4.setLayoutX(630);
                textFieldNC4.setLayoutY(470);
                groupDelCustomer.getChildren().add(textFieldNC4);

                Button finCus =new Button("Find",new ImageView("finCus.png"));
                finCus.setStyle("-fx-background-color: null");
                finCus.setFont(font);
                finCus.setLayoutX(150);
                finCus.setLayoutY(600);
                groupDelCustomer.getChildren().add(finCus);

                Button delCus =new Button("Delete",new ImageView("delCus.png"));
                delCus.setStyle("-fx-background-color: null");
                delCus.setFont(font);
                delCus.setLayoutX(450);
                delCus.setLayoutY(600);
                groupDelCustomer.getChildren().add(delCus);

                Button back12 =new Button("",new ImageView("back1.png"));
                back12.setStyle("-fx-background-color: null");
                back12.setLayoutX(900);
                back12.setLayoutY(600);
                groupDelCustomer.getChildren().add(back12);

                addBinding(finCus, textFieldNC);
                addBinding(delCus, textFieldNC);

                finCus.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent-> finCus.setEffect(dropShadow)));
                finCus.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent-> finCus.setEffect(null)));

                delCus.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent-> delCus.setEffect(dropShadow)));
                delCus.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent-> delCus.setEffect(null)));

                back12.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent-> back12.setEffect(dropShadow)));
                back12.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent-> back12.setEffect(null)));

                /*action fin cus when click it , there's two steps.The first step is to check the customer's ID
                 if it exists or not, if exists show data in text fieldsIf it not exists it displays a warning
                  message that the ID is not found.*/
                finCus.setOnAction(actionEvent112 -> {

                    //Create a variable of a boolean type and its initial value is false.
                    boolean found = false;

                    /*for loop spin from 0 to array list customer length, if id customer existed , View user data in
                    text fields ,and makes value the found variable true and break for loop */
                    for(int i=0;i < mediaRental.customer.size();i++){
                        if(textFieldNC.getText().equals(mediaRental.customer.get(i).getID())){
                            textFieldNC1.setText(mediaRental.customer.get(i).getName());
                            textFieldNC2.setText(mediaRental.customer.get(i).getAddress());
                            textFieldNC3.setText(mediaRental.customer.get(i).getMobile());
                            textFieldNC4.setText(mediaRental.customer.get(i).getPlan());
                            found = true;
                            break;
                        }
                    }

                    /*if statement to check value of found ,If the value is false, it displays a warning message to
                     the user that the ID was not exist. */

                    if(!found){
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setContentText("The ID entered is not found.");
                        alert.show();

                    }
                });

                /*action del cus when click it ,The first step is to check the customer's ID
                 if it exists or not, if exists remove from array list customer.*/
                delCus.setOnAction(actionEvent113 -> {

                    for(int i=0;i < mediaRental.customer.size();i++){
                        if(textFieldNC.getText().equals(mediaRental.customer.get(i).getID())){
                            mediaRental.customer.remove(i);
                            break;
                        }
                    }

                    writeCustomers(customerFile);

                    textFieldNC.clear();
                    textFieldNC1.clear();
                    textFieldNC2.clear();
                    textFieldNC3.clear();
                    textFieldNC4.clear();
                });

                /*Action back to return customer scene*/
                back12.setOnAction(actionEvent1 -> stage.setScene(Customer));

                stage.setScene(SceneDelCustomer);

            });

            //action customerUpdate move to new scene to update information customer from list
            customerUpdate.setOnAction(actionEvent14 -> {

                //Create Group and Scene for action customerUpdate
                Group groupUpdateCustomer =new Group();
                Scene SceneUpdateCustomer = new Scene(groupUpdateCustomer, 1650, 900);

                //set background for SceneUpdateCustomer Scene
                groupUpdateCustomer.getChildren().add(viewBG2);

                //text Customer ID
                Text textNC = new Text();
                textNC.setLayoutX(200);
                textNC.setLayoutY(110);
                textNC.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR,35));
                textNC.setFill(Color.WHITE);
                textNC.setStroke(Color.BLACK);
                textNC.setStrokeWidth(2);
                textNC.setText("Customer ID:");
                groupUpdateCustomer.getChildren().add(textNC);

                //text field to show customer ID
                TextField textFieldNC=new TextField();
                textFieldNC.setMinSize(350,70);
                textFieldNC.setLayoutX(630);
                textFieldNC.setLayoutY(70);
                groupUpdateCustomer.getChildren().add(textFieldNC);

                //text Customer Name
                Text textNC1 = new Text();
                textNC1.setLayoutX(200);
                textNC1.setLayoutY(210);
                textNC1.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR,35));
                textNC1.setFill(Color.WHITE);
                textNC1.setStroke(Color.BLACK);
                textNC1.setStrokeWidth(2);
                textNC1.setText("Customer Name:");
                groupUpdateCustomer.getChildren().add(textNC1);

                //text field to show customer Name
                TextField textFieldNC1=new TextField();
                textFieldNC1.setMinSize(350,70);
                textFieldNC1.setLayoutX(630);
                textFieldNC1.setLayoutY(170);
                groupUpdateCustomer.getChildren().add(textFieldNC1);

                //text Customer Address
                Text textNC2 = new Text();
                textNC2.setLayoutX(200);
                textNC2.setLayoutY(310);
                textNC2.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR,35));
                textNC2.setFill(Color.WHITE);
                textNC2.setStroke(Color.BLACK);
                textNC2.setStrokeWidth(2);
                textNC2.setText("Customer Address:");
                groupUpdateCustomer.getChildren().add(textNC2);

                //text field to show customer Address
                TextField textFieldNC2=new TextField();
                textFieldNC2.setMinSize(350,70);
                textFieldNC2.setLayoutX(630);
                textFieldNC2.setLayoutY(270);
                groupUpdateCustomer.getChildren().add(textFieldNC2);

                //text Customer Mobile
                Text textNC3 = new Text();
                textNC3.setLayoutX(200);
                textNC3.setLayoutY(410);
                textNC3.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR,35));
                textNC3.setFill(Color.WHITE);
                textNC3.setStroke(Color.BLACK);
                textNC3.setStrokeWidth(2);
                textNC3.setText("Customer Mobile:");
                groupUpdateCustomer.getChildren().add(textNC3);

                //text field to show customer Mobile
                TextField textFieldNC3=new TextField();
                textFieldNC3.setMinSize(350,70);
                textFieldNC3.setLayoutX(630);
                textFieldNC3.setLayoutY(370);
                groupUpdateCustomer.getChildren().add(textFieldNC3);

                //text Customer Plan
                Text textNC4 = new Text();
                textNC4.setLayoutX(200);
                textNC4.setLayoutY(510);
                textNC4.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR,35));
                textNC4.setFill(Color.WHITE);
                textNC4.setStroke(Color.BLACK);
                textNC4.setStrokeWidth(2);
                textNC4.setText("Customer Plan:");
                groupUpdateCustomer.getChildren().add(textNC4);

                ToggleGroup toggleGroup = new ToggleGroup();

                //create two radio button to show customer plan
                //first radio button show limited
                //second radio button show unlimited

    /*first*/   RadioButton limited=new RadioButton("LIMITED");
                limited.setFont(fontMedia);
                limited.setLayoutX(630);
                limited.setLayoutY(470);
                limited.setToggleGroup(toggleGroup);
                groupUpdateCustomer.getChildren().add(limited);

    /*second*/  RadioButton unlimited =new RadioButton("UNLIMITED");
                unlimited.setFont(fontMedia);
                unlimited.setLayoutX(630);
                unlimited.setLayoutY(520);
                unlimited.setToggleGroup(toggleGroup);
                groupUpdateCustomer.getChildren().add(unlimited);

                Button finCus =new Button("Find",new ImageView("finCus.png"));
                finCus.setStyle("-fx-background-color: null");
                finCus.setFont(font);
                finCus.setLayoutX(150);
                finCus.setLayoutY(600);
                groupUpdateCustomer.getChildren().add(finCus);

                Button updCus =new Button("Update",new ImageView("updateCus.png"));
                updCus.setStyle("-fx-background-color: null");
                updCus.setFont(font);
                updCus.setLayoutX(450);
                updCus.setLayoutY(600);
                groupUpdateCustomer.getChildren().add(updCus);

                Button back12 =new Button("",new ImageView("back1.png"));
                back12.setStyle("-fx-background-color: null");
                back12.setLayoutX(900);
                back12.setLayoutY(600);
                groupUpdateCustomer.getChildren().add(back12);

                addBinding(finCus, textFieldNC);
                addBinding(updCus, textFieldNC);

                finCus.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent-> finCus.setEffect(dropShadow)));
                finCus.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent-> finCus.setEffect(null)));

                updCus.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent-> updCus.setEffect(dropShadow)));
                updCus.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent-> updCus.setEffect(null)));

                back12.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent-> back12.setEffect(dropShadow)));
                back12.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent-> back12.setEffect(null)));

                /*action fin cus when click it , there's two steps.The first step is to check the customer's ID
                 if it exists or not, if exists show data in text fieldsIf it not exists it displays a warning
                  message that the ID is not found.*/
                finCus.setOnAction(actionEvent112 -> {

                    //Create a variable of a boolean type and its initial value is false.
                    boolean found = false;

                    /*for loop spin from 0 to array list customer length, if id customer existed , View user data in
                    text fields ,and makes value the found variable true and break for loop */
                    for(int i=0;i < mediaRental.customer.size();i++){
                        if(textFieldNC.getText().equals(mediaRental.customer.get(i).getID())){
                            textFieldNC1.setText(mediaRental.customer.get(i).getName());
                            textFieldNC2.setText(mediaRental.customer.get(i).getAddress());
                            textFieldNC3.setText(mediaRental.customer.get(i).getMobile());
                            if(mediaRental.customer.get(i).getPlan().equals("LIMITED")){
                                limited.setSelected(true);
                            }else {
                                unlimited.setSelected(true);
                            }
                            found = true;
                            break;
                        }
                    }

                    /*if statement to check value of found ,If the value is false, it displays a warning message to
                     the user that the ID was not exist. */
                    if(!found){
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setContentText("The ID entered is not found.");
                        alert.show();

                    }
                });

                /*action upd cus when click it , there's two steps.The first step is to check the customer's ID
                 if it exists or not if exist remove it from  array list customer.The second step save the
                  updated data and takes its statement from the text fields and saves it in array list for customer
                   If it exists, it displays a warning message that the ID is used.*/
                updCus.setOnAction(actionEvent111 -> {

                    for(int i=0;i < mediaRental.customer.size();i++){
                        if(textFieldNC.getText().equals(mediaRental.customer.get(i).getID())){
                            mediaRental.customer.remove(i);
                            break;
                        }
                    }

                    String ID=textFieldNC.getText();
                    String Name=textFieldNC1.getText();
                    String Address =textFieldNC2.getText();
                    String Mobile =textFieldNC3.getText();
                    String Plan;
                    if(limited.isSelected()){
                        Plan="LIMITED";
                    }else {
                        Plan ="UNLIMITED";
                    }

                    mediaRental.addCustomer(ID, Name, Address,Mobile, Plan);
                    writeCustomers(customerFile);

                    textFieldNC.clear();
                    textFieldNC1.clear();
                    textFieldNC2.clear();
                    textFieldNC3.clear();
                    limited.setSelected(false);
                    unlimited.setSelected(false);
                });

                /*Action back to return customer scene*/
                back12.setOnAction(actionEvent1 -> stage.setScene(Customer));

                stage.setScene(SceneUpdateCustomer);

            });

            //action customerUpdate move to new scene to update information customer from list
            customerSearch.setOnAction(actionEvent15 -> {

                //Create Group and Scene for action customerSearch
                Group groupSearchCustomer =new Group();
                Scene SceneSearchCustomer = new Scene(groupSearchCustomer, 1650, 900);

                //set background for SceneSearchCustomer Scene
                groupSearchCustomer.getChildren().add(viewBG2);

                //text Customer ID
                Text textNC = new Text();
                textNC.setLayoutX(500);
                textNC.setLayoutY(110);
                textNC.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR,35));
                textNC.setFill(Color.WHITE);
                textNC.setStroke(Color.BLACK);
                textNC.setStrokeWidth(2);
                textNC.setText("Customer ID:");
                groupSearchCustomer.getChildren().add(textNC);

                //text field to show customer ID
                TextField textFieldNC =new TextField();
                textFieldNC.setMinSize(350,70);
                textFieldNC.setLayoutX(450);
                textFieldNC.setLayoutY(140);
                groupSearchCustomer.getChildren().add(textFieldNC);

                //text Customer Information
                Text textNC1 = new Text();
                textNC1.setLayoutX(440);
                textNC1.setLayoutY(280);
                textNC1.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR,35));
                textNC1.setFill(Color.WHITE);
                textNC1.setStroke(Color.BLACK);
                textNC1.setStrokeWidth(2);
                textNC1.setText("Customer Information:");
                groupSearchCustomer.getChildren().add(textNC1);

                //text field to show customer Information
                TextField textFieldNC1 =new TextField();
                textFieldNC1.setMinSize(600,150);
                textFieldNC1.setLayoutX(340);
                textFieldNC1.setLayoutY(300);
                textFieldNC1.setEditable(false);
                groupSearchCustomer.getChildren().add(textFieldNC1);

                Button finCus =new Button("Find",new ImageView("finCus.png"));
                finCus.setStyle("-fx-background-color: null");
                finCus.setFont(font);
                finCus.setLayoutX(250);
                finCus.setLayoutY(600);
                groupSearchCustomer.getChildren().add(finCus);

                Button back12 =new Button("",new ImageView("back1.png"));
                back12.setStyle("-fx-background-color: null");
                back12.setLayoutX(850);
                back12.setLayoutY(600);
                groupSearchCustomer.getChildren().add(back12);

                addBinding(finCus, textFieldNC);

                finCus.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent-> finCus.setEffect(dropShadow)));
                finCus.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent-> finCus.setEffect(null)));

                back12.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent-> back12.setEffect(dropShadow)));
                back12.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent-> back12.setEffect(null)));

                /*action fin cus when click it , there's two steps.The first step is to check the customer's ID
                 if it exists or not, if exists show data in text field If it not exists it displays a warning
                  message that the ID is not found.*/
                finCus.setOnAction(actionEvent112 -> {

                    //Create a variable of a boolean type and its initial value is false.
                    boolean found = false;

                    /*for loop spin from 0 to array list customer length, if id customer existed , View user data in
                    text field ,and makes value the found variable true and break for loop */
                    for(int i=0;i < mediaRental.customer.size();i++){
                        if(textFieldNC.getText().equals(mediaRental.customer.get(i).getID())){
                            textFieldNC1.setText(mediaRental.customer.get(i).toString());
                            found = true;
                            break;
                        }
                    }

                    /*if statement to check value of found ,If the value is false, it displays a warning message to
                     the user that the ID was not exist. */
                    if(!found){
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setContentText("The ID entered is not found.");
                        alert.show();
                    }
                });

                /*Action back to return customer scene*/
                back12.setOnAction(actionEvent1 -> stage.setScene(Customer));

                stage.setScene(SceneSearchCustomer);
            });

            /*Action back to return primary scene*/
             back.setOnAction(actionEvent1 -> {
                 stage.setScene(primaryScene);
                 stage.show();
             });

            stage.setScene(Customer);
            stage.show();
        });

        //action add Media move to new scene and select the option need
        addMedia.setOnAction(actionEvent -> {

            //Create Group and Scene for action add Media
            Group groupMedia =new Group();
            Scene Media = new Scene(groupMedia, 1650, 900);

            //set background for Media Scene
            groupMedia.getChildren().add(viewBG1);

            //create button to add Media
            Button MediaAdd=new Button("Add new media");
            MediaAdd.setStyle("-fx-background-color: null");
            MediaAdd.setFont(font);
            MediaAdd.setLayoutX(50);
            MediaAdd.setLayoutY(40);
            groupMedia.getChildren().add(MediaAdd);

            //create button to Delete Media
            Button MediaDelete=new Button("Delete media");
            MediaDelete.setStyle("-fx-background-color: null");
            MediaDelete.setFont(font);
            MediaDelete.setLayoutX(50);
            MediaDelete.setLayoutY(160);
            groupMedia.getChildren().add(MediaDelete);

            //create button to Update Media
            Button MediaUpdate=new Button("Update Information about Media");
            MediaUpdate.setStyle("-fx-background-color: null");
            MediaUpdate.setFont(font);
            MediaUpdate.setLayoutX(50);
            MediaUpdate.setLayoutY(280);
            groupMedia.getChildren().add(MediaUpdate);

            //create button to Search Media
            Button MediaSearch =new Button("Search a Media by code");
            MediaSearch.setStyle("-fx-background-color: null");
            MediaSearch.setFont(font);
            MediaSearch.setLayoutX(50);
            MediaSearch.setLayoutY(400);
            groupMedia.getChildren().add(MediaSearch);

            //create button to Print All Media
            Button MediaPrint =new Button("Print All Media information");
            MediaPrint.setStyle("-fx-background-color: null");
            MediaPrint.setFont(font);
            MediaPrint.setLayoutX(50);
            MediaPrint.setLayoutY(520);
            groupMedia.getChildren().add(MediaPrint);

            //create button to back primary Scene
            Button back =new Button("",new ImageView("back.png"));
            back.setStyle("-fx-background-color: null");
            back.setLayoutX(1100);
            back.setLayoutY(600);
            groupMedia.getChildren().add(back);

            MediaAdd.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent-> MediaAdd.setEffect(dropShadow)));
            MediaAdd.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent-> MediaAdd.setEffect(null)));

            MediaDelete.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent-> MediaDelete.setEffect(dropShadow)));
            MediaDelete.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent-> MediaDelete.setEffect(null)));

            MediaUpdate.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent-> MediaUpdate.setEffect(dropShadow)));
            MediaUpdate.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent-> MediaUpdate.setEffect(null)));

            MediaSearch.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent-> MediaSearch.setEffect(dropShadow)));
            MediaSearch.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent-> MediaSearch.setEffect(null)));

            MediaPrint.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent-> MediaPrint.setEffect(dropShadow)));
            MediaPrint.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent-> MediaPrint.setEffect(null)));

            //action MediaAdd move to new scene to add new Media to list
            MediaAdd.setOnAction(actionEvent16 -> {

                //Create Group and Scene for action MediaAdd
                Group groupMediaAdd =new Group();
                Scene sceneMediaAdd = new Scene(groupMediaAdd, 5000, 5000);

                //set background for SceneNewCustomer Scene
                groupMediaAdd.getChildren().add(viewBG2);

                //Create ComboBox to select type media entered
                ComboBox <String>comboBox = new ComboBox<>();
                comboBox.setLayoutX(900);
                comboBox.setLayoutY(70);
                comboBox.setMinSize(200,50);
                comboBox.getItems().addAll("Movie","Game","Album");
                groupMediaAdd.getChildren().add(comboBox);

                comboBox.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent-> comboBox.setEffect(dropShadow)));
                comboBox.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent-> comboBox.setEffect(null)));

                //text Media Code
                Text textNM = new Text();
                textNM.setLayoutX(50);
                textNM.setLayoutY(110);
                textNM.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR,35));
                textNM.setFill(Color.WHITE);
                textNM.setStroke(Color.BLACK);
                textNM.setStrokeWidth(2);
                textNM.setText("Media Code:");
                groupMediaAdd.getChildren().add(textNM);

                //text field to input Media Code
                TextField textFieldNM =new TextField();
                textFieldNM.setMinSize(350,70);
                textFieldNM.setLayoutX(420);
                textFieldNM.setLayoutY(70);
                groupMediaAdd.getChildren().add(textFieldNM);

                //text Media Title
                Text textNM1 = new Text();
                textNM1.setLayoutX(50);
                textNM1.setLayoutY(220);
                textNM1.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR,35));
                textNM1.setFill(Color.WHITE);
                textNM1.setStroke(Color.BLACK);
                textNM1.setStrokeWidth(2);
                textNM1.setText("Media Title:");
                groupMediaAdd.getChildren().add(textNM1);

                //text field to input Media Title
                TextField textFieldNM1 =new TextField();
                textFieldNM1.setMinSize(350,70);
                textFieldNM1.setLayoutX(420);
                textFieldNM1.setLayoutY(180);
                groupMediaAdd.getChildren().add(textFieldNM1);

                //text Name Artist
                Text textNM2 = new Text();
                textNM2.setLayoutX(50);
                textNM2.setLayoutY(330);
                textNM2.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR,35));
                textNM2.setFill(Color.WHITE);
                textNM2.setStroke(Color.BLACK);
                textNM2.setStrokeWidth(2);
                textNM2.setText("Name Artist:");
                groupMediaAdd.getChildren().add(textNM2);

                //text field to input Name Artist
                TextField textFieldNM2 =new TextField();
                textFieldNM2.setMinSize(350,70);
                textFieldNM2.setLayoutX(420);
                textFieldNM2.setLayoutY(290);
                groupMediaAdd.getChildren().add(textFieldNM2);

                //text Song Title
                Text textNM3 = new Text();
                textNM3.setLayoutX(50);
                textNM3.setLayoutY(440);
                textNM3.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR,35));
                textNM3.setFill(Color.WHITE);
                textNM3.setStroke(Color.BLACK);
                textNM3.setStrokeWidth(2);
                textNM3.setText("Song Title:");
                groupMediaAdd.getChildren().add(textNM3);

                //text field to input Song Title
                TextField textFieldNM3 =new TextField();
                textFieldNM3.setMinSize(350,70);
                textFieldNM3.setLayoutX(420);
                textFieldNM3.setLayoutY(400);
                groupMediaAdd.getChildren().add(textFieldNM3);

                //text Number of coby's
                Text textNM4 = new Text();
                textNM4.setLayoutX(50);
                textNM4.setLayoutY(540);
                textNM4.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR,35));
                textNM4.setFill(Color.WHITE);
                textNM4.setStroke(Color.BLACK);
                textNM4.setStrokeWidth(2);
                textNM4.setText("Number of coby's:");
                groupMediaAdd.getChildren().add(textNM4);

                //text field to input Number of coby's
                TextField textFieldNM4 =new TextField();
                textFieldNM4.setMinSize(350,70);
                textFieldNM4.setLayoutX(420);
                textFieldNM4.setLayoutY(510);
                groupMediaAdd.getChildren().add(textFieldNM4);

                //text Weight
                Text textNM5 = new Text();
                textNM5.setLayoutX(950);
                textNM5.setLayoutY(250);
                textNM5.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR,35));
                textNM5.setFill(Color.WHITE);
                textNM5.setStroke(Color.BLACK);
                textNM5.setStrokeWidth(2);
                textNM5.setText("Weight:");
                groupMediaAdd.getChildren().add(textNM5);

                //text field to input Weight
                TextField textFieldNM5 =new TextField();
                textFieldNM5.setMinSize(350,70);
                textFieldNM5.setLayoutX(850);
                textFieldNM5.setLayoutY(270);
                groupMediaAdd.getChildren().add(textFieldNM5);

                ToggleGroup toggleGroup =new ToggleGroup();

                //text Rating
                Text textR = new Text();
                textR.setLayoutX(950);
                textR.setLayoutY(450);
                textR.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR,35));
                textR.setFill(Color.WHITE);
                textR.setStroke(Color.BLACK);
                textR.setStrokeWidth(2);
                textR.setText("Rating:");
                groupMediaAdd.getChildren().add(textR);

                //Create RadioButton to select rate movie entered

                RadioButton DR =new RadioButton("DR");
                DR.setFont(fontMedia);
                DR.setLayoutX(850);
                DR.setLayoutY(470);
                DR.setToggleGroup(toggleGroup);
                groupMediaAdd.getChildren().add(DR);

                RadioButton HR =new RadioButton("HR");
                HR.setFont(fontMedia);
                HR.setLayoutX(1000);
                HR.setLayoutY(470);
                HR.setToggleGroup(toggleGroup);
                groupMediaAdd.getChildren().add(HR);

                RadioButton AC =new RadioButton("AC");
                AC.setFont(fontMedia);
                AC.setLayoutX(1150);
                AC.setLayoutY(470);
                AC.setToggleGroup(toggleGroup);
                groupMediaAdd.getChildren().add(AC);

                Button addMed =new Button("Add Media",new ImageView("addMed.png"));
                addMed.setStyle("-fx-background-color: null");
                addMed.setFont(fontMedia);
                addMed.setLayoutX(250);
                addMed.setLayoutY(600);
                groupMediaAdd.getChildren().add(addMed);

                Button back1 =new Button("",new ImageView("back1.png"));
                back1.setStyle("-fx-background-color: null");
                back1.setLayoutX(850);
                back1.setLayoutY(600);
                groupMediaAdd.getChildren().add(back1);

                textFieldNM.setDisable(true);
                textFieldNM1.setDisable(true);
                textFieldNM2.setDisable(true);
                textFieldNM3.setDisable(true);
                textFieldNM4.setDisable(true);
                textFieldNM5.setDisable(true);
                DR.setDisable(true);
                HR.setDisable(true);
                AC.setDisable(true);

                comboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

                    switch (newValue) {
                        case "Movie" -> {
                            textFieldNM.setDisable(false);
                            textFieldNM.setText("M");
                            textFieldNM1.setDisable(false);
                            textFieldNM2.setDisable(true);
                            textFieldNM3.setDisable(true);
                            textFieldNM4.setDisable(false);
                            textFieldNM5.setDisable(true);
                            DR.setDisable(false);
                            HR.setDisable(false);
                            AC.setDisable(false);

                        }
                        case "Game" -> {
                            textFieldNM.setDisable(false);
                            textFieldNM.setText("G");
                            textFieldNM1.setDisable(false);
                            textFieldNM2.setDisable(true);
                            textFieldNM3.setDisable(true);
                            textFieldNM4.setDisable(false);
                            textFieldNM5.setDisable(false);
                            DR.setDisable(true);
                            HR.setDisable(true);
                            AC.setDisable(true);
                        }
                        case "Album" -> {
                            textFieldNM.setText("A");
                            textFieldNM5.setDisable(true);
                            textFieldNM2.setDisable(false);
                            textFieldNM3.setDisable(false);
                            DR.setDisable(true);
                            HR.setDisable(true);
                            AC.setDisable(true);
                        }
                    }
                });

                DR.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent-> DR.setEffect(dropShadow)));
                DR.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent-> DR.setEffect(null)));

                HR.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent-> HR.setEffect(dropShadow)));
                HR.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent-> HR.setEffect(null)));

                AC.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent-> AC.setEffect(dropShadow)));
                AC.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent-> AC.setEffect(null)));

                addMed.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent-> addMed.setEffect(dropShadow)));
                addMed.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent-> addMed.setEffect(null)));

                back1.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent-> back1.setEffect(dropShadow)));
                back1.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent-> back1.setEffect(null)));

                addMed.setOnAction(actionEvent114 -> {

                    boolean found =false;

                    if(comboBox.getValue().equals("Movie")){

                        for (int i=0 ; i < mediaRental.media.size() ;i++){
                            if (textFieldNM.getText().equals(mediaRental.media.get(i).getCode())){
                                if(mediaRental.media.get(i).getClass().equals(Movie.class)){
                                    found=true;
                                    break;
                                }
                            }
                        }

                        if(!found){
                            String code=textFieldNM.getText();
                            String title=textFieldNM1.getText();
                            int numCopy= Integer.parseInt(textFieldNM4.getText());
                            String rate=null;

                            if(AC.isSelected()){
                                rate="AC";
                            }
                            else if(DR.isSelected()){
                                rate="DR";
                            }
                            else if(HR.isSelected()){
                                rate="HR";
                            }
                            mediaRental.addMovie(code,title,numCopy,rate);

                            writeMedia(mediaFile);
                        }
                        else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("The code movie entered is already exists.");
                            alert.show();
                        }
                        textFieldNM.clear();
                        textFieldNM1.clear();
                        textFieldNM4.clear();
                        AC.setSelected(false);
                        DR.setSelected(false);
                        HR.setSelected(false);
                    }

                    else if(comboBox.getValue().equals("Game")){

                        for (int i=0 ; i < mediaRental.media.size() ;i++){
                            if (textFieldNM.getText().equals(mediaRental.media.get(i).getCode())){
                                if(mediaRental.media.get(i).getClass().equals(Game.class)){
                                    found = true;
                                    break;
                                }
                            }
                        }

                        if(!found){
                            String code=textFieldNM.getText();
                            String title=textFieldNM1.getText();
                            double weight = Double.parseDouble(textFieldNM5.getText());
                            int numCopy= Integer.parseInt(textFieldNM4.getText());

                            mediaRental.addGame(code,title,numCopy,weight);

                            writeMedia(mediaFile);
                        }
                        else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("The code game entered is already exists.");
                            alert.show();

                        }
                        textFieldNM.clear();
                        textFieldNM1.clear();
                        textFieldNM4.clear();
                        textFieldNM5.clear();
                    }

                    else if (comboBox.getValue().equals("Album")){

                        for (int i=0 ; i < mediaRental.media.size() ;i++){
                            if (textFieldNM.getText().equals(mediaRental.media.get(i).getCode())){
                                if(mediaRental.media.get(i).getClass().equals(Game.class)){
                                    found = true;
                                    break;
                                }
                            }
                        }

                        if(!found){
                            String code=textFieldNM.getText();
                            String title=textFieldNM1.getText();
                            String artist=textFieldNM2.getText();
                            String songs=textFieldNM3.getText();
                            int numCopy= Integer.parseInt(textFieldNM4.getText());

                            mediaRental.addAlbum(code,title,numCopy,artist,songs);

                            writeMedia(mediaFile);
                        }
                        else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("The code album entered is already exists.");
                            alert.show();
                        }

                        textFieldNM.clear();
                        textFieldNM1.clear();
                        textFieldNM2.clear();
                        textFieldNM3.clear();
                        textFieldNM4.clear();
                    }

                    textFieldNM.setDisable(true);
                    textFieldNM1.setDisable(true);
                    textFieldNM2.setDisable(true);
                    textFieldNM3.setDisable(true);
                    textFieldNM4.setDisable(true);
                    textFieldNM5.setDisable(true);
                    DR.setDisable(true);
                    HR.setDisable(true);
                    AC.setDisable(true);
                });

                back1.setOnAction(actionEvent17 -> {
                    stage.setScene(Media);
                    stage.show();
                });

                stage.setScene(sceneMediaAdd);
                stage.show();

            });

            MediaDelete.setOnAction(actionEvent18 -> {

                Group groupMediaDelete =new Group();
                Scene sceneMediaDelete = new Scene(groupMediaDelete, 5000, 5000,Color.POWDERBLUE);

                groupMediaDelete.getChildren().add(viewBG2);

                ComboBox <String>comboBox = new ComboBox<>();
                comboBox.setLayoutX(900);
                comboBox.setLayoutY(70);
                comboBox.setMinSize(200,50);
                comboBox.getItems().addAll("Movie","Game","Album");
                groupMediaDelete.getChildren().add(comboBox);

                comboBox.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent-> comboBox.setEffect(dropShadow)));
                comboBox.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent-> comboBox.setEffect(null)));

                Text textNM = new Text();
                textNM.setLayoutX(500);
                textNM.setLayoutY(110);
                textNM.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR,35));
                textNM.setFill(Color.WHITE);
                textNM.setStroke(Color.BLACK);
                textNM.setStrokeWidth(2);
                textNM.setText("Media Code:");
                groupMediaDelete.getChildren().add(textNM);

                TextField textFieldNM =new TextField();
                textFieldNM.setMinSize(350,70);
                textFieldNM.setLayoutX(450);
                textFieldNM.setLayoutY(140);
                groupMediaDelete.getChildren().add(textFieldNM);

                Text textNM1 = new Text();
                textNM1.setLayoutX(440);
                textNM1.setLayoutY(280);
                textNM1.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR,35));
                textNM1.setFill(Color.WHITE);
                textNM1.setStroke(Color.BLACK);
                textNM1.setStrokeWidth(2);
                textNM1.setText("Media Information:");
                groupMediaDelete.getChildren().add(textNM1);

                TextField textFieldNM1 =new TextField();
                textFieldNM1.setMinSize(600,150);
                textFieldNM1.setLayoutX(340);
                textFieldNM1.setLayoutY(300);
                textFieldNM1.setEditable(false);
                groupMediaDelete.getChildren().add(textFieldNM1);

                Button finMed =new Button("Find",new ImageView("finCus.png"));
                finMed.setStyle("-fx-background-color: null");
                finMed.setFont(font);
                finMed.setLayoutX(150);
                finMed.setLayoutY(600);
                groupMediaDelete.getChildren().add(finMed);

                Button deleteMed =new Button("Delete",new ImageView("addMed.png"));
                deleteMed.setStyle("-fx-background-color: null");
                deleteMed.setFont(font);
                deleteMed.setLayoutX(500);
                deleteMed.setLayoutY(600);
                groupMediaDelete.getChildren().add(deleteMed);


                Button back1 =new Button("",new ImageView("back1.png"));
                back1.setStyle("-fx-background-color: null");
                back1.setLayoutX(900);
                back1.setLayoutY(600);
                groupMediaDelete.getChildren().add(back1);

                comboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                            switch (newValue) {
                                case "Movie" -> textFieldNM.setText("M");
                                case "Game" -> textFieldNM.setText("G");
                                case "Album" -> textFieldNM.setText("A");
                            }
                });

                finMed.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent-> finMed.setEffect(dropShadow)));
                finMed.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent-> finMed.setEffect(null)));

                deleteMed.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent-> deleteMed.setEffect(dropShadow)));
                deleteMed.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent-> deleteMed.setEffect(null)));

                back1.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent-> back1.setEffect(dropShadow)));
                back1.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent-> back1.setEffect(null)));

                finMed.setOnAction(actionEvent115 -> {

                    boolean found =false;

                    if(comboBox.getValue().equals("Movie")){

                        for (int i=0 ; i < mediaRental.media.size() ;i++){
                            if (textFieldNM.getText().equals(mediaRental.media.get(i).getCode())){
                                if(mediaRental.media.get(i).getClass().equals(Movie.class)){
                                    textFieldNM1.setText(mediaRental.media.get(i).toString());
                                    found=true;
                                    break;
                                }
                            }
                        }

                        if(!found){
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("The ID entered is not found.");
                            alert.show();

                        }
                    }

                    else if(comboBox.getValue().equals("Game")){

                        for (int i=0 ; i < mediaRental.media.size() ;i++){
                            if (textFieldNM.getText().equals(mediaRental.media.get(i).getCode())){
                                if(mediaRental.media.get(i).getClass().equals(Game.class)){
                                    textFieldNM1.setText(mediaRental.media.get(i).toString());
                                    found = true;
                                    break;
                                }
                            }
                        }

                        if(!found){
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("The ID entered is not found.");
                            alert.show();

                        }
                    }

                    else if (comboBox.getValue().equals("Album")){

                        for (int i=0 ; i < mediaRental.media.size() ;i++){
                            if (textFieldNM.getText().equals(mediaRental.media.get(i).getCode())){
                                if(mediaRental.media.get(i).getClass().equals(Album.class)){
                                    textFieldNM1.setText(mediaRental.media.get(i).toString());
                                    found = true;
                                    break;
                                }
                            }
                        }

                        if(!found){
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("The ID entered is not found.");
                            alert.show();

                        }
                    }
                });

                deleteMed.setOnAction(actionEvent116 -> {

                    if(comboBox.getValue().equals("Movie")){

                        for (int i=0 ; i < mediaRental.media.size() ;i++){
                            if (textFieldNM.getText().equals(mediaRental.media.get(i).getCode())){
                                if(mediaRental.media.get(i).getClass().equals(Movie.class)){
                                    mediaRental.media.remove(i);
                                    break;
                                }
                            }
                        }
                    }

                    else if(comboBox.getValue().equals("Game")){

                        for (int i=0 ; i < mediaRental.media.size() ;i++){
                            if (textFieldNM.getText().equals(mediaRental.media.get(i).getCode())){
                                if(mediaRental.media.get(i).getClass().equals(Game.class)){
                                    mediaRental.media.remove(i);
                                    break;
                                }
                            }
                        }
                    }

                    else if (comboBox.getValue().equals("Album")){

                        for (int i=0 ; i < mediaRental.media.size() ;i++){
                            if (textFieldNM.getText().equals(mediaRental.media.get(i).getCode())){
                                if(mediaRental.media.get(i).getClass().equals(Album.class)){
                                    mediaRental.media.remove(i);
                                    break;
                                }
                            }
                        }
                    }

                    writeMedia(mediaFile);

                    textFieldNM.clear();
                    textFieldNM1.clear();
                });

                back1.setOnAction(actionEvent17 -> {
                    stage.setScene(Media);
                    stage.show();
                });

                stage.setScene(sceneMediaDelete);
                stage.show();
            });

            MediaUpdate.setOnAction(actionEvent19 -> {

                Group groupMediaUpdate =new Group();
                Scene sceneMediaUpdate = new Scene(groupMediaUpdate, 1500, 950,Color.POWDERBLUE);

                groupMediaUpdate.getChildren().add(viewBG2);

                ComboBox <String>comboBox = new ComboBox<>();
                comboBox.setLayoutX(900);
                comboBox.setLayoutY(70);
                comboBox.setMinSize(200,50);
                comboBox.getItems().addAll("Movie","Game","Album");
                groupMediaUpdate.getChildren().add(comboBox);

                comboBox.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent-> comboBox.setEffect(dropShadow)));
                comboBox.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent-> comboBox.setEffect(null)));

                Text textNM = new Text();
                textNM.setLayoutX(50);
                textNM.setLayoutY(110);
                textNM.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR,35));
                textNM.setFill(Color.WHITE);
                textNM.setStroke(Color.BLACK);
                textNM.setStrokeWidth(2);
                textNM.setText("Media Code:");
                groupMediaUpdate.getChildren().add(textNM);

                TextField textFieldNM =new TextField();
                textFieldNM.setMinSize(350,70);
                textFieldNM.setLayoutX(420);
                textFieldNM.setLayoutY(70);
                groupMediaUpdate.getChildren().add(textFieldNM);

                Text textNM1 = new Text();
                textNM1.setLayoutX(50);
                textNM1.setLayoutY(220);
                textNM1.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR,35));
                textNM1.setFill(Color.WHITE);
                textNM1.setStroke(Color.BLACK);
                textNM1.setStrokeWidth(2);
                textNM1.setText("Media Title:");
                groupMediaUpdate.getChildren().add(textNM1);

                TextField textFieldNM1 =new TextField();
                textFieldNM1.setMinSize(350,70);
                textFieldNM1.setLayoutX(420);
                textFieldNM1.setLayoutY(180);
                groupMediaUpdate.getChildren().add(textFieldNM1);

                Text textNM2 = new Text();
                textNM2.setLayoutX(50);
                textNM2.setLayoutY(330);
                textNM2.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR,35));
                textNM2.setFill(Color.WHITE);
                textNM2.setStroke(Color.BLACK);
                textNM2.setStrokeWidth(2);
                textNM2.setText("Name Artist:");
                groupMediaUpdate.getChildren().add(textNM2);

                TextField textFieldNM2 =new TextField();
                textFieldNM2.setMinSize(350,70);
                textFieldNM2.setLayoutX(420);
                textFieldNM2.setLayoutY(290);
                groupMediaUpdate.getChildren().add(textFieldNM2);

                Text textNM3 = new Text();
                textNM3.setLayoutX(50);
                textNM3.setLayoutY(440);
                textNM3.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR,35));
                textNM3.setFill(Color.WHITE);
                textNM3.setStroke(Color.BLACK);
                textNM3.setStrokeWidth(2);
                textNM3.setText("Song Title:");
                groupMediaUpdate.getChildren().add(textNM3);

                TextField textFieldNM3 =new TextField();
                textFieldNM3.setMinSize(350,70);
                textFieldNM3.setLayoutX(420);
                textFieldNM3.setLayoutY(400);
                groupMediaUpdate.getChildren().add(textFieldNM3);

                Text textNM4 = new Text();
                textNM4.setLayoutX(50);
                textNM4.setLayoutY(540);
                textNM4.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR,35));
                textNM4.setFill(Color.WHITE);
                textNM4.setStroke(Color.BLACK);
                textNM4.setStrokeWidth(2);
                textNM4.setText("Number of coby's:");
                groupMediaUpdate.getChildren().add(textNM4);

                TextField textFieldNM4 =new TextField();
                textFieldNM4.setMinSize(350,70);
                textFieldNM4.setLayoutX(420);
                textFieldNM4.setLayoutY(510);
                groupMediaUpdate.getChildren().add(textFieldNM4);

                Text textNM5 = new Text();
                textNM5.setLayoutX(950);
                textNM5.setLayoutY(250);
                textNM5.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR,35));
                textNM5.setFill(Color.WHITE);
                textNM5.setStroke(Color.BLACK);
                textNM5.setStrokeWidth(2);
                textNM5.setText("Weight:");
                groupMediaUpdate.getChildren().add(textNM5);

                TextField textFieldNM5 =new TextField();
                textFieldNM5.setMinSize(350,70);
                textFieldNM5.setLayoutX(850);
                textFieldNM5.setLayoutY(270);
                groupMediaUpdate.getChildren().add(textFieldNM5);

                ToggleGroup toggleGroup =new ToggleGroup();

                //text Rating
                Text textR = new Text();
                textR.setLayoutX(950);
                textR.setLayoutY(450);
                textR.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR,35));
                textR.setFill(Color.WHITE);
                textR.setStroke(Color.BLACK);
                textR.setStrokeWidth(2);
                textR.setText("Rating:");
                groupMediaUpdate.getChildren().add(textR);

                //Create RadioButton to select rate movie entered

                RadioButton DR =new RadioButton("DR");
                DR.setFont(fontMedia);
                DR.setLayoutX(850);
                DR.setLayoutY(470);
                DR.setToggleGroup(toggleGroup);
                groupMediaUpdate.getChildren().add(DR);

                RadioButton HR =new RadioButton("HR");
                HR.setFont(fontMedia);
                HR.setLayoutX(1000);
                HR.setLayoutY(470);
                HR.setToggleGroup(toggleGroup);
                groupMediaUpdate.getChildren().add(HR);

                RadioButton AC =new RadioButton("AC");
                AC.setFont(fontMedia);
                AC.setLayoutX(1150);
                AC.setLayoutY(470);
                AC.setToggleGroup(toggleGroup);
                groupMediaUpdate.getChildren().add(AC);

                textFieldNM.setDisable(true);
                textFieldNM1.setDisable(true);
                textFieldNM2.setDisable(true);
                textFieldNM3.setDisable(true);
                textFieldNM4.setDisable(true);
                textFieldNM5.setDisable(true);


                comboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

                    switch (newValue) {
                        case "Movie" -> {

                            textFieldNM.setDisable(false);
                            textFieldNM.setText("M");
                            textFieldNM1.setDisable(false);
                            textFieldNM2.setDisable(true);
                            textFieldNM3.setDisable(true);
                            textFieldNM4.setDisable(false);
                            textFieldNM5.setDisable(true);


                        }
                        case "Game" -> {

                            textFieldNM.setDisable(false);
                            textFieldNM.setText("G");
                            textFieldNM1.setDisable(false);
                            textFieldNM2.setDisable(true);
                            textFieldNM3.setDisable(true);
                            textFieldNM4.setDisable(false);
                            textFieldNM5.setDisable(false);


                        }
                        case "Album" -> {

                            textFieldNM.setDisable(false);
                            textFieldNM.setText("A");
                            textFieldNM1.setDisable(false);
                            textFieldNM2.setDisable(false);
                            textFieldNM3.setDisable(false);
                            textFieldNM4.setDisable(false);
                            textFieldNM5.setDisable(true);


                        }
                    }
                });

                Button finMed =new Button("Find",new ImageView("finCus.png"));
                finMed.setStyle("-fx-background-color: null");
                finMed.setFont(font);
                finMed.setLayoutX(150);
                finMed.setLayoutY(600);
                groupMediaUpdate.getChildren().add(finMed);

                Button updateMed =new Button("update",new ImageView("addMed.png"));
                updateMed.setStyle("-fx-background-color: null");
                updateMed.setFont(font);
                updateMed.setLayoutX(500);
                updateMed.setLayoutY(600);
                groupMediaUpdate.getChildren().add(updateMed);

                Button back1 =new Button("",new ImageView("back1.png"));
                back1.setStyle("-fx-background-color: null");
                back1.setLayoutX(900);
                back1.setLayoutY(600);
                groupMediaUpdate.getChildren().add(back1);

                finMed.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent-> finMed.setEffect(dropShadow)));
                finMed.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent-> finMed.setEffect(null)));

                updateMed.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent-> updateMed.setEffect(dropShadow)));
                updateMed.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent-> updateMed.setEffect(null)));

                back1.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent-> back1.setEffect(dropShadow)));
                back1.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent-> back1.setEffect(null)));

                finMed.setOnAction(actionEvent119 -> {

                    if(comboBox.getValue().equals("Movie")){

                        for (int i=0 ; i < mediaRental.media.size() ;i++){
                            if (textFieldNM.getText().equals(mediaRental.media.get(i).getCode())){
                                if(mediaRental.media.get(i).getClass().equals(Movie.class)){
                                    textFieldNM.setText(mediaRental.media.get(i).getCode());
                                    textFieldNM1.setText(mediaRental.media.get(i).getTitle());
                                    textFieldNM4.setText(String.valueOf(mediaRental.media.get(i).getNumber_of_copies()));

                                    if(mediaRental.media.get(i).getRating().equals("DR")){
                                        DR.setSelected(true);
                                    }else if (mediaRental.media.get(i).getRating().equals("HR")){
                                        HR.setSelected(true);
                                    }else {
                                        AC.setSelected(true);
                                    }

                                    break;
                                }
                            }
                        }
                    }
                    else if(comboBox.getValue().equals("Game")){

                        for (int i=0 ; i < mediaRental.media.size() ;i++){
                            if (textFieldNM.getText().equals(mediaRental.media.get(i).getCode())){
                                if(mediaRental.media.get(i).getClass().equals(Game.class)){
                                    textFieldNM.setText(mediaRental.media.get(i).getCode());
                                    textFieldNM1.setText(mediaRental.media.get(i).getTitle());
                                    textFieldNM4.setText(String.valueOf(mediaRental.media.get(i).getNumber_of_copies()));
                                    textFieldNM5.setText(String.valueOf(mediaRental.media.get(i).getWeight()));
                                    break;
                                }
                            }
                        }
                    }else if (comboBox.getValue().equals("Album")){

                        for (int i=0 ; i < mediaRental.media.size() ;i++){
                            if (textFieldNM.getText().equals(mediaRental.media.get(i).getCode())){
                                if(mediaRental.media.get(i).getClass().equals(Game.class)){
                                    textFieldNM.setText(mediaRental.media.get(i).getCode());
                                    textFieldNM1.setText(mediaRental.media.get(i).getTitle());
                                    textFieldNM2.setText(mediaRental.media.get(i).getArtist());
                                    textFieldNM3.setText(mediaRental.media.get(i).getSong());
                                    textFieldNM4.setText(String.valueOf(mediaRental.media.get(i).getNumber_of_copies()));
                                    break;
                                }
                            }
                        }
                    }
                });

                updateMed.setOnAction(actionEvent120 -> {

                    if(comboBox.getValue().equals("Movie")){

                        for (int i=0 ; i < mediaRental.media.size() ;i++){
                            if (textFieldNM.getText().equals(mediaRental.media.get(i).getCode())){
                                if(mediaRental.media.get(i).getClass().equals(Movie.class)){
                                    mediaRental.media.remove(i);
                                    break;
                                }
                            }
                        }

                        String code=textFieldNM.getText();
                        String title=textFieldNM1.getText();
                        int numCopy = Integer.parseInt(textFieldNM4.getText());
                        String rating;

                        if(DR.isSelected()){
                            rating="DR";
                        }else if(HR.isSelected()){
                            rating ="HR";
                        }else{
                            rating="AC";
                        }

                        mediaRental.addMovie(code,title,numCopy,rating);

                        writeMedia(mediaFile);

                        textFieldNM.clear();
                        textFieldNM1.clear();
                        textFieldNM4.clear();
                        DR.setSelected(false);
                        HR.setSelected(false);
                        AC.setSelected(false);

                    }
                    else if(comboBox.getValue().equals("Game")){

                        for (int i=0 ; i < mediaRental.media.size() ;i++){
                            if (textFieldNM.getText().equals(mediaRental.media.get(i).getCode())){
                                if(mediaRental.media.get(i).getClass().equals(Game.class)){
                                    mediaRental.media.remove(i);
                                    break;
                                }
                            }
                        }

                        String code=textFieldNM.getText();
                        String title=textFieldNM1.getText();
                        int numCopy = Integer.parseInt(textFieldNM4.getText());
                        double weight = Double.parseDouble(textFieldNM5.getText());

                        mediaRental.addGame(code,title,numCopy,weight);

                        writeMedia(mediaFile);

                        textFieldNM.clear();
                        textFieldNM1.clear();
                        textFieldNM4.clear();
                        textFieldNM5.clear();

                    }else if (comboBox.getValue().equals("Album")){

                        for (int i=0 ; i < mediaRental.media.size() ;i++){
                            if (textFieldNM.getText().equals(mediaRental.media.get(i).getCode())){
                                if(mediaRental.media.get(i).getClass().equals(Game.class)){

                                    mediaRental.media.remove(i);

                                    textFieldNM.setText(mediaRental.media.get(i).getCode());
                                    textFieldNM1.setText(mediaRental.media.get(i).getTitle());
                                    textFieldNM2.setText(mediaRental.media.get(i).getArtist());
                                    textFieldNM3.setText(mediaRental.media.get(i).getSong());
                                    textFieldNM4.setText(String.valueOf(mediaRental.media.get(i).getNumber_of_copies()));
                                    break;
                                }
                            }
                        }

                        String code=textFieldNM.getText();
                        String title=textFieldNM1.getText();
                        String artist = textFieldNM2.getText();
                        String song = textFieldNM3.getText();
                        int numCopy = Integer.parseInt(textFieldNM4.getText());

                        mediaRental.addAlbum(code,title,numCopy,artist,song);

                        writeMedia(mediaFile);

                        textFieldNM.clear();
                        textFieldNM1.clear();
                        textFieldNM2.clear();
                        textFieldNM3.clear();
                        textFieldNM4.clear();


                    }
                });

                back1.setOnAction(actionEvent17 -> {
                    stage.setScene(Media);
                    stage.show();
                });

                stage.setScene(sceneMediaUpdate);
                stage.show();
            });

            MediaSearch.setOnAction(actionEvent110 -> {

                Group groupMediaSearch =new Group();
                Scene sceneMediaSearch = new Scene(groupMediaSearch, 5000, 5000);

                groupMediaSearch.getChildren().add(viewBG2);

                ComboBox <String>comboBox = new ComboBox<>();
                comboBox.setLayoutX(900);
                comboBox.setLayoutY(70);
                comboBox.setMinSize(200,50);
                comboBox.getItems().addAll("Movie","Game","Album");
                groupMediaSearch.getChildren().add(comboBox);

                Text textNM = new Text();
                textNM.setLayoutX(500);
                textNM.setLayoutY(110);
                textNM.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR,35));
                textNM.setFill(Color.WHITE);
                textNM.setStroke(Color.BLACK);
                textNM.setStrokeWidth(2);
                textNM.setText("Media Code:");
                groupMediaSearch.getChildren().add(textNM);

                TextField textFieldNM =new TextField();
                textFieldNM.setMinSize(350,70);
                textFieldNM.setLayoutX(450);
                textFieldNM.setLayoutY(140);
                groupMediaSearch.getChildren().add(textFieldNM);

                Text textNM1 = new Text();
                textNM1.setLayoutX(440);
                textNM1.setLayoutY(280);
                textNM1.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR,35));
                textNM1.setFill(Color.WHITE);
                textNM1.setStroke(Color.BLACK);
                textNM1.setStrokeWidth(2);
                textNM1.setText("Media Information:");
                groupMediaSearch.getChildren().add(textNM1);

                TextField textFieldNM1 =new TextField();
                textFieldNM1.setMinSize(600,150);
                textFieldNM1.setLayoutX(340);
                textFieldNM1.setLayoutY(300);
                textFieldNM1.setEditable(false);
                groupMediaSearch.getChildren().add(textFieldNM1);

                Button finMed =new Button("Find Media",new ImageView("addMed.png"));
                finMed.setStyle("-fx-background-color: null");
                finMed.setFont(font);
                finMed.setLayoutX(250);
                finMed.setLayoutY(600);
                groupMediaSearch.getChildren().add(finMed);


                Button back1 =new Button("",new ImageView("back1.png"));
                back1.setStyle("-fx-background-color: null");
                back1.setLayoutX(850);
                back1.setLayoutY(600);
                groupMediaSearch.getChildren().add(back1);

                comboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    switch (newValue) {
                        case "Movie" -> textFieldNM.setText("M");
                        case "Game" -> textFieldNM.setText("G");
                        case "Album" -> textFieldNM.setText("A");
                    }
                });

                finMed.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent-> finMed.setEffect(dropShadow)));
                finMed.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent-> finMed.setEffect(null)));

                back1.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent-> back1.setEffect(dropShadow)));
                back1.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent-> back1.setEffect(null)));

                finMed.setOnAction(actionEvent115 -> {

                    boolean found =false;

                    if(comboBox.getValue().equals("Movie")){

                        for (int i=0 ; i < mediaRental.media.size() ;i++){
                            if (textFieldNM.getText().equals(mediaRental.media.get(i).getCode())){
                                if(mediaRental.media.get(i).getClass().equals(Movie.class)){
                                    textFieldNM1.setText(mediaRental.media.get(i).toString());
                                    found=true;
                                    break;
                                }
                            }
                        }

                        if(!found){
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("The ID entered is not found.");
                            alert.show();

                        }
                    }

                    else if(comboBox.getValue().equals("Game")){

                        for (int i=0 ; i < mediaRental.media.size() ;i++){
                            if (textFieldNM.getText().equals(mediaRental.media.get(i).getCode())){
                                if(mediaRental.media.get(i).getClass().equals(Game.class)){
                                    textFieldNM1.setText(mediaRental.media.get(i).toString());
                                    found = true;
                                    break;
                                }
                            }
                        }

                        if(!found){
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("The ID entered is not found.");
                            alert.show();

                        }
                    }

                    else if (comboBox.getValue().equals("Album")){

                        for (int i=0 ; i < mediaRental.media.size() ;i++){
                            if (textFieldNM.getText().equals(mediaRental.media.get(i).getCode())){
                                if(mediaRental.media.get(i).getClass().equals(Album.class)){
                                    textFieldNM1.setText(mediaRental.media.get(i).toString());
                                    found = true;
                                    break;
                                }
                            }
                        }

                        if(!found){
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("The ID entered is not found.");
                            alert.show();

                        }
                    }
                });

                back1.setOnAction(actionEvent17 -> {
                    stage.setScene(Media);
                    stage.show();
                });

                stage.setScene(sceneMediaSearch);
                stage.show();
            });

            MediaPrint.setOnAction(actionEvent117 -> {

                Group groupMediaPrint =new Group();
                Scene sceneMediaPrint = new Scene(groupMediaPrint, 5000, 5000);

                groupMediaPrint.getChildren().add(viewBG2);

                Text textNM1 = new Text();
                textNM1.setLayoutX(440);
                textNM1.setLayoutY(120);
                textNM1.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR,35));
                textNM1.setFill(Color.WHITE);
                textNM1.setStroke(Color.BLACK);
                textNM1.setStrokeWidth(2);
                textNM1.setText("All Media Information:");
                groupMediaPrint.getChildren().add(textNM1);

                TextArea textArea=new TextArea();
                textArea.setMinSize(1000,400);
                textArea.setLayoutX(150);
                textArea.setLayoutY(150);
                textArea.setEditable(false);
                groupMediaPrint.getChildren().add(textArea);

                /*TextField textFieldNM1 =new TextField();
                textFieldNM1.setMinSize(1000,400);
                textFieldNM1.setLayoutX(150);
                textFieldNM1.setLayoutY(150);
                textFieldNM1.setEditable(false);
                groupMediaPrint.getChildren().add(textFieldNM1);*/

                Button printMed =new Button("Print Media",new ImageView("addMed.png"));
                printMed.setStyle("-fx-background-color: null");
                printMed.setFont(font);
                printMed.setLayoutX(250);
                printMed.setLayoutY(600);
                groupMediaPrint.getChildren().add(printMed);

                Button back1 =new Button("",new ImageView("back1.png"));
                back1.setStyle("-fx-background-color: null");
                back1.setLayoutX(850);
                back1.setLayoutY(600);
                groupMediaPrint.getChildren().add(back1);

                back1.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent-> back1.setEffect(dropShadow)));
                back1.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent-> back1.setEffect(null)));

                printMed.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent-> printMed.setEffect(dropShadow)));
                printMed.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent-> printMed.setEffect(null)));

                printMed.setOnAction(actionEvent118 -> textArea.setText(mediaRental.getAllMediaInfo()));

                back1.setOnAction(actionEvent17 -> {
                    stage.setScene(Media);
                    stage.show();
                });

                stage.setScene(sceneMediaPrint);
                stage.show();

            });

            back.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent-> back.setEffect(dropShadow)));
            back.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent-> back.setEffect(null)));

            back.setOnAction(actionEvent1 -> {

                stage.setScene(primaryScene);
                stage.show();
            });

            stage.setScene(Media);
            stage.show();
        });

        rent.setOnAction(actionEvent -> {

            Group rentGroup = new Group();
            Scene rentScene = new Scene(rentGroup , 1600 , 900);

            rentGroup.getChildren().add(viewBG1);

            Button interestedButton =new Button("Print the interested media in the cart");
            interestedButton.setStyle("-fx-background-color: null");
            interestedButton.setFont(font1);
            interestedButton.setLayoutX(50);
            interestedButton.setLayoutY(40);
            rentGroup.getChildren().add(interestedButton);

            Button rentedButton =new Button("Print the rented media in the cart");
            rentedButton.setStyle("-fx-background-color: null");
            rentedButton.setFont(font1);
            rentedButton.setLayoutX(50);
            rentedButton.setLayoutY(160);
            rentGroup.getChildren().add(rentedButton);

            Button returnMediaButton =new Button("Return Rented media");
            returnMediaButton.setStyle("-fx-background-color: null");
            returnMediaButton.setFont(font1);
            returnMediaButton.setLayoutX(50);
            returnMediaButton.setLayoutY(280);
            rentGroup.getChildren().add(returnMediaButton);

            Button requestButton =new Button("Process Requests");
            requestButton.setStyle("-fx-background-color: null");
            requestButton.setFont(font1);
            requestButton.setLayoutX(50);
            requestButton.setLayoutY(400);
            rentGroup.getChildren().add(requestButton);

            Image backMain=new Image("back.png");
            Button back =new Button("",new ImageView(backMain));
            back.setStyle("-fx-background-color: null");
            back.setFont(font);
            back.setLayoutX(1100);
            back.setLayoutY(600);
            rentGroup.getChildren().add(back);

            interestedButton.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent-> interestedButton.setEffect(dropShadow)));
            interestedButton.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent-> interestedButton.setEffect(null)));

            rentedButton.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent-> rentedButton.setEffect(dropShadow)));
            rentedButton.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent-> rentedButton.setEffect(null)));

            returnMediaButton.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent-> returnMediaButton.setEffect(dropShadow)));
            returnMediaButton.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent-> returnMediaButton.setEffect(null)));

            requestButton.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent-> requestButton.setEffect(dropShadow)));
            requestButton.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent-> requestButton.setEffect(null)));

            back.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent-> back.setEffect(dropShadow)));
            back.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent-> back.setEffect(null)));

            interestedButton.setOnAction(actionEvent124 -> {

                Group interestedButtonGroup = new Group();
                Scene interestedButtonScene = new Scene(interestedButtonGroup,1600,900);

                interestedButtonGroup.getChildren().add(viewBG2);

                Text textNM = new Text();
                textNM.setLayoutX(500);
                textNM.setLayoutY(110);
                textNM.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR,35));
                textNM.setFill(Color.WHITE);
                textNM.setStroke(Color.BLACK);
                textNM.setStrokeWidth(2);
                textNM.setText("Customer ID:");
                interestedButtonGroup.getChildren().add(textNM);

                TextField textFieldNM =new TextField();
                textFieldNM.setMinSize(350,70);
                textFieldNM.setLayoutX(450);
                textFieldNM.setLayoutY(140);
                interestedButtonGroup.getChildren().add(textFieldNM);

                Text textNM1 = new Text();
                textNM1.setLayoutX(440);
                textNM1.setLayoutY(280);
                textNM1.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR,35));
                textNM1.setFill(Color.WHITE);
                textNM1.setStroke(Color.BLACK);
                textNM1.setStrokeWidth(2);
                textNM1.setText("Cart Information:");
                interestedButtonGroup.getChildren().add(textNM1);

                TextField textFieldNM1 =new TextField();
                textFieldNM1.setMinSize(600,150);
                textFieldNM1.setLayoutX(340);
                textFieldNM1.setLayoutY(300);
                textFieldNM1.setEditable(false);
                interestedButtonGroup.getChildren().add(textFieldNM1);

                Button printCart =new Button("Print Items in cart",new ImageView("addMed.png"));
                printCart.setStyle("-fx-background-color: null");
                printCart.setFont(font);
                printCart.setLayoutX(250);
                printCart.setLayoutY(600);
                interestedButtonGroup.getChildren().add(printCart);

                Button back1 =new Button("",new ImageView("back1.png"));
                back1.setStyle("-fx-background-color: null");
                back1.setLayoutX(850);
                back1.setLayoutY(600);
                interestedButtonGroup.getChildren().add(back1);

                printCart.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent-> printCart.setEffect(dropShadow)));
                printCart.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent-> printCart.setEffect(null)));

                back1.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent-> back1.setEffect(dropShadow)));
                back1.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent-> back1.setEffect(null)));

                stage.setScene(interestedButtonScene);
                stage.show();

                printCart.setOnAction(actionEvent125 -> {

                    for(int i =0 ; i < mediaRental.customer.size() ; i++){
                        if(textFieldNM.getText().equals(mediaRental.customer.get(i).getID())){
                            textFieldNM1.setText(mediaRental.customer.get(i).add_to_cart.toString());
                            break;
                        }
                    }
                });

                back1.setOnAction(actionEvent1 -> {
                    stage.setScene(rentScene);
                    stage.show();
                });

            });

            rentedButton.setOnAction(actionEvent126 -> {
                Group rentButtonGroup = new Group();
                Scene rentButtonScene = new Scene(rentButtonGroup,1600,900);

                rentButtonGroup.getChildren().add(viewBG2);

                Text textNM = new Text();
                textNM.setLayoutX(500);
                textNM.setLayoutY(110);
                textNM.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR,35));
                textNM.setFill(Color.WHITE);
                textNM.setStroke(Color.BLACK);
                textNM.setStrokeWidth(2);
                textNM.setText("Customer ID:");
                rentButtonGroup.getChildren().add(textNM);

                TextField textFieldNM =new TextField();
                textFieldNM.setMinSize(350,70);
                textFieldNM.setLayoutX(450);
                textFieldNM.setLayoutY(140);
                rentButtonGroup.getChildren().add(textFieldNM);

                Text textNM1 = new Text();
                textNM1.setLayoutX(440);
                textNM1.setLayoutY(280);
                textNM1.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR,35));
                textNM1.setFill(Color.WHITE);
                textNM1.setStroke(Color.BLACK);
                textNM1.setStrokeWidth(2);
                textNM1.setText("Cart Information:");
                rentButtonGroup.getChildren().add(textNM1);

                TextField textFieldNM1 =new TextField();
                textFieldNM1.setMinSize(600,150);
                textFieldNM1.setLayoutX(340);
                textFieldNM1.setLayoutY(300);
                textFieldNM1.setEditable(false);
                rentButtonGroup.getChildren().add(textFieldNM1);

                Button printCart =new Button("Print Items in rent",new ImageView("addMed.png"));
                printCart.setStyle("-fx-background-color: null");
                printCart.setFont(font);
                printCart.setLayoutX(250);
                printCart.setLayoutY(600);
                rentButtonGroup.getChildren().add(printCart);


                Button back1 =new Button("",new ImageView("back1.png"));
                back1.setStyle("-fx-background-color: null");
                back1.setLayoutX(850);
                back1.setLayoutY(600);
                rentButtonGroup.getChildren().add(back1);


                printCart.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent-> printCart.setEffect(dropShadow)));
                printCart.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent-> printCart.setEffect(null)));

                back1.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent-> back1.setEffect(dropShadow)));
                back1.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent-> back1.setEffect(null)));


                stage.setScene(rentButtonScene);
                stage.show();

                printCart.setOnAction(actionEvent125 -> {

                    for(int i =0 ; i < mediaRental.customer.size() ; i++){
                        if(textFieldNM.getText().equals(mediaRental.customer.get(i).getID())){
                            textFieldNM1.setText(mediaRental.customer.get(i).customer_rented_it.toString());
                            break;
                        }
                    }
                });

                back1.setOnAction(actionEvent1 -> {
                    stage.setScene(rentScene);
                    stage.show();
                });
            });

            requestButton.setOnAction(actionEvent121 -> {

                Group groupRent =new Group();
                Scene Rent = new Scene(groupRent, 5000, 5000);

                groupRent.getChildren().add(viewBG2);

                DropShadow dropShadow1 =new DropShadow();
                dropShadow1.setColor(Color.BLUE);

                ComboBox <String>comboBox = new ComboBox<>();
                comboBox.setLayoutX(600);
                comboBox.setLayoutY(340);
                comboBox.setMinSize(200,50);
                comboBox.getItems().addAll("Movie","Game","Album");
                groupRent.getChildren().add(comboBox);

                comboBox.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent-> comboBox.setEffect(dropShadow1)));
                comboBox.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent-> comboBox.setEffect(null)));

                Button backt =new Button("",new ImageView("back.png"));
                backt.setStyle("-fx-background-color: null");
                backt.setLayoutX(1100);
                backt.setLayoutY(600);
                groupRent.getChildren().add(backt);

                Button addCart =new Button("Add to cart",new ImageView("addCart.png"));
                addCart.setStyle("-fx-background-color: null");
                addCart.setLayoutX(1050);
                addCart.setLayoutY(100);
                Font fontAC = new Font(20);
                addCart.setFont(fontAC);
                groupRent.getChildren().add(addCart);

                Button processCart =new Button("Process Cart",new ImageView("processCart.png"));
                processCart.setStyle("-fx-background-color: null");
                processCart.setLayoutX(1050);
                processCart.setLayoutY(350);
                Font fontPC = new Font(20);
                processCart.setFont(fontPC);
                groupRent.getChildren().add(processCart);

                Text textNC = new Text();
                textNC.setLayoutX(50);
                textNC.setLayoutY(90);
                textNC.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR,25));
                textNC.setFill(Color.WHITE);
                textNC.setStroke(Color.BLACK);
                textNC.setStrokeWidth(2);
                textNC.setText("Customer ID:");
                groupRent.getChildren().add(textNC);

                TextField textFieldR=new TextField();
                textFieldR.setLayoutX(250);
                textFieldR.setLayoutY(50);
                textFieldR.setMinSize(300,70);
                groupRent.getChildren().add(textFieldR);

                TextField textFieldR1 =new TextField();
                textFieldR1.setLayoutX(250);
                textFieldR1.setLayoutY(150);
                textFieldR1.setMinSize(700,120);
                groupRent.getChildren().add(textFieldR1);

                Text textNC1 = new Text();
                textNC1.setLayoutX(50);
                textNC1.setLayoutY(370);
                textNC1.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR,25));
                textNC1.setFill(Color.WHITE);
                textNC1.setStroke(Color.BLACK);
                textNC1.setStrokeWidth(2);
                textNC1.setText("Media Code:");
                groupRent.getChildren().add(textNC1);

                TextField textFieldRm =new TextField();
                textFieldRm.setLayoutX(250);
                textFieldRm.setLayoutY(335);
                textFieldRm.setMinSize(300,70);
                groupRent.getChildren().add(textFieldRm);

                TextField textFieldRm1 =new TextField();
                textFieldRm1.setLayoutX(250);
                textFieldRm1.setLayoutY(435);
                textFieldRm1.setMinSize(700,120);
                groupRent.getChildren().add(textFieldRm1);

                Text textNC2 = new Text();
                textNC2.setLayoutX(50);
                textNC2.setLayoutY(640);
                textNC2.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR,25));
                textNC2.setFill(Color.WHITE);
                textNC2.setStroke(Color.BLACK);
                textNC2.setStrokeWidth(2);
                textNC2.setText("Rented Date:");
                groupRent.getChildren().add(textNC2);

                Date date=new Date();

                TextField textFieldRr =new TextField();
                textFieldRr.setLayoutX(250);
                textFieldRr.setLayoutY(600);
                textFieldRr.setMinSize(300,70);
                groupRent.getChildren().add(textFieldRr);

                textFieldRr.setText(date.toString());



                comboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    switch (newValue) {
                        case "Movie" -> textFieldRm.setText("M");
                        case "Game" -> textFieldRm.setText("G");
                        case "Album" -> textFieldRm.setText("A");
                    }
                });

                backt.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent-> backt.setEffect(dropShadow1)));
                backt.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent-> backt.setEffect(null)));

                addCart.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent-> addCart.setEffect(dropShadow1)));
                addCart.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent-> addCart.setEffect(null)));

                processCart.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent-> processCart.setEffect(dropShadow1)));
                processCart.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent-> processCart.setEffect(null)));

                addCart.setOnAction(actionEvent122 -> {

                    boolean flag;

                    flag = mediaRental.addToCart(comboBox.getValue(),textFieldR.getText(),textFieldRm.getText());

                    writeCustomers(customerFile);

                    Alert alert;
                    if(flag){
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("Adding ["+textFieldRm.getText()+"] to cart "+textFieldR.getText()+"\n");
                    }else {
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Check media code["+textFieldRm.getText()+"] or customer ID "+textFieldR.getText()+"\n");
                    }
                    alert.show();

                    for(int i=0;i < mediaRental.customer.size();i++){
                        if(textFieldR.getText().equals(mediaRental.customer.get(i).getID())){
                            textFieldR1.setText(mediaRental.customer.get(i).toString());
                            break;
                        }
                    }

                    for(int i=0;i < mediaRental.media.size();i++){
                        if(textFieldRm.getText().equals(mediaRental.media.get(i).getCode())){
                            textFieldRm1.setText(mediaRental.media.get(i).toString());
                            break;
                        }
                    }


                });

                processCart.setOnAction(actionEvent123 ->{

                    Archive.add(date +" "+mediaRental.processRequests(textFieldR.getText()));
                    writeCustomers(customerFile);
                    writeMedia(mediaFile);
                    writeArchive(archiveFile);

                    for(int i=0;i < mediaRental.customer.size();i++){
                        if(textFieldR.getText().equals(mediaRental.customer.get(i).getID())){
                            textFieldR1.setText(mediaRental.customer.get(i).toString());
                            break;
                        }
                    }

                    for(int i=0;i < mediaRental.media.size();i++){
                        if(textFieldRm.getText().equals(mediaRental.media.get(i).getCode())){
                            textFieldRm1.setText(mediaRental.media.get(i).toString());
                            break;
                        }
                    }




                });

                backt.setOnAction(actionEvent1 -> {

                    stage.setScene(rentScene);
                    stage.show();
                });

                stage.setScene(Rent);
                stage.show();

            });

            returnMediaButton.setOnAction(actionEvent127 -> {

                Group groupReturn =new Group();
                Scene Return = new Scene(groupReturn, 5000, 5000);

                groupReturn.getChildren().add(viewBG2);

                Text textNM = new Text();
                textNM.setLayoutX(300);
                textNM.setLayoutY(110);
                textNM.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR,35));
                textNM.setFill(Color.WHITE);
                textNM.setStroke(Color.BLACK);
                textNM.setStrokeWidth(2);
                textNM.setText("Customer ID:");
                groupReturn.getChildren().add(textNM);

                TextField textFieldNM =new TextField();
                textFieldNM.setMinSize(350,70);
                textFieldNM.setLayoutX(250);
                textFieldNM.setLayoutY(140);
                groupReturn.getChildren().add(textFieldNM);

                Text textNMa = new Text();
                textNMa.setLayoutX(700);
                textNMa.setLayoutY(110);
                textNMa.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR,35));
                textNMa.setFill(Color.WHITE);
                textNMa.setStroke(Color.BLACK);
                textNMa.setStrokeWidth(2);
                textNMa.setText("Media code:");
                groupReturn.getChildren().add(textNMa);

                TextField textFieldNMa =new TextField();
                textFieldNMa.setMinSize(350,70);
                textFieldNMa.setLayoutX(650);
                textFieldNMa.setLayoutY(140);
                groupReturn.getChildren().add(textFieldNMa);

                Text textNM1 = new Text();
                textNM1.setLayoutX(440);
                textNM1.setLayoutY(280);
                textNM1.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR,35));
                textNM1.setFill(Color.WHITE);
                textNM1.setStroke(Color.BLACK);
                textNM1.setStrokeWidth(2);
                textNM1.setText("Cart Information:");
                groupReturn.getChildren().add(textNM1);

                TextField textFieldNM1 =new TextField();
                textFieldNM1.setMinSize(600,150);
                textFieldNM1.setLayoutX(340);
                textFieldNM1.setLayoutY(300);
                textFieldNM1.setEditable(false);
                groupReturn.getChildren().add(textFieldNM1);

                Button printCart =new Button("Print Items in rent",new ImageView("addMed.png"));
                printCart.setStyle("-fx-background-color: null");
                printCart.setFont(font);
                printCart.setLayoutX(250);
                printCart.setLayoutY(600);
                groupReturn.getChildren().add(printCart);

                Button removeCart =new Button("return",new ImageView("addMed.png"));
                removeCart.setStyle("-fx-background-color: null");
                removeCart.setFont(font);
                removeCart.setLayoutX(100);
                removeCart.setLayoutY(300);
                groupReturn.getChildren().add(removeCart);


                Button back1 =new Button("",new ImageView("back1.png"));
                back1.setStyle("-fx-background-color: null");
                back1.setLayoutX(850);
                back1.setLayoutY(600);
                groupReturn.getChildren().add(back1);


                removeCart.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent-> removeCart.setEffect(dropShadow)));
                removeCart.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent-> removeCart.setEffect(null)));

                printCart.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent-> printCart.setEffect(dropShadow)));
                printCart.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent-> printCart.setEffect(null)));

                back1.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent-> back1.setEffect(dropShadow)));
                back1.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent-> back1.setEffect(null)));

                printCart.setOnAction(actionEvent125 -> {

                    for(int i =0 ; i < mediaRental.customer.size() ; i++){
                        if(textFieldNM.getText().equals(mediaRental.customer.get(i).getID())){
                            textFieldNM1.setText(mediaRental.customer.get(i).customer_rented_it.toString());
                            break;
                        }
                    }
                });

                removeCart.setOnAction(actionEvent128 -> {
                    mediaRental.removeFromRent(textFieldNM.getText(),textFieldNMa.getText());
                    writeCustomers(customerFile);
                    writeMedia(mediaFile);

                    for(int i =0 ; i < mediaRental.customer.size() ; i++){
                        if(textFieldNM.getText().equals(mediaRental.customer.get(i).getID())){
                            textFieldNM1.setText(mediaRental.customer.get(i).customer_rented_it.toString());
                            break;
                        }
                    }
                });

                back1.setOnAction(actionEvent1 -> {
                    stage.setScene(rentScene);
                    stage.show();
                });

                stage.setScene(Return);
                stage.show();
            });

            stage.setScene(rentScene);

            back.setOnAction(actionEvent1 -> {
                stage.setScene(primaryScene);
                stage.show();
            });

        });


        stage.setTitle("Media Rental");
        stage.setScene(primaryScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}