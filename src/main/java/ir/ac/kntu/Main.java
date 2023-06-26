package ir.ac.kntu;

import ir.ac.kntu.HelperClasses.DefaultData;
import ir.ac.kntu.menu.Auth.AuthMenu;
import ir.ac.kntu.models.Store;

public class Main {
    public static void main(String[] args) {
        //Store storeDB = DefaultData.addDefaultData();
        //DAOStore.write(storeDB);
        Store storeDB = DAOStore.read();
        AuthMenu authMenu = new AuthMenu(storeDB);
        authMenu.showMenu();
        DAOStore.write(storeDB);
    }
}