import java.util.Scanner;
import java.util.Arrays;

public class OX{

    String[][] board_array;
    String player = "X";
    int turn_count = 0;
    int size;

//=========================================================================
    void setSize(int s){
        size = s;
        board_array = new String[s][s];
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
            board_array[i][j] = " ";
            }
        }
    }
    void display_board() {
        for (int i=0;i<size;++i){
            for (int j=0;j<size;++j){
                System.out.print("("+board_array[i][j]+")");
            }
            System.out.print("\n");
        }
    }

    Boolean add_position(Integer pos) {

        int row = (pos-1)/size;
        int col = (pos-1)%size;
        if(board_array[row][col] == "X" || board_array[row][col] == "O"){
            System.out.print("Fail, This position has been chosen. plese try again : ");
            return false;
        } else {
            board_array[row][col] = this.player;
            return true;
        }
    }

    Boolean check_winner(){
        String[] checker = new String[size];
        for (int i=0;i<size;i++){
            checker[i] = this.player;
        }
        String[] temp_hor = new String[size];
        String[] temp_ver = new String[size];
        for (int i=0;i<size;i++){
            if(Arrays.deepEquals(checker, board_array[i])){
                return true;
            }
            String[] temp = new String[size];
            for (int j=0;j<size;j++){
                temp[j] = board_array[j][i];
            }
            if(Arrays.deepEquals(checker, temp)){
                return true;
            }
            temp_hor[i] = board_array[i][i];
            temp_ver[i] = board_array[i][(size-1)-i];
        }
        if(Arrays.deepEquals(checker, temp_hor) || Arrays.deepEquals(checker, temp_ver)){
            return true;
        }
        return false;
    }

    void change_player(){
        if (this.player.equals("X")){
            this.player = "O";
        }
        else{
            this.player = "X";
        }
        ++this.turn_count;
    }

    String getPlayingmark(){
        return player;
    }

    Integer getTurncount(){
        return turn_count;
    }
//=========================================================================

//=========================================================================
    public static void main(String[] args) {
        OX game = new OX();
        Scanner sc = new Scanner(System.in);
        System.out.print("Size : ");
        int s = sc.nextInt();
        game.setSize(s);
        game.display_board();
        while(game.getTurncount() < s*s ){
            System.out.print("Pos "+game.getPlayingmark()+": ");
            while(!game.add_position(sc.nextInt()));
            game.display_board();
            if(game.check_winner()){
                System.out.println("Win");
                break;
            }
            game.change_player();
        }
        sc.close();
        }
    }