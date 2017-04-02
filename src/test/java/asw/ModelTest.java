package asw;

import asw.model.Comment;
import asw.model.Proposal;
import asw.model.User;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

public class ModelTest {

    @Test
    public void testComment() {
        Comment com = new Comment();
        com.setUser(new User("name", "surname", "email", new Date()));
        assertNotNull(com.getUser());
        assertEquals(com.getCommentString(), null);
        com.setCommentString("comment");
        assertEquals(com.getCommentString(), "comment");
        com.toString();
    }

    @Test
    public void testProposal() {
        Proposal prop = new Proposal("testProposal");
        assertEquals("testProposal", prop.getTitle());
        prop.setTitle("testProposal2");
        assertEquals("testProposal2", prop.getTitle());
        assertTrue(prop.equals(new Proposal()));
        prop.addUpvote();
        assertEquals(prop.getVotes(), 1);
        prop.addDownvote();
        assertEquals(prop.getVotes(), 0);
        assertEquals(prop.getUpvotes(), 1);
        assertEquals(prop.getDownvotes(), 1);
        prop.toString();
    }

    @Test
    public void testUser() throws Exception {
        String password;
        String firstName;
        String lastName;
        String email;
        String address;
        String nationality;
        String ID;
        String NIF;
        Integer pollingStation;
        String birthDate;
        firstName = "name";
        lastName = "lastName";
        password = "password";
        email = "mail@mail.com";
        birthDate = "17/09/1990";
        address = "Calle";
        nationality = "Ingles";
        NIF = "10203040A";
        pollingStation = 123;
        ID = "@irsohgjisejgoiesgj";
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        User user0 = new User();
        User user = new User(password, firstName, lastName, email, format.parse(birthDate));
        User user2 = new User(firstName,lastName, email, format.parse(birthDate));
        User user3 = new User(firstName, lastName, email, birthDate, address, nationality, ID, NIF, pollingStation.toString());

        assertTrue(user.getPassword().equals(password));
        assertTrue(user.getFirstName().equals(firstName));
        assertTrue(user.getLastName().equals(lastName));
        assertTrue(user.getEmail().equals(email));
        assertTrue(user.getBirthDate().equals(format.parse(birthDate)));
        assertTrue(user2.getFirstName().equals(firstName));
        assertTrue(user2.getLastName().equals(lastName));
        assertTrue(user2.getEmail().equals(email));
        assertTrue(user2.getBirthDate().equals(format.parse(birthDate)));
        assertTrue(user3.getFirstName().equals(firstName));
        assertTrue(user3.getLastName().equals(lastName));
        assertTrue(user3.getEmail().equals(email));
        assertTrue(user3.getBirthDate().equals(format.parse(birthDate)));
        assertTrue(user3.getAddress().equals(address));
        assertTrue(user3.getNationality().equals(nationality));
        assertTrue(user3.getId().equals(ID));
        assertTrue(user3.getNIF().equals(NIF));
        assertEquals((Integer)user3.getPollingStation(),pollingStation);
        assertTrue(user3.equals(user3));
        assertFalse(user2.toString().equals(user3.toString()));
        assertTrue(user3.toString().equals(user3.toString()));
        assertFalse(user2.hashCode() == user3.hashCode());
        assertTrue(user3.hashCode() == user3.hashCode());
        user0.setPassword("password");
        assertEquals(user0.getPassword(), "password");
    }
}
