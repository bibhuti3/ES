package com.stackroute.elasticsearch.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sun.istack.NotNull;


import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Id;


@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Product implements Serializable {

    @Id
    private UUID _id;
    @NotNull
    private String productname;
    @NotNull
    private String productdescription;
    @NotNull
    private String productcategory;
    @NotNull
    private double productprice;
    @NotNull
    private String productlink;


    public Product() {
    }

    public Product(String productname, String productdescription, String productcategory,
                   double productprice, String productlink) {
        this._id = UUID.randomUUID();
        this.productname = productname;
        this.productdescription = productdescription;
        this.productcategory = productcategory;
        this.productprice = productprice;
        this.productlink = productlink;
    }

    public UUID get_id() {
        return _id;
    }

    public void set_id(UUID _id) {
        this._id = _id;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProductdescription() {
        return productdescription;
    }

    public void setProductdescription(String productdescription) {
        this.productdescription = productdescription;
    }

    public String getProductcategory() {
        return productcategory;
    }

    public void setProductcategory(String productcategory) {
        this.productcategory = productcategory;
    }

    public double getProductprice() {
        return productprice;
    }

    public void setProductprice(double productprice) {
        this.productprice = productprice;
    }

    public String getProductlink() {
        return productlink;
    }

    public void setProductlink(String productlink) {
        this.productlink = productlink;
    }

    @Override
    public String toString() {
        return "Product [_id=" + _id + ", productname=" + productname + ", productdescription=" + productdescription
                + ", productcategory=" + productcategory + ", productprice=" + productprice + ", productlink="
                + productlink + "]";
    }
}
