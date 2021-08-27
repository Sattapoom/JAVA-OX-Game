import java.util.Arrays;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class OX extends JFrame implements MouseListener{

    String[][] board_array;
    String player = "X";
    int turn_count = 0;
    int board_size = 5;

//=========================================================================
    void setBoardboard_size(int s){
        board_size = s;
        board_array = new String[s][s];
        for(int i=0;i<board_size;i++){
            for(int j=0;j<board_size;j++){
            board_array[i][j] = " ";
            }
        }
    }

    Boolean add_position(Integer row,Integer col) {

        if(board_array[row][col] == "X" || board_array[row][col] == "O"){
            return false;
        } else {
            board_array[row][col] = this.player;
            return true;
        }
    }

    Boolean check_winner(){
        String[] checker = new String[board_size];
        for (int i=0;i<board_size;i++){
            checker[i] = this.player;
        }
        String[] temp_hor = new String[board_size];
        String[] temp_ver = new String[board_size];
        for (int i=0;i<board_size;i++){
            if(Arrays.deepEquals(checker, board_array[i])){
                return true;
            }
            String[] temp = new String[board_size];
            for (int j=0;j<board_size;j++){
                temp[j] = board_array[j][i];
            }
            if(Arrays.deepEquals(checker, temp)){
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
        return player;
    }

    Integer getTurncount(){
        return turn_count;
    }
//=========================================================================
    void Convert_mousepos(int mouse_x,int mouse_y){
        int row,col;
        row = (int)mouse_y/(sc_size/board_size);
        col = (int)mouse_x/(sc_size/board_size);
        System.out.println(row +" "+col);
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
            if (int_size>1){
                board_size = int_size;
            }
        }
        else if(game_mode.equals("Play")){
            Convert_mousepos(e.getX(),e.getY());
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
            System.out.println(board_size);
        }
    }
    //=========================================================================
    JPanel screen = new JPanel();
    String game_mode = "Play";
    final int sc_size = 800;

    void Draw_game(){
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
                g2d.setColor(Color.blue);
                g2d.setFont(new Font("Calibri", Font.PLAIN, 150));
            }
        }
    }
    JTextField tf1;
    void Draw_menu(){
        
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
    void Draw_win(){
        Graphics2D g2d = (Graphics2D) screen.getGraphics();
        g2d.setColor(Color.ORANGE);
        g2d.setFont(new Font("Calibri", Font.PLAIN, 150));
        g2d.drawString("You Win",150,325);
        g2d.setColor(Color.gray);
        g2d.setFont(new Font("Calibri", Font.PLAIN, 20));
        g2d.drawString("Click for back to main menu.",300,500);
    }
    //------------------################
    public void paint(Graphics g){
        switch (game_mode) {
            // super.paint(g);
            case "Menu":
                Draw_menu();
                break;
            case "Play":
                Draw_game();
                break;
            case "Win":  
                Draw_win();
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