import java.util.Arrays;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class OX extends JFrame implements MouseListener{

    String[][] board_array;
    String player = "X";
    int turn_count = 0;
    int board_size;

//=========================================================================
    void setboard_size(int s){
        board_size = s;
        board_array = new String[s][s];
        for(int i=0;i<board_size;i++){
            for(int j=0;j<board_size;j++){
            board_array[i][j] = " ";
            }
        }
    }

    void add_position(Integer row,Integer col) {

        if(board_array[row][col] == "X" || board_array[row][col] == "O"){
        } else {
            board_array[row][col] = this.player;
            change_player();
        }
    }

    Boolean check_winner(){
        String[] checker = new String[board_size];
        for (int i=0;i<board_size;i++){
            if(this.player.equals("X")){
                checker[i] = "O";
            }
            else{
                checker[i] = "X";
            }
        }
        String[] temp_hor = new String[board_size];
        String[] temp_ver = new String[board_size];
        for (int i=0;i<board_size;i++){
            if(Arrays.deepEquals(checker, board_array[i])){ // check row
                return true;
            }
            String[] temp = new String[board_size];
            for (int j=0;j<board_size;j++){
                temp[j] = board_array[j][i];
            }
            if(Arrays.deepEquals(checker, temp)){ // check collumn
                return true;
            }
            temp_hor[i] = board_array[i][i];
            temp_ver[i] = board_array[i][(board_size-1)-i];
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
        if (player.equals("X")){
            return "O";
        }
        else{
            return "X";
        }
    }

    Integer getTurncount(){
        return turn_count;
    }
//=========================================================================
    int[] convert_mousepos(int mouse_x,int mouse_y){
        int[] row_col = {(int)mouse_y/(sc_size/board_size),(int)mouse_x/(sc_size/board_size)};
        return row_col;
    }
    //-----------UESLESS--################
    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {
        if (game_mode.equals("Menu") && e.getX() > 250 && e.getX() < 550 && e.getY() >450 && e.getY()<550){
            Graphics2D g2d = (Graphics2D) screen.getGraphics();
            g2d.setColor(Color.decode("#33D81A"));
            g2d.fillRect(250,450,300,100);
            g2d.setColor(Color.black);
            g2d.drawRect(250,450,300,100);
            g2d.setFont(new Font("Calibri", Font.PLAIN, 50));
            g2d.drawString("Start",340,515);
            g2d.setFont(new Font("Calibri", Font.PLAIN, 20));
            g2d.drawString("Insert board size.",320,290);

            String str_size = tf1.getText();
            int int_size = Integer.parseInt(str_size);
            if (int_size>2){
                board_size = int_size;
                setboard_size(board_size);
            }
            else{
                tf1.setText("");
                g2d.setColor(Color.RED);
                g2d.drawString("Board size much more than 2",250,430);
            }
        }
    }
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    //------MOUSE------MOUSE------MOUSE------MOUSE------MOUSE------MOUSE------
    @Override
    public void mouseReleased(MouseEvent e) {
        if (game_mode.equals("Menu") && e.getX() > 250 && e.getX() < 550 && e.getY() >450 && e.getY()<550){
            repaint();
            if (board_size>1){
                game_mode = "Background";
                repaint();
                game_mode = "Play";
                screen.removeAll();
            }
        }
        else if(game_mode.equals("Play")){
            int[] row_col = convert_mousepos(e.getX(),e.getY());
            add_position(row_col[0],row_col[1]);
            if(check_winner()){
                game_mode = "Win";
            }
            repaint();
        }
        else if(game_mode.equals("Win")){
            
            game_mode = "Play";
            turn_count = 0;
            setboard_size(board_size);
            screen.removeAll();
            repaint();
            tf1 = new JTextField();
            tf1.setBounds(250,300,300,100);
            tf1.setFont(new Font("Calibri", Font.PLAIN, 50));
            repaint();
        }
    }
    //=========================================================================
    JPanel screen = new JPanel();
    String game_mode = "Menu";
    final int sc_size = 800;

    void draw_Game(){
        int table_size = sc_size/board_size;
        screen.removeAll();
        Graphics2D g2d = (Graphics2D) screen.getGraphics();
        g2d.setStroke(new BasicStroke(2));
        for (int i=0;i<board_size;i++){
            g2d.setColor(Color.black);
            g2d.drawLine(0, i*table_size, sc_size, i*table_size);
            for (int j=0;j<board_size;j++){
                g2d.setColor(Color.black);
                g2d.drawLine(j*table_size, 0, j*table_size, sc_size);
                g2d.setFont(new Font("Calibri", Font.PLAIN, table_size));
                if (board_array[i][j].equals("O")){
                    g2d.setColor(Color.blue);
                }
                else if(board_array[i][j].equals("X")){
                    g2d.setColor(Color.red);                    
                }
                g2d.drawString(board_array[i][j],(table_size*j)+20,(table_size*(i+1))-20);
            }
        }
        if(turn_count == board_size*board_size && !check_winner()){
            game_mode = "Win";
            repaint();
        }
    }
    JTextField tf1;
    void draw_Menu(){
        
        Graphics2D g2d = (Graphics2D) screen.getGraphics();
        g2d.setStroke(new BasicStroke(3));

        g2d.setColor(Color.decode("#2AEA0E"));
        g2d.fillRect(250,450,300,100);
        g2d.setColor(Color.black);
        g2d.drawRect(250,450,300,100);
        g2d.setFont(new Font("Calibri", Font.PLAIN, 50));
        g2d.drawString("Start",340,515);
        g2d.setFont(new Font("Calibri", Font.PLAIN, 20));
        g2d.drawString("Insert board size.",320,290);
    }
    void draw_Win(){
        screen.removeAll();
        Graphics2D g2d = (Graphics2D) screen.getGraphics();
        g2d.setColor(Color.gray);
        g2d.setFont(new Font("Calibri", Font.PLAIN, 20));
        g2d.drawString("Click for play again.",300,500);
        g2d.setColor(Color.ORANGE);
        g2d.setFont(new Font("Calibri", Font.PLAIN, 150));
        if(turn_count == board_size*board_size && !check_winner()){
            g2d.drawString("DRAW",150,325);
        }
        else {
            g2d.drawString(getPlayingmark()+" Win",150,325);
        }
    }
    //------------------################
    public void paint(Graphics g){
        switch (game_mode) {
            // super.paint(g);
            case "Menu":
                draw_Menu();
                break;
            case "Play":
                super.paint(g);
                draw_Game();
                break;
            case "Win":
                super.paint(g);
                draw_Win();
                break;
            case "Background":
                super.paint(g);
                break;
            default:
                break;
        }
    }

//------------------################
    OX(){

        this.setTitle("Sorting Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        this.setResizable(false);

        screen.setPreferredSize(new Dimension(sc_size, sc_size));
        screen.addMouseListener(this);
        this.add(screen);
        this.pack();
        this.setVisible(true);

        tf1 = new JTextField();
        tf1.setBounds(250,300,300,100);
        tf1.setFont(new Font("Calibri", Font.PLAIN, 50));
        screen.add(tf1);
    }
//=========================================================================
    public static void main(String[] args) {
        new OX();
    }
}