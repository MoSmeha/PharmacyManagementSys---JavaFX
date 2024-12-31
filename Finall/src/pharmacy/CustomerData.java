package pharmacy;

import java.sql.Date;

/**
 *
 * @author WINDOWS 10
 */
public class CustomerData {
    
    private Integer customerId;
    private String type;
    private Integer medicineId;
    private String brand;
    private String productName;
    private Integer quantity;
    private Double price;
    
    
    public CustomerData(Integer customerId, String type, Integer medicineId
            , String brand, String productName, Integer quantity, Double price){
        this.customerId = customerId;
        this.type = type;
        this.medicineId = medicineId;
        this.brand = brand;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        
    }
    
    public Integer getCustomerId(){
        return customerId;
    }
    public String getType(){
        return type;
    }
    public Integer getMedicineId(){
        return medicineId;
    }
    public String getBrand(){
        return brand;
    }
    public String getProductName(){
        return productName;
    }
    public Integer getQuantity(){
        return quantity;
    }
    public Double getPrice(){
        return price;
    }
    
  
}
