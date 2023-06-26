package ir.ac.kntu;

import ir.ac.kntu.HelperClasses.DefaultData;
import ir.ac.kntu.menu.Auth.AuthMenu;
import ir.ac.kntu.models.Store;

/**
 * @author Mohammad hosein Shabaniraad
 */

public class Main {
    public static void main(String[] args) {

        Store storeDB = null;

        /**
         * add stroe from defaultData class
         */
        storeDB = DefaultData.addDefaultData();
        DAOStore.write(storeDB);
        /**
         * add store from DataBase file
         */
        storeDB = DAOStore.read();

        AuthMenu authMenu = new AuthMenu(storeDB);
        authMenu.showMenu();
        DAOStore.write(storeDB);
    }
}