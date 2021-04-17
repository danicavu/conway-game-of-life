public class Collaborative1 {
        public static void main(String[] args) {
            // parameters
            int cellSize = 4;   // should be an even number
            int numCells = 200; // number across and down, so really this many squared
            int pauseTime = 50; // milliseconds

            // set up everything for drawing
            StdDraw.enableDoubleBuffering();
            StdDraw.setCanvasSize(numCells * cellSize, numCells * cellSize);
            StdDraw.setScale(0, numCells * cellSize);
            StdDraw.setPenColor(0, 0, 0);

            // initialize the world randomly
            int n = 4; // block size
            int[][] cells = new int[numCells][numCells];
            for (int row = 0; row < numCells/n-1; row++)
                for (int column = 0; column < numCells/n-1; column++)
                    if(Math.random() > 0.9){
                        // Creates a glider in the block
                        cells[n*row+2][n*column + 1] = 1;
                        cells[n*row+1][n*column + 2] = 1;
                        for(int i = 0; i < 3; i++) {
                            cells[n*row][n*column + i] = 1;
                        }
                    }else if(Math.random() > 0.99){
                        for(int i = 0; i < 3; i++) {
                            cells[n*row + 1][n*column + i] = 1;
                        }
                    }else{
                    for(int i_idx = 0; i_idx < n; i_idx++){
                        for(int j_idx = 0; j_idx < n; j_idx++){
                            cells[n*row + i_idx][n*column + j_idx] = 0;
                        }
                    }
                    }
            // repeatedly draw, pause, and update the world
            while (true) {
                drawCells(cells, cellSize);
                StdDraw.pause(pauseTime);
                cells = update(cells);
            }
        }

        static void drawCells(int[][] cells, int size) {
            int half = size / 2;
            StdDraw.clear();
            for (int r = 0; r < cells.length; r++)
                for (int c = 0; c < cells[r].length; c++)
                    if (cells[r][c] == 1)
                        StdDraw.filledSquare(c * size + half, r * size + half, half);
            StdDraw.show();
        }

        static int[][] update(int[][] cells) {
            int[][] newCells = new int[cells.length][cells.length];
            for (int r = 0; r < cells.length; r++) {
                for (int c = 0; c < cells.length; c++) {
                    int aliveNeighbors = countAliveNeighbors(cells, r, c);
                    if (cells[r][c] == 1) {
                        if (aliveNeighbors == 2 || aliveNeighbors == 3)
                            newCells[r][c] = 1;
                    } else if (aliveNeighbors == 3) {
                        newCells[r][c] = 1;
                    }
                }
            }
            return newCells;
        }

        static int countAliveNeighbors(int[][] cells, int r, int c) {
            int aliveNeighbors = 0;
            for (int i = -1; i <= 1; i++)
                for (int j = -1; j <= 1; j++) {
                    if (i != 0 || j != 0) {
                        int neighR = (cells.length + r + i) % cells.length;
                        int neighC = (cells.length + c + j) % cells.length;
                        aliveNeighbors += cells[neighR][neighC];
                    }
                }
            return aliveNeighbors;
        }
    }

