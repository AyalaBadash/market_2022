package test.UnitTests;

import main.businessLayer.LoginCard;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashMap;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class SecurityTests extends mainTest{
    @Mock
    LoginCard card;
    String userName;

//    @Test
//    @DisplayName("Security Tests - No such user")
//    public void testNoSuchUser(){
//        Mockito.when(userName).thenReturn("moshe");
//        Assertions.assertEquals("moshe",userName);
//        System.out.println("1");
//        Map<String,LoginCard> userInfo = new HashMap<>();
//        userInfo.put("raz",card);
//        Assertions.assertFalse(userInfo.containsKey(userName));
//    }

}
