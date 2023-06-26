package ir.ac.kntu.HelperClasses;

import ir.ac.kntu.models.Admin;
import ir.ac.kntu.models.User;
import ir.ac.kntu.models.product.accessories.*;
import ir.ac.kntu.utils.Scan;
import ir.ac.kntu.utils.TerminalColor;

public class AccessoryHelper {
    private static Accessory makeAccessory(Admin admin) {
        System.out.println("Pleas enter name of Accessory :");
        String name = Scan.getLine();
        System.out.println("Pleas enter detail of Accessory :");
        String detail = Scan.getLine();
        System.out.println("Pleas enter price :");
        String priceSrt = Scan.getLine();
        System.out.println("Pleas enter amount :");
        String amountSrt = Scan.getLine();
        TerminalColor.red();
        if (!priceSrt.matches("[0-9.]+") || !amountSrt.matches("[0-9.]+")) {
            System.out.println("Price  or Amount is not Valid!");
            TerminalColor.reset();
            return null;
        }
        if (name.length() < 3 || detail.length() < 3) {
            System.out.println("Name and detail must be more than 3 character!");
            TerminalColor.reset();
            return null;
        }
        TerminalColor.reset();
        double price = Double.parseDouble(priceSrt);
        return new Accessory(name, detail, price, Integer.parseInt(amountSrt), null, admin);
    }

    public static GamePad makeGamePad(Admin admin) {
        Accessory accessory = makeAccessory(admin);
        if (accessory == null) {
            TerminalColor.red();
            System.out.println("Error in create new product . try again");
            TerminalColor.reset();
            return null;
        }
        System.out.println("Choose Connections type : ");
        Connection connection = ProductHelper.getInputEnumData(Connection.class);
        System.out.println("Choose Devise type : ");
        Device device = ProductHelper.getInputEnumData(Device.class);
        String name = accessory.getName();
        String detail = accessory.getDetails();
        return new GamePad(name, detail, accessory.getPrice(), accessory.getAmount(), connection, device, admin);
    }

    public static Monitor makeMonitor(Admin admin) {
        Accessory accessory = makeAccessory(admin);
        if (accessory == null) {
            TerminalColor.red();
            System.out.println("Error in create new product . try again");
            TerminalColor.reset();
            return null;
        }
        System.out.println("Enter Monitor Size : ");
        String sizeStr = Scan.getLine();
        System.out.println("Enter Monitor Refresh Rate : ");
        String refRateStr = Scan.getLine();
        System.out.println("Enter Monitor Response time : ");
        String resTimeStr = Scan.getLine();
        if (!sizeStr.matches("[1-9.]+") || !resTimeStr.matches("[0-9.]+") ||
                !refRateStr.matches("[1-9.]+")) {
            System.out.println("Size RefreshTIme or Response Time  is not Valid!");
            TerminalColor.reset();
            return null;
        }
        int size = Integer.parseInt(sizeStr);
        int refRate = Integer.parseInt(refRateStr);
        int resTime = Integer.parseInt(refRateStr);
        String name = accessory.getName();
        String detail = accessory.getDetails();
        return new Monitor(name, detail, accessory.getPrice(), accessory.getAmount(), size, resTime, resTime, admin);
    }

    public static void printAccessory(Accessory accessory, User user) {
        TerminalColor.blue();
        System.out.println("|----------------------------");
        TerminalColor.cyan();
        System.out.println("Accessory type : " + accessory.getAccessoryType());
        System.out.print("| Name     : " + accessory.getName());
        TerminalColor.reset();
        System.out.print("  -----  ");
        if (accessory.getPrice() == 0) {
            TerminalColor.green();
            System.out.println("Free");
        } else {
            TerminalColor.cyan();
            System.out.print(accessory.getPrice() + "$ coast  ");
        }
        TerminalColor.yellow();
        System.out.print(" | Score : ");
        ProductHelper.scoreColor(accessory);
        System.out.print(accessory.getScore());
        TerminalColor.cyan();
        System.out.println(" (" + accessory.getRates().size() + ")");
        System.out.println("Amount in Store : " + accessory.getAmount());
        System.out.println(accessory.getDetails());
        TerminalColor.purple();
        if (accessory.getClass() == Monitor.class){
            printMonitorData((Monitor) accessory);
        } else {
            printGamePadData((GamePad) accessory);
        }
        System.out.println();
        TerminalColor.blue();
        System.out.println("|----------------------------");
        TerminalColor.reset();
    }

    private static void printMonitorData(Monitor monitor){
        System.out.println("Size : "+ monitor.getSize() + " inch");
        System.out.println("RefreshRate : " + monitor.getRefreshRate() + "Hz");
        System.out.println("ResponseTime" + monitor.getResponseTime() + "mls");
    }

    private static void printGamePadData(GamePad gamePad){
        System.out.println("Connection : "+ gamePad.getConnection());
        System.out.println("Device : "+ gamePad.getDevice());
    }
}
