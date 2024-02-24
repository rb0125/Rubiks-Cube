public class AppTest1
{
    public AppTest1()
    {
        Cube cube = new Cube();
        System.out.println(cube.isSolved());
        
        // Scramble
        cube.D2();
        cube.U();
        cube.F2();
        cube.U2();
        cube.F2();
        cube.R2();
        cube.B2();
        cube.U();
        cube.B2();
        cube.F2();
        cube.R2();
        cube.UPrime();
        cube.LPrime();
        cube.B2();
        cube.R();
        cube.DPrime();
        cube.L();
        cube.RPrime();
        cube.BPrime();
        cube.L2();
        cube.R2();
        System.out.println(cube.isSolved());

        // Cross
        cube.L2();
        cube.DPrime();
        cube.R2();
        cube.U();
        cube.RPrime();
        cube.F();
        cube.R();
        cube.DPrime();
        System.out.println(cube.isSolved());

        // F2L
        cube.UPrime();
        cube.RPrime();
        cube.U();
        cube.R();
        cube.U2();
        cube.B();
        cube.U();
        cube.BPrime();
        cube.DPrime();
        cube.LPrime();
        cube.U2();
        cube.L();
        cube.D();
        cube.U();
        cube.LPrime();
        cube.U();
        cube.L();
        cube.UPrime();
        cube.LPrime();
        cube.UPrime();
        cube.L();
        cube.L();
        cube.UPrime();
        cube.LPrime();
        cube.BPrime();
        cube.U();
        cube.B();
        cube.U2();
        cube.BPrime();
        cube.UPrime();
        cube.B();
        System.out.println(cube.isSolved());

        // OLL
        cube.F();
        cube.R();
        cube.U();
        cube.RPrime();
        cube.UPrime();
        cube.FPrime();
        cube.U2();
        cube.F();
        cube.U();
        cube.R();
        cube.UPrime();
        cube.RPrime();
        cube.FPrime();
        cube.R();
        cube.U2();
        cube.R2();
        cube.UPrime();
        cube.R2();
        cube.UPrime();
        cube.R2();
        cube.U2();
        cube.R();
        System.out.println(cube.isSolved());

        // PLL
        cube.LPrime();
        cube.U();
        cube.LPrime();
        cube.UPrime();
        cube.LPrime();
        cube.UPrime();
        cube.LPrime();
        cube.U();
        cube.L();
        cube.U();
        cube.L2();
        cube.U2();
        cube.F();
        cube.R();
        cube.UPrime();
        cube.RPrime();
        cube.UPrime();
        cube.R();
        cube.U();
        cube.RPrime();
        cube.FPrime();
        cube.R();
        cube.U();
        cube.RPrime();
        cube.UPrime();
        cube.RPrime();
        cube.F();
        cube.R();
        cube.FPrime();
        System.out.println(cube.isSolved());
    }
}
