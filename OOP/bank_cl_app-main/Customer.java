import java.lang.String;

public class Customer {
    private int id;
    private String fullName;

    Customer(int id, String fullName){
        this.id = id;
        this.fullName = fullName;
    }

    String getName(){
        return this.fullName;
    }

    int getId(){
        return this.id;
    }

    void setName(String newName){
        this.fullName = newName;
    }
}

