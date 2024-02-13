package cardano;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardanoTest {


    @Test
    public void move_right_once() {
        var grid = sourceGrid();

        var rotated = new boolean[][]{
                {true, false, false, false, false},
                {false, true, false, false, true},
                {true, false, false, false, false},
                {false, false, true, false, true},
                {false, false, false, false, false},
        };

        grid = Cardano.moveRight(grid);

        assertEqualsMatrix(rotated, grid);
    }

    private void assertEqualsMatrix(boolean[][] rotated, boolean[][] grid) {
        for (int i = 0; i < rotated.length; i++) {
            assertArrayEquals(rotated[i], grid[i]);
        }
    }

    @Test
    public void move_right_4_times() {
        var grid = sourceGrid();


        for (int i = 0; i < 4; i++) {
            grid = Cardano.moveRight(grid);
        }

        assertEqualsMatrix(grid, sourceGrid());
    }

    @Test
    public void move_left_4_times() {
        var grid = sourceGrid();


        for (int i = 0; i < 4; i++) {
            grid = Cardano.moveLeft(grid);
        }

        assertEqualsMatrix(grid, sourceGrid());
    }

    @Test
    public void move_left_once() {
        var grid = sourceGrid();

        var rotated = new boolean[][]{
                {false, false, false, false, false},
                {true, false, true, false, false},
                {false, false, false, false, true},
                {true, false, false, true, false},
                {false, false, false, false, true},
        };

        grid = Cardano.moveLeft(grid);

        assertEqualsMatrix(rotated, grid);
    }

    private static boolean[][] sourceGrid() {
        return new boolean[][]{
                {false, true, false, true, false},
                {false, false, false, false, false},
                {false, false, false, true, false},
                {false, true, false, false, false},
                {true, false, true, false, false},
        };
    }


    @Test
    public void decrypt() {
        String source = "ППЕЕПОРИИАВС РВАЕЬНТНЬАМУ";

        boolean[][] grid = sourceGrid();
        for (int i = 0; i < 3; i++) {
            grid = Cardano.moveRight(grid);
        }

        var decrypted = Cardano.decrypt(source, grid);

        assertEquals("ПЕРЕНАПРАВЬТЕПИСЬМОИВАНУ", decrypted);
    }

    @Test
    public void encrypt() {
        String source = "ПЕРЕНАПРАВЬТЕПИСЬМОИВАНУ";

        boolean[][] grid = sourceGrid();

        var encrypted = Cardano.encrypt(source, grid);

        assertEquals("ППЕЕПОРИИАВС РВАЕЬНТНЬАМУ", encrypted);
    }

}
