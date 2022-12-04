// Names:
// x500s:

import java.util.Random;
import java.util.Scanner;

public class MyMaze{
    Cell[][] maze;
    int startRow;
    int endRow;

    public MyMaze(int rows, int cols, int startRow, int endRow) {
        maze = new Cell[rows][cols];
        for (int i=0;i<rows;i++){
            for (int j=0;j<cols;j++){
                maze[i][j] = new Cell();
            }
        }
        this.startRow = startRow;
        this.endRow = endRow;
    }

    /* make and initialize the maze */
    public static MyMaze makeMaze(int rows, int cols, int startRow, int endRow) {
        Random rand = new Random();
        MyMaze newMaze = new MyMaze(rows, cols, startRow, endRow);//initialize maze
        Stack1Gen<Integer[]> stack = new Stack1Gen<>();//initialize stack
        stack.push(new Integer[] {startRow,0});//push the start into stack
        newMaze.maze[startRow][0].setVisited(true);
        while(!stack.isEmpty()){
            Integer[] current = stack.top();
            int currentRow = current[0];
            int currentCol = current[1];
            Integer[][] neighbors = new Integer[4][2];
            int nextEmpty = 0;
            //account for edge cases
            //top row
            if (currentRow==0){
                //top left
                if (currentCol==0){
                    if (!newMaze.maze[0][1].getVisited()){
                        neighbors[nextEmpty] = new Integer[]{0, 1};
                        nextEmpty++;
                    }
                    if (!newMaze.maze[1][0].getVisited()){
                        neighbors[nextEmpty] = new Integer[]{1, 0};
                        nextEmpty++;
                    }
                }
                //top right
                else if (currentCol == cols-1){
                    if (!newMaze.maze[0][cols-2].getVisited()){
                        neighbors[nextEmpty] = new Integer[]{0, cols-2};
                        nextEmpty++;
                    }
                    if (!newMaze.maze[1][cols-1].getVisited()){
                        neighbors[nextEmpty] = new Integer[]{1, cols-1};
                        nextEmpty++;
                    }
                }
                //top other
                else{
                    if (!newMaze.maze[1][currentCol].getVisited()){
                        neighbors[nextEmpty] = new Integer[]{1, currentCol};
                        nextEmpty++;
                    }
                    if (!newMaze.maze[0][currentCol+1].getVisited()){
                        neighbors[nextEmpty] = new Integer[]{0, currentCol+1};
                        nextEmpty++;
                    }
                    if (!newMaze.maze[0][currentCol-1].getVisited()){
                        neighbors[nextEmpty] = new Integer[]{0, currentCol-1};
                        nextEmpty++;
                    }
                }
            }
            //bottom row
            else if (currentRow == rows-1){
                //bottom left
                if (currentCol == 0){
                    if (!newMaze.maze[rows-1][1].getVisited()){
                        neighbors[nextEmpty] = new Integer[]{rows-1, 1};
                        nextEmpty++;
                    }
                    if (!newMaze.maze[rows-2][0].getVisited()){
                        neighbors[nextEmpty] = new Integer[]{rows-2, 0};
                        nextEmpty++;
                    }
                }
                //bottom right
                else if (currentCol == cols-1){
                    if (!newMaze.maze[rows-1][cols-2].getVisited()){
                        neighbors[nextEmpty] = new Integer[]{rows-1, cols-2};
                        nextEmpty++;
                    }
                    if (!newMaze.maze[rows-2][cols-1].getVisited()){
                        neighbors[nextEmpty] = new Integer[]{rows-2, cols-1};
                        nextEmpty++;
                    }
                }
                //other bottom
                else{
                    if (!newMaze.maze[rows-2][currentCol].getVisited()){
                        neighbors[nextEmpty] = new Integer[]{rows-2, currentCol};
                        nextEmpty++;
                    }
                    if (!newMaze.maze[rows-1][currentCol+1].getVisited()){
                        neighbors[nextEmpty] = new Integer[]{rows-1, currentCol+1};
                        nextEmpty++;
                    }
                    if (!newMaze.maze[rows-1][currentCol-1].getVisited()){
                        neighbors[nextEmpty] = new Integer[]{rows-1, currentCol-1};
                        nextEmpty++;
                    }
                }
            }
            //middle rows cases
            else{
                //most left col
                if (currentCol==0){
                    if (!newMaze.maze[currentRow-1][0].getVisited()){
                        neighbors[nextEmpty] = new Integer[]{currentRow-1, 0};
                        nextEmpty++;
                    }
                    if (!newMaze.maze[currentRow+1][0].getVisited()){
                        neighbors[nextEmpty] = new Integer[]{currentRow+1, 0};
                        nextEmpty++;
                    }
                    if (!newMaze.maze[currentRow][1].getVisited()){
                        neighbors[nextEmpty] = new Integer[]{currentRow, 1};
                        nextEmpty++;
                    }
                }
                //most right col
                else if (currentCol==cols-1){
                    if (!newMaze.maze[currentRow-1][currentCol].getVisited()){
                        neighbors[nextEmpty] = new Integer[]{currentRow-1, currentCol};
                        nextEmpty++;
                    }
                    if (!newMaze.maze[currentRow+1][currentCol].getVisited()){
                        neighbors[nextEmpty] = new Integer[]{currentRow+1, currentCol};
                        nextEmpty++;
                    }
                    if (!newMaze.maze[currentRow][currentCol-1].getVisited()){
                        neighbors[nextEmpty] = new Integer[]{currentRow, currentCol-1};
                        nextEmpty++;
                    }
                }
                //all the cells that is not on the edge
                else{
                    if (!newMaze.maze[currentRow-1][currentCol].getVisited()){
                        neighbors[nextEmpty] = new Integer[]{currentRow-1, currentCol};
                        nextEmpty++;
                    }
                    if (!newMaze.maze[currentRow+1][currentCol].getVisited()){
                        neighbors[nextEmpty] = new Integer[]{currentRow+1, currentCol};
                        nextEmpty++;
                    }
                    if (!newMaze.maze[currentRow][currentCol+1].getVisited()){
                        neighbors[nextEmpty] = new Integer[]{currentRow, currentCol+1};
                        nextEmpty++;
                    }
                    if (!newMaze.maze[currentRow][currentCol-1].getVisited()){
                        neighbors[nextEmpty] = new Integer[]{currentRow, currentCol-1};
                        nextEmpty++;
                    }
                }
            }
            //pop the cell if the cell does not have any available neighbors
            if (nextEmpty==0){
                stack.pop();
            }
            else {
                int chosen = rand.nextInt(nextEmpty);//select available neighbor with random
                Integer[] nextCell = neighbors[chosen];
                newMaze.maze[nextCell[0]][nextCell[1]].setVisited(true);//mark neighbor as visited
                stack.push(new Integer[]{nextCell[0], nextCell[1]});//push the selected nextCell's index in the stack
                //remove the walls between nextCell and current
                if (nextCell[0]==currentRow-1){
                    newMaze.maze[nextCell[0]][nextCell[1]].setBottom(false);
                }
                else if (nextCell[0]==currentRow+1){
                    newMaze.maze[currentRow][currentCol].setBottom(false);
                }
                else if (nextCell[1]==currentCol-1){
                    newMaze.maze[nextCell[0]][nextCell[1]].setRight(false);
                }
                else if (nextCell[1]==currentCol-1){
                    newMaze.maze[currentRow][currentCol].setRight(false);
                }
            }
        }
        //set every cell's visited attribute to false
        for (int i=0;i<rows;i++){
            for (int j=0;j<cols;j++){
                newMaze.maze[i][j].setVisited(false);
            }
        }
        return newMaze;
    }


    public void printMaze() {
        String output = "";
        int row = maze.length;
        int col = maze[0].length;
        maze[endRow][col-1].setRight(false);
        output+="|";
        //upper wall
        for (int i=0;i<col;i++){
            output += "---|";
        }
        output += "\n";
        //inner content of the maze
        for (int j=0;j<row;j++){
            if (j==startRow){
                output += "  ";
                for (int k=0;k<col;k++){
                    if (maze[j][k].getVisited()){
                        output += " * ";
                    }
                    else {
                        output += " ";
                    }
                    if (maze[j][k].getRight()){
                        output += " | ";
                    }
                    else{
                        output += " ";
                    }
                }
                output += "\n";
                output += "|";
                for (int p=0;p<col;p++){
                    if (maze[j][p].getBottom()){
                        output += "---|";
                    }
                    else{
                        output += "   |";
                    }
                }
                output += "\n";
            }
            else{
                output += "| ";
                for (int k=0;k<col;k++){
                    if (maze[j][k].getVisited()){
                        output += " * ";
                    }
                    else {
                        output += " ";
                    }
                    if (maze[j][k].getRight()){
                        output += " | ";
                    }
                    else{
                        output += " ";
                    }

                }
                output += "\n";
                output += "|";
                for (int p=0;p<col;p++){
                    if (maze[j][p].getBottom()){
                        output += "---|";
                    }
                    else{
                        output += "   |";
                    }
                }
                output += "\n";
            }
        }
        System.out.println(output);
    }

    /* TODO: Solve the maze using the algorithm found in the writeup. */
    public void solveMaze() {
    }

    public static void main(String[] args){
        MyMaze maze = new MyMaze(5,20,1,2);
        maze.makeMaze(5,20,1,2);
        maze.printMaze();
        /*Make and solve maze */
    }
}