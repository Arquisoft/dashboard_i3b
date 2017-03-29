package asw.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by juanf on 20/03/2017.
 */
@Document(collection = "VotingSystem")
public class User {
    @Id
    String id;
    private String password;
    private Date birthDate;
    private String address;
    private String nationality;
    private int pollingStation;

    private String firstName;
    private String lastName;
    private Integer age;
    private String NIF;
    private String email;

    public User() {}

    public User(String firstName, String lastName, String email, Date birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthDate = birthDate;
    }

    public User(String password, String firstName, String lastName, String email, Date birthDate) {
        this(firstName, lastName, email, birthDate);
        this.password = password;
    }

    public User(String firstName, String lastName, String email, String birthDate,
                    String address, String nationality, String ID, String NIF,
                    String pollingStation) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        setBirthDate(birthDate);
        this.address = address;
        this.nationality = nationality;
        this.id = ID;
        this.NIF = NIF;
        this.pollingStation = Integer.parseInt(pollingStation);
    }

    public User(String[] data) {
        this(data[0], data[1], data[2], data[3], data[4], data[5], data[6], data[7], data[8]);
    }

    private void setBirthDate(String birthDate) {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = format.parse(birthDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.birthDate = date;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Integer getAge() {
        setAge(getBirthDate());
        return age;
    }

    private void setAge(Date birthdate) {
        LocalDate date = birthdate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        this.age = Period.between(date, LocalDate.now()).getYears();
    }

    public String getNationality() {
        return nationality;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public String getAddress() {
        return address;
    }

    public String getId() {
        return id;
    }

    public void setPassword(String pw) {
        this.password = pw;
    }

    public String getNIF() {
        return NIF;
    }

    public int getPollingStation() {
        return pollingStation;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "User [ID=" + id + ", password=" + password + ", birthDate=" + birthDate + ", address=" + address
                + ", nationality=" + nationality + ", pollingStation=" + pollingStation + ", firstName=" + firstName
                + ", lastName=" + lastName + ", age=" + age + ", NIF=" + NIF + ", email=" + email + "]";
    }
}
