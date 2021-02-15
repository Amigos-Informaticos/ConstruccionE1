package Tests;

import Models.Admin;
import org.junit.Test;

public class AdminTests {
    @Test
    public void a_getHashedPassword(){
        Admin admin = new Admin();
        System.out.println(admin.getHashedPassword("beethoven"));
    }
}
