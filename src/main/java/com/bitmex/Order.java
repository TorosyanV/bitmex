package com.bitmex;

public class Order {

  private Long id;
  private String symbol;
  private String side;
  private Long size;
  private Float price;

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getSide() {
    return side;
  }

  public void setSide(String side) {
    this.side = side;
  }

  public Long getSize() {
    return size;
  }

  public void setSize(Long size) {
    this.size = size;
  }


  public Float getPrice() {
    return price;
  }

  public void setPrice(Float price) {
    this.price = price;
  }

  @Override
  public String toString() {
    return "Order{" +
        "id=" + id +
        ", symbol='" + symbol + '\'' +
        ", side='" + side + '\'' +
        ", size=" + size +
        ", price=" + price +
        '}';
  }
}
