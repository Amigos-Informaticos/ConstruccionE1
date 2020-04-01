package tools;

/**
 * Clase Mat
 * 
 * @author Edson Manuel Carballo Vera
 * @version 1.0.0
 */
public abstract class Mat {
    /**
     * Retorna un valor al azar ubicado de forma inclusiva entre los parametros min
     * y max
     * 
     * @param min El valor minimo que sera incluido en el rango de retorno
     * @param max El valor maximo que sera incluido en el rango de retorno
     * @return Un entero al azar entre el minimo y maximo
     * @since 1.0.0
     */
    public static int randInt(int min, int max) {
        return (int) ((Math.random() * max) + min);
    }

    /**
     * Retorna el resultado de elevar el primer parametro a la potencia indicada por
     * el segundo parametro
     * 
     * @param base     El numero a elevar
     * @param potencia La potencia
     * @return base ^ potencia
     * @since 1.0.0
     */
    public static double pow(double base, double potencia) {
        return Math.pow(base, potencia);
    }
}