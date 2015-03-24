package Shared;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Server.Server;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Mnesymne
 */
public class TestUser {
    User ValidUser;
   @Test
   public void testCreation()
   {
       /**
     * Constructor
     *
     * @param username as String minimal of 6 letters
     * @param password as String minimal of 6 symbols
     * @param email as String , must be a valid email meaning it contains a @
     * and f.ex .com
     * @param selectedServer as Server, this is the server the user is currently
     * in.
     */
       Server s = new Server();
       try
       {
       ValidUser = new User("Tester","tester","test@test.nl",s);
       }
       catch(IllegalArgumentException ex)
       {
           
       }
        try
       {
       ValidUser = new User("Test","tester","test@test.nl",s);
       fail();
       }
       catch(IllegalArgumentException ex)
       {
           
       }
         try
       {
       ValidUser = new User("Tester","test","test@test.nl",s);
       fail();
       }
       catch(IllegalArgumentException ex)
       {
           
       }
          try
       {
       ValidUser = new User("Tester","tester","testtest.nl",s);
       fail();
       }
       catch(IllegalArgumentException ex)
       {
           
       }
       assertEquals("Tester",ValidUser.getUsername());
       assertEquals("tester",ValidUser.getPassword());
       assertEquals("test@test.nl",ValidUser.getEmail());
       
       try
       {
           ValidUser.setUsername("MyTest");
           assertEquals("MyTest",ValidUser.getUsername());
       }
       catch(IllegalArgumentException ex)
       {
           
       }
       try
       {
           ValidUser.setPassword("myTest");
           assertEquals("myTest",ValidUser.getPassword());
       }
       catch(IllegalArgumentException ex)
       {
           
       }
       
       try
       {
           ValidUser.setEmail("MyTest@Test.test");
           assertEquals("MyTest@Test.test",ValidUser.getEmail());
       }
       catch(IllegalArgumentException ex)
       {
           
       }
       
          try
       {
           ValidUser.setUsername("Test");
           fail();
       }
       catch(IllegalArgumentException ex)
       {
           
       }
       try
       {
           ValidUser.setPassword("Test");
           fail();
       }
       catch(IllegalArgumentException ex)
       {
           
       }
       
       try
       {
           ValidUser.setEmail("MyTestest.test");
           fail();
       }
       catch(IllegalArgumentException ex)
       {
           
       }
               

   }
}
