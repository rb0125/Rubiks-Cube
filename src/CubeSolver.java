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

    public int[][] getTopLayer()
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

    public String getOLL()
    {
        int[][] topLayer = getTopLayer();
        if (topLayer == null)
            return "Invalid OLL";
        String oll = "";
        for (int[] row: topLayer)
        {
            for (int x: row)
                oll += "" + x;
            oll += "\n";
        }
        return oll;
    }

    public String getPLLName()
    {
        return getPLLInfo()[0];
    }

    public String getPLLMoves()
    {
        return getPLLInfo()[1];
    }

    public void solvePLL()
    {
        cube.move(getPLLMoves());
    }

    public String[] getPLLInfo()
    {
        int[][] topLayer = getTopLayer();
        if (topLayer == null)
            return new String[]{"Invalid PLL", ""};

        String front, right, back, left;
        front = "" + topLayer[4][1] + topLayer[4][2] + topLayer[4][3];
        right = "" + topLayer[3][4] + topLayer[2][4] + topLayer[1][4];
        back = "" + topLayer[0][3] + topLayer[0][2] + topLayer[0][1];
        left = "" + topLayer[1][0] + topLayer[2][0] + topLayer[3][0];

        String[] pllStrings = new String[4];
        pllStrings[0] = String.format("%s %s %s %s", front, right, back, left);
        pllStrings[1] = String.format("%s %s %s %s", right, back, left, front);
        pllStrings[2] = String.format("%s %s %s %s", back, left, front, right);
        pllStrings[3] = String.format("%s %s %s %s", left, front, right, back);
        
        for (int i = 0; i < pllStrings.length; i ++)
            System.out.println(offsetPllString(pllStrings[0], i));

        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(new File("src" + File.separator + "PLLs.txt")));
            String pllName, pllString, pllMoves;
            while ((pllName = reader.readLine()) != null)
            {
                pllString = reader.readLine();
                pllMoves = reader.readLine();
                reader.readLine();
                for (int i = 0; i < pllStrings.length; i ++)
                {
                    for (int j = 0; j < 4; j ++)
                    {
                        String pllStringOffset = offsetPllString(pllString, j);
                        if (pllStrings[i].equals(pllStringOffset))
                        {
                            reader.close();
                            String preU = getU(i);
                            return new String[]{pllName, preU + " " + pllMoves};
                        }
                    }
                }
            }
            reader.close();
        }
        catch (IOException e){  System.out.println("IOException");  }
        return new String[]{"Invalid PLL", ""};
    }

    private String getU(int numU)
    {
        switch (numU)
        {
            case 0: return "";
            case 1: return "U";
            case 2: return "U2";
            case 3: return "U'";
            default: return null;
        }
    }

    public String offsetPllString(String pllString, int offset)
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
