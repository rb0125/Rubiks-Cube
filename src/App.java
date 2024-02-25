public class App
{
    public static void main(String[] args) throws Exception
    {
        Cube cube = new Cube();
        System.out.println(cube.isSolved());
        System.out.println(cube.getOrientation());

        // for (int i = 0; i < 4; i ++)
        // {
        //     cube.b();
        //     System.out.println(cube.isSolved());
        //     System.out.println(cube.getOrientation());
        // }

        // cube.b();
        // System.out.println(cube.isSolved());
        // cube.bPrime();
        // System.out.println(cube.isSolved());

        // for (int i = 0; i < 15; i ++)
        // {
        //     cube.l();
        //     cube.u();
        //     cube.lPrime();
        //     cube.uPrime();
        //     cube.lPrime();
        //     cube.b();
        //     cube.l();
        //     cube.bPrime();
        //     System.out.println(cube.isSolved());
        // }
    }
}
