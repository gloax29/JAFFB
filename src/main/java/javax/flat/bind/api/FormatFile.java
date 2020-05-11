/*
 * Creation : 17 mars 2014
 */
package javax.flat.bind.api;

import java.lang.reflect.Field;

/**
 * @author Gloaguen Joel
 */
public class FormatFile {

    /**
     * Getter name
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter name
     * 
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter header
     * 
     * @return the header
     */
    public boolean isHeader() {
        return header;
    }

    /**
     * Setter header
     * 
     * @param header the header to set
     */
    public void setHeader(boolean header) {
        this.header = header;
    }

    private String name;
    private boolean header = false;

    @Override
    public String toString() {

        Field[] nomChamps = this.getClass().getDeclaredFields();
        StringBuffer formatString = new StringBuffer(this.getClass().getSimpleName() + "(");
        for (Field field : nomChamps) {
            if (!"serialVersionUID".equals(field.getName())) {
                try {
                    Object Valu = this.getClass().getMethod("get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1))
                            .invoke(this, new Object[] {});
                    formatString.append(";[ " + field.getName() + "='" + Valu.toString() + "' ]");
                } catch (Exception e) {
                    formatString.append(";[ " + field.getName() + "='null' ]");
                }

            }
        }
        return formatString.append(")\n").toString().replaceFirst(";", "");

    }
}
