package Model;

import java.util.*;

public class PathFinder {
    private final Tile[][] tiles;
    private final int width;
    private final int height;

    public PathFinder(Tile[][] tiles) {
        this.tiles = tiles;
        this.width = tiles.length;
        this.height = tiles[0].length;
    }

    public Path walk(int startX, int startY, int destX, int destY, Energy energy) {
        // Early exit checks
        if (startX == destX && startY == destY) {
            return new Path(false, Collections.emptyList(), 0,
                    "congratulations! You are already at destination.");
        }
        if (!isValid(destX, destY)) {
            return new Path(false, Collections.emptyList(), 0,
                    "invalid destination");
        }
        if (!tiles[destX][destY].isWalkable() ||
                !((tiles[destX][destY].getOwner() == null) ||
                        tiles[destX][destY].getOwner().equals(tiles[startX][startY].getOwner()))) {
            return new Path(false, Collections.emptyList(), 0,
                    "destination unreachable");
        }

        // Optimized data structures
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingDouble(n -> n.fScore));
        double[][] gScore = new double[width][height];
        Node[][] cameFrom = new Node[width][height];

        // Initialize grid with infinity
        for (int x = 0; x < width; x++) {
            Arrays.fill(gScore[x], Double.POSITIVE_INFINITY);
        }

        Point start = new Point(startX, startY);
        gScore[startX][startY] = 0;
        openSet.add(new Node(start, null, 0, heuristic(startX, startY, destX, destY)));

        // Direction vectors for 4-way movement
        final int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();
            int currentX = current.point.x;
            int currentY = current.point.y;

            // Check if we've reached the destination
            if (currentX == destX && currentY == destY) {
                return reconstructPath(current, current.gScore);
            }

            // Skip if we found a better path already
            if (current.gScore > gScore[currentX][currentY]) {
                continue;
            }

            // Explore neighbors
            for (int[] dir : directions) {
                int neighborX = currentX + dir[0];
                int neighborY = currentY + dir[1];

                if (!isValid(neighborX, neighborY) || !tiles[neighborX][neighborY].isWalkable()) {
                    continue;
                }

                // Calculate tentative gScore
                double tentativeGScore = gScore[currentX][currentY] +
                        calculateEnergyCost(current, neighborX, neighborY);

                if (tentativeGScore < gScore[neighborX][neighborY]) {
                    // This path to neighbor is better than any previous one
                    cameFrom[neighborX][neighborY] = current;
                    gScore[neighborX][neighborY] = tentativeGScore;
                    double fScore = tentativeGScore + heuristic(neighborX, neighborY, destX, destY);
                    openSet.add(new Node(new Point(neighborX, neighborY), current, tentativeGScore, fScore));
                }
            }
        }

        // No path found
        return new Path(false, Collections.emptyList(), 0, "no path found");
    }

    private double heuristic(int x1, int y1, int x2, int y2) {
        // Manhattan distance as heuristic
        return (Math.abs(x1 - x2) + Math.abs(y1 - y2)) / 20.0;
    }

    private double calculateEnergyCost(Node current, int neighborX, int neighborY) {
        // Base cost: 1 tile distance
        double cost = 1.0;

        // Add turn cost if there's a direction change
        if (current.parent != null) {
            Point prev = current.parent.point;
            int prevDX = current.point.x - prev.x;
            int prevDY = current.point.y - prev.y;
            int currDX = neighborX - current.point.x;
            int currDY = neighborY - current.point.y;

            // Check if direction changed (turn)
            if (prevDX != currDX || prevDY != currDY) {
                cost += 10.0; // Turn penalty
            }
        }

        return cost / 20.0;
    }

    private Path reconstructPath(Node endNode, double totalEnergy) {
        LinkedList<Point> path = new LinkedList<>();
        Node current = endNode;
        while (current != null) {
            path.addFirst(current.point);
            current = current.parent;
        }
        return new Path(true, path, totalEnergy, "walking to the destination ...");
    }

    private boolean isValid(int x, int y) {
        return x >= 0 && y >= 0 && x < width && y < height;
    }

    private static class Node {
        final Point point;
        final Node parent;
        final double gScore; // Cost from start to this node
        final double fScore; // Estimated total cost (gScore + heuristic)

        Node(Point point, Node parent, double gScore, double fScore) {
            this.point = point;
            this.parent = parent;
            this.gScore = gScore;
            this.fScore = fScore;
        }
    }

    public record Path(boolean reachable, List<Point> path, double totalEnergy, String message) {
    }
}