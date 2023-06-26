package ir.ac.kntu.models;

import ir.ac.kntu.models.SearchEnum.UserFilterBy;
import ir.ac.kntu.models.product.games.Game;
import ir.ac.kntu.models.product.Product;
import ir.ac.kntu.models.product.ProductType;
import ir.ac.kntu.models.product.accessories.Accessory;

import java.util.*;

public class Store {
    private ArrayList<User> users = new ArrayList<>();

    private ArrayList<Admin> admins = new ArrayList<>();

    private HashMap<ProductType, ArrayList<Product>> products = new HashMap<>();

    public Store(Set<User> users, HashMap<ProductType, ArrayList<Product>> data) {
        this.users = new ArrayList<>(users);
        this.products = new HashMap<>(data);
    }

    public Store() {
        Admin admin = new Admin("admin", "", "", "admin", true);
        admins.add(admin);
        products.put(ProductType.GAME, new ArrayList<>());
        products.put(ProductType.ACCESSORIES, new ArrayList<>());
    }

    public ArrayList<User> getUsers() {
        return new ArrayList<>(users);
    }

    public ArrayList<Admin> getAdmins() {
        return new ArrayList<>(admins);
    }

    public void setUsers(ArrayList<User> users) {
        this.users = new ArrayList<>(users);
    }

    public User findUserByUsername(String username) {
        if (username == null) {
            return null;
        }
        username = username.toUpperCase();
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        for (User user : admins) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public User findUserById(String userId) {
        userId = userId.toUpperCase();
        for (User user : users) {
            if (user.getId().equals(userId)) {
                return user;
            }
        }
        for (Admin admin : admins) {
            if (admin.getId().equals(userId)) {
                return admin;
            }
        }
        return null;
    }

    public ArrayList<User> usersFilterBy(String inputQuery, UserFilterBy userFilterBy) {
        ArrayList<User> result = new ArrayList<>();
        for (User u : users) {
            if (checkFilter(userFilterBy, u, inputQuery)) {
                result.add(u);
            }
        }
        return result;
    }

    public ArrayList<Admin> adminsFilterBy(String inputQuery, UserFilterBy userFilterBy) {
        ArrayList<Admin> result = new ArrayList<>();
        for (Admin u : admins) {
            if (checkFilter(userFilterBy, u, inputQuery)) {
                result.add(u);
            }
        }
        return result;
    }

    public ArrayList<Admin> gameDevelopers(Game game, boolean access) {
        ArrayList<Admin> result = new ArrayList<>();
        for (Admin u : admins) {
            if (access) {
                if ((game.getDevelopers().contains(u.getId()) && !u.isMastetAdmin()) ||
                        u.isMastetAdmin() && game.creatorId.equals(u.getId())) {
                    result.add(u);
                }
            } else {
                if (!game.getDevelopers().contains(u.getId()) && !u.isMastetAdmin()) {
                    result.add(u);
                }
            }
        }
        return result;
    }

    private boolean checkFilter(UserFilterBy userFilterBy, User user, String input) {
        switch (userFilterBy) {
            case EMAIL: {
                return user.getEmail().startsWith(input);
            }
            case USERNAME: {
                return user.getUsername().startsWith(input);
            }
            case PHONE_NUMBER: {
                return user.getPhoneNumber().startsWith(input);
            }
            case ALL_USER: {
                return true;
            }
            default: {
                return false;
            }
        }
    }

    public ArrayList<Game> getGames() {
        ArrayList<Game> games = new ArrayList<>();
        for (Product p : products.get(ProductType.GAME)) {
            games.add((Game) p);
        }
        return games;
    }

    public Game getGame(String id) {
        for (Product p : products.get(ProductType.GAME)) {
            if (p.getId().equals(id)) {
                return (Game) p;
            }
        }
        return null;
    }

    public ArrayList<Accessory> getAccessories() {
        ArrayList<Accessory> accessories = new ArrayList<>();
        for (Product p : products.get(ProductType.ACCESSORIES)) {
            accessories.add((Accessory) p);
        }
        return accessories;
    }

    public ArrayList<Product> getProducts() {
        ArrayList<Product> productsList = new ArrayList<>();
        for (Product p : products.get(ProductType.GAME)) {
            Collections.addAll(productsList, p);
        }
        for (Product p : products.get(ProductType.ACCESSORIES)) {
            Collections.addAll(productsList, p);
        }
        return productsList;
    }

    public Product findProduct(String id) {
        for (Product product : getProducts()) {
            if (product.getId().equals(id)) {
                return product;
            }
        }
        return null;
    }

    public ArrayList<Product> findProductByName(String name) {
        ArrayList<Product> result = new ArrayList<>();
        for (Product product : getProducts()) {
            if (product.getName().startsWith(name)) {
                result.add(product);
            }
        }
        return result;
    }

    public ArrayList<Product> findProductByPrice(double basePrice, double maxPrice) {
        ArrayList<Product> result = new ArrayList<>();
        for (Product product : getProducts()) {
            if (product.getPrice() >= basePrice && product.getPrice() <= maxPrice) {
                result.add(product);
            }
        }
        return result;
    }

    public boolean addProduct(Product newProduct) {
        if (newProduct == null) {
            return false;
        }
        if (findProduct(newProduct.getId()) == null) {
            if (newProduct.getClass() == Game.class) {
                products.get(ProductType.GAME).add(newProduct);
            } else {
                products.get(ProductType.ACCESSORIES).add(newProduct);
            }
            return true;
        }
        return false;
    }

    public boolean removeProduct(Product product) {
        ProductType productType;
        if (product.getClass() == Game.class) {
            productType = ProductType.GAME;
        } else {
            productType = ProductType.ACCESSORIES;
        }
        if (products.get(productType).contains(product)) {
            products.get(productType).remove(product);
            for (User user : users) {
                user.removeProduct(product);
            }
            return true;
        }
        return false;
    }

    public boolean addUser(User newUser) {
        if (newUser == null) {
            return false;
        }
        if (newUser.getClass() != Admin.class) {
            return users.add(newUser);
        } else {
            return admins.add((Admin) newUser);
        }

    }

    public void removeUser(User user) {
        users.remove(user);
        for (User u : users) {
            u.removeFriend(user);
        }
    }

    public boolean loginUser(String username, String password) {
        username = username.toUpperCase().trim();
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user.isLogin(password);
            }
        }
        for (Admin admin : admins) {
            if (admin.getUsername().equals(username)) {
                return admin.isLogin(password);
            }
        }
        return false;
    }

    public void addAdmin(Admin admin) {
        if (findUserByUsername(admin.getUsername()) == null) {
            admins.add(admin);
        }
    }

    public ArrayList<Product> getAdminProducts(Admin admin, ProductType productType) {
        ArrayList<Product> result = new ArrayList<>();
        if (productType == null) {
            for (ArrayList<Product> eachProducts : products.values()) {
                for (Product product : eachProducts) {
                    if (admin.isMastetAdmin()) {
                        result.add(product);
                    } else if (admin.getProductAccessID().contains(product.getId())) {
                        result.add(product);
                    }
                }
            }
        } else {
            for (Product product : products.get(productType)) {
                if (admin.isMastetAdmin()) {
                    result.add(product);
                } else if (admin.getProductAccessID().contains(product.getId())) {
                    result.add(product);
                }
            }
        }
        return result;
    }
}
