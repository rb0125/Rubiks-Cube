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
    public Piece[] getPieces()      {   return pieces;  }

    public Piece getPiece(String name)
    {   
        for (Piece piece: pieces)
            if (piece.getName().equals(name))
                return piece;
        return null;
    }

    public Piece getPiece(int[] coordinates)
    {
        for (Piece piece: pieces)
            if (Arrays.equals(piece.getCoordinates(), coordinates))
                return piece;
        return null;
    }

    public boolean setOrientation(String newOrientation)
    {
        if (newOrientation == null || newOrientation.length() != 2)
            return false;
        char a = newOrientation.charAt(0), b = newOrientation.charAt(1);
        if (!colorScheme.contains("" + a) || !colorScheme.contains("" + b) || (colorScheme.indexOf("" + a) - colorScheme.indexOf("" + b))%3 == 0)
            return false;
        char c;
        while ((c = orientation.charAt(0)) != a && c != b)
            x(false);
        if (c == a)
            while (orientation.charAt(1) != b)
                y(false);
        else
        {
            xPrime(false);
            while (orientation.charAt(0) != a)
                z(false);
        }
        return true;
    }

    public boolean undo()
    {
        if (undo.isEmpty())
            return false;
        String undoMove = undo.pop();
        redo.push(undoMove);
        if (undoMove.contains("'"))
            undoMove = undoMove.replace("'", "");
        else if (!undoMove.contains("2"))
            undoMove += "'";
        move(undoMove);
        undo.pop();
        return true;
    }

    public boolean redo()
    {
        if (redo.isEmpty())
            return false;
        String move = redo.pop();
        undo.push(move);
        move(move);
        undo.pop();
        return true;
    }

    public boolean move(String moves)
    {
        if (moves == null || !balancedParenthesis(moves))
            return false;
        if (moves.length() == 0)
            return true;
        moves = moves.trim().replaceAll(" {2,}", " ").replaceAll("[\\(\\)]+", "");
        if (!moves.matches("([RUFLDBMESxyzrufldb][2']? )*[RUFLDBMESxyzrufldb][2']?"))
            return false;
        String[] moveStrings = moves.split(" ");
        for (int i = 0; i < moveStrings.length; i ++)
        {
            switch(moveStrings[i])
            {
                case "R": R(true);
                    break;
                case "R'": RPrime(true);
                    break;
                case "R2": R2(true);
                    break;
                case "U": U(true);
                    break;
                case "U'": UPrime(true);
                    break;
                case "U2": U2(true);
                    break;
                case "F": F(true);
                    break;
                case "F'": FPrime(true);
                    break;
                case "F2": F2(true);
                    break;
                case "L": L(true);
                    break;
                case "L'": LPrime(true);
                    break;
                case "L2": L2(true);
                    break;
                case "D": D(true);
                    break;
                case "D'": DPrime(true);
                    break;
                case "D2": D2(true);
                    break;
                case "B": B(true);
                    break;
                case "B'": BPrime(true);
                    break;
                case "B2": B2(true);
                    break;
                case "M": M(true);
                    break;
                case "M'": MPrime(true);
                    break;
                case "M2": M2(true);
                    break;
                case "E": E(true);
                    break;
                case "E'": EPrime(true);
                    break;
                case "E2": E2(true);
                    break;
                case "S": S(true);
                    break;
                case "S'": SPrime(true);
                    break;
                case "S2": S2(true);
                    break;
                case "x": x(true);
                    break;
                case "x'": xPrime(true);
                    break;
                case "x2": x2(true);
                    break;
                case "y": y(true);
                    break;
                case "y'": yPrime(true);
                    break;
                case "y2": y2(true);
                    break;
                case "z": z(true);
                    break;
                case "z'": zPrime(true);
                    break;
                case "z2": z2(true);
                    break;
                case "r": r(true);
                    break;
                case "r'": rPrime(true);
                    break;
                case "r2": r2(true);
                    break;
                case "u": u(true);
                    break;
                case "u'": uPrime(true);
                    break;
                case "u2": u2(true);
                    break;
                case "f": f(true);
                    break;
                case "f'": fPrime(true);
                    break;
                case "f2": f2(true);
                    break;
                case "l": l(true);
                    break;
                case "l'": lPrime(true);
                    break;
                case "l2": l2(true);
                    break;
                case "d": d(true);
                    break;
                case "d'": dPrime(true);
                    break;
                case "d2": d2(true);
                    break;
                case "b": b(true);
                    break;
                case "b'": bPrime(true);
                    break;
                case "b2": b2(true);
                    break;
                default:
                    for (int j = 0; j < i; j ++)
                        undo();
                    return false;
            }
        }
        return true;
    }

    private boolean balancedParenthesis(String s)
    {
        int count = 0;
        for (int i = 0; i < s.length(); i ++)
        {
            char c = s.charAt(i);
            if (c == '(')
                count ++;
            else if (c == ')')
                count --;
            if (count < 0)
                return false;
        }
        return count == 0;
    }

    public void reset()
    {
        initialize();
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

    public void R()         {   R(true);    }
    public void RPrime()    {   RPrime(true);    }
    public void R2()        {   R2(true);    }
    public void U()         {   U(true);    }
    public void UPrime()    {   UPrime(true);    }
    public void U2()        {   U2(true);    }
    public void F()         {   F(true);    }
    public void FPrime()    {   FPrime(true);    }
    public void F2()        {   F2(true);    }
    public void L()         {   L(true);    }
    public void LPrime()    {   LPrime(true);    }
    public void L2()        {   L2(true);    }
    public void D()         {   D(true);    }
    public void DPrime()    {   DPrime(true);    }
    public void D2()        {   D2(true);    }
    public void B()         {   B(true);    }
    public void BPrime()    {   BPrime(true);    }
    public void B2()        {   B2(true);    }
    public void M()         {   M(true);    }
    public void MPrime()    {   MPrime(true);    }
    public void M2()        {   M2(true);    }
    public void E()         {   E(true);    }
    public void EPrime()    {   EPrime(true);    }
    public void E2()        {   E2(true);    }
    public void S()         {   S(true);    }
    public void SPrime()    {   SPrime(true);    }
    public void S2()        {   S2(true);    }
    public void x()         {   x(true);    }
    public void xPrime()    {   xPrime(true);    }
    public void x2()        {   x2(true);    }
    public void y()         {   y(true);    }
    public void yPrime()    {   yPrime(true);    }
    public void y2()        {   y2(true);    }
    public void z()         {   z(true);    }
    public void zPrime()    {   zPrime(true);    }
    public void z2()        {   z2(true);    }
    public void r()         {   r(true);    }
    public void rPrime()    {   rPrime(true);    }
    public void r2()        {   r2(true);    }
    public void u()         {   u(true);    }
    public void uPrime()    {   uPrime(true);    }
    public void u2()        {   u2(true);    }
    public void f()         {   f(true);    }
    public void fPrime()    {   fPrime(true);    }
    public void f2()        {   f2(true);    }
    public void l()         {   l(true);    }
    public void lPrime()    {   lPrime(true);    }
    public void l2()        {   l2(true);    }
    public void d()         {   d(true);    }
    public void dPrime()    {   dPrime(true);    }
    public void d2()        {   d2(true);    }
    public void b()         {   b(true);    }
    public void bPrime()    {   bPrime(true);    }
    public void b2()        {   b2(true);    }

    private void R(boolean undoPush)
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
        if (undoPush)
            undo.push("R");
    }

    private void RPrime(boolean undoPush)
    {
        for (int i = 0; i < 3; i ++)
            R(false);
        if (undoPush)
            undo.push("R'");
    }

    private void R2(boolean undoPush)
    {
        for (int i = 0; i < 2; i ++)
            R(false);
        if (undoPush)
            undo.push("R2");
    }

    private void U(boolean undoPush)
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
        if (undoPush)
            undo.push("U");
    }

    private void UPrime(boolean undoPush)
    {
        for (int i = 0; i < 3; i ++)
            U(false);
        if (undoPush)
            undo.push("U'");
    }

    private void U2(boolean undoPush)
    {
        for (int i = 0; i < 2; i ++)
            U(false);
        if (undoPush)
            undo.push("U2");
    }

    private void F(boolean undoPush)
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
        if (undoPush)
            undo.push("F");
    }

    private void FPrime(boolean undoPush)
    {
        for (int i = 0; i < 3; i ++)
            F(false);
        if (undoPush)
            undo.push("F'");
    }

    private void F2(boolean undoPush)
    {
        for (int i = 0; i < 2; i ++)
            F(false);
        if (undoPush)
            undo.push("F2");
    }

    private void L(boolean undoPush)
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
        if (undoPush)
            undo.push("L");
    }

    private void LPrime(boolean undoPush)
    {
        for (int i = 0; i < 3; i ++)
            L(false);
        if (undoPush)
            undo.push("L'");
    }

    private void L2(boolean undoPush)
    {
        for (int i = 0; i < 2; i ++)
            L(false);
        if (undoPush)
            undo.push("L2");
    }

    private void D(boolean undoPush)
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
        if (undoPush)
            undo.push("D");
    }

    private void DPrime(boolean undoPush)
    {
        for (int i = 0; i < 3; i ++)
            D(false);
        if (undoPush)
            undo.push("D'");
    }

    private void D2(boolean undoPush)
    {
        for (int i = 0; i < 2; i ++)
            D(false);
        if (undoPush)
            undo.push("D2");
    }

    private void B(boolean undoPush)
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
        if (undoPush)
            undo.push("B");
    }

    private void BPrime(boolean undoPush)
    {
        for (int i = 0; i < 3; i ++)
            B(false);
        if (undoPush)
            undo.push("B'");
    }

    private void B2(boolean undoPush)
    {
        for (int i = 0; i < 2; i ++)
            B(false);
        if (undoPush)
            undo.push("B2");
    }

    private void M(boolean undoPush)
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
        if (undoPush)
            undo.push("M");
    }

    private void MPrime(boolean undoPush)
    {
        for (int i = 0; i < 3; i ++)
            M(false);
        if (undoPush)
            undo.push("M'");
    }

    private void M2(boolean undoPush)
    {
        for (int i = 0; i < 2; i ++)
            M(false);
        if (undoPush)
            undo.push("M2");
    }

    private void E(boolean undoPush)
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
        String topColor = "" + orientation.charAt(0), frontColor = "" + orientation.charAt(1);
        String temp = colorScheme.replace("" + colorScheme.charAt((colorScheme.indexOf(topColor) + 3)%6), "").replace(topColor, "");
        if (colorScheme.indexOf(frontColor)%2 == 1)
            temp = new StringBuilder(temp).reverse().toString();
        orientation = topColor + temp.charAt((temp.indexOf(frontColor) + 1)%4);
        if (undoPush)
            undo.push("E");
    }

    private void EPrime(boolean undoPush)
    {
        for (int i = 0; i < 3; i ++)
            E(false);
        if (undoPush)
            undo.push("E'");
    }

    private void E2(boolean undoPush)
    {
        for (int i = 0; i < 2; i ++)
            E(false);
        if (undoPush)
            undo.push("E2");
    }

    private void S(boolean undoPush)
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
        String topColor = "" + orientation.charAt(0), frontColor = "" + orientation.charAt(1);
        String temp = colorScheme.replace("" + colorScheme.charAt((colorScheme.indexOf(frontColor) + 3)%6), "").replace(frontColor, "");
        if (colorScheme.indexOf(frontColor)%2 == 1)
            temp = new StringBuilder(temp).reverse().toString();
        orientation = "" + temp.charAt((temp.indexOf(topColor) + 1)%4) + frontColor;
        if (undoPush)
            undo.push("S");
    }

    private void SPrime(boolean undoPush)
    {
        for (int i = 0; i < 3; i ++)
            S(false);
        if (undoPush)
            undo.push("S'");
    }

    private void S2(boolean undoPush)
    {
        for (int i = 0; i < 2; i ++)
            S(false);
        if (undoPush)
            undo.push("S2");
    }

    private void x(boolean undoPush)
    {
        R(false);
        MPrime(false);
        LPrime(false);
        if (undoPush)
            undo.push("x");
    }

    private void xPrime(boolean undoPush)
    {
        RPrime(false);
        M(false);
        L(false);
        if (undoPush)
            undo.push("x'");
    }

    private void x2(boolean undoPush)
    {
        for (int i = 0; i < 2; i ++)
            x(false);
        if (undoPush)
            undo.push("x2");
    }

    private void y(boolean undoPush)
    {
        U(false);
        EPrime(false);
        DPrime(false);
        if (undoPush)
            undo.push("y");
    }

    private void yPrime(boolean undoPush)
    {
        UPrime(false);
        E(false);
        D(false);
        if (undoPush)
            undo.push("y'");
    }

    private void y2(boolean undoPush)
    {
        for (int i = 0; i < 2; i ++)
            y(false);
        if (undoPush)
            undo.push("y2");
    }

    private void z(boolean undoPush)
    {
        F(false);
        S(false);
        BPrime(false);
        if (undoPush)
            undo.push("z");
    }

    private void zPrime(boolean undoPush)
    {
        FPrime(false);
        SPrime(false);
        B(false);
        if (undoPush)
            undo.push("z'");
    }

    private void z2(boolean undoPush)
    {
        for (int i = 0; i < 2; i ++)
            z(false);
        if (undoPush)
            undo.push("z2");
    }

    private void r(boolean undoPush)
    {
        R(false);
        MPrime(false);
        if (undoPush)
            undo.push("r");
    }

    private void rPrime(boolean undoPush)
    {
        RPrime(false);
        M(false);
        if (undoPush)
            undo.push("r'");
    }

    private void r2(boolean undoPush)
    {
        for (int i = 0; i < 2; i ++)
            r(false);
        if (undoPush)
            undo.push("r2");
    }

    private void u(boolean undoPush)
    {
        U(false);
        EPrime(false);
        if (undoPush)
            undo.push("u");
    }
    
    private void uPrime(boolean undoPush)
    {
        UPrime(false);
        E(false);
        if (undoPush)
            undo.push("u'");
    }

    private void u2(boolean undoPush)
    {
        for (int i = 0; i < 2; i ++)
            u(false);
        if (undoPush)
            undo.push("u2");
    }

    private void f(boolean undoPush)
    {
        F(false);
        S(false);
        if (undoPush)
            undo.push("f");
    }

    private void fPrime(boolean undoPush)
    {
        FPrime(false);
        SPrime(false);
        if (undoPush)
            undo.push("f'");
    }

    private void f2(boolean undoPush)
    {
        for (int i = 0; i < 2; i ++)
            f(false);
        if (undoPush)
            undo.push("f2");
    }

    private void l(boolean undoPush)
    {
        L(false);
        M(false);
        if (undoPush)
            undo.push("l");
    }

    private void lPrime(boolean undoPush)
    {
        LPrime(false);
        MPrime(false);
        if (undoPush)
            undo.push("l'");
    }

    private void l2(boolean undoPush)
    {
        for (int i = 0; i < 2; i ++)
            l(false);
        if (undoPush)
            undo.push("l2");
    }

    private void d(boolean undoPush)
    {
        D(false);
        E(false);
        if (undoPush)
            undo.push("d");
    }

    private void dPrime(boolean undoPush)
    {
        DPrime(false);
        EPrime(false);
        if (undoPush)
            undo.push("d'");
    }

    private void d2(boolean undoPush)
    {
        for (int i = 0; i < 2; i ++)
            d(false);
        if (undoPush)
            undo.push("d2");
    }

    private void b(boolean undoPush)
    {
        B(false);
        SPrime(false);
        if (undoPush)
            undo.push("b");
    }

    private void bPrime(boolean undoPush)
    {
        BPrime(false);
        S(false);
        if (undoPush)
            undo.push("b'");
    }

    private void b2(boolean undoPush)
    {
        for (int i = 0; i < 2; i ++)
            b(false);
        if (undoPush)
            undo.push("b2");
    }
}