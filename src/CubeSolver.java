import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;

public class CubeSolver
{
    private Cube cube;
    private String colorScheme;
    private char crossColor, lastLayerColor;

    public CubeSolver(Cube cube)
    {
        this.cube = cube;
        colorScheme = cube.getColorScheme();
        setCrossColor('W');
    }

    public void setCrossColor(char crossColor)
    {
        this.crossColor = crossColor;
        lastLayerColor = colorScheme.charAt((colorScheme.indexOf("" + crossColor) + 3)%6);
    }

    public void solveOLL()
    {
        String oll = getOLL();
        if (!oll.equals("Invalid OLL"));
            cube.move(oll);
    }

    public String getOLL()
    {
        int[][] topLayer = getTopLayer();
        if (topLayer == null)
            return "Invalid OLL";
        for (int i = 0; i < topLayer.length; i ++)
            for (int j = 0; j < topLayer.length; j ++)
                topLayer[i][j] /= 5;
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(new File("src" + File.separator + "OLLs.txt")));
            String ollString, ollMoves;
            while (reader.readLine() != null)
            {
                ollString = "";
                for (int i = 0; i < 5; i ++)
                    ollString += reader.readLine() + "\n";
                ollMoves = reader.readLine();
                reader.readLine();
                for (int uMoves = 0; uMoves < 4; uMoves ++)
                {
                    String curOll = getTopLayerString(topLayer);
                    if (curOll.equals(ollString))
                    {
                        reader.close();
                        String preU = getU(uMoves);
                        return (uMoves > 0 ? "(" + preU + ") " : "") + ollMoves;
                    }
                    topLayer = topLayerU(topLayer);
                }
            }
            reader.close();
        }
        catch (IOException e)   {   System.out.println("IOException");  }
        return "Invalid OLL";
    }

    public String getPLLName()
    {
        return getPLL()[0];
    }

    public String getPLLMoves()
    {
        return getPLL()[1];
    }

    public void solvePLL()
    {
        cube.move(getPLLMoves());
    }

    public String[] getPLL()
    {
        int[][] topLayer = getTopLayer();
        if (topLayer == null)
            return new String[]{"Invalid PLL", ""};
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(new File("src" + File.separator + "PLLs.txt")));
            String pllName, pllString, pllMoves;
            while ((pllName = reader.readLine()) != null)
            {
                pllString = reader.readLine();
                pllMoves = reader.readLine();
                reader.readLine();
                for (int uMoves = 0; uMoves < 4; uMoves ++)
                {
                    String front, right, back, left, curPll;
                    front = "" + topLayer[4][1] + topLayer[4][2] + topLayer[4][3];
                    right = "" + topLayer[3][4] + topLayer[2][4] + topLayer[1][4];
                    back = "" + topLayer[0][3] + topLayer[0][2] + topLayer[0][1];
                    left = "" + topLayer[1][0] + topLayer[2][0] + topLayer[3][0];
                    curPll = String.format("%s %s %s %s", front, right, back, left);

                    for (int offset = 0; offset < 4; offset ++)
                    {
                        String pllStringOffset = offsetPllColors(pllString, offset);
                        if (curPll.equals(pllStringOffset))
                        {
                            reader.close();
                            String preU = getU(uMoves);
                            return new String[]{pllName, (uMoves > 0 ? "(" + preU + ") " : "") + pllMoves};
                        }
                    }
                    topLayer = topLayerU(topLayer);
                }
            }
            reader.close();
        }
        catch (IOException e)   {  System.out.println("IOException");  }
        return new String[]{"Invalid PLL", ""};
    }

    private int[][] getTopLayer()
    {
        int[][] topLayer = new int[5][5];
        String temp = colorScheme.replace("" + colorScheme.charAt((colorScheme.indexOf(lastLayerColor) + 3)%6), "").replace("" + lastLayerColor, "");
        if (colorScheme.indexOf(crossColor)%2 == 0)
            temp = new StringBuilder(temp).reverse().toString();
        for (Piece piece: cube.getPieces())
        {
            if (piece.getY() != 1)
                continue;
            int x = piece.getX() + 2, z = piece.getZ() + 2;
            for (String[] colors: piece.getColors())
            {
                if (colors[0].equals("" + crossColor))
                    return null;
                int row = z, col = x;
                boolean isTopColor = colors[0].equals("" + lastLayerColor);
                String colorOrientation = colors[1];
                if (colorOrientation.equals("+z"))
                    row ++;
                else if (colorOrientation.equals("-z"))
                    row --;
                else if (colorOrientation.equals("+x"))
                    col ++;
                else if (colorOrientation.equals("-x"))
                    col --;
                topLayer[row][col] = isTopColor ? 5 : temp.indexOf(colors[0]) + 1;
            }
        }
        return topLayer;
    }

    private int[][] topLayerU(int[][] topLayer)
    {
        int n = 5;
        int[][] newTopLayer = new int[n][n];
        for (int i = 0; i < n; i ++)
            for (int j = 0; j < n; j ++)
                newTopLayer[i][j] = topLayer[n - j - 1][i];
        return newTopLayer;
    }

    private String getTopLayerString(int[][] topLayer)
    {
        String topLayerString = "";
        for (int[] row: topLayer)
        {
            for (int x: row)
                topLayerString += x;
            topLayerString += "\n";
        }
        return topLayerString;
    }

    private String getU(int uMoves)
    {
        switch (uMoves)
        {
            case 0: return "";
            case 1: return "U";
            case 2: return "U2";
            case 3: return "U'";
            default: return null;
        }
    }

    public String offsetPllColors(String pllString, int offset)
    {
        String pllStringOffset = "";
        for (int i = 0; i < pllString.length(); i ++)
        {
            char c = pllString.charAt(i);
            pllStringOffset += (c == ' ' ? " " : (Integer.parseInt("" + c) + offset - 1)%4 + 1);
        }
        return pllStringOffset;
    }
}
