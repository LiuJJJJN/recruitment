package com.ibm.rms;

import com.ibm.rms.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@SpringBootTest
public class SpringSecurityTest {

    @Test
    public void testSecurityContextHolder() {
        // User user = (User) SecurityContextHolder.getContext().getAuthentication();
        // System.out.println(user.getId());
    }

}
