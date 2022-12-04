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
                output += " ";
            }
            else{
                output += "|";
            }
            for (int k=0;k<col;k++){
                if (maze[j][k].getVisited()){
                    output += " * ";
                }
                else {
                    output += "   ";
                }
                if (maze[j][k].getRight()){
                    output += "|";
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
        System.out.println(output);
    }

    /* TODO: Solve the maze using the algorithm found in the writeup. */
    public void solveMaze() {
        Q1Gen solve = new Q1Gen();
        int[] current = new int[2];
        int[] temp = new int[2];
        solve.add(temp);
        if (solve.length() > 0) {
            do {//while queue is not empty
                current = (int[]) solve.remove();//current takes from front of the queue
                maze[current[0]][current[1]].setVisited(true);//mark current tile as visited
                if (current[0] == maze.length - 1 && current[1] == maze[0].length - 1) {
                    break;
                }
                if (current[0] - 1 >= 0) {//makes sure not out of bounds
                    if (!maze[current[0] - 1][current[1]].getVisited() && !maze[current[0] - 1][current[1]].getBottom()) {
                        temp = new int[2];
                        temp[0] = current[0] - 1;
                        temp[1] = current[1];
                        solve.add(temp);//adds into temp array so coordinates dont get messed with when current is changed
                    }
                }
                if (current[0] + 1 <= maze.length - 1) {//makes sure not out of bounds
                    if (!maze[current[0] + 1][current[1]].getVisited() && !maze[current[0]][current[1]].getBottom()) {
                        temp = new int[2];
                        temp[0] = current[0] + 1;
                        temp[1] = current[1];
                        solve.add(temp);//adds into temp array so coordinates dont get messed with when current is changed
                    }
                }
                if (current[1] - 1 >= 0) {//makes sure not out of bounds
                    if (!maze[current[0]][current[1] - 1].getVisited() && !maze[current[0]][current[1] - 1].getRight()) {
                        temp = new int[2];
                        temp[0] = current[0];
                        temp[1] = current[1] - 1;
                        solve.add(temp);//adds into temp array so coordinates dont get messed with when current is changed
                    }
                }
                if (current[1] + 1 <= maze[0].length - 1) {//makes sure not out of bounds
                    if (!maze[current[0]][current[1] + 1].getVisited() && !maze[current[0]][current[1]].getRight()) {
                        temp = new int[2];
                        temp[0] = current[0];
                        temp[1] = current[1] + 1;
                        solve.add(temp);//adds into temp array so coordinates dont get messed with when current is changed
                    }
                }
            } while (solve.length() > 0);
        }
        printMaze();
    }


    public static void main(String[] args){
        Scanner user = new Scanner(System.in);
        System.out.println("Please enter settings of the maze(format: total rows, total columns, start row, end row. Separate them with a space.)");
        System.out.println("Beware that total rows and columns must be between 5 and 20, and the index of rows start at 0");
        String input = user.nextLine();
        String[] inputs = input.split(" ");
        int rows = Integer.parseInt(inputs[0]);
        int cols = Integer.parseInt(inputs[1]);
        int startRow = Integer.parseInt(inputs[2]);
        int endRow = Integer.parseInt(inputs[3]);
        while ( rows>20 || rows <5 || cols <5 || cols >20 || startRow < 0 || startRow >= rows || endRow < 0 || endRow >= rows){
            System.out.println("Invalid inputs");
            System.out.println("Please enter settings of the maze(format: total rows, total columns, start row, end row. Separate them with a space.)");
            System.out.println("Be ware that total rows and columns must be between 5 and 20, and the index of rows start at 0");
            input = user.nextLine();
            inputs = input.split(" ");
            rows = Integer.parseInt(inputs[0]);
            cols = Integer.parseInt(inputs[1]);
            startRow = Integer.parseInt(inputs[2]);
            endRow = Integer.parseInt(inputs[3]);
        }
        MyMaze maze = new MyMaze(rows,cols,startRow,endRow);
        maze.makeMaze(rows,cols,startRow,endRow).printMaze();
    }
}
