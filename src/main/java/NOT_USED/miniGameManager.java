package NOT_USED;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class miniGameManager {

    public static miniGameManager instance;
    private String[] board;
    private int turn;
    private Map<Integer, String> playerLetters; // Map to store client IDs and their letters
    private boolean startGame=false;
    private int numOfPlayers=0;

    public int BoradSize=9;


    public miniGameManager()
    {
        board = new String[BoradSize];
        for(int i=0;i<BoradSize;i++){
            board[i]="_";
        }
        turn=0;
        playerLetters = new HashMap<>();

    }
    public static miniGameManager get()
    {
        if(instance==null)
        {
            instance = new miniGameManager();
        }
        return instance;
    }
    public void addLetter(int index,String letter)
    {
        board[index]=letter;


    }
    public void changeTurn()
    {
        turn=(turn+1)%numOfPlayers;
    }
    public int getTurn()
    {
        return turn;
    }
    public String getBoard()
    {
        String boardString="";
        for(int i=0;i<BoradSize;i++){
            boardString+=board[i];
        }
        return boardString;
    }
    public boolean isGameOver()
    {
        for(int i=0; i<board.length;i++)
        {
            if(board[i].equals(""))
            {
                return false;
            }
        }
        return true;
    }
    public void setPlayerLetters(int clientID, String letters) {
        playerLetters.put(clientID, letters);
    }

    public String getPlayerLetters(int clientID) {
        return playerLetters.get(clientID);
    }

    public void printBoard(){
        System.out.println("Board:");
        for(int i=0;i<BoradSize;i++){
            System.out.print(board[i]);
            if(i%3==2){
                System.out.println();
            }
        }
    }

    public void startGame() {
        if(startGame)
            return;
        startGame=true;
        numOfPlayers=playerLetters.size();
        for(int i=0; i<numOfPlayers;i++)
            this.setPlayerLetters(i, this.randomLetters());
    }

    public String randomLetters()
    {
        String letters="";
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < 7; i++) {
            char randomLetter = (char) (random.nextInt(26) + 'a');
            stringBuilder.append(randomLetter);
            if(i!=6)
                stringBuilder.append(',');
        }
        String randomLetters = stringBuilder.toString();
        return randomLetters;
    }

    public boolean isGameStarted() {
        return startGame;
    }
}
