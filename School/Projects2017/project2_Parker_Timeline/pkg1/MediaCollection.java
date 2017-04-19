package project.pkg1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.Date;

public class MediaCollection {

    private ObservableList<MediaItem> collection = FXCollections.observableArrayList();

    
    public MediaItem retrieveItem(String title) throws Exception {
        
        MediaItem theItem = new MediaItem(title, "temp");
        for (MediaItem item : collection) {
            if (item.equals(theItem)) {
                return item;
            }
        }

        throw new Exception("There is no item called " + title
                + " in the collection.");
    }
        
        
    public void addItem(String title, String format) throws Exception {
        
        MediaItem newItem = new MediaItem(title, format);
        if (!collection.contains(newItem)) {
            collection.add(newItem);
        } else {
            throw new Exception("An item with the title " + title + 
                    " already exists");
        }
    }

    
    public void removeItem(MediaItem theItem) throws Exception {  
        
        if (collection.contains(theItem)) {
            collection.remove(theItem);
        } else {
            throw new Exception("There is no item called " + theItem.getTitle() + 
                    " in the collection.");
        }
    }

    
    public boolean isLoanable(MediaItem theItem) {
        return (theItem.getLoanedTo() == null);
    }
    
    
    public void loanItem(MediaItem theItem, String loanedTo, Date loanedOn) 
        throws Exception {
        
        if (!isLoanable(theItem)) throw new Exception(theItem.getTitle() + 
                " is already on loan.");
        theItem.loan(loanedTo, loanedOn);
    }

    
    public void returnItem(MediaItem theItem) throws Exception {

        if (theItem.getLoanedTo() == null) {
            throw new Exception(theItem.getTitle() + " is not on loan");
        } else {
            theItem.returnItem();
        }
    }

    
    public void listItems() {
        System.out.println("Your Media Collection");
        for (MediaItem item : collection) {
            System.out.println("\t" + item);
        }
        System.out.println("");
    }

    
    public void storeCollection() throws Exception {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(new File("media.dat")));) {
            oos.writeObject(new ArrayList<MediaItem>(collection));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception("Unable to save the updates to the collection.");            
        }
    }

    
    public void readCollection() throws Exception{

        File f = new File("media.dat");

        if (f.exists()) {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
            List<MediaItem> list = (List<MediaItem>) ois.readObject();
            collection = FXCollections.observableArrayList(list);
        }
    }
    
    
    public int getSize() {
        return collection.size();
    }
    
    public ObservableList<MediaItem> getCollection(){
        return collection;
    }

}
