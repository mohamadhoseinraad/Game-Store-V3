package ir.ac.kntu.models.product.accessories;

import ir.ac.kntu.HelperClasses.AccessoryHelper;
import ir.ac.kntu.models.Admin;
import ir.ac.kntu.models.User;

import java.io.Serializable;
import java.util.Objects;

public class Monitor extends Accessory implements Serializable {

    private static int countMonitor = 0;

    private final String id;

    private int size;

    private int refreshRate;

    private int responseTime;

    public Monitor(String name, String details, double price, int amount, int size, int refreshRate, int responseTime, Admin admin) {
        super(name, details, price, amount, AccessoryType.MONITOR, admin);
        this.refreshRate = refreshRate;
        this.responseTime = responseTime;
        this.size = size;
        id = "ACC_MO" + countMonitor++;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getRefreshRate() {
        return refreshRate;
    }

    public void setRefreshRate(int refreshRate) {
        this.refreshRate = refreshRate;
    }

    public int getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(int responseTime) {
        this.responseTime = responseTime;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Monitor monitor = (Monitor) o;
        return Objects.equals(id, monitor.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Monitor{" +
                "id='" + id + '\'' +
                "name='" + getName() + '\'' +
                ", refreshRate hz=" + refreshRate + '\'' +
                ", responseTime=" + responseTime + '\'' +
                ", size=" + size + '\'' +
                "amount=" + getAmount() + '\'' +
                ", price=" + getPrice() +
                ", buys=" + getCountBuy() +
                '}';
    }

    @Override
    public void showProduct(User currentUser) {
        AccessoryHelper.printAccessory(this, currentUser);
    }
}
