import java.util.Arrays;

public class Piece
{
    private String name;
    private String[][] colors;
    private int[] coordinates;

    public Piece(String name, String[][] colors, int[] coordinates)
    {
        this.name = name;
        this.colors = colors;
        this.coordinates = coordinates;
    }

    public String getName() {   return name;    }
    public String[][] getColors()   {   return colors;  }
    public int[] getCoordinates()   {   return coordinates; }
    public int getX()   {   return coordinates[0];  }
    public int getY()   {   return coordinates[1];  }
    public int getZ()   {   return coordinates[2];  }
    public int getNumColors()   {   return colors.length;   }
    public String getType()
    {
        switch(colors.length)
        {
            case 1: return "Center";
            case 2: return "Edge";
            case 3: return "Corner";
            default: return "";
        }
    }
    
    public void setCoordinates(int[] coordinates)    {   this.coordinates = coordinates;    }
    public void setX(int xCoordinate) {   coordinates[0] = xCoordinate;   }
    public void setY(int yCoordinate) {   coordinates[1] = yCoordinate;   }
    public void setZ(int zCoordinate) {   coordinates[2] = zCoordinate;   }

    public boolean equals(Piece other)  {   return name.equals(other.name) && Arrays.deepEquals(colors, other.colors) && Arrays.equals(coordinates, other.coordinates); }
}