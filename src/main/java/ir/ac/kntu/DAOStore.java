package ir.ac.kntu;

import ir.ac.kntu.models.Store;

import java.io.*;

public class DAOStore {
    public static void write(Store store) {
        File file = new File("DataBase");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(store);
        } catch (IOException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }

        //System.out.println("Serialized data is saved in DataBase");
    }

    public static Store read() {
        Store obj = null;
        File file = new File("DataBase");
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            obj = (Store) objectInputStream.readObject();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


        return obj;
    }
}
