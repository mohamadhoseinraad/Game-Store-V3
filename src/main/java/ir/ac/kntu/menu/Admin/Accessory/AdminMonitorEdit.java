package ir.ac.kntu.menu.Admin.Accessory;

import ir.ac.kntu.HelperClasses.ProductHelper;
import ir.ac.kntu.menu.Menu;
import ir.ac.kntu.models.Admin;
import ir.ac.kntu.models.product.accessories.Monitor;
import ir.ac.kntu.models.product.games.Genre;
import ir.ac.kntu.utils.Scan;
import ir.ac.kntu.utils.TerminalColor;

public class AdminMonitorEdit extends Menu {

    private Monitor monitor;

    private Admin admin;

    public AdminMonitorEdit(Monitor monitor, Admin admin) {
        this.monitor = monitor;
        this.admin = admin;
    }

    @Override
    public void showMenu() {
        AdminMonitorEditOptions option;
        while (showMonitor() && (option = printMenuOptions("EDIT Monitor", AdminMonitorEditOptions.class)) != AdminMonitorEditOptions.EXIT) {
            if (option != null) {
                switch (option) {
                    case EDIT_NAME: {
                        editName();
                        break;
                    }
                    case EDIT_DETAIL: {
                        editDetail();
                        break;
                    }
                    case ADD_AMOUNT: {
                        editAmount();
                        break;
                    }
                    case EDIT_REFRESH_RATE: {
                        editRfRate();
                        break;
                    }
                    case EDIT_SIZE: {
                        editSize();
                        break;
                    }
                    case EDIT_RESPONSE_TIME: {
                        editResTime();
                        break;
                    }
                    case EDIT_PRICE: {
                        editPrice();
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

    private void editResTime() {
        System.out.println("Enter new Response Time : ");
        String input = Scan.getLine();
        if (input.matches("[1-9][0-9.]*")) {
            int newResT = Integer.parseInt(input);
            monitor.setResponseTime(newResT);
            return;
        }
        TerminalColor.red();
        System.out.println("Enter valid input!");
        TerminalColor.reset();
    }

    private void editSize() {
        System.out.println("Enter new Size : ");
        String input = Scan.getLine();
        if (input.matches("[1-9][0-9.]*")) {
            int size = Integer.parseInt(input);
            monitor.setSize(size);
            return;
        }
        TerminalColor.red();
        System.out.println("Enter valid input!");
        TerminalColor.reset();
    }

    private void editRfRate() {
        System.out.println("Enter new Refresh rate : ");
        String input = Scan.getLine();
        if (input.matches("[1-9][0-9.]*")) {
            int newRefRT = Integer.parseInt(input);
            monitor.setRefreshRate(newRefRT);
            return;
        }
        TerminalColor.red();
        System.out.println("Enter valid input!");
        TerminalColor.reset();
    }

    private boolean showMonitor() {
        monitor.showProduct(admin);
        return true;
    }

    private void editPrice() {
        System.out.println("Enter new price : ");
        String input = Scan.getLine();
        if (input.matches("[1-9][0-9.]*")) {
            double newPrice = Double.parseDouble(input);
            monitor.setPrice(newPrice);
            return;
        }
        TerminalColor.red();
        System.out.println("Enter valid price!");
        TerminalColor.reset();
    }

    private void editAmount() {
        System.out.println("Enter amount : ");
        String input = Scan.getLine();
        if (input.matches("[0-9][0-9.]*")) {
            int newAmount = Integer.parseInt(input);
            monitor.addAmount(newAmount);
            return;
        }
        TerminalColor.red();
        System.out.println("Enter valid input!");
        TerminalColor.reset();
    }

    private void editName() {
        System.out.println("Enter new name : ");
        String input = Scan.getLine().trim().toUpperCase();
        if (input.length() > 2) {
            monitor.setName(input);
            return;
        }
        TerminalColor.red();
        System.out.println("Minimum Length 3 character!");
        TerminalColor.reset();
    }

    private void editDetail() {
        System.out.println("Enter new detail : ");
        String input = Scan.getLine().trim().toUpperCase();
        if (input.length() > 2) {
            monitor.setDetails(input);
            return;
        }
        TerminalColor.red();
        System.out.println("Minimum Length 3 character!");
        TerminalColor.reset();
    }


}
