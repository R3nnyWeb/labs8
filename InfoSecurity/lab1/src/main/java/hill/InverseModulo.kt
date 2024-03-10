package hill

import java.lang.IllegalArgumentException

class InverseModulo {
    // Global Variables
    var x = 0
    var y = 0

    // Function for extended Euclidean Algorithm
    fun gcdExtended(a: Int, b: Int): Int {

        // Base Case
        if (a == 0) {
            x = 0
            y = 1
            return b
        }

        // To store results of recursive call
        val gcd = gcdExtended(b % a, a)
        val x1 = x
        val y1 = y

        // Update x and y using results of recursive
        // call
        val tmp = b / a
        x = y1 - tmp * x1
        y = x1
        return gcd
    }

    fun modInverse(A: Int, M: Int) : Int {
        val g = gcdExtended(A, M)
        if (g != 1) {
            throw IllegalArgumentException("Для данной матрицы модульной обратной матрицы не существует")
        } else {

            // m is added to handle negative x
            val res = (x % M + M) % M
            return res;
        }
    }


}