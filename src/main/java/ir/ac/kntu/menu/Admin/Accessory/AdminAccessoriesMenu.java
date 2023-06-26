package ir.ac.kntu.menu.Admin.Accessory;

import ir.ac.kntu.HelperClasses.AccessoryHelper;
import ir.ac.kntu.HelperClasses.ProductHelper;
import ir.ac.kntu.HelperClasses.SelectItemHelper;
import ir.ac.kntu.menu.ExportUserProduct;
import ir.ac.kntu.menu.Menu;
import ir.ac.kntu.models.Admin;
import ir.ac.kntu.models.Store;
import ir.ac.kntu.models.product.Product;
import ir.ac.kntu.models.product.ProductType;
import ir.ac.kntu.models.product.accessories.Accessory;
import ir.ac.kntu.models.product.accessories.AccessoryType;
import ir.ac.kntu.models.product.accessories.Monitor;
import ir.ac.kntu.utils.TerminalColor;

public class AdminAccessoriesMenu extends Menu {

    private final Store storeDB;

    private final Admin admin;


    public AdminAccessoriesMenu(Store storeDB, Admin admin) {
        this.storeDB = storeDB;
        this.admin = admin;
    }

    @Override
    public void showMenu() {
        AdminAccessoriesMenuOptions option;
        while ((option = printMenuOptions("Admin Menu-Accessories", AdminAccessoriesMenuOptions.class)) != AdminAccessoriesMenuOptions.EXIT) {
            if (option != null) {
                switch (option) {
                    case ADD_ACCESSORY: {
                        addAccessory();
                        break;
                    }
                    case EDIT_ACCESSORY: {
                        editAccessory();
                        break;
                    }
                    case REMOVE_ACCESSORY: {
                        removeAccessory();
                        break;
                    }
                    case EXPORT_TO_HTML: {
                        exportHtml();
                        break;
                    }
                    case BACK: {
                        return;
                    }
                    default:
                        System.out.println("Invalid choose");
                }
            }
        }
        System.exit(0);
    }

    private void exportHtml() {
        ExportUserProduct exportProducts = new ExportUserProduct(storeDB.getAdminProducts(admin, ProductType.ACCESSORIES));
        exportProducts.showMenu();
    }

    private void addAccessory() {
        System.out.println("Select what you want add ? ");
        AccessoryType accessoryType = ProductHelper.getInputEnumData(AccessoryType.class);
        Product newAccessory;
        switch (accessoryType) {
            case MONITOR: {
                newAccessory = AccessoryHelper.makeMonitor(admin);
                break;
            }
            case GAME_PAD: {
                newAccessory = AccessoryHelper.makeGamePad(admin);
                break;
            }
            default: {
                return;
            }
        }
        if (newAccessory != null) {
            if (storeDB.addProduct(newAccessory)) {
                TerminalColor.green();
                admin.addAccessProduct(newAccessory);
                System.out.println(newAccessory.getName() + " added to DB");
                TerminalColor.reset();
            }
        }
    }

    private void editAccessory() {
        Product product = SelectItemHelper.searchInCostumeProtectByName(storeDB.getAdminProducts(admin, ProductType.ACCESSORIES));
        Accessory accessory = (Accessory) product;
        if (product == null) {
            return;
        }
        switch (accessory.getAccessoryType()){
            case GAME_PAD :{
                System.out.println("Coming soon !");
                break;
            }
            case MONITOR:{
                AdminMonitorEdit adminMonitorEdit = new AdminMonitorEdit((Monitor) accessory, admin);
                adminMonitorEdit.showMenu();
                break;
            }
            default:{
                return;
            }
        }
    }

    private void removeAccessory() {
        Accessory accessory = (Accessory) SelectItemHelper.searchInCostumeProtectByName(storeDB.getAdminProducts(admin, ProductType.ACCESSORIES));

        if (accessory != null && checkPermission(accessory) && storeDB.removeProduct(accessory)) {
            TerminalColor.green();
            admin.removeAccessProduct(accessory);
            System.out.println(accessory.getName() + " with " + accessory.getId() + " id successfully deleted !");
            TerminalColor.reset();
            return;
        }
        TerminalColor.red();
        System.out.println("Fail delete game !");
        TerminalColor.red();
    }

    private boolean checkPermission(Accessory accessory) {
        if (!accessory.getSellerId().equals(admin.getId()) && !admin.isMastetAdmin()) {
            TerminalColor.red();
            System.out.println("Fail delete game ! You are not creator of this game !");
            TerminalColor.reset();
            return false;
        }
        return true;
    }

}
