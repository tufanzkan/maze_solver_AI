import java.util.ArrayList;
import java.util.Stack;

public class Robot {
    int size;
    int[][] array;
    boolean[][] visitted_way;
    public Robot(){
        this.size=21;
        Obstacle obstacle=new Obstacle(21);
        array= obstacle.array;
        visitted_way= obstacle.visitted_way;;
    }
    public Robot(int size)
    {
        this.size=size;
        Obstacle obstacle=new Obstacle(size);
        array=obstacle.array;
        visitted_way= obstacle.visitted_way;
    }

    private boolean canMove(Position current, Position next) {
        ArrayList<Position> nexts = new ArrayList<Position>();
        if (canMoveUp(current))
            nexts.add(new Position(current.getX() - 1, current.getY()));
        if (canMoveRight(current))
            nexts.add(new Position(current.getX(), current.getY() + 1));
        if (canMoveDown(current))
            nexts.add(new Position(current.getX() + 1, current.getY()));
        if (canMoveLeft(current))
            nexts.add(new Position(current.getX(), current.getY() - 1));
        return nexts.contains(next);
    }
    public boolean canMoveUp(Position position) {
        return position.getX() - 1 > 0 && array[position.getX() - 1][position.getY()] == 0;
    }

    public boolean canMoveRight(Position position) {
        return position.getY() + 1 <= size + 1 && array[position.getX()][position.getY() + 1] == 0;
    }

    public boolean canMoveDown(Position position) {
        return position.getX() + 1 <= size && array[position.getX() + 1][position.getY()] == 0;
    }

    public boolean canMoveLeft(Position position) {
        return position.getY() - 1 > 0 && array[position.getX()][position.getY() - 1] == 0;
    }

    public Stack<Position> getWay(Position start, Position end) {
        Stack<Position> visitted_pos = new Stack<Position>();
        Stack<Position> valid_pos = new Stack<Position>();

        Position current = start;
        visitted_way[1][0] = true;
        visitted_pos.push(new Position(start.getX(), start.getY()));
        while (!current.equals(end)) {
            int x = current.getX();
            int y = current.getY();
            if (canMoveUp(current) && visitted_way[x - 1][y] == false)
                valid_pos.push(new Position(x - 1, y));
            if (canMoveLeft(current) && visitted_way[x][y - 1] == false)
                valid_pos.push(new Position(x, y - 1));
            if (canMoveRight(current) && visitted_way[x][y + 1] == false)
                valid_pos.push(new Position(x, y + 1));
            if (canMoveDown(current) && visitted_way[x + 1][y] == false)
                valid_pos.push(new Position(x + 1, y));

            current = valid_pos.pop();
            visitted_pos.push(new Position(current.getX(), current.getY()));
            x = current.getX();
            y = current.getY();

            visitted_way[x][y] = true;
        }
        Stack<Position> way = new Stack<Position>();
        while (!visitted_pos.empty()) {
            way.push(visitted_pos.pop());
        }
        resetVisittedWay();
        return way;

    }

    public Stack<Position> getDirectWay(Position start, Position end) {
        Stack<Position> visitted_pos = getWay(start, end);
        Stack<Position> revrse_way = new Stack<Position>();
        while (!visitted_pos.empty()) {
            revrse_way.push(visitted_pos.pop());
        }
        visitted_pos.push(new Position(end.getX(), end.getY()));
        revrse_way.pop();
        Position current = end;
        while (!revrse_way.empty()) {
            current = revrse_way.pop();
            Position next = visitted_pos.peek();

            if (canMove(current, next)) {
                visitted_pos.push(new Position(current.getX(), current.getY()));
            }
        }
        return visitted_pos;
    }

    private void resetVisittedWay() {
        for (int i = 0; i < size + 2; i++) {
            for (int j = 0; j < size + 2; j++) {
                visitted_way[i][j] = false;
            }
        }
    }
}