package cardano;

public class Cardano {

    public static String encrypt(String source, boolean[][] grid) {
        var sb = new StringBuilder();
        //заполняем пробелами
        sb.append(" ".repeat(grid.length * grid.length));

        char[] chars = source.toCharArray();
        int writtenCount = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < grid.length; j++) {
                for (int k = 0; k < grid.length; k++) {
                    if (grid[j][k]) {
                        //записали на нужное место
                        sb.setCharAt(j * grid.length + k, chars[writtenCount++]);
                    }
                }
            }
            //повернули сетку
            grid = moveRight(grid);
        }
        return sb.toString();
    }

    public static String decrypt(String encrypted, boolean[][] grid) {
        var sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            //Чтение из таблицы по сетке
            for (int j = 0; j < grid.length; j++) {
                for (int k = 0; k < grid.length; k++) {
                    if (grid[j][k]) {
                        //записали на нужное место
                        sb.append(encrypted.charAt(j * grid.length + k));
                    }
                }
            }
            //поворот сетки
            grid = moveRight(grid);
        }
        return sb.toString();
    }

    static boolean[][] moveRight(boolean[][] grid) {
        boolean[][] result = new boolean[grid.length][grid.length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                if (grid[i][j]) {
                    //поворот на 90
                    if (grid[j][grid.length - i - 1]) throw new IllegalArgumentException("Сетка не валидна");
                    else result[j][grid.length - i - 1] = true;
                }
            }
        }
        return result;
    }

}
