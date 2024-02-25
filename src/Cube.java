import java.util.Arrays;
import java.util.Stack;

public class Cube
{
    private Piece[] pieces;
    private Stack<String> undo, redo;
    private String colorScheme, orientation;

    public Cube()   {   initialize();   }

    public String getColorScheme() {   return colorScheme; }
    public String getOrientation() {   return orientation; }
    public Piece getPiece(String name)
    {   
        for (Piece piece: pieces)
            if (piece.getName().equals(name))
                return piece;
        return null;
    }

    public boolean setOrientation(String newOrientation)
    {
        if (newOrientation == null || newOrientation.length() != 2)
            return false;
        char a = newOrientation.charAt(0), b = newOrientation.charAt(1), c;
        if (!colorScheme.contains("" + a) || !colorScheme.contains("" + b) || (colorScheme.indexOf("" + a) - colorScheme.indexOf("" + b))%3 == 0)
            return false;
        while ((c = orientation.charAt(0)) != a && c != b)
        {
            x();
            undo.pop();
        }
        if (c == a)
        {
            while (orientation.charAt(1) != b)
            {
                y();
                undo.pop();
            }
        }
        else
        {
            xPrime();
            undo.pop();
            while (orientation.charAt(0) != a)
            {
                z();
                undo.pop();
            }
        }
        return true;
    }

    public boolean undo()
    {
        if (undo.isEmpty())
            return false;
        String move = undo.pop();
        redo.push(move);
        if (move.contains("'"))
            move = move.replace("'", "");
        else if (!move.contains("2"))
            move += "'";
        move(move);
        undo.pop();
        return true;
    }

    public void reset()
    {
        initialize();
    }

    public boolean move(String moves)
    {
        if (moves == null || moves.length() == 0)
            return false;
        String[] moveStrings = moves.split(" ");
        for (int i = 0; i < moveStrings.length; i ++)
        {
            switch(moveStrings[i])
            {
                case "R": R();
                    break;
                case "R'": RPrime();
                    break;
                case "R2": R2();
                    break;
                case "U": U();
                    break;
                case "U'": UPrime();
                    break;
                case "U2": U2();
                    break;
                case "F": F();
                    break;
                case "F'": FPrime();
                    break;
                case "F2": F2();
                    break;
                case "L": L();
                    break;
                case "L'": LPrime();
                    break;
                case "L2": L2();
                    break;
                case "D": D();
                    break;
                case "D'": DPrime();
                    break;
                case "D2": D2();
                    break;
                case "B": B();
                    break;
                case "B'": BPrime();
                    break;
                case "B2": B2();
                    break;
                case "M": M();
                    break;
                case "M'": MPrime();
                    break;
                case "M2": M2();
                    break;
                case "E": E();
                    break;
                case "E'": EPrime();
                    break;
                case "E2": E2();
                    break;
                case "S": S();
                    break;
                case "S'": SPrime();
                    break;
                case "S2": S2();
                    break;
                case "x": x();
                    break;
                case "x'": xPrime();
                    break;
                case "x2": x2();
                    break;
                case "y": y();
                    break;
                case "y'": yPrime();
                    break;
                case "y2": y2();
                    break;
                case "z": z();
                    break;
                case "z'": zPrime();
                    break;
                case "z2": z2();
                    break;
                case "r": r();
                    break;
                case "r'": rPrime();
                    break;
                case "r2": r2();
                    break;
                case "u": u();
                    break;
                case "u'": uPrime();
                    break;
                case "u2": u2();
                    break;
                case "f": f();
                    break;
                case "f'": fPrime();
                    break;
                case "f2": f2();
                    break;
                case "l": l();
                    break;
                case "l'": lPrime();
                    break;
                case "l2": l2();
                    break;
                case "d": d();
                    break;
                case "d'": dPrime();
                    break;
                case "d2": d2();
                    break;
                case "b": b();
                    break;
                case "b'": bPrime();
                    break;
                case "b2": b2();
                    break;
                default:
                    for (int j = 0; j < i; j ++)
                        undo();
                    return false;
            }
        }
        return true;
    }

    private void initialize()
    {
        pieces = new Piece[] {
            new Piece("W", new String[][]{{"W", "+y"}}, new int[]{0, 1, 0}),
            new Piece("Y", new String[][]{{"Y", "-y"}}, new int[]{0, -1, 0}),
            new Piece("G", new String[][]{{"G", "+z"}}, new int[]{0, 0, 1}),
            new Piece("B", new String[][]{{"B", "-z"}}, new int[]{0, 0, -1}),
            new Piece("R", new String[][]{{"R", "+x"}}, new int[]{1, 0, 0}),
            new Piece("O", new String[][]{{"O", "-x"}}, new int[]{-1, 0, 0}),
            new Piece("WG", new String[][]{{"W", "+y"}, {"G", "+z"}}, new int[]{0, 1, 1}),
            new Piece("WR", new String[][]{{"W", "+y"}, {"R", "+x"}}, new int[]{1, 1, 0}),
            new Piece("WB", new String[][]{{"W", "+y"}, {"B", "-z"}}, new int[]{0, 1, -1}),
            new Piece("WO", new String[][]{{"W", "+y"}, {"O", "-x"}}, new int[]{-1, 1, 0}),
            new Piece("GR", new String[][]{{"G", "+z"}, {"R", "+x"}}, new int[]{1, 0, 1}),
            new Piece("RB", new String[][]{{"R", "+x"}, {"B", "-z"}}, new int[]{1, 0, -1}),
            new Piece("BO", new String[][]{{"B", "-z"}, {"O", "-x"}}, new int[]{-1, 0, -1}),
            new Piece("OG", new String[][]{{"O", "-x"}, {"G", "+z"}}, new int[]{-1, 0, 1}),
            new Piece("YG", new String[][]{{"Y", "-y"}, {"G", "+z"}}, new int[]{0, -1, 1}),
            new Piece("YR", new String[][]{{"Y", "-y"}, {"R", "+x"}}, new int[]{1, -1, 0}),
            new Piece("YB", new String[][]{{"Y", "-y"}, {"B", "-z"}}, new int[]{0, -1, -1}),
            new Piece("YO", new String[][]{{"Y", "-y"}, {"O", "-x"}}, new int[]{-1, -1, 0}),
            new Piece("WGR", new String[][]{{"W", "+y"}, {"G", "+z"}, {"R", "+x"}}, new int[]{1, 1, 1}),
            new Piece("WRB", new String[][]{{"W", "+y"}, {"R", "+x"}, {"B", "-z"}}, new int[]{1, 1, -1}),
            new Piece("WBO", new String[][]{{"W", "+y"}, {"B", "-z"}, {"O", "-x"}}, new int[]{-1, 1, -1}),
            new Piece("WOG", new String[][]{{"W", "+y"}, {"O", "-x"}, {"G", "+z"}}, new int[]{-1, 1, 1}),
            new Piece("YGR", new String[][]{{"Y", "-y"}, {"G", "+z"}, {"R", "+x"}}, new int[]{1, -1, 1}),
            new Piece("YRB", new String[][]{{"Y", "-y"}, {"R", "+x"}, {"B", "-z"}}, new int[]{1, -1, -1}),
            new Piece("YBO", new String[][]{{"Y", "-y"}, {"B", "-z"}, {"O", "-x"}}, new int[]{-1, -1, -1}),
            new Piece("YOG", new String[][]{{"Y", "-y"}, {"O", "-x"}, {"G", "+z"}}, new int[]{-1, -1, 1})
        };

        colorScheme = "WGRYBO";
        orientation = "WG";
        undo = new Stack<>();
        redo = new Stack<>();
    }

    public boolean isSolved()
    {
        boolean solved = true;
        String oldOrientation = orientation;
        setOrientation("WG");
        solved = solved && (Arrays.deepEquals(pieces[0].getColors(), new String[][]{{"W", "+y"}}) && Arrays.equals(pieces[0].getCoordinates(), new int[]{0, 1, 0}));
        solved = solved && (Arrays.deepEquals(pieces[1].getColors(), new String[][]{{"Y", "-y"}}) && Arrays.equals(pieces[1].getCoordinates(), new int[]{0, -1, 0}));
        solved = solved && (Arrays.deepEquals(pieces[2].getColors(), new String[][]{{"G", "+z"}}) && Arrays.equals(pieces[2].getCoordinates(), new int[]{0, 0, 1}));
        solved = solved && (Arrays.deepEquals(pieces[3].getColors(), new String[][]{{"B", "-z"}}) && Arrays.equals(pieces[3].getCoordinates(), new int[]{0, 0, -1}));
        solved = solved && (Arrays.deepEquals(pieces[4].getColors(), new String[][]{{"R", "+x"}}) && Arrays.equals(pieces[4].getCoordinates(), new int[]{1, 0, 0}));
        solved = solved && (Arrays.deepEquals(pieces[5].getColors(), new String[][]{{"O", "-x"}}) && Arrays.equals(pieces[5].getCoordinates(), new int[]{-1, 0, 0}));
        solved = solved && (Arrays.deepEquals(pieces[6].getColors(), new String[][]{{"W", "+y"}, {"G", "+z"}}) && Arrays.equals(pieces[6].getCoordinates(), new int[]{0, 1, 1}));
        solved = solved && (Arrays.deepEquals(pieces[7].getColors(), new String[][]{{"W", "+y"}, {"R", "+x"}}) && Arrays.equals(pieces[7].getCoordinates(), new int[]{1, 1, 0}));
        solved = solved && (Arrays.deepEquals(pieces[8].getColors(), new String[][]{{"W", "+y"}, {"B", "-z"}}) && Arrays.equals(pieces[8].getCoordinates(), new int[]{0, 1, -1}));
        solved = solved && (Arrays.deepEquals(pieces[9].getColors(), new String[][]{{"W", "+y"}, {"O", "-x"}}) && Arrays.equals(pieces[9].getCoordinates(), new int[]{-1, 1, 0}));
        solved = solved && (Arrays.deepEquals(pieces[10].getColors(), new String[][]{{"G", "+z"}, {"R", "+x"}}) && Arrays.equals(pieces[10].getCoordinates(), new int[]{1, 0, 1}));
        solved = solved && (Arrays.deepEquals(pieces[11].getColors(), new String[][]{{"R", "+x"}, {"B", "-z"}}) && Arrays.equals(pieces[11].getCoordinates(), new int[]{1, 0, -1}));
        solved = solved && (Arrays.deepEquals(pieces[12].getColors(), new String[][]{{"B", "-z"}, {"O", "-x"}}) && Arrays.equals(pieces[12].getCoordinates(), new int[]{-1, 0, -1}));
        solved = solved && (Arrays.deepEquals(pieces[13].getColors(), new String[][]{{"O", "-x"}, {"G", "+z"}}) && Arrays.equals(pieces[13].getCoordinates(), new int[]{-1, 0, 1}));
        solved = solved && (Arrays.deepEquals(pieces[14].getColors(), new String[][]{{"Y", "-y"}, {"G", "+z"}}) && Arrays.equals(pieces[14].getCoordinates(), new int[]{0, -1, 1}));
        solved = solved && (Arrays.deepEquals(pieces[15].getColors(), new String[][]{{"Y", "-y"}, {"R", "+x"}}) && Arrays.equals(pieces[15].getCoordinates(), new int[]{1, -1, 0}));
        solved = solved && (Arrays.deepEquals(pieces[16].getColors(), new String[][]{{"Y", "-y"}, {"B", "-z"}}) && Arrays.equals(pieces[16].getCoordinates(), new int[]{0, -1, -1}));
        solved = solved && (Arrays.deepEquals(pieces[17].getColors(), new String[][]{{"Y", "-y"}, {"O", "-x"}}) && Arrays.equals(pieces[17].getCoordinates(), new int[]{-1, -1, 0}));
        solved = solved && (Arrays.deepEquals(pieces[18].getColors(), new String[][]{{"W", "+y"}, {"G", "+z"}, {"R", "+x"}}) && Arrays.equals(pieces[18].getCoordinates(), new int[]{1, 1, 1}));
        solved = solved && (Arrays.deepEquals(pieces[19].getColors(), new String[][]{{"W", "+y"}, {"R", "+x"}, {"B", "-z"}}) && Arrays.equals(pieces[19].getCoordinates(), new int[]{1, 1, -1}));
        solved = solved && (Arrays.deepEquals(pieces[20].getColors(), new String[][]{{"W", "+y"}, {"B", "-z"}, {"O", "-x"}}) && Arrays.equals(pieces[20].getCoordinates(), new int[]{-1, 1, -1}));
        solved = solved && (Arrays.deepEquals(pieces[21].getColors(), new String[][]{{"W", "+y"}, {"O", "-x"}, {"G", "+z"}}) && Arrays.equals(pieces[21].getCoordinates(), new int[]{-1, 1, 1}));
        solved = solved && (Arrays.deepEquals(pieces[22].getColors(), new String[][]{{"Y", "-y"}, {"G", "+z"}, {"R", "+x"}}) && Arrays.equals(pieces[22].getCoordinates(), new int[]{1, -1, 1}));
        solved = solved && (Arrays.deepEquals(pieces[23].getColors(), new String[][]{{"Y", "-y"}, {"R", "+x"}, {"B", "-z"}}) && Arrays.equals(pieces[23].getCoordinates(), new int[]{1, -1, -1}));
        solved = solved && (Arrays.deepEquals(pieces[24].getColors(), new String[][]{{"Y", "-y"}, {"B", "-z"}, {"O", "-x"}}) && Arrays.equals(pieces[24].getCoordinates(), new int[]{-1, -1, -1}));
        solved = solved && (Arrays.deepEquals(pieces[25].getColors(), new String[][]{{"Y", "-y"}, {"O", "-x"}, {"G", "+z"}}) && Arrays.equals(pieces[25].getCoordinates(), new int[]{-1, -1, 1}));
        setOrientation(oldOrientation);
        return solved;
    }

    public void R()
    {
        for (Piece piece: pieces)
        {
            if (piece.getX() == 1)
            {
                if (piece.getType().equals("Edge"))
                {
                    if (piece.getY() == 0)
                    {
                        piece.setY(piece.getZ());
                        piece.setZ(0);
                    }
                    else
                    {
                        piece.setZ(-1*piece.getY());
                        piece.setY(0);
                    }
                }
                else if (piece.getType().equals("Corner"))
                {
                    if (piece.getY()*piece.getZ() == 1)
                        piece.setZ(-1*piece.getZ());
                    else
                        piece.setY(-1*piece.getY());
                }
                for (String[] color: piece.getColors())
                {
                    if (color[1].equals("+z"))
                        color[1] = "+y";
                    else if (color[1].equals("+y"))
                        color[1] = "-z";
                    else if (color[1].equals("-z"))
                        color[1] = "-y";
                    else if (color[1].equals("-y"))
                        color[1] = "+z";
                }
            }
        }
        undo.push("R");
    }

    public void RPrime()
    {
        for (int i = 0; i < 3; i ++)
        {
            R();
            undo.pop();
        }
        undo.push("R'");
    }

    public void R2()
    {
        for (int i = 0; i < 2; i ++)
        {
            R();
            undo.pop();
        }
        undo.push("R2");
    }

    public void U()
    {
        for (Piece piece: pieces)
        {
            if (piece.getY() == 1)
            {
                if (piece.getType().equals("Edge"))
                {
                    if (piece.getZ() == 0)
                    {
                        piece.setZ(piece.getX());
                        piece.setX(0);
                    }
                    else
                    {
                        piece.setX(-1*piece.getZ());
                        piece.setZ(0);
                    }
                }
                else if (piece.getType().equals("Corner"))
                {
                    if (piece.getX()*piece.getZ() == 1)
                        piece.setX(-1*piece.getX());
                    else
                        piece.setZ(-1*piece.getZ());
                }
                for (String[] color: piece.getColors())
                {
                    if (color[1].equals("+x"))
                        color[1] = "+z";
                    else if (color[1].equals("+z"))
                        color[1] = "-x";
                    else if (color[1].equals("-x"))
                        color[1] = "-z";
                    else if (color[1].equals("-z"))
                        color[1] = "+x";
                }
            }
        }
        undo.push("U");
    }

    public void UPrime()
    {
        for (int i = 0; i < 3; i ++)
        {
            U();
            undo.pop();
        }
        undo.push("U'");
    }

    public void U2()
    {
        for (int i = 0; i < 2; i ++)
        {
            U();
            undo.pop();
        }
        undo.push("U2");
    }

    public void F()
    {
        for (Piece piece: pieces)
        {
            if (piece.getZ() == 1)
            {
                if (piece.getType().equals("Edge"))
                {
                    if (piece.getX() == 0)
                    {
                        piece.setX(piece.getY());
                        piece.setY(0);
                    }
                    else
                    {
                        piece.setY(-1*piece.getX());
                        piece.setX(0);
                    }
                }
                else if (piece.getType().equals("Corner"))
                {
                    if (piece.getX()*piece.getY() == 1)
                        piece.setY(-1*piece.getY());
                    else
                        piece.setX(-1*piece.getX());
                }
                for (String[] color: piece.getColors())
                {
                    if (color[1].equals("+y"))
                        color[1] = "+x";
                    else if (color[1].equals("+x"))
                        color[1] = "-y";
                    else if (color[1].equals("-y"))
                        color[1] = "-x";
                    else if (color[1].equals("-x"))
                        color[1] = "+y";
                }
            }
        }
        undo.push("F");
    }

    public void FPrime()
    {
        for (int i = 0; i < 3; i ++)
        {
            F();
            undo.pop();
        }
        undo.push("F'");
    }

    public void F2()
    {
        for (int i = 0; i < 2; i ++)
        {
            F();
            undo.pop();
        }
        undo.push("F2");
    }

    public void L()
    {
        for (Piece piece: pieces)
        {
            if (piece.getX() == -1)
            {
                if (piece.getType().equals("Edge"))
                {
                    if (piece.getZ() == 0)
                    {
                        piece.setZ(piece.getY());
                        piece.setY(0);
                    }
                    else
                    {
                        piece.setY(-1*piece.getZ());
                        piece.setZ(0);
                    }
                }
                else if (piece.getType().equals("Corner"))
                {
                    if (piece.getY()*piece.getZ() == 1)
                        piece.setY(-1*piece.getY());
                    else
                        piece.setZ(-1*piece.getZ());
                }
                for (String[] color: piece.getColors())
                {
                    if (color[1].equals("+y"))
                        color[1] = "+z";
                    else if (color[1].equals("+z"))
                        color[1] = "-y";
                    else if (color[1].equals("-y"))
                        color[1] = "-z";
                    else if (color[1].equals("-z"))
                        color[1] = "+y";
                }
            }
        }
        undo.push("L");
    }

    public void LPrime()
    {
        for (int i = 0; i < 3; i ++)
        {
            L();
            undo.pop();
        }
        undo.push("L'");
    }

    public void L2()
    {
        for (int i = 0; i < 2; i ++)
        {
            L();
            undo.pop();
        }
        undo.push("L2");
    }

    public void D()
    {
        for (Piece piece: pieces)
        {
            if (piece.getY() == -1)
            {
                if (piece.getType().equals("Edge"))
                {
                    if (piece.getX() == 0)
                    {
                        piece.setX(piece.getZ());
                        piece.setZ(0);
                    }
                    else
                    {
                        piece.setZ(-1*piece.getX());
                        piece.setX(0);
                    }
                }
                else if (piece.getType().equals("Corner"))
                {
                    if (piece.getX()*piece.getZ() == 1)
                        piece.setZ(-1*piece.getZ());
                    else
                        piece.setX(-1*piece.getX());
                }
                for (String[] color: piece.getColors())
                {
                    if (color[1].equals("+z"))
                        color[1] = "+x";
                    else if (color[1].equals("+x"))
                        color[1] = "-z";
                    else if (color[1].equals("-z"))
                        color[1] = "-x";
                    else if (color[1].equals("-x"))
                        color[1] = "+z";
                }
            }
        }
        undo.push("D");
    }

    public void DPrime()
    {
        for (int i = 0; i < 3; i ++)
        {
            D();
            undo.pop();
        }
        undo.push("D'");
    }

    public void D2()
    {
        for (int i = 0; i < 2; i ++)
        {
            D();
            undo.pop();
        }
        undo.push("D2");
    }

    public void B()
    {
        for (Piece piece: pieces)
        {
            if (piece.getZ() == -1)
            {
                if (piece.getType().equals("Edge"))
                {
                    if (piece.getY() == 0)
                    {
                        piece.setY(piece.getX());
                        piece.setX(0);
                    }
                    else
                    {
                        piece.setX(-1*piece.getY());
                        piece.setY(0);
                    }
                }
                else if (piece.getType().equals("Corner"))
                {
                    if (piece.getX()*piece.getY() == 1)
                        piece.setX(-1*piece.getX());
                    else
                        piece.setY(-1*piece.getY());
                }
                for (String[] color: piece.getColors())
                {
                    if (color[1].equals("+x"))
                        color[1] = "+y";
                    else if (color[1].equals("+y"))
                        color[1] = "-x";
                    else if (color[1].equals("-x"))
                        color[1] = "-y";
                    else if (color[1].equals("-y"))
                        color[1] = "+x";
                }
            }
        }
        undo.push("B");
    }

    public void BPrime()
    {
        for (int i = 0; i < 3; i ++)
        {
            B();
            undo.pop();
        }
        undo.push("B'");
    }

    public void B2()
    {
        for (int i = 0; i < 2; i ++)
        {
            B();
            undo.pop();
        }
        undo.push("B2");
    }

    public void M()
    {
        for (Piece piece: pieces)
        {
            if (piece.getX() == 0)
            {
                if (piece.getType().equals("Center"))
                {
                    if (piece.getZ() == 0)
                    {
                        piece.setZ(piece.getY());
                        piece.setY(0);
                    }
                    else
                    {
                        piece.setY(-1*piece.getZ());
                        piece.setZ(0);
                    }
                }
                else if (piece.getType().equals("Edge"))
                {
                    if (piece.getY()*piece.getZ() == 1)
                        piece.setY(-1*piece.getY());
                    else
                        piece.setZ(-1*piece.getZ());
                }
                for (String[] color: piece.getColors())
                {
                    if (color[1].equals("+y"))
                        color[1] = "+z";
                    else if (color[1].equals("+z"))
                        color[1] = "-y";
                    else if (color[1].equals("-y"))
                        color[1] = "-z";
                    else if (color[1].equals("-z"))
                        color[1] = "+y";
                }
            }
        }
        orientation = "" + colorScheme.charAt((colorScheme.indexOf(orientation.charAt(1)) + 3)%6) + orientation.charAt(0);
        undo.push("M");
    }

    public void MPrime()
    {
        for (int i = 0; i < 3; i ++)
        {
            M();
            undo.pop();
        }
        undo.push("M'");
    }

    public void M2()
    {
        for (int i = 0; i < 2; i ++)
        {
            M();
            undo.pop();
        }
        undo.push("M2");
    }

    public void E()
    {
        for (Piece piece: pieces)
        {
            if (piece.getY() == 0)
            {
                if (piece.getType().equals("Center"))
                {
                    if (piece.getX() == 0)
                    {
                        piece.setX(piece.getZ());
                        piece.setZ(0);
                    }
                    else
                    {
                        piece.setZ(-1*piece.getX());
                        piece.setX(0);
                    }
                }
                else if (piece.getType().equals("Edge"))
                {
                    if (piece.getX()*piece.getZ() == 1)
                        piece.setZ(-1*piece.getZ());
                    else
                        piece.setX(-1*piece.getX());
                }
                for (String[] color: piece.getColors())
                {
                    if (color[1].equals("+z"))
                        color[1] = "+x";
                    else if (color[1].equals("+x"))
                        color[1] = "-z";
                    else if (color[1].equals("-z"))
                        color[1] = "-x";
                    else if (color[1].equals("-x"))
                        color[1] = "+z";
                }
            }
        }
        String temp = colorScheme.replace("" + colorScheme.charAt((colorScheme.indexOf(orientation.charAt(0)) + 3)%6), "").replace("" + orientation.charAt(0), "");
        orientation = "" + orientation.charAt(0) + temp.charAt((temp.indexOf(orientation.charAt(1)) + 3 - 2*(colorScheme.indexOf(orientation.charAt(0))%2))%4);
        undo.push("E");
    }

    public void EPrime()
    {
        for (int i = 0; i < 3; i ++)
        {
            E();
            undo.pop();
        }
        undo.push("E'");
    }

    public void E2()
    {
        for (int i = 0; i < 2; i ++)
        {
            E();
            undo.pop();
        }
        undo.push("E2");
    }

    public void S()
    {
        for (Piece piece: pieces)
        {
            if (piece.getZ() == 0)
            {
                if (piece.getType().equals("Center"))
                {
                    if (piece.getX() == 0)
                    {
                        piece.setX(piece.getY());
                        piece.setY(0);
                    }
                    else
                    {
                        piece.setY(-1*piece.getX());
                        piece.setX(0);
                    }
                }
                else if (piece.getType().equals("Edge"))
                {
                    if (piece.getX()*piece.getY() == 1)
                        piece.setY(-1*piece.getY());
                    else
                        piece.setX(-1*piece.getX());
                }
                for (String[] color: piece.getColors())
                {
                    if (color[1].equals("+y"))
                        color[1] = "+x";
                    else if (color[1].equals("+x"))
                        color[1] = "-y";
                    else if (color[1].equals("-y"))
                        color[1] = "-x";
                    else if (color[1].equals("-x"))
                        color[1] = "+y";
                }
            }
        }
        String temp = colorScheme.replace("" + colorScheme.charAt((colorScheme.indexOf(orientation.charAt(1)) + 3)%6), "").replace("" + orientation.charAt(1), "");
        orientation = "" + temp.charAt((temp.indexOf(orientation.charAt(0)) + 1 + 2*(colorScheme.indexOf(orientation.charAt(1))%2))%4) + orientation.charAt(1);
        undo.push("S");
    }

    public void SPrime()
    {
        for (int i = 0; i < 3; i ++)
        {
            S();
            undo.pop();
        }
        undo.push("S'");
    }

    public void S2()
    {
        for (int i = 0; i < 2; i ++)
        {
            S();
            undo.pop();
        }
        undo.push("S2");
    }

    public void x()
    {
        R();
        MPrime();
        LPrime();
        for (int i = 0; i < 3; i ++)
            undo.pop();
        undo.push("x");
    }

    public void xPrime()
    {
        RPrime();
        M();
        L();
        for (int i = 0; i < 3; i ++)
            undo.pop();
        undo.push("x'");
    }

    public void x2()
    {
        for (int i = 0; i < 2; i ++)
        {
            x();
            undo.pop();
        }
        undo.push("x2");
    }

    public void y()
    {
        U();
        EPrime();
        DPrime();
        for (int i = 0; i < 3; i ++)
            undo.pop();
        undo.push("y");
    }

    public void yPrime()
    {
        UPrime();
        E();
        D();
        for (int i = 0; i < 3; i ++)
            undo.pop();
        undo.push("y'");
    }

    public void y2()
    {
        for (int i = 0; i < 2; i ++)
        {
            y();
            undo.pop();
        }
        undo.push("y2");
    }

    public void z()
    {
        F();
        S();
        BPrime();
        for (int i = 0; i < 3; i ++)
            undo.pop();
        undo.push("z");
    }

    public void zPrime()
    {
        FPrime();
        SPrime();
        B();
        for (int i = 0; i < 3; i ++)
            undo.pop();
        undo.push("z'");
    }

    public void z2()
    {
        for (int i = 0; i < 2; i ++)
        {
            z();
            undo.pop();
        }
        undo.push("z2");
    }

    public void r()
    {
        R();
        MPrime();
        for (int i = 0; i < 2; i ++)
            undo.pop();
        undo.push("r");
    }

    public void rPrime()
    {
        RPrime();
        M();
        for (int i = 0; i < 2; i ++)
            undo.pop();
        undo.push("r'");
    }

    public void r2()
    {
        for (int i = 0; i < 2; i ++)
        {
            r();
            undo.pop();
        }
        undo.push("r2");
    }

    public void u()
    {
        U();
        EPrime();
        for (int i = 0; i < 2; i ++)
            undo.pop();
        undo.push("u");
    }
    
    public void uPrime()
    {
        UPrime();
        E();
        for (int i = 0; i < 2; i ++)
            undo.pop();
        undo.push("u'");
    }

    public void u2()
    {
        for (int i = 0; i < 2; i ++)
        {
            u();
            undo.pop();
        }
        undo.push("u2");
    }

    public void f()
    {
        F();
        S();
        for (int i = 0; i < 2; i ++)
            undo.pop();
        undo.push("f");
    }

    public void fPrime()
    {
        FPrime();
        SPrime();
        for (int i = 0; i < 2; i ++)
            undo.pop();
        undo.push("f'");
    }

    public void f2()
    {
        for (int i = 0; i < 2; i ++)
        {    
            f();
            undo.pop();
        }
        undo.push("f2");
    }

    public void l()
    {
        L();
        M();
        for (int i = 0; i < 2; i ++)
            undo.pop();
        undo.push("l");
    }

    public void lPrime()
    {
        LPrime();
        MPrime();
        for (int i = 0; i < 2; i ++)
            undo.pop();
        undo.push("l'");
    }

    public void l2()
    {
        for (int i = 0; i < 2; i ++)
        {
            l();
            undo.pop();
        }
        undo.push("l2");
    }

    public void d()
    {
        D();
        E();
        for (int i = 0; i < 2; i ++)
            undo.pop();
        undo.push("d");
    }

    public void dPrime()
    {
        DPrime();
        EPrime();
        for (int i = 0; i < 2; i ++)
            undo.pop();
        undo.push("d'");
    }

    public void d2()
    {
        for (int i = 0; i < 2; i ++)
        {
            d();
            undo.pop();
        }
        undo.push("d2");
    }

    public void b()
    {
        B();
        SPrime();
        for (int i = 0; i < 2; i ++)
            undo.pop();
        undo.push("b");
    }

    public void bPrime()
    {
        BPrime();
        S();
        for (int i = 0; i < 2; i ++)
            undo.pop();
        undo.push("b'");
    }

    public void b2()
    {
        for (int i = 0; i < 2; i ++)
        {
            b();
            undo.pop();
        }
        undo.push("b2");
    }
}