package ir.ac.kntu.models.product.accessories;

import ir.ac.kntu.HelperClasses.AccessoryHelper;
import ir.ac.kntu.models.Admin;
import ir.ac.kntu.models.User;

import java.io.Serializable;
import java.util.Objects;

public class GamePad extends Accessory implements Serializable {

    private static int countGamePad = 0;

    private final String id;

    private Device device;

    private Connection connection;

    public GamePad(String name, String details, double price, int amount, Connection connection, Device device, Admin admin) {
        super(name, details, price, amount, AccessoryType.GAME_PAD, admin);
        this.device = device;
        this.connection = connection;
        id = "ACC_GP" + countGamePad++;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
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
        GamePad gamePad = (GamePad) o;
        return Objects.equals(id, gamePad.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "GamePad{" +
                "id='" + id + '\'' +
                "name='" + getName() + '\'' +
                ", device=" + device + '\'' +
                ", connection=" + connection + '\'' +
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
