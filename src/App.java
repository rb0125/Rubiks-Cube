import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import java.util.Timer;
import java.util.TimerTask;

public class App extends JPanel implements KeyListener
{
    Cube cube;
    CubeSolver solver;
    Timer timer;
    TimerTask moveTask;
    char prevKey;
    long prevKeyTypedTime;
    int frameX, frameY;

    public App()
    {
        cube = new Cube();
        solver = new CubeSolver(cube);
        timer = new Timer();
        prevKey = 0;
        prevKeyTypedTime = 0;

        JFrame frame = new JFrame();
        frame.add(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addKeyListener(this);
        
        // GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        // if (device.isFullScreenSupported())
        //     device.setFullScreenWindow(frame);
        // // else
        //     frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        // frameX = frame.getWidth();
        // frameY = frame.getHeight();
        frameX = 1000;
        frameY = 600;
        frame.setSize(frameX, frameY);

        cube.move("z2");
        frame.setVisible(true);
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        ((Graphics2D) g).setStroke(new BasicStroke(3));
        g.setColor(Color.BLACK);
        int s = 300;
        int x = (int) ((frameX - s)/2.0), y = 250;
        // int r = (int) (s/Math.sqrt(2)/2);

        // Cabinet projection
        // g.drawRect(x, y, s, s);
        // g.drawPolygon(new int[]{x, x + r, x + s + r, x + s}, new int[]{y, y - r, y - r, y}, 4);
        // g.drawPolygon(new int[]{x + s, x + s + r, x + s + r, x + s}, new int[]{y, y - r, y + s - r, y + s}, 4);
        // g.drawLine(x, y + s, x + r, y + s - r);
        // g.drawLine(x + r, y - r, x + r, y + s - r);
        // g.drawLine(x + s + r, y + s - r, x + r, y + s - r);

        // One-point perspective projection
        // g.drawRect(x, y, s, s);
        // g.drawPolygon(new int[]{x, x + 25, x + s - 25, x + s}, new int[]{y, y - 25, y - 25, y}, 4);

        // Two-point perspective projection
        int tw = 20, th = (int) (s/2.0), bw = 10, bh = (int) (3*s/4.0);
        g.drawPolygon(new int[]{x, x + tw, x + s - tw, x + s}, new int[]{y, y - th, y - th, y}, 4);
        g.drawPolygon(new int[]{x, x + s, x + s - bw, x + bw}, new int[]{y, y, y + bh, y + bh}, 4);

        for (Piece piece: cube.getPieces())
        {
            if (piece.getZ() == 1)
            {
                int pX = piece.getX(), pY = piece.getY();
                int px1 = pbx(x, y, pX, pY, s, bw);
                int px2 = pbx(x, y, pX + 1, pY, s, bw);
                int px3 = pbx(x, y, pX + 1, pY - 1, s, bw);
                int px4 = pbx(x, y, pX, pY - 1, s, bw);
                int py1 = pby(x, y, pX, pY, s, bh);
                int py2 = pby(x, y, pX, pY - 1, s, bh);

                Color color = null;
                for (String[] c: piece.getColors())
                    if (c[1].equals("+z"))
                        color = getColor(c[0]);
                
                g.setColor(color);
                g.fillPolygon(new int[]{px1, px2, px3, px4}, new int[]{py1, py1, py2, py2}, 4);
                g.setColor(Color.BLACK);
                g.drawPolygon(new int[]{px1, px2, px3, px4}, new int[]{py1, py1, py2, py2}, 4);
            }
            if (piece.getY() == 1)
            {
                int pX = piece.getX(), pZ = piece.getZ();
                int px1 = ptx(x, y, pX, pZ, s, tw);
                int px2 = ptx(x, y, pX, pZ - 1, s, tw);
                int px3 = ptx(x, y, pX + 1, pZ - 1, s, tw);
                int px4 = ptx(x, y, pX + 1, pZ, s, tw);
                int py1 = pty(x, y, pX, pZ, s, th);
                int py2 = pty(x, y, pX, pZ - 1, s, th);

                Color color = Color.BLACK;
                for (String[] c: piece.getColors())
                    if (c[1].equals("+y"))
                        color = getColor(c[0]);

                g.setColor(color);
                g.fillPolygon(new int[]{px1, px2, px3, px4}, new int[]{py1, py2, py2, py1}, 4);
                g.setColor(Color.BLACK);
                g.drawPolygon(new int[]{px1, px2, px3, px4}, new int[]{py1, py2, py2, py1}, 4);
            }
        }
    }

    private int pbx(int x, int y, int pX, int pY, int s, int bw)
    {
        return (int) (x + (1 + pX)*(s - 2*(1 - pY)*bw/3.0)/3.0 + (1 - pY)*bw/3.0);
    }

    private int pby(int x, int y, int pX, int pY, int s, int bh)
    {
        return (int) (y + (1 - pY)*bh/3.0);
    }

    private int ptx(int x, int y, int pX, int pZ, int s, int tw)
    {
        return (int) (x + (1 + pX)*(s - 2*(1 - pZ)*tw/3.0)/3.0 + (1 - pZ)*tw/3.0);
    }

    private int pty(int x, int y, int pX, int pZ, int s, int th)
    {
        return (int) (y - (1 - pZ)*th/3.0);
    }

    private Color getColor(String color)
    {
        switch (color)
        {
            case "W": return Color.WHITE;
            case "G": return Color.GREEN;
            case "R": return Color.RED;
            case "Y": return Color.YELLOW;
            case "B": return Color.BLUE;
            case "O": return Color.ORANGE;
            default: return null;
        }
    }

    /* Normal move: Do move 0.25s after key typed
     * Double move: If same move typed within 0.25s, append "2" and do double move at originally scheduled time
     * Prime move: If ' typed within 0.25s, append ' and do prime move at originally scheduled time
     */
    public void keyTyped(KeyEvent e)
    {
        char c = e.getKeyChar();
        if (c == ' ')
        {
            String oll = solver.getOLL();
            System.out.println(oll);
            if (!oll.equals("Invalid OLL"))
                cube.move(oll);
            repaint();
            return;
        }
        if ("XYZ".contains(c + ""))
            c += 32;
        if (!"RUFLDBMESxyzrufldb'".contains(c + ""))
            return;
        long elapsedTime = System.currentTimeMillis() - prevKeyTypedTime;
        if (elapsedTime < 250 && prevKey != '\'' && prevKey != '2' && (c == prevKey || c == '\''))
        {
            moveTask.cancel();
            char c2 = (c == prevKey ? '2' : '\'');
            String newMove = "" + prevKey + c2;
            moveTask = createMoveTimerTask(newMove);
            prevKey = c2;
            timer.schedule(moveTask, 250 - elapsedTime);
        }
        else if (c != '\'')
        {
            moveTask = createMoveTimerTask(c + "");
            prevKey = c;
            prevKeyTypedTime = System.currentTimeMillis();
            timer.schedule(moveTask, 250);
        }
    }

    public TimerTask createMoveTimerTask(String move)
    {
        return new TimerTask()
        {
            public void run()
            {
                cube.move(move);
                System.out.println(move);
                repaint();
            }
        };
    }

    public void keyPressed(KeyEvent e)
    {
        
    }

    public void keyReleased(KeyEvent e)
    {
        
    }

    public static void main(String[] args) throws Exception
    {
        new App();
    }
}
