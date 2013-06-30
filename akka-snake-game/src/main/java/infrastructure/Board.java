package infrastructure;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: noam
 * Date: 6/30/13
 * Time: 11:35 AM
 * To change this template use File | Settings | File Templates.
 */
public class Board {
    private final int maxX;
    private final int maxY;

    private final Map<Point,BoardElement> boardElements;

    public Board(int maxX,int maxY) {
        this.boardElements = new HashMap<Point,BoardElement>();
        this.maxX=maxX;
        this.maxY=maxY;
    }

    public void put(Point point,BoardElement boardElement) {
        if (point.getX()<0 || point.getX()>=maxX || point.getY()<0 || point.getY()>=maxY) {
            throw new IllegalArgumentException("Illegal point");
        }

        boardElements.put(point,boardElement);
    }

    public BoardElement remove(Point point) {
        if (point.getX()<0 || point.getX()>=maxX || point.getY()<0 || point.getY()>=maxY) {
            throw new IllegalArgumentException("Illegal point");
        }

        return boardElements.remove(point);
    }

    public BoardElement get(Point point) {
        if (point.getX()<0 || point.getX()>=maxX || point.getY()<0 || point.getY()>=maxY) {
            throw new IllegalArgumentException("Illegal point");
        }

        return boardElements.get(point);
    }
}
