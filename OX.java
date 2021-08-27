import java.util.Scanner;

public class OX{

    String[][] board_array;
    public String[] player = {"X","O"};
    public int turn_count = 1;
    int size;

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
        String mark = this.player[0];
        int row = (pos-1)/size;
        int col = (pos-1)%size;
        if(board_array[row][col] == "X" || board_array[row][col] == "O"){
            System.out.print("Fail, This position has been chosen. plese try again : ");
            return false;
        } else {
            board_array[row][col] = mark;
            display_board();
            return true;
        }
    }

    Boolean check_winner(){
        String mark = this.player[0];
        for(int i=0;i<size;i++){
            boolean temp_col = true;
            for(int j=0;j<size;j++){
                boolean temp_row = true;
                for(int k=0;k<size;k++){
                    if (!(board_array[j][k] == mark)){
                        temp_row = false;
                    }
                }
                if (temp_row){
                    return true;
                }
                if (!(board_array[i][j] == mark)){
                    temp_row = false;
                }
            }
            if (temp_col){
                return true;
            }
        }

        String[] other_array1 = {board_array[0][0],board_array[1][1],board_array[2][2]};
        String[] other_array2 = {board_array[0][2],board_array[1][1],board_array[2][0]};
        if((mark == other_array1[0] && mark == other_array1[1] && mark == other_array1[2]) || (mark == other_array2[0] && mark == other_array2[1] && mark == other_array2[2])){
            return true;
        } else {return false;}
    }

    void change_player(){
        String switch1, switch2;
        switch1 = this.player[0];
        switch2 = this.player[1];
        this.player[0] = switch2;
        this.player[1] = switch1;
        ++this.turn_count;
        if(this.turn_count== 10){
            System.out.println("Draw! No one wins.");}
    }
    String getPlayingmark(){
        return player[0];
    }
    Integer getTurncount(){
        return turn_count;
    }

    public static void main(String[] args) {
        System.out.println("Welcome To OX Game.");
        OX ox_board = new OX();
        input_processor input = new input_processor();
        ox_board.display_board();

        ox_board.setSize(input.getSize());

        while (ox_board.getTurncount() <= 9){
            System.out.print("Choose position to placed your mark ("+ox_board.getPlayingmark()+"): ");
            do{
                input.setPosition();
                input.input_checker();
            }while(!ox_board.add_position(input.getPosition()));
            System.out.println("====================");
            if (ox_board.getTurncount() >= 5){
                if (ox_board.check_winner()){
                    System.out.println("Gameover! The winner is "+ox_board.getPlayingmark());
                    break;
                }
            }
            ox_board.change_player();
        }
    }
}

class input_processor{
    Scanner sc = new Scanner(System.in);
    int position = -1;
    Boolean numpad_checker;
    int req_size;

    input_processor(){
        System.out.print("Choose Board format between Numpad or Phonepad (np/pp): ");
        String req_ans = sc.nextLine();
        System.out.print("Choose size:");
        req_size = sc.nextInt();
        while(!("np".equals(req_ans) || "pp".equals(req_ans))){
            System.out.print("Choose Board format between Numpad or Phonepad (np/pp): ");
            req_ans = sc.nextLine();
        }
        if ("np".equals(req_ans)){
            numpad_checker = true;
        } else{
            numpad_checker = false;
        }
    }

    Integer getSize(){
        return req_size;
    }

    Integer getPosition(){
        return position;
    }

    void setPosition(){
        boolean valid = false;
        do{
            if(sc.hasNextInt()){ // This checks to see if the next input is a valid **int**
                position = sc.nextInt();
                valid = true;
            }
            else{
                System.out.print("Plese insert integer 1-9 : ");
                sc.next();
            }
        }while(valid == false);
    }

    void input_checker(){
        while(position < 1 || position > 9){
            System.out.print("Position need to be 1-9, Rechoose :");
            setPosition();
        }
        if(numpad_checker){
            int[][] numpadArray = {{7,8,9},{4,5,6},{1,2,3}};
            int row = (position-1)/3;
            int col = (position-1)%3;
            position = numpadArray[row][col];
        }
    }
}
