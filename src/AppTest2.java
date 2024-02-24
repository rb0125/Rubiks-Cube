public class AppTest2
{
    public AppTest2()
    {
        Cube cube = new Cube();
        System.out.println(cube.isSolved());
        System.out.println(cube.getOrientation());

        for (int i = 0; i < 2; i ++)
        {
            for (int j = 0; j < 3; j ++)
            {
                for (int k = 0; k < 4; k ++)
                {
                    cube.U();
                    cube.MPrime();
                }
                System.out.print(cube.getOrientation());
                cube.y();
                cube.zPrime();
                System.out.println(" " + cube.getOrientation());
            }
            System.out.println(cube.isSolved());
        }
    }
}
