using System;
using System.Collections.Generic;
using System.Text;

namespace AmazingNS
{
public class Amazing
{
   private Maze maze;
   private int curX, curY;
   private bool makingExit, madeExit;
   private Random random = new Random();

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
      int entranceX = rnd(maze.getWidth());
      maze.setEntrance(entranceX);
      maze.markCellDone(entranceX, 1);

      curX=entranceX;
      curY=1;
   }


   private bool canGoUp() {
      return !(curY == 1 || maze.doneWithCell(curX, curY-1));
   }
   private bool canGoDown() {
      return !(curY == maze.getHeight() || maze.doneWithCell(curX, curY+1));
   }
   private bool canGoLeft() {
      return !(curX == 1 || maze.doneWithCell(curX-1, curY));
   }
   private bool canGoRight() {
      return !(curX == maze.getWidth() || maze.doneWithCell(curX+1, curY));
   }

   public bool isThereAnExit()
   {
      return madeExit;
   }

   private void findNextUndoneCell()
   {
      // find the next undone spot in the maze
      do {
         if (curX == maze.getWidth()) {
            curX=1;
            if (curY == maze.getHeight()) curY = 1;
            else                       curY++;
         }
         else curX++;
      }
      while (!maze.doneWithCell(curX,curY));
   }

   private const int LEFT  = 1;
   private const int RIGHT = 2;
   private const int UP    = 4;
    private const int DOWN = 8;

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
         if (curY == maze.getHeight()) {
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
      if (curY == maze.getHeight()) {
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

      return (int) random.Next(limit) + 1;
   }

   public static void Main(String[] args)
   {
      Amazing a = new Amazing();
      Console.WriteLine(a.generateMaze(23,23));
   }
}



public class Maze
{
   private const int CLOSED      = 0;
   private const int RIGHT_WALL  = 1;
   private const int BOTTOM_WALL = 2;
   private const int OPEN        = 3;

   private int[,] maze;
   private int width, height;
   private int entranceX;
   private int cellsDone;
   private bool[,] done;

   public Maze(int width, int height)
   {
      this.width = width;
      this.height = height;
      maze = new int[width,height];

      cellsDone=0;
      done = new bool[width,height];
   }

   public int getWidth() {
      return width;
   }

   public int getHeight() {
      return height;
   }

   public void setEntrance(int x) {
      entranceX = x;
   }

   //----------------------------------------------------------

   public void markCellDone(int x, int y)
   {
      done[x-1,y-1]=true;
      cellsDone++;
   }

   public bool doneWithCell(int x, int y) {
      return done[x-1,y-1];
   }

   public bool doneWithMaze() {
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
      maze[x-1,y-1] = keepWall(maze[x-1,y-1], BOTTOM_WALL);
   }

   public void breakDownBottomWall(int x, int y) {
      maze[x-1,y-1] = keepWall(maze[x-1,y-1], RIGHT_WALL);
   }

   //----------------------------------------------------------

   public override String ToString()
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
            switch(maze[i-1,j-1]) {
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
            switch(maze[i-1,j-1]) {
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


}
