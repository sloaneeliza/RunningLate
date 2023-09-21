import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.Random;

/**
 * GameTest - a class to test the zuul game engine.
 *
 * @author  William H. Hooper
 * @version 2018-11-19
 */
public class GameTest
{
    private Game game1;
    private Console console1; 

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
        System.out.println("\f");
        game1 = new Game();
        console1 = new Console(game1);
        String message = game1.readMessages();
        System.out.print(message + "> ");
    }

    @Test
    public void start()
    {
        assertEquals(false, game1.finished());
    }

    @Test
    public void map()
    {
        assertEquals(true, console1.testCommand("go east", "outside"));
        // assertEquals(true, console1.testCommand("go north", "caf"));
        // assertEquals(true, console1.testCommand("go west", "main_lawn"));
        // assertEquals(true, console1.testCommand("go west", "bell_tower"));
        // assertEquals(true, console1.testCommand("go north", "massey"));
        // assertEquals(true, console1.testCommand("go east", "office"));
        // assertEquals(true, console1.testCommand("go west", "lab"));
        // assertEquals(true, console1.testCommand("go north", "outside"));
    }

    @Test
    public void back()
    {
        //assertEquals(true, console1.testCommand("go east", "outside"));
        // assertEquals(true, console1.testCommand("back", "outside"));
        //assertEquals(true, console1.testCommand("back", "caf"));
        //assertEquals(true, console1.testCommand("back", "main_lawn"));
        assertEquals(true, console1.testCommand("back", "You cannot go back since you are at the start!"));
        // assertEquals(true, console1.testCommand("go east", "office"));
        // assertEquals(true, console1.testCommand("go west", "lab"));
        // assertEquals(true, console1.testCommand("go north", "outside"));
    }
    
    @Test
    public void noDoor()
    {
        assertEquals(true, console1.testCommand("go north", "no door!"));
    }

    @Test
    public void quit()
    {
        console1.testCommand("quit");
        assertEquals(true, game1.finished());
        assertEquals(false, console1.testCommand("go North", "doorway"));
        assertEquals(true, console1.testCommand("anything", "game is over"));
    }

    @Test
    public void help()
    {
        String string1 = console1.getResponse("help");
        assertNotNull(string1);
        assertEquals(true, string1.contains("go"));
        assertEquals(true, string1.contains("quit"));
        assertEquals(true, string1.contains("help"));
    }

     @Test
    public void Items()
    {
        //assertEquals(true, game1.contains(Item));
    }  
}




