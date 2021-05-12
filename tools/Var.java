package tools;


public class Var {
    private Object var = null;
    
    public Var() {
    }
    
    public Var(Object var) {
        this.var = var;
    }
    
    public void set(Object var) {
        this.var = var;
    }
    
    public Object get() {
        return this.var;
    }
    
    public int getInt() {
        try {
            if (this.var instanceof Integer)
                return (Integer) this.var;
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
    
    public float getFloat() {
        try {
            if (this.var instanceof Integer)
                return ((Integer) this.var).floatValue();
            if (this.var instanceof Float)
                return (Float) this.var;
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
    
    public double getDouble() {
        try {
            if (this.var instanceof Integer)
                return ((Integer) this.var).doubleValue();
            if (this.var instanceof Float)
                return ((Float) this.var).doubleValue();
            if (this.var instanceof Double)
                return (Double) this.var;
            if (this.var instanceof Short)
                return ((Short) this.var).doubleValue();
            if (this.var instanceof Long)
                return ((Long) this.var).doubleValue();
        } catch (Exception e) {
            P.err(e.getMessage());
        }
        return 0;
    }
    
    public short getShort() {
        try {
            if (this.var instanceof Integer)
                return ((Integer) this.var).shortValue();
            if (this.var instanceof Float)
                return ((Float) this.var).shortValue();
            if (this.var instanceof Double)
                return ((Double) this.var).shortValue();
            if (this.var instanceof Short)
                return (Short) this.var;
            if (this.var instanceof Long)
                return ((Long) this.var).shortValue();
        } catch (Exception e) {
            P.err(e.getMessage());
        }
        return 0;
    }
    
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
                return (Long) this.var;
        } catch (Exception e) {
            P.err(e.getMessage());
        }
        return 0;
    }
    
    public char getChar() {
        try {
            return (char) this.var;
        } catch (Exception e) {
            P.err(e.getMessage());
        }
        return 0;
    }
    
    public String getString() {
        try {
            return (String) this.var;
        } catch (Exception e) {
            P.err(e.getMessage());
        }
        return null;
    }
    
    public String type() {
        if (this.var == null)
            return "null";
        return this.var.getClass().getSimpleName();
    }

    public String toString() {
        return this.var.toString();
    }
}