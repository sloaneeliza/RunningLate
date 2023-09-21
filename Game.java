import java.util.Stack;
import java.util.HashMap;
import java.util.Map;
/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @author  Sloane Wright
 * @version 2023.04.21
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Room oldRoom;
    private int currentTime;
    private Stack <Room> lastRoom;
    private Map<String, Item> items;
    private Player player;
    private Room DrHoopersClass;
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        items = new HashMap<>();
        player = new Player("player");
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room dorm, outside, tall_hall, caf, main_lawn, bell_tower, massey,
        circle, baskin, JAAC_enter, JAAC_elev, JAAC_lobby, starbucks;

        // create the rooms
        outside = new Room("outside your dorm hall");
        dorm = new Room("in your cute, little dorm room");
        caf = new Room("in the caf...fine dining.");
        tall_hall = new Room("outside of Tall Hall. RIP Sophomore Housing");
        main_lawn = new Room("the main lawn. You might get hit in the head by a spikeball.");        
        bell_tower = new Room("at the Bell Tower!");
        massey = new Room("outside of Massey! You can get a sandwich from here.");
        circle = new Room("next to the circle.");
        baskin = new Room("next to the College of Law.");
        JAAC_enter = new Room("entering the JAAC.");
        JAAC_elev = new Room("by the JAAC elevators. These things take forever.");
        JAAC_lobby = new Room("in the lobby of the JAAC.");
        starbucks = new Room("at the Starbucks in the JAAC. You can take a drink from here!");
        DrHoopersClass = new Room("in the classroom! You made it on time :D");
        
        // assign images
        outside.setImage("TK.jpg");
        dorm.setImage("dorm.jpg");
        tall_hall.setImage("tall-hall.jpg");
        caf.setImage("the-caf.jpg");
        main_lawn.setImage("main-lawn.jpg");
        bell_tower.setImage("bell-tower.jpg");
        massey.setImage("massey.jpg");
        circle.setImage("circle.jpg");
        baskin.setImage("baskin.jpg");
        JAAC_enter.setImage("JAAC.jpg");
        JAAC_elev.setImage("elevators.png");
        JAAC_lobby.setImage("JAAC-lobby.jpg");
        starbucks.setImage("starbucks.jpg");
        DrHoopersClass.setImage("class.png");

        // initialise room exits
        //dorm exit
        dorm.setExit("east", outside);  //8.8
        //outside exits
        outside.setExit("north", caf);
        outside.setExit("east", tall_hall);
        outside.setExit("west", dorm);
        //tall hall exits
        tall_hall.setExit("west", outside);
        //caf exits
        caf.setExit("north", baskin);
        caf.setExit("south", outside);
        caf.setExit("west", main_lawn);
        //main lawn exits
        main_lawn.setExit("north", circle);
        main_lawn.setExit("east", caf);
        main_lawn.setExit("west", bell_tower);
        //bell tower exits
        bell_tower.setExit("east", main_lawn);
        bell_tower.setExit("north", massey);
        //massey exits
        massey.setExit("south", bell_tower);
        massey.setExit("east", circle);
        //circle exits
        circle.setExit("north", JAAC_enter);
        circle.setExit("east", baskin);
        circle.setExit("south", main_lawn);
        circle.setExit("west", massey);
        //baskin exits
        baskin.setExit("west", circle);
        baskin.setExit("south", caf);
        //JAAC entrance exits
        JAAC_enter.setExit("north", JAAC_lobby);
        JAAC_enter.setExit("east", starbucks);
        JAAC_enter.setExit("south", circle);
        JAAC_enter.setExit("west", JAAC_elev);
        //JAAC elevator exits
        JAAC_elev.setExit("north", DrHoopersClass);
        JAAC_elev.setExit("east", JAAC_enter);
        //JAAC lobby exits
        JAAC_lobby.setExit("west", DrHoopersClass);
        JAAC_lobby.setExit("south", JAAC_enter);
        //Starbucks exits
        starbucks.setExit("west", JAAC_enter);
        //Dr Hooper's Class exit
        DrHoopersClass.setExit("east", JAAC_lobby);
        DrHoopersClass.setExit("south", JAAC_elev);
        
        //items
        Item laptop = new Item("a laptop");
        laptop.setWeight(3);
        dorm.addItem("laptop", laptop);

        Item textbook = new Item("the BlueJ textbook");
        textbook.setWeight(2);
        dorm.addItem("textbook", textbook);

        Item drink = new Item("a Starbucks drink");
        drink.setWeight(1);
        starbucks.addItem("drink", drink);

        Item sandwich = new Item("a Corner Court sandwich");
        sandwich.setWeight(1);
        massey.addItem("sandwich",sandwich);

        currentRoom = dorm; // start game in the dorm 
        lastRoom = new Stack<>();   //8.26
        currentTime = 12;
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        println();
        println("You woke up late and have to find your way to your");
        println("programming class before the attendance is taken off");
        println("the board!");
        println(" ");
        println("Note: Every time you enter a new room, you lose a minute");
        println("of your time! You will start with 12 minutes.");
        println(" ");
        println("Type 'help' if you need help.");
        println();
        println(currentRoom.getLongDescription());   //8.11
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private void processCommand(Command command) 
    {
        if(command.isUnknown()) {
            println("I don't know what you mean...");
            return;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            quit(command);
        }
        else if(commandWord.equals("look")){
            look();
        }
        else if(commandWord.equals("time")){
            time();
        }
        else if(commandWord.equals("back")){
            back();     //8.26
        }
        else if(commandWord.equals("take")){
            take(command);
        }
        else if(commandWord.equals("drop")){
            drop(command);
        }
        else if(commandWord.equals("inventory")){
            inventory();
        }
        else if(commandWord.equals("sleep")){
            sleep();
        }
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        println("You are lost. You are alone. You wander");
        println("around at the university.");
        println();
        println("Your command words are:");
        println(parser.getCommands());      //classwork & 8.16
    }

    //8.6
    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            println("Go where?");
            return;
        }

        String direction = command.getSecondWord();
        Room nextRoom = currentRoom.getExit(direction);
        Room room = new Room("");

        if (nextRoom == null) {
            println("There is no door!");
        }
        else {
            lastRoom.push(currentRoom);   //8.26
            currentRoom = nextRoom;
            println("");
            printLocationInfo();
        }
        if(nextRoom != null){
            currentTime -= 1;  //you lose a minute for every room you enter!
        }
        if(currentTime == 0){
            wantToQuit = true;
            println("");
            println("You ran out of time! Please close out of the game.");
        }
        if(currentRoom.equals(DrHoopersClass)){
            wantToQuit = true;
        }
    }
    //8.5
    public void printLocationInfo(){
        println("You are " + currentRoom.getDescription());
        //8.7
        print(currentRoom.getExitString());
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private void quit(Command command) 
    {
        if(command.hasSecondWord()) {
            println("Quit what?");
            return;
        }
        
        wantToQuit = true;  // signal that we want to quit
    }
    //8.14
    private void look(){
        println(currentRoom.getLongDescription()); 
    }
    //8.15
    //8.41 
    private void time(){
        println("You have " + currentTime + " minutes until class starts.");
    }
    //8.26
    /**
     * Go back to previous room.
     */
    private void back()
    {
        if(lastRoom.empty()){
            println("You cannot go back since you are at the start!");
        }
        else{
            currentRoom = lastRoom.pop();
            printLocationInfo();
        }
    }

    /** 
     * Take an item from a room and add it to player's inventory.
     */
    private void take(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to take...
            println("Take what?");
            return;
        }
        String keyWord = command.getSecondWord();
        Item it = currentRoom.getItem(keyWord);
        if(it == null){
            println("You can't take " + keyWord);
            return;
        }
        if(player.getInventoryWeight() >= 5){
            println("You are carrying too many objects and can't pick this item up!");
            return;
        }

        currentRoom.removeItem(keyWord);
        addItem(keyWord, it);
        player.takeItem(it);
        println("You've taken " + keyWord);
    }

    /** 
     * Drop an item into a room from the player's inventory
     */
    private void drop(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to drop...
            println("Drop what?");
            return;
        }
        String keyWord = command.getSecondWord();
        Item it = getItem(keyWord);
        if(it == null){
            println("You can't drop " + keyWord);
            return;
        }

        addItem(keyWord, it);
        currentRoom.removeItem(keyWord);
        player.removeItem(it);
        println("You have dropped "+ keyWord);

    }

    public void inventory(){
        println(player.playerInv());
    }
    
    public void sleep(){
        println("You have decided to go back to sleep and skip class.");
        println("The game is now over.");
        wantToQuit = true;
    }

    //items code
    public void addItem(String key, Item it)
    {
        items.put(key, it);
    }

    public Item getItem(String description)
    {
        return items.get(description);
    }

    public void removeItem(String description)
    {
        items.remove(description);
    }

    public String getItemString()
    {
        String message = "";
        for(Item it : items.values()){
            message += "\nYou see " + it.getDescription();
        }
        return message;
    }

    /****************************************************************
     * If you want to launch an Applet
     ****************************************************************/

    /**
     * @return an Image from the current room
     * @see Image
     */
    public String getImage()
    {
        return currentRoom.getImage();
    }

    /**
     * @return an audio clip from the current room
     * @see AudioClip
     */
    public String getAudio()
    {
        return currentRoom.getAudio();
    }

    /****************************************************************
     * Variables & Methods added 2018-04-16 by William H. Hooper
     ****************************************************************/

    private String messages;
    private boolean wantToQuit;

    /**
     * Initialize the new variables and begin the game.
     */
    private void start()
    {
        messages = "";
        wantToQuit = false;
        printWelcome();
    }

    /**
     * process commands or queries to the game
     * @param input user-supplied input
     */
    public void processInput(String input)
    {
        if(finished()) {
            println("This game is over.  Please go away.");
            return;
        }

        Command command = parser.getCommand(input);
        processCommand(command);
    }

    /**
     * clear and return the output messages
     * @return current contents of the messages.
     */
    public String readMessages()
    {
        if(messages == null) {
            start();
        }
        String oldMessages = messages;
        messages = "";
        return oldMessages;
    }

    /**
     * @return true when the game is over.
     */
    public boolean finished()
    {
        return wantToQuit;
    }

    /**
     * add a message to the output list.
     * @param message the string to be displayed
     */
    private void print(String message)
    {
        messages += message;
    }

    /**
     * add a message to the output list, 
     * followed by newline.
     * @param message the string to be displayed
     * @see readMessages
     */
    private void println(String message)
    {
        print(message + "\n");
    }

    /**
     * add a blank line to the output list.
     */
    private void println()
    {
        println("");
    }
}
