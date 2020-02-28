package astar;

import java.util.ArrayList;
import java.util.List;

public class A_Star {


    public static void linkNeighbors(Spot[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < (grid[i].length) - 1; j++) {
                if (i > 0) {
                    grid[i][j].addNeighbor(grid[i - 1][j]);
                }
                if (j > 0) {
                    grid[i][j].addNeighbor(grid[i][j - 1]);
                }
                if (i < grid.length - 1) {
                    grid[i][j].addNeighbor(grid[i + 1][j]);
                }
                if (j < grid.length - 1) {
                    grid[i][j].addNeighbor(grid[i][j + 1]);
                }
                if (i > 0 && j > 0) {
                    grid[i][j].addNeighbor(grid[i - 1][j - 1]);
                }
                if (i < grid.length - 1 && j < grid.length - 1) {
                    grid[i][j].addNeighbor(grid[i + 1][j + 1]);
                }
            }
        }
    }


    public static Spot A_Star(Spot[][] grid, int x1, int y1, int x2, int y2) {
        java.util.List<Spot> openSet = new ArrayList<>();
        List<Spot> closedSet = new ArrayList<>();

        Spot start = grid[x1][y1];
        Spot end = grid[x2][y2];
        if (start.wall) {
            System.out.println("Start point is a wall");
            return null;
        }
        if (end.wall) {
            System.out.println("End point is a wall");
            return null;
        }

        start.setH(euclidean(start, end));
        start.updateF();

        openSet.add(start);

        Spot current;
        Spot path = null;

        while (openSet.size() > 0) {


            //*** PART OF DRAWING ***
            //timer is only here for slowly down, to give the GUI time to render each step
//            try {
//                TimeUnit.MILLISECONDS.sleep(50);
//            } catch (Exception e) {
//                continue;
//            }


            //check the node in the openSet with lowest F score
            current = openSet.get(0);

            for (Spot candidate : openSet) {
                if (candidate.getF() < current.getF()) {
                    current = candidate;
                }
            }


            openSet.remove(current);
            closedSet.add(current);

            // Part of Drawing
//            MyDrawable green = new MyDrawable(new Rectangle(current.getX() * w, current.getY() * h, w, h), new Color(0f, 1f, 0f, 0.1f), new BasicStroke(0), true);
//            gui.addMyDrawable(green);


            for (Spot neighbor : current.getNeighbors()) {
                if (!closedSet.contains(neighbor) && !neighbor.wall) {
                    double tent_score = current.getG() + euclidean(current, neighbor);

                    if (openSet.contains(neighbor)) {
                        if (tent_score <= neighbor.getG()) {
                            neighbor.setG(tent_score);
                        }
                    } else {
                        neighbor.setG(tent_score);
                        openSet.add(neighbor);

                        // Part of Drawing
//                        MyDrawable red = new MyDrawable(new Rectangle(neighbor.getX() * w, neighbor.getY() * h, w, h), new Color(1f, 0f, 0f, 0.4f), new BasicStroke(0), true);
//                        gui.addMyDrawable(red);
                    }

                    neighbor.setPrevious(current);

                    neighbor.setH(euclidean(neighbor, end));
                    neighbor.updateF();
                }

            }

            if (current.equals(end)) {
                path = current;
                break;
            }

        }
        return path;

    }

    //manhattan distance formula
    public int manh(Spot a, Spot b) {
        return (Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY()));
    }

    //euclidean distance formula
    static public double euclidean(Spot a, Spot b) {
        int deltaX = a.getX() - b.getX();
        int deltaY = a.getY() - b.getY();
        return Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
    }


}
