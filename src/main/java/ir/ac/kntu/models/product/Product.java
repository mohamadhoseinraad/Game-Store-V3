package ir.ac.kntu.models.product;

import ir.ac.kntu.models.User;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class Product implements Comparable, Serializable {

    private String name;

    private String details;

    private double price;

    private double score = 0;

    private int countBuy = 0;

    private Map<String, Double> rates = new HashMap<>();

    private ProductType productType;

    private ArrayList<Community> communities;

    public Product(String name, String details, double price, ProductType productType) {
        this.name = name;
        this.details = details;
        this.price = price;
        this.productType = productType;
        communities = new ArrayList<>();
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public Map<String, Double> getRates() {
        return rates;
    }

    public void setRates(Map<String, Double> rates) {
        this.rates = rates;
    }

    public void rating(User user, Double rate) {
        if (rate >= 0 && rate <= 10) {
            rates.put(user.getId(), rate);
            updateScore();
        }
    }

    private void updateScore() {
        double sumRate = 0;
        int numberOfVoter = rates.size();
        for (Map.Entry<String, Double> userRateMap : rates.entrySet()) {
            sumRate += userRateMap.getValue();
        }
        score = sumRate / numberOfVoter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ArrayList<Community> getCommunities() {
        return communities;
    }

    public void setCommunities(ArrayList<Community> communities) {
        this.communities = communities;
    }

    public void showAllComment() {
        for (Community community : communities) {
            community.showComment();
        }
    }

    public void addCommunity(Community community) {
        communities.add(community);
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public abstract void showProduct(User currentUser);

    public abstract String getId();

    public void addBuy() {
        countBuy++;
    }

    public int getCountBuy() {
        return countBuy;
    }

    @Override
    public int compareTo(@NotNull Object o) {
        Product product = (Product) o;
        if (this.countBuy > product.countBuy) {
            return 1;
        } else if (this.countBuy < product.countBuy) {
            return -1;
        } else {
            return 0;
        }
    }
}
