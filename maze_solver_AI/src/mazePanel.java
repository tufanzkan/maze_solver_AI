import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.Stack;

import javax.swing.JPanel;

class mazePanel extends JPanel{
    private static final long serialVersionUID = 1L;
    private Rectangle2D[][] cells;
    private static final int dimension = 600;
    private int[][] array;
    private int cellsMaze;
    private float cell_size;
    private Position start;
    private Position end;
    private Position current;
    private int delay = 100;
    private Robot robot;
    App app=new App();
    String rslt;


    public mazePanel(int cellsMaze) {
        refresh(cellsMaze);
    }

    public int getCellsMaze() {
        return this.cellsMaze;
    }

    public void refresh(int cellsMaze) {
        this.cellsMaze = cellsMaze;
        setSize(dimension, dimension);
        cell_size = (float) (1.0 * dimension / cellsMaze);

        setFocusable(true);

        // Maze Object
        robot = new Robot(cellsMaze - 2);
        array = robot.array;

        // Set Position
        start = current = new Position(1, 0);
        end = new Position(cellsMaze - 2, cellsMaze - 1);

        // Set maze on panel
        cells = new Rectangle2D[cellsMaze][cellsMaze];
        for (int i = 0; i < cellsMaze; i++) {
            for (int j = 0; j < cellsMaze; j++) {
                cells[i][j] = new Rectangle2D.Double(j * cell_size, i * cell_size, cell_size, cell_size);
            }
        }
    }

    private void drawMaze(Graphics2D g2d) {
        for (int i = 0; i < cellsMaze; i++) {
            for (int j = 0; j < cellsMaze; j++) {
                if (array[i][j] == 0) {
                    g2d.setColor(Color.WHITE);
                } else {
                    g2d.setColor(Color.BLACK);
                }
                g2d.fill(cells[i][j]);
            }
        }

        // Fill first cell
        int x = start.getX();
        int y = start.getY();

        g2d.setColor(Color.GREEN);
        g2d.fill(cells[x][y]);
    }

    public void autoMove(Graphics2D g2d) {

        app.starttime=System.nanoTime();

        Stack<Position> way = robot.getDirectWay(start, end);
        g2d.setColor(Color.GREEN);
        while (!way.empty()) {
            Position next = way.pop();
            int x = next.getX();
            int y = next.getY();
            g2d.fill(cells[x][y]);
            app.count2++;
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        app.endtime=System.nanoTime();
        app.result=app.endtime-app.starttime;
    }

    public void algorithm(Graphics2D g2d) {

        app.starttime=System.nanoTime();

        Stack<Position> way = robot.getWay(start, end);
        g2d.setColor(Color.GREEN);
        while (!way.empty()) {
            Position next = way.pop();
            int x = next.getX();
            int y = next.getY();
            g2d.fill(cells[x][y]);
            app.count++;
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        app.endtime=System.nanoTime();
        app.result=app.endtime-app.starttime;

    }
    public void cevir(){
        rslt= String.valueOf(app.result);
    }

    @Override
    protected void paintComponent(Graphics g) {
        drawMaze((Graphics2D) g);
    }
}