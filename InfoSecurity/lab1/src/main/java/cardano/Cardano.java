package cardano;

public class Cardano {

    public static String encrypt(String source, boolean[][] grid) {
        var size = getSize(grid);

        var table = encryptToTable(source, grid);

        if (size % 2 != 0) {
            int center = size / 2;
            table[center][center] = ' ';
        }

        return tableToString(grid, table);
    }

    public static String decrypt(String encrypted, boolean[][] grid) {
        var size = getSize(grid);
        var table = stringToTable(encrypted, size);

        var sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            sb.append(readFromTable(table, grid));
            grid = moveRight(grid);
        }
        return sb.toString();
    }

    static boolean[][] moveRight(boolean[][] grid) {
        int size = getSize(grid);
        boolean[][] result = new boolean[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (grid[i][j]) {
                    result[j][size - i - 1] = true;
                }
            }
        }
        return result;
    }

    private static int getSize(boolean[][] grid) {
        return grid.length;
    }

    private static char[][] encryptToTable(String source, boolean[][] grid) {
        var size = getSize(grid);
        char[][] table = new char[size][size];

        char[] chars = source.toCharArray();
        int writtenCount = 0;
        for (int i = 0; i < 4; i++) {
            writtenCount = writeToTable(chars, writtenCount, table, grid);
            grid = moveRight(grid);
        }
        return table;
    }

    private static String tableToString(boolean[][] grid, char[][] table) {
        var sb = new StringBuilder();

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                sb.append(table[i][j]);
            }
        }

        return sb.toString();
    }


    private static int writeToTable(char[] chars, int writtenCount, char[][] table, boolean[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                if (grid[i][j]) {
                    table[i][j] = chars[writtenCount++];
                }
            }
        }
        return writtenCount;
    }

    private static char[][] stringToTable(String encrypted, int size) {
        var chars = encrypted.toCharArray();
        char[][] table = new char[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                table[i][j] = chars[i * size + j];
            }
        }
        return table;
    }

    private static StringBuilder readFromTable(char[][] table, boolean[][] grid) {
        var sb = new StringBuilder();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                if (grid[i][j]) {
                    sb.append(table[i][j]);
                }
            }
        }
        return sb;
    }

}
