
public class Amazing
{
   private Maze maze;
   private int curX, curY;
   private boolean makingExit, madeExit;

   public Maze generateMaze(int width, int height)
   {
      maze = new Maze(width, height);

      initMaze();

      while(!maze.doneWithMaze()) {
         takeStep();
      }
      
      return maze;
   }

   private void initMaze()
   {
      makingExit=false;
      madeExit=false;

      // get the opening on top
      int entranceX = rnd(maze.width());
      maze.setEntrance(entranceX);
      maze.markCellDone(entranceX, 1);

      curX=entranceX;
      curY=1;
   }


   private boolean canGoUp() {
      return !(curY == 1 || maze.doneWithCell(curX, curY-1));
   }
   private boolean canGoDown() {
      return !(curY == maze.height() || maze.doneWithCell(curX, curY+1));
   }
   private boolean canGoLeft() {
      return !(curX == 1 || maze.doneWithCell(curX-1, curY));
   }
   private boolean canGoRight() {
      return !(curX == maze.width() || maze.doneWithCell(curX+1, curY));
   }

   public boolean isThereAnExit()
   {
      return madeExit;
   }

   private void findNextUndoneCell()
   {
      // find the next undone spot in the maze
      do {
         if (curX == maze.width()) {
            curX=1;
            if (curY == maze.height()) curY = 1;
            else                       curY++;
         }
         else curX++;
      }
      while (!maze.doneWithCell(curX,curY));
   }

   private static final int LEFT  = 1;
   private static final int RIGHT = 2;
   private static final int UP    = 4;
   private static final int DOWN  = 8;

   private void takeStep()
   {
      int direction = (canGoLeft()?  LEFT:0) |
                      (canGoRight()? RIGHT:0) |
                      (canGoUp()?    UP:0);

      if (direction == 0) {
         punt();
         return;
      }

      if (direction != (LEFT | RIGHT | UP))
      {
         if (curY == maze.height()) {
            if (!madeExit) {
               makingExit=true;
               direction |= DOWN;
            }
         }
         else {
            if (canGoDown()) direction |= DOWN;
         }
      }

      goInRandomDirection(direction);
   }

   private int getRandomDirection()
   {
      switch(rnd(4)) {
         case 1:  return LEFT;
         case 2:  return RIGHT;
         case 3:  return UP;
         default: return DOWN;
      }
   }

   private void goInRandomDirection(int direction)
   {
      int randomDirection;
      do {
         randomDirection = getRandomDirection();
         if ((direction & randomDirection) > 0) {
            switch(randomDirection) {
                case LEFT:  goLeft();
                            break;
                case RIGHT: goRight();
                            break;
                case UP:    goUp();
                            break;
                case DOWN:  goDown();
                            break;
            }
         }
      } while ((direction & randomDirection) == 0);
   }

   private void punt()
   {
      if (curY == maze.height()) {
         if (!madeExit) {
            makingExit=true;
            goDown();
            return;
         }
      }
      else {
         if (canGoDown()) {
            goDown();
            return;
         }
      }
      findNextUndoneCell();
   }

   private void goLeft()
   {
      curX--;
      maze.markCellDone(curX, curY);
      maze.breakDownRightWall(curX, curY);
      makingExit=false;
   }

   private void goUp()
   {
      curY--;
      maze.markCellDone(curX, curY);
      maze.breakDownBottomWall(curX, curY);
      makingExit=false;
   }

   private void goRight()
   {
      maze.breakDownRightWall(curX, curY);
      curX++;
      maze.markCellDone(curX, curY);
   }

   private void goDown()
   {
      maze.breakDownBottomWall(curX, curY);

      if (!makingExit) {
         curY++;
         maze.markCellDone(curX, curY);
      }
      else {
         madeExit=true;
         makingExit=false;
         findNextUndoneCell();
      }
   }

   public int rnd(int limit) {
      return (int)(java.lang.Math.random() * limit) + 1;
   }

   public static void main(String args[])
   {
      Amazing a = new Amazing();
      System.out.println(a.generateMaze(23,23));
   }
}



public class Maze
{
   private static final int CLOSED      = 0;
   private static final int RIGHT_WALL  = 1;
   private static final int BOTTOM_WALL = 2;
   private static final int OPEN        = 3;

   private int[][] maze;
   private int width, height;
   private int entranceX;
   private int cellsDone;
   private boolean[][] done;

   public Maze(int width, int height)
   {
      this.width = width;
      this.height = height;
      maze = new int[width][height];

      cellsDone=0;
      done = new boolean[width][height];
   }

   public int width() {
      return width;
   }

   public int height() {
      return height;
   }

   public void setEntrance(int x) {
      entranceX = x;
   }

   //----------------------------------------------------------

   public void markCellDone(int x, int y)
   {
      done[x-1][y-1]=true;
      cellsDone++;
   }

   public boolean doneWithCell(int x, int y) {
      return done[x-1][y-1];
   }

   public boolean doneWithMaze() {
      return (cellsDone >= width*height);
   }

   //----------------------------------------------------------

   private int keepWall(int wallState, int wallToKeep)
   {
      if (wallState == CLOSED || wallState == wallToKeep)
           return wallToKeep;

      return OPEN;
   }

   public void breakDownRightWall(int x, int y) {
      maze[x-1][y-1] = keepWall(maze[x-1][y-1], BOTTOM_WALL);
   }

   public void breakDownBottomWall(int x, int y) {
      maze[x-1][y-1] = keepWall(maze[x-1][y-1], RIGHT_WALL);
   }

   //----------------------------------------------------------

   public String toString()
   {
      String output = "";

      for (int i=1; i<=width; i++) {
         if (i == entranceX) output += ":  ";
         else                output += ":--";
      }
      output += ":\n";

      for (int j=1; j<=height; j++) {
         output += "I";
         for (int i=1; i<=width; i++) {
            switch(maze[i-1][j-1]) {
               case CLOSED:
               case RIGHT_WALL:  output += "  I";
                                 break;
               case BOTTOM_WALL:
               case OPEN:        output += "   ";
                                 break;
            }
         }

         output += "\n";

         for (int i=1; i<=width; i++) {
            switch(maze[i-1][j-1]) {
               case CLOSED:
               case BOTTOM_WALL: output += ":--";
                                 break;
               case RIGHT_WALL:
               case OPEN:        output += ":  ";
                                 break;
            }
         }
         output += ":\n";
      }
      return output;
   }
}

