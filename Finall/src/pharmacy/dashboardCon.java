package pharmacy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import javafx.stage.Stage;
import javafx.stage.StageStyle;





public class dashboardCon implements Initializable {

    @FXML
    private Button AddMedicines_AddBtn;

    @FXML
    private TextField AddMedicines_Brand;

    @FXML
    private Button AddMedicines_ClearBtn;

    @FXML
    private Button AddMedicines_DeleteBtn;

    @FXML
    private TextField AddMedicines_ID;

    @FXML
    private TextField AddMedicines_Name;

    @FXML
    private TextField AddMedicines_Price;

    @FXML
    private TextField AddMedicines_Search;

    @FXML
    private TableView<medicinedata> AddMedicines_TableView;

    @FXML
    private ComboBox<?> AddMedicines_Status;
    
    @FXML
    private ComboBox<?> AddMedicines_Type;

    @FXML
    private Button AddMedicines_UpdateBtn;

    @FXML
    private TableColumn<medicinedata, String> AddMedicines_col_Brand;

    @FXML
    private TableColumn<medicinedata, String> AddMedicines_col_ID;

    @FXML
    private TableColumn<medicinedata, String> AddMedicines_col_Name;

    @FXML
    private TableColumn<medicinedata, String> AddMedicines_col_Price;

    @FXML
    private TableColumn<medicinedata, String> AddMedicines_col_Status;

    @FXML
    private TableColumn<medicinedata, String> AddMedicines_col_Type;

    @FXML
    private AnchorPane AddMedicines_form;

    @FXML
    private Button addmed_btn;

    @FXML
    private Button close_btn;

    @FXML
    private Button menu_AddNotes;

    @FXML
    private TextField menu_Notes;
    
    @FXML
    private Button logout_btn;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button menu_btn;

    @FXML
    private AnchorPane menu_form;

    @FXML
    private Label menu_totalcustomers;

    @FXML
    private Label menu_totalincome;

    @FXML
    private Label menu_totalmedicine;

    @FXML
    private Button purchase_addBtn;

    @FXML
    private TextField purchase_amount;

    @FXML
    private Label purchase_balance;

    @FXML
    private ComboBox<?> purchase_brand;

    @FXML
    private Button purchase_btn;

    @FXML
    private TableColumn<CustomerData, String> purchase_col_Id;

    @FXML
    private TableColumn<CustomerData, String> purchase_col_brand;

    @FXML
    private TableColumn<CustomerData, String> purchase_col_name;

    @FXML
    private TableColumn<CustomerData, String> purchase_col_price;

    @FXML
    private TableColumn<CustomerData, String> purchase_col_quantity;

    @FXML
    private TableColumn<CustomerData, String> purchase_col_type;

    @FXML
    private AnchorPane purchase_form;

    @FXML
    private ComboBox<?> purchase_medicineID;

    @FXML
    private Button purchase_payBtn;

    @FXML
    private ComboBox<?> purchase_productname;

    @FXML
    private TableView<CustomerData> purchase_tableview;

    @FXML
    private Label purchase_total;

    @FXML
    private ComboBox<String> purchase_type;
    
    @FXML
    private TextArea menu_notes_read;

    @FXML
    private Button menu_read_notesbtn;
 
    @FXML
    private Spinner<Integer> purchase_quantity;

    
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;
    
    
    public void menunote() {
        String sql = "INSERT INTO notes (note_content) VALUES (?)";
        String noteContent = menu_Notes.getText();
        Connection connect = null;

        try {
            if (noteContent.isEmpty()) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please write your note");
                alert.showAndWait();
            } else {
                connect = Database.connectDb();

                try (PreparedStatement preparedStatement = connect.prepareStatement(sql)) {
                    preparedStatement.setString(1, noteContent);
                    preparedStatement.executeUpdate();

                    
                    appendNoteToCSV(noteContent);

                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("Use read to note to get reminded");
                    alert.showAndWait();

                    menu_Notes.clear();
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
           
        } finally {
            
            try {
                if (connect != null) {
                    connect.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
               
            }
        }
    }

    private void appendNoteToCSV(String note) throws IOException {
        
        String csvFilePath = "text.csv";

        try (FileWriter fileWriter = new FileWriter(csvFilePath, true)) {
           
            fileWriter.append(note);
            fileWriter.append("\n");  
        }
    }

    public void readNoteBtn() {
        try {
            String notes = readnotesfromCSV();
            menu_notes_read.setText(notes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readnotesfromCSV() throws IOException {
        String csvFilePath = "text.csv";
        StringBuilder notes = new StringBuilder();

        try (Scanner scanner = new Scanner(new File(csvFilePath))) {
            while (scanner.hasNextLine()) {
                notes.append(scanner.nextLine()).append("\n");
            }
        }

        return notes.toString();
    }
    
    //USING BUFFER READER BUT THE PDF STATED SCANNER 
    /*private String readnotesfromCSV() throws IOException {
        
        String csvFilePath = "text.csv";
        StringBuilder notes = new StringBuilder();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                notes.append(line).append("\n");
            }
        }

        return notes.toString();
    }

    
    public void readNoteBtn() {
        try {
            String notes = readnotesfromCSV();
            menu_notes_read.setText(notes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
    
        
    public void homeAM(){
        
        String sql = "SELECT COUNT(id) FROM medicine WHERE status = 'Available'";
        
        connect = Database.connectDb();
        int countAM = 0;
        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            while(result.next()){
                countAM = result.getInt("COUNT(id)");
            }
            
            menu_totalmedicine.setText(String.valueOf(countAM));
            
        }catch(Exception e){e.printStackTrace();}
        
    }
    
    public void homeTI(){
        String sql = "SELECT SUM(total) FROM customer_info";
        
        connect = Database.connectDb();
        double totalDisplay = 0;
        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            while(result.next()){
                totalDisplay = result.getDouble("SUM(total)");
            }
            
            menu_totalincome.setText("$" + String.valueOf(totalDisplay));
            
        }catch(Exception e){e.printStackTrace();}
        
    }
    
    
    
    public void homeTC(){
        
        String sql = "SELECT COUNT(id) FROM customer_info";
        
        connect = Database.connectDb();
        int countTC = 0;
        
        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            while(result.next()){
                countTC = result.getInt("COUNT(id)");
            }
            
            menu_totalcustomers.setText(String.valueOf(countTC));
            
        }catch(Exception e){e.printStackTrace();}
        
    }
    
    public void addMedicinesAdd() {
        try {
            Alert alert;

            if (AddMedicines_ID.getText().isEmpty()
                    || AddMedicines_Brand.getText().isEmpty()
                    || AddMedicines_Name.getText().isEmpty()
                    || AddMedicines_Type.getSelectionModel().getSelectedItem() == null
                    || AddMedicines_Status.getSelectionModel().getSelectedItem() == null
                    || AddMedicines_Price.getText().isEmpty()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("FILL ALL THE BLANK FIELDS");
                alert.showAndWait();

            } else {
                int medicineId;
                try {
                    medicineId = Integer.parseInt(AddMedicines_ID.getText());
                    if (medicineId < 0) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException e) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Medicine ID must be a valid non-negative integer");
                    alert.showAndWait();
                    return;
                }

                double price;
                try {
                    price = Double.parseDouble(AddMedicines_Price.getText());
                    if (price < 0) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException e) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Price must be a valid non-negative number");
                    alert.showAndWait();
                    return;
                }

                String checkData = "SELECT medicine_id FROM medicine WHERE medicine_id = '" + medicineId + "'";

                statement = connect.createStatement();
                result = statement.executeQuery(checkData);

                if (result.next()) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Medicine ID: " + medicineId + " was already exist!");
                    alert.showAndWait();
                } else {
                    String sql = "INSERT INTO medicine (medicine_id, brand, productName, type, status, price) "
                            + "VALUES(?,?,?,?,?,?)";

                    prepare = connect.prepareStatement(sql);
                    prepare.setInt(1, medicineId);
                    prepare.setString(2, AddMedicines_Brand.getText());
                    prepare.setString(3, AddMedicines_Name.getText());
                    prepare.setString(4, (String) AddMedicines_Type.getSelectionModel().getSelectedItem());
                    prepare.setString(5, (String) AddMedicines_Status.getSelectionModel().getSelectedItem());
                    prepare.setDouble(6, price);

                    prepare.executeUpdate();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();

                    addMedicineShowListData();
                    addMedicineReset();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addMedicineUpdate() {
        try {
            Alert alert;

            if (AddMedicines_ID.getText().isEmpty()
                    || AddMedicines_Brand.getText().isEmpty()
                    || AddMedicines_Name.getText().isEmpty()
                    || AddMedicines_Type.getSelectionModel().getSelectedItem() == null
                    || AddMedicines_Status.getSelectionModel().getSelectedItem() == null
                    || AddMedicines_Price.getText().isEmpty()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("fill all blank fields");
                alert.showAndWait();
            } else {
                int medicineId;
                try {
                    medicineId = Integer.parseInt(AddMedicines_ID.getText());
                    if (medicineId < 0) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException e) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Medicine ID must be a valid non-negative integer");
                    alert.showAndWait();
                    return;
                }

                double price;
                try {
                    price = Double.parseDouble(AddMedicines_Price.getText());
                    if (price < 0) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException e) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Price must be a valid non-negative number");
                    alert.showAndWait();
                    return;
                }

                String sql = "UPDATE medicine SET brand = '"
                        + AddMedicines_Brand.getText() + "', productName = '"
                        + AddMedicines_Name.getText() + "', type = '"
                        + AddMedicines_Type.getSelectionModel().getSelectedItem() + "', status = '"
                        + AddMedicines_Status.getSelectionModel().getSelectedItem() + "', price = '"
                        + price + "' WHERE medicine_id = " + medicineId;

                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Sure:" + medicineId + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Updated!");
                    alert.showAndWait();

                    addMedicineShowListData();
                    addMedicineReset();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void addMedicineDelete(){
        
        String sql = "DELETE FROM medicine WHERE medicine_id = '"+AddMedicines_ID.getText()+"'";
        
        connect = Database.connectDb();
        
        try{
            Alert alert;
            
            if(AddMedicines_ID.getText().isEmpty()
                    || AddMedicines_Brand.getText().isEmpty()
                    || AddMedicines_Name.getText().isEmpty()
                    || AddMedicines_Type.getSelectionModel().getSelectedItem() == null
                    || AddMedicines_Status.getSelectionModel().getSelectedItem() == null
                    || AddMedicines_Price.getText().isEmpty())
                    {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText(" fill all blank fields");
                alert.showAndWait();
            }else{
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to DELETE Medicine ID:" + AddMedicines_ID.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();
                
                if(option.get().equals(ButtonType.OK)){
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);
                    
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Deleted!");
                    alert.showAndWait();
                    
                    addMedicineShowListData();
                    addMedicineReset();
                }
            }
        }catch(Exception e){e.printStackTrace();}
    }
    
    public void addMedicineReset(){
        AddMedicines_ID.setText("");
        AddMedicines_Brand.setText("");
        AddMedicines_Name.setText("");
        AddMedicines_Price.setText("");
        AddMedicines_Type.getSelectionModel().clearSelection();
        AddMedicines_Status.getSelectionModel().clearSelection();
        
        
        
    }
    
    private String[] addMedicineListT = {"Hydrocodone", "Antibiotics", "Metformin", "Losartan", "Albuterol"};
    public void addMedicineListType(){
        List<String> listT = new ArrayList<>();
        
        for(String data: addMedicineListT){
            listT.add(data);
        }
        
        ObservableList listData = FXCollections.observableArrayList(listT);
        AddMedicines_Type.setItems(listData);
        
    }
    
    
    
    private String[] addMedicineStatus = {"Available", "Not Available"};
    public void addMedicineListStatus(){
        List<String> listS = new ArrayList<>();
        
        for(String data: addMedicineStatus){
            listS.add(data);
        }
        
        ObservableList listData = FXCollections.observableArrayList(listS);
        AddMedicines_Status.setItems(listData);
    }
    
    
    
    public ObservableList<medicinedata> addMedicinesListData(){
        
        String sql = "SELECT * FROM medicine";
        
        ObservableList<medicinedata> listData = FXCollections.observableArrayList();
        
        connect = Database.connectDb();
        
        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            medicinedata medData;
            
            while(result.next()){
                medData = new medicinedata(result.getInt("medicine_id"), result.getString("brand")
                        , result.getString("productName"), result.getString("type")
                        , result.getString("status"), result.getDouble("price")
                        );
                
                listData.add(medData);
            }
            
        }catch(Exception e){e.printStackTrace();}
        return listData;
    }
    
    private ObservableList<medicinedata> addMedicineList;
    public void addMedicineShowListData(){
        addMedicineList = addMedicinesListData();
        
        AddMedicines_col_ID.setCellValueFactory(new PropertyValueFactory<>("medicineId"));
        AddMedicines_col_Brand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        AddMedicines_col_Name.setCellValueFactory(new PropertyValueFactory<>("productName"));
        AddMedicines_col_Type.setCellValueFactory(new PropertyValueFactory<>("type"));
        AddMedicines_col_Price.setCellValueFactory(new PropertyValueFactory<>("price"));
        AddMedicines_col_Status.setCellValueFactory(new PropertyValueFactory<>("status"));
        
        
        AddMedicines_TableView.setItems(addMedicineList);
        
    }
    
    public void addMedicineSearch(){
        
        FilteredList<medicinedata> filter = new FilteredList<>(addMedicineList, e-> true);
        
        AddMedicines_Search.textProperty().addListener((Observable, oldValue, newValue) ->{
            
            filter.setPredicate(predicateMedicineData ->{
                
                if(newValue == null || newValue.isEmpty()){
                    return true;
                }
                
                String searchKey = newValue.toLowerCase();
                
                if(predicateMedicineData.getMedicineId().toString().contains(searchKey)){
                    return true;
                }else if(predicateMedicineData.getBrand().toLowerCase().contains(searchKey)){
                    return true;
                }else if(predicateMedicineData.getProductName().toLowerCase().contains(searchKey)){
                    return true;
                }else if(predicateMedicineData.getType().toLowerCase().contains(searchKey)){
                    return true;
                }else if(predicateMedicineData.getStatus().toLowerCase().contains(searchKey)){
                    return true;
                }else if(predicateMedicineData.getPrice().toString().contains(searchKey)){
                    return true;
                
                }else return false;
            });
        });
        
        SortedList<medicinedata> sortList = new SortedList<>(filter);
        
        sortList.comparatorProperty().bind(AddMedicines_TableView.comparatorProperty());
        AddMedicines_TableView.setItems(sortList);
        
    }
    
    public void addMedicineSelect(){
        medicinedata medData = AddMedicines_TableView.getSelectionModel().getSelectedItem();
        int num = AddMedicines_TableView.getSelectionModel().getSelectedIndex();

        if((num - 1) < - 1){return;}
        
        AddMedicines_ID.setText(String.valueOf(medData.getMedicineId()));
        AddMedicines_Brand.setText(medData.getBrand());
        AddMedicines_Name.setText(medData.getProductName());
        AddMedicines_Price.setText(String.valueOf(medData.getPrice()));
          
    }
    
    private double totalP;
    public void purchaseAdd(){
        purchaseCustomerId();
        
        String sql = "INSERT INTO customer (customer_id,type,medicine_id,brand,productName,quantity,price)"
                + " VALUES(?,?,?,?,?,?,?)";
        
        connect = Database.connectDb();
        
        try{
            Alert alert;
            
            if(purchase_type.getSelectionModel().getSelectedItem() == null
                    || purchase_medicineID.getSelectionModel().getSelectedItem() == null
                    || purchase_brand.getSelectionModel().getSelectedItem() == null
                    || purchase_productname.getSelectionModel().getSelectedItem() == null){
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }else{
                
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, String.valueOf(customerId));
                prepare.setString(2, (String)purchase_type.getSelectionModel().getSelectedItem());
                prepare.setString(3, (String)purchase_medicineID.getSelectionModel().getSelectedItem());
                prepare.setString(4, (String)purchase_brand.getSelectionModel().getSelectedItem());
                prepare.setString(5, (String)purchase_productname.getSelectionModel().getSelectedItem());
                prepare.setString(6, String.valueOf(qty));
                
                String checkData = "SELECT price FROM medicine WHERE medicine_id = '"
                        +purchase_medicineID.getSelectionModel().getSelectedItem()+"'";
                
                statement = connect.createStatement();
                result = statement.executeQuery(checkData);
                double priceD = 0;
                if(result.next()){
                    priceD = result.getDouble("price");
                }
                
                totalP = (priceD * qty);
                
                prepare.setString(7, String.valueOf(totalP));
                
                prepare.executeUpdate();
                
                purchaseShowListData();
                purchaseDisplayTotal();
            }
            
            prepare = connect.prepareStatement(sql);
        }catch(Exception e){e.printStackTrace();}
    }
    private double totalPriceD;
    public void purchaseDisplayTotal(){
        
        String sql = "SELECT SUM(price) FROM customer WHERE customer_id = '"+customerId+"'";
        
        connect = Database.connectDb();
        
        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            if(result.next()){
                totalPriceD = result.getDouble("SUM(price)");
            }
            purchase_total.setText("$" + String.valueOf(totalPriceD));
            
        }catch(Exception e){e.printStackTrace();}
        
    }
    
    private double balance;
    private double amount;
    public void purchaseAmount(){
        
        if(purchase_amount.getText().isEmpty() || totalPriceD == 0){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Invalid :3");
            alert.showAndWait();
        }else{
            amount = Double.parseDouble(purchase_amount.getText());
            if(totalPriceD > amount){
                purchase_amount.setText("");
            }else{
                balance = (amount - totalPriceD);
                
                purchase_balance.setText("$"+String.valueOf(balance));
            }
        }
        
    }
    
    public void purchasePay(){
        purchaseCustomerId();
        String sql = "INSERT INTO customer_info (customer_id, total) "
                + "VALUES(?,?)";
        
        connect = Database.connectDb();
        
        try{
            Alert alert;
            
            if(totalPriceD == 0){
                alert = new Alert(AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Something wrong :3");
                alert.showAndWait();
            }else{
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setContentText("Are you sure?");
                Optional<ButtonType> option = alert.showAndWait();
                
                if(option.get().equals(ButtonType.OK)){
                
                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, String.valueOf(customerId));
                    prepare.setString(2, String.valueOf(totalPriceD));

                    
                    prepare.executeUpdate();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Successful!");
                    alert.showAndWait();
                    
                    purchase_amount.setText("");
                    purchase_balance.setText("$0.0");
                    
                }
            }
            
        }catch(Exception e){e.printStackTrace();}
        
    }
    
    private SpinnerValueFactory<Integer> spinner;
    public void purchaseShowValue(){
        spinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20, 0);
        purchase_quantity.setValueFactory(spinner);
    }
    
    private int qty;
    public void purchaseQuantity(){
        qty = purchase_quantity.getValue();
    }
    
    public ObservableList<CustomerData> purchaseListData(){
        purchaseCustomerId();
        
        String sql = "SELECT * FROM customer WHERE customer_id = '"+customerId+"'";
        
        ObservableList<CustomerData> listData = FXCollections.observableArrayList();
        
        connect = Database.connectDb();
        
        try{
            CustomerData customerD;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            while(result.next()){
                customerD = new CustomerData(result.getInt("customer_id")
                        , result.getString("type"), result.getInt("medicine_id")
                        , result.getString("brand"), result.getString("productName")
                        , result.getInt("quantity"), result.getDouble("price"));
                        
                
                listData.add(customerD);
            }
            
        }catch(Exception e){e.printStackTrace();}
        return listData;
    }
    
    private ObservableList<CustomerData> purchaseList;
    public void purchaseShowListData(){
        purchaseList = purchaseListData();
        
        purchase_col_Id.setCellValueFactory(new PropertyValueFactory<>("medicineId"));
        purchase_col_brand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        purchase_col_name.setCellValueFactory(new PropertyValueFactory<>("productName"));
        purchase_col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        purchase_col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        purchase_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        purchase_tableview.setItems(purchaseList);
        
    }
    
    private int customerId;
    public void purchaseCustomerId(){
        
        String sql = "SELECT customer_id FROM customer";
        
        connect = Database.connectDb();
        
        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            while(result.next()){
                customerId = result.getInt("customer_id");
            }
            int cID = 0;
            String checkData = "SELECT customer_id FROM customer_info";
            
            statement = connect.createStatement();
            result = statement.executeQuery(checkData);
            
            while(result.next()){
                cID = result.getInt("customer_id");
            }
            
            if(customerId == 0){
                customerId+=1;
            }else if(cID == customerId){
                customerId = cID+1;
            }
            
        }catch(Exception e){e.printStackTrace();}
        
    }
    
    public void purchaseType(){
        
        String sql = "SELECT type FROM medicine WHERE status = 'Available'";
        
        connect = Database.connectDb();
        
        try{
            ObservableList listData = FXCollections.observableArrayList();
            
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            while(result.next()){
                listData.add(result.getString("type"));
            }
            purchase_type.setItems(listData);
            
            purchaseMedicineId();
            
        }catch(Exception e){e.printStackTrace();}
        
    }
    
    public void purchaseMedicineId(){
        
        String sql = "SELECT * FROM medicine WHERE type = '"
                +purchase_type.getSelectionModel().getSelectedItem()+"'";
        
        connect = Database.connectDb();
        
        try{
            ObservableList listData = FXCollections.observableArrayList();
            
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            while(result.next()){
                listData.add(result.getString("medicine_id"));
            }
            purchase_medicineID.setItems(listData);
            
            purchaseBrand();
        }catch(Exception e){e.printStackTrace();}
    }
    
    public void purchaseBrand(){
        
        String sql = "SELECT * FROM medicine WHERE medicine_id = '"
                +purchase_medicineID.getSelectionModel().getSelectedItem()+"'";
        
        connect = Database.connectDb();
        
        try{
            ObservableList listData = FXCollections.observableArrayList();
            
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            while(result.next()){
                listData.add(result.getString("brand"));
            }
            purchase_brand.setItems(listData);
            
            purchaseProductName();
            
        }catch(Exception e){e.printStackTrace();}
        
    }
    
    public void purchaseProductName(){
        
        String sql = "SELECT * FROM medicine WHERE brand = '"
                +purchase_brand.getSelectionModel().getSelectedItem()+"'";
        
        connect = Database.connectDb();
        
        try{
            ObservableList listData = FXCollections.observableArrayList();
            
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            while(result.next()){
                listData.add(result.getString("productName"));
            }
            purchase_productname.setItems(listData);
        }catch(Exception e){e.printStackTrace();}
        
    }
    
    public void defaultNav(){
        menu_btn.setStyle("-fx-background-color:linear-gradient(to bottom right,  #4cbb17 , #203354);");
    }
    
    public void switchForm(ActionEvent event){
        
        if(event.getSource() == menu_btn){
            menu_form.setVisible(true);
            AddMedicines_form.setVisible(false);
            purchase_form.setVisible(false);
            
            menu_btn.setStyle("-fx-background-color:linear-gradient(to bottom right,  #4cbb17 , #203354);");
            addmed_btn.setStyle("-fx-background-color:transparent");
            purchase_btn.setStyle("-fx-background-color:transparent");
            
            
            homeAM();
            homeTI();
            homeTC();
            
        }else if(event.getSource() == addmed_btn){
            menu_form.setVisible(false);
            AddMedicines_form.setVisible(true);
            purchase_form.setVisible(false);
            
            addmed_btn.setStyle("-fx-background-color:linear-gradient(to bottom right,  #4cbb17 , #203354);");
            menu_btn.setStyle("-fx-background-color:transparent");
            purchase_btn.setStyle("-fx-background-color:transparent");
            
            addMedicineShowListData();
            addMedicineListStatus();
            addMedicineListType();
            addMedicineSearch();
            
        }else if(event.getSource() == purchase_btn){
            menu_form.setVisible(false);
            AddMedicines_form.setVisible(false);
            purchase_form.setVisible(true);
            
            purchase_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #4cbb17 , #203354);");
            menu_btn.setStyle("-fx-background-color:transparent");
            addmed_btn.setStyle("-fx-background-color:transparent");
            
            purchase_type.setValue(null);
            purchaseType();
            purchaseMedicineId();
            purchaseBrand();
            purchaseProductName();
            purchaseShowListData();
            purchaseShowValue();
            purchaseDisplayTotal();
            
        }
        
    }
    
    
    private double x = 0;
    private double y = 0;

    public void logout() {

        try {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                
            	logout_btn.getScene().getWindow().hide();
                
                Parent root = FXMLLoader.load(getClass().getResource("FXMLDoc.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);

                root.setOnMousePressed((MouseEvent event) -> {
                    x = event.getSceneX();
                    y = event.getSceneY();
                });

                root.setOnMouseDragged((MouseEvent event) -> {
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);

                    stage.setOpacity(.8);
                });

                root.setOnMouseReleased((MouseEvent event) -> {
                    stage.setOpacity(1);
                });

                stage.initStyle(StageStyle.TRANSPARENT);

                stage.setScene(scene);
                stage.show();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

  

    public void close() {
        System.exit(0);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        defaultNav();

        homeAM();
        homeTI();
        homeTC();
       
        
        addMedicineShowListData();
        addMedicineListStatus();
        addMedicineListType();
        
        purchaseType();
        purchaseMedicineId();
        purchaseBrand();
        purchaseProductName();
        purchaseShowListData();
        purchaseShowValue();
        purchaseDisplayTotal();
        
    }

}
