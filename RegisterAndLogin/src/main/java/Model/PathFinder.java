package Model;

import java.util.*;


    public class PathFinder {
        private Tile[][] tiles;

        public PathFinder(Tile[][] tiles) {
            this.tiles = tiles;
        }

        public Path walk(int startX, int startY, int destX, int destY) {
            if(startX == destX && startY == destY) {
                return new Path(false , Collections.emptyList() , 0 ,
                        "congratulations! You are already at destination.");
            }
            if(!isValid(destX, destY)) {
                return new Path(false , Collections.emptyList() , 0 ,
                        "invalid destination");
            }
            if(!tiles[destX][destY].isWalkable() ||
                    !((tiles[destX][destY].getOwner() == null) ||
                    tiles[destX][destY].getOwner().equals(tiles[startX][startY].getOwner()))) {
                return new Path(false , Collections.emptyList() , 0 ,
                        "destination unreachable");
            }
            PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingDouble(n -> n.totalEnergy));
            HashMap<Point, Double> energyMap = new HashMap<>();
            HashMap<Point, Point> cameFrom = new HashMap<>();

            Point start = new Point(startX, startY);
            openSet.add(new Node(start, null, 0, 0));
            energyMap.put(start, 0.0);

            while (!openSet.isEmpty()) {
                Node current = openSet.poll();

                // Check if we've reached the destination
                if (current.point.x == destX && current.point.y == destY) {
                    // Reconstruct path
                    List<Point> path = new ArrayList<>();
                    Node node = current;
                    while (node != null) {
                        path.add(0, node.point);
                        node = node.parent;
                    }
                    return new Path(true, path , current.totalEnergy , "walking to the destination ...");
                }

                // Explore neighbors
                for (Point neighbor : getNeighbors(current.point)) {
                    if (!isValid(neighbor.x, neighbor.y) || !tiles[neighbor.x][neighbor.y].isWalkable()) {
                        continue;
                    }

                    // Calculate energy cost to move to neighbor
                    double energyCost = calculateEnergyCost(current, neighbor);
                    double newEnergy = current.totalEnergy + energyCost;

                    if (!energyMap.containsKey(neighbor)) {
                        energyMap.put(neighbor, Double.POSITIVE_INFINITY);
                    }

                    if (newEnergy < energyMap.get(neighbor)) {
                        energyMap.put(neighbor, newEnergy);
                        cameFrom.put(neighbor, current.point);
                        openSet.add(new Node(neighbor, current, newEnergy, energyCost));
                    }
                }
            }

            // No path found
            return new Path(false, Collections.emptyList(), 0 , "no path found");
        }

        private double calculateEnergyCost(Node current, Point neighbor) {
            // Base cost: 1 tile distance
            double cost = 1.0;

            // Add turn cost if there's a direction change
            if (current.parent != null) {
                Point prev = current.parent.point;
                int prevDX = current.point.x - prev.x;
                int prevDY = current.point.y - prev.y;
                int currDX = neighbor.x - current.point.x;
                int currDY = neighbor.y - current.point.y;

                // Check if direction changed (turn)
                if (prevDX != currDX || prevDY != currDY) {
                    cost += 10.0; // Turn penalty
                }
            }

            // Apply energy formula: (distance + 10 * turns) / 20
            // Since we're calculating incremental cost, we adjust the formula
            return cost / 20.0;
        }

        private List<Point> getNeighbors(Point point) {
            List<Point> neighbors = new ArrayList<>();
            // 4-directional movement (up, down, left, right)
            neighbors.add(new Point(point.x + 1, point.y));
            neighbors.add(new Point(point.x - 1, point.y));
            neighbors.add(new Point(point.x, point.y + 1));
            neighbors.add(new Point(point.x, point.y - 1));
            return neighbors;
        }

        private boolean isValid(int x, int y) {
            return x >= 0 && y >= 0 && x < tiles.length && y < tiles[0].length;
        }

        private static class Node {
            final Point point;
            final Node parent;
            final double totalEnergy;
            final double stepEnergy;

            Node(Point point, Node parent, double totalEnergy, double stepEnergy) {
                this.point = point;
                this.parent = parent;
                this.totalEnergy = totalEnergy;
                this.stepEnergy = stepEnergy;
            }
        }

        public record Path(boolean reachable, List<Point> path, double totalEnergy , String message) {
        }
    }

