package project.pkg1;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Objects;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class MediaItem implements Serializable {
    
    private String title;
    private String format;
    private String loanedTo;
    private Date dateLoaned;
    
    public MediaItem(String title, String format) {
        this.title = title;
        this.format = format;
    }
    
    public void loan(String loanedTo, Date loanedOn) {
        this.loanedTo = loanedTo;
        this.dateLoaned = loanedOn;
    }
    
    public void returnItem() {
        this.loanedTo = null;
        this.dateLoaned = null;
    }
    
    public String getTitle() {
        return title;
    }
    
    @Override
    public String toString() {
        String response = title + " - " + format;
        
        if (loanedTo != null) {
            response += " (" + loanedTo + " on " + DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.US).format(dateLoaned) + ")";
        }
        
        return response;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.title);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MediaItem other = (MediaItem) obj;
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        return true;
    }
    
    
    public String getLoanedTo() { return loanedTo; }

    
    public static Comparator<MediaItem> compareTitle = new Comparator<MediaItem>(){
        @Override
        public int compare(MediaItem item1, MediaItem item2){
            String title1 = item1.getTitle().toLowerCase();
            String title2 = item2.getTitle().toLowerCase();
            
            return title1.compareTo(title2);
        }
    };
    
    public static Comparator<MediaItem> compareDate = new Comparator<MediaItem>(){
        @Override
        public int compare(MediaItem item1, MediaItem item2){
            Date date1 = item1.dateLoaned;
            Date date2 = item2.dateLoaned;

            try{
                if(date1 == null){
                    date1 = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.US).parse("Dec 31, 9999");
                }
                if(date2 == null){
                    date2 = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.US).parse("Dec 31, 9999");
                }
                return date1.compareTo(date2);
            }catch(Exception e){
                System.out.println(e.getMessage());
                return 0;
            }
        }
    };
    
}
