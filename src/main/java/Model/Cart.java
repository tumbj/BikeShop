package Model;

import java.util.ArrayList;

public class Cart {
    ArrayList<Product> products;

    public static Cart getInstance(){
        return  new Cart();
    }
    private Cart() {
        this.products =new ArrayList<>();
    }

    public ArrayList<Product> getProducts() {
        return products;
    }
    public void addProduct(Product product){
        products.add(product);
    }
    public void removeProduct(String id){
        for (Product product:products) {
            if(product.getId().equals(id)){
                products.remove(product);
                break;
            }
        }
    }


}
