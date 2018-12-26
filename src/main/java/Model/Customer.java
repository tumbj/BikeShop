package Model;

public class Customer {
    private String username;
    private String password;
    private String address;
    private String firstname;
    private String lastname;
    private String tel_number;

    public Customer(String username, String password, String address, String firstname, String lastname, String tel_number) {
        this.username = username;
        this.password = password;
        this.address = address;
        this.firstname = firstname;
        this.lastname = lastname;
        this.tel_number = tel_number;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getTel_number() {
        return tel_number;
    }
}
