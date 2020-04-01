package tools;

/** 
 * Clase Var
 * @author Edson Manuel Carballo Vera
 * @version 1.1.1
 * 
*/
public class Var {
    /**
     * Atributo privado de tipo Object donde se guarda el dato de la instancia
     * @since 1.0.0
     */
    private Object var = null;

    /** 
     * Constructor vacio
     * <pre>
     * Crea una nueva instancia de la clase Var sin ningun valor almacenado.
     * Por defecto, el valor de {@link #var} es null.
     * Si se desea inicializar la instancia con un valor, usar {@link #Var(Object)}
     * </pre>
     * @see {@link #Var(Object)}
     * @since 1.0.0
    */
    public Var() {
    }

    /**
     * Constructor con inicializador
     * <pre>
     * Crea una nueva instancia de la clase Var con el dato indicado.
     * Se establece el valor de {@link #var} con el parametro indicado.
     * Si se desea crear una instancia sin valor, usar {@link #Var()}
     * </pre>
     * @param var El valor para inicializar {@link #var}
     * @see {@link #Var()}
     * @since 1.0.0
     */
    public Var(Object var) {
        this.var = var;
    }

    /**
     * Setter del valor de la instancia
     * <pre>
     * Establece el valor de {@link #var} con el dato del parametro.
     * </pre>
     * @param var El valor que se asignara a {@link #var}
     * @since 1.0.0
     */
    public void set(Object var) {
        this.var = var;
    }

    /**
     * Getter generico
     * <pre>
     * Retorna un Object con el valor almacenado en {@link #var}
     * Es necesario castearse
     * Si se desea obtener el valor de {@link #var} casteado a algun tipo primitivo usar getTIPO
     * </pre>
     * @return Object. Valor almacenado casteado como Object
     * @see {@link #getChar()}
     * @see {@link #getDouble()}
     * @see {@link #getFloat()}
     * @see {@link #getInt()}
     * @see {@link #getString()}
     * @since 1.0.0
     */
    public Object get() {
        return this.var;
    }

    /**
     * Getter con cast para entero
     * <pre>
     * Retorna un Int con el valor almacenado en {@link #var}
     * Si no se puede castear, retornara 0.
     * </pre>
     * @return Int. Valor almacenado casteado como int
     * @since 1.1.0
     */
    public int getInt() {
        try {
            if (this.var instanceof Integer)
                return ((Integer) this.var).intValue();
            if (this.var instanceof Float)
                return ((Float) this.var).intValue();
            if (this.var instanceof Double)
                return ((Double) this.var).intValue();
            if (this.var instanceof Short)
                return ((Short) this.var).intValue();
            if (this.var instanceof Long)
                return ((Long) this.var).intValue();
        } catch (Exception e) {
            P.err(e.getMessage());
        }
        return 0;
    }

    /**
     * Getter con cast para flotante
     * <pre>
     * Retorna un Float con el valor almacenado en {@link #var}
     * Si no se puede castear, retornara 0.
     * </pre>
     * @return Valor almacenado casteado como flotante
     * @since 1.1.0
     */
    public float getFloat() {
        try {
            if (this.var instanceof Integer)
                return ((Integer) this.var).floatValue();
            if (this.var instanceof Float)
                return ((Float) this.var).floatValue();
            if (this.var instanceof Double)
                return ((Double) this.var).floatValue();
            if (this.var instanceof Short)
                return ((Short) this.var).floatValue();
            if (this.var instanceof Long)
                return ((Long) this.var).floatValue();
        } catch (Exception e) {
            P.err(e.getMessage());
        }
        return 0;
    }

    /**
     * Getter con cast para doble
     * <pre>
     * Retorna un Double con el valor almacenado en {@link #var}
     * Si no se puede castear, retornara 0.
     * </pre>
     * @return Valor almacenado casteado como doble
     * @since 1.1.0
     */
    public double getDouble() {
        try {
            if (this.var instanceof Integer)
                return ((Integer) this.var).doubleValue();
            if (this.var instanceof Float)
                return ((Float) this.var).doubleValue();
            if (this.var instanceof Double)
                return ((Double) this.var).doubleValue();
            if (this.var instanceof Short)
                return ((Short) this.var).doubleValue();
            if (this.var instanceof Long)
                return ((Long) this.var).doubleValue();
        } catch (Exception e) {
            P.err(e.getMessage());
        }
        return 0;
    }
    
    /**
     * Getter con cast para Short
     * <pre>
     * Retorna un Short con el valor almacenado en {@link #var}
     * Si no se puede castear, retonara 0.
     * </pre>
     * @return Short. Valor almacenado casteado como short.
     */
    public short getShort() {
        try {
            if (this.var instanceof Integer)
                return ((Integer) this.var).shortValue();
            if (this.var instanceof Float)
                return ((Float) this.var).shortValue();
            if (this.var instanceof Double)
                return ((Double) this.var).shortValue();
            if (this.var instanceof Short)
                return ((Short) this.var).shortValue();
            if (this.var instanceof Long)
                return ((Long) this.var).shortValue();
        } catch (Exception e) {
            P.err(e.getMessage());
        }
        return 0;
    }

    /**
     * Geter con cast para Long
     * <pre>
     * Retorna un Long con el valor almacenado en {@link #var}
     * Si no se puede castear, retornara 0.
     * </pre>
     * @return Long. Valor almacenado casteado como long.
     */
    public long getLong() {
        try {
            if (this.var instanceof Integer)
                return ((Integer) this.var).longValue();
            if (this.var instanceof Float)
                return ((Float) this.var).longValue();
            if (this.var instanceof Double)
                return ((Double) this.var).longValue();
            if (this.var instanceof Short)
                return ((Short) this.var).longValue();
            if (this.var instanceof Long)
                return ((Long) this.var).longValue();
        } catch (Exception e) {
            P.err(e.getMessage());
        }
        return 0;
    }

    /**
     * Getter con cast para caracter
     * <pre>
     * Retorna un Char con el valor almacenado en {@link #var}
     * Si no se puede castear, retornara \n.
     * </pre>
     * @return Valor almacenado casteado como Char
     * @since 1.1.0
     */
    public char getChar() {
        try {
            return (char) this.var;
        } catch (Exception e) {
            P.err(e.getMessage());
        }
        return '\n';
    }

    /**
     * Getter con cast para cadenas
     * <pre>
     * Retorna un String con el valor almacenado en {@link #var}
     * Si no se puede castear, retornara null.
     * </pre>
     * @return Valor almacenado casteado como cadena
     * @since 1.1.0
     */
    public String getString() {
        try {
            return (String) this.var;
        } catch (Exception e) {
            P.err(e.getMessage());
        }
        return null;
    }

    /**
     * Definidor del tipo de valor almacenado
     * <pre>
     * Retorna una cadena con el tipo de dato almacenado en {@link #var}
     * Si el valor de {@link #var} no esta definido, retornara null.
     * </pre>
     * @return Cadena con el tipo del valor almacenado
     * @since 1.1.1
     */
    public String type() {
        if (this.var == null)
            return "null";
        return this.var.getClass().getSimpleName();
    }

    public String toString() {
        return this.var.toString();
    }
}