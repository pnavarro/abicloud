/*
 * The contents of this file are subject to the Mozilla Public License
 * Version 1.1 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 * 
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 * 
 * The Original Code is available at http://www.abiquo.com/.....
 * 
 * The Initial Developer of the Original Code is Soluciones Grid, S.L. (www.abiquo.com),
 * Consell de Cent 296 principal 2�, 08007 Barcelona, Spain.
 * No portions of the Code have been created by third parties. 
 * All Rights Reserved.
 * 
 * Contributor(s): ______________________________________.
 * 
 * Graphical User Interface of this software may be used under the terms
 * of the Common Public Attribution License Version 1.0 (the  "CPAL License",
 * available at http://cpal.abiquo.com), in which case the provisions of CPAL
 * License are applicable instead of those above. In relation of this portions 
 * of the Code, a Legal Notice according to Exhibits A and B of CPAL Licence 
 * should be provided in any distribution of the corresponding Code to Graphical
 * User Interface.
 */
package com.abiquo.util;

import java.util.regex.*;
import java.lang.reflect.*;
import java.security.MessageDigest;
import java.util.*;
import org.apache.log4j.Logger;

/**
 * @author akpor
 */
public class ToString
{

    static Logger logger = Logger.getLogger(ToString.class);

    /**
     * Generic toString method call the overload toString method which takes a reference to an
     * Object and a String
     * 
     * @param obj reference to an object of class Object
     * @return String a String representation of the Object reference passed to it
     */
    public static final String toString(Object obj)
    {

        if (obj != null)
        {
            return toString(obj, "");
        }
        else
        {
            return "{Null}";
        }
    }

    /**
     * Second overloaded toString method
     * 
     * @param obj reference to an object of class Object
     * @param pad A String which can be empty or contains tab character(s)
     * @return String a String representation of the Object reference passed to it
     */
    private static String toString(Object obj, String pad)
    {

        StringBuffer buf = new StringBuffer();

        if (obj == null)
        {
            buf.append("{Null}");

        }
        else
        {
            // A bit of reflection here
            Class cl = obj.getClass();
            String name = cl.getName();

            // See if the name is an inner class
            name = scanForInnerClass(name);

            String startOfString = "";
            String endOfString = "";

            if (!(obj instanceof String))
            {

                String borderStart = "";
                String borderEnd = "";
                String endIndicator = "//end of " + name;
                String newLine = "\n";
                String endPad = pad;
                String space = " ";

                if (obj instanceof ArrayList || obj instanceof Object[] || cl.isArray())
                {
                    borderStart = "[";
                    borderEnd = "]";
                }
                else
                {

                    if (!(obj instanceof Boolean) && !(obj instanceof Integer)
                        && !(obj instanceof Double) && !(obj instanceof Long))
                    {
                        borderStart = "{";
                        borderEnd = "}";
                    }
                    else
                    {
                        name = newLine = endIndicator = space = endPad = "";
                    }
                }
                startOfString = pad + name + space + borderStart + newLine;
                endOfString = endPad + borderEnd + endIndicator + newLine;

            }

            buf.append(startOfString);

            if (obj instanceof Map)
            {
                buf.append(mapToString(obj, pad));

            }
            else if (obj instanceof ArrayList || obj instanceof Vector)
            {
                buf.append(listToString(obj, pad));
            }
            else if (obj instanceof String)
            {
                buf.append(pad + obj);
            }
            else if (obj instanceof StringBuffer)
            {
                buf.append(pad + "\t" + obj + "\n");
            }
            else if (cl.isArray())
            {
                buf.append(arrayToString(obj, pad + "\t"));
            }
            else if (Pattern.compile("^com\\.mysql\\.jdbc\\..*").matcher(name).matches())
            {
                buf.append(pad + "\t" + obj + "\n");
            }
            else
            {
                buf.append(objectToString(obj, pad));
            }

            buf.append(endOfString);
        }

        return buf.toString();

    }// end of toString

    /**
     * Helper method to toString
     * 
     * @param obj
     * @param pad
     * @return String
     */
    private static String arrayToString(Object obj, String pad)
    {

        StringBuffer buf = new StringBuffer();

        // Get the length of the array
        int length = Array.getLength(obj);

        for (int i = 0; i < length; ++i)
        {

            Object object = Array.get(obj, i);

            if (object != null)
            {

                if (object instanceof Boolean || object instanceof Integer
                    || object instanceof Double || object instanceof Long)
                {
                    // buf.append(pad + object + "\n");
                    if (object instanceof Boolean)
                        buf.append(pad + ((Boolean) object).booleanValue() + "\n");
                    if (object instanceof Integer)
                        buf.append(pad + ((Integer) object).intValue() + "\n");
                    if (object instanceof Double)
                        buf.append(pad + ((Double) object).doubleValue() + "\n");
                }
                else
                {
                    buf.append(pad + toString(object, object.getClass(), pad) + "\n");
                }
            }
        }

        return buf.toString();

    }// end of arrayToString

    /**
     * Helper method to mapToString,arrayToString,listToString
     * 
     * @param object a reference to an object of class Object
     * @param cl a reference to a Class obj
     * @param pad
     * @return String containing the representation of a Class !!UPDATE UPDATE
     */
    private static String toString(Object object, Class cl, String pad)
    {

        String name = cl.getName();

        // First make sure that the superclass in not from the java.* package javax.* or com.*
        // or the class variable (of the class object belongs to) getSuperClassDetails is set to
        // true
        if (!Pattern.compile("^java.*|com.*|javax.*").matcher(name).matches()
            || getSuperClassDetails(object, cl))
        {

            // This must be a user defined class but also check if this is an array of Objects
            if (object instanceof Object[])
                return "[\n" + arrayToString(object, pad) + pad + "]\n";
            else
                return addPad(object, pad);

        }
        else
        {
            return (Pattern.compile("^com\\.mysql\\.jdbc\\..*").matcher(name).matches())
                ? (pad + object) : toString(object, pad);
        }
    }

    /**
     * Helper method to toString
     * 
     * @param obj a reference to an object of Object
     * @param pad A String which can be empty or contains tab character(s)
     * @return String containing the representation of a Class !!UPDATE UPDATE
     */
    private static String mapToString(Object obj, String pad)
    {

        StringBuffer buf = new StringBuffer();

        Map map = (Map) obj;

        int padLength = 0;
        Iterator iter = map.keySet().iterator();

        while (iter.hasNext())
        {
            String key = (String) iter.next();
            if (key.length() > padLength)
            {
                padLength = key.length();
            }
        }

        iter = map.entrySet().iterator();

        // Add a tab to pad
        pad += "\t";

        while (iter.hasNext())
        {

            Map.Entry entry = (Map.Entry) iter.next();

            String key = (String) entry.getKey();
            Object val = entry.getValue();
            if (val == null)
                val = "NULL";

            buf.append(pad + padSpaces(key, padLength) + " = ");

            // Now get a String value of val
            String str = (val == null) ? (pad + val) : toString(val, val.getClass(), pad);

            buf.append(str + "\n");
        }

        return buf.toString();

    }// end of iterToString

    /**
     * Helper method to toString
     * 
     * @param obj a reference to an object of Object
     * @param pad A String which can be empty or contains tab character(s)
     * @return String containing the representation of a Class !!UPDATE UPDATE
     */
    private static String listToString(Object obj, String pad)
    {

        StringBuffer buf = new StringBuffer();

        List list = (List) obj;

        Iterator iter = list.listIterator();

        while (iter.hasNext())
        {

            Object val = iter.next();
            buf.append(pad + toString(val, val.getClass(), pad + "\t\t") + "\n");
        }

        return buf.toString();

    }// end of iterToString

    /**
     * Helper method to toString
     * 
     * @param obj a reference to an object of Object
     * @param pad A String which can be empty or contains tab character(s)
     * @return String containing the representation of a Class !!UPDATE UPDATE
     */
    private static String objectToString(Object obj, String pad)
    {

        StringBuffer buf = new StringBuffer();

        Class cl = obj.getClass();

        Field[] fields;

        int padLength = 0;

        // Get the superclass and then see what fields are contained in this super clas but remember
        // this is only for
        // superclasses that I have made not the ones in the java library
        Class supercl = cl.getSuperclass();

        if (supercl != null && supercl != Object.class)
        {

            String superclName = supercl.getName();

            // First make sure that the superclass is not from the java.* package javax.* or com.*
            // or
            // if the static field getSuperClassDetails of the class that the object belong to is
            // true
            if (!Pattern.compile("^java.*|com.*|javax.*").matcher(superclName).matches()
                || getSuperClassDetails(obj, cl))
            {

                fields = supercl.getDeclaredFields();

                padLength = getPadLength(padLength, fields, obj);
                objectToString(fields, obj, buf, pad, padLength);

            }

        }

        if (obj instanceof Boolean || obj instanceof Integer || obj instanceof Double
            || obj instanceof Long)
        {
            // buf.append(pad + object + "\n");
            if (obj instanceof Boolean)
                buf.append(((Boolean) obj).booleanValue());
            if (obj instanceof Integer)
                buf.append(((Integer) obj).intValue());
            if (obj instanceof Double)
                buf.append(((Double) obj).doubleValue());
        }
        else
        {
            // Get all the fields of the object obj
            fields = cl.getDeclaredFields();

            padLength = getPadLength(padLength, fields, obj);
            objectToString(fields, obj, buf, pad, padLength);

            // Now make the fields inaccessible again
            AccessibleObject.setAccessible(fields, false);
        }

        return buf.toString();

    }// end of objectToString

    private static boolean getSuperClassDetails(Object object, Class cl)
    {

        boolean getSuperClassDetails = false;

        try
        {
            getSuperClassDetails =
                (Boolean) cl.getDeclaredField("getSuperClassDetails").get(object);
        }
        catch (Exception e)
        {

        }

        return getSuperClassDetails;
    }

    /**
     * Helper method to setFieldType
     * 
     * @param padLength the initial pad length
     * @param fields an array of Field objects
     * @param obj reference to an object of a any Class
     * @return int containing the pad length
     */
    private static int getPadLength(int padLength, Field[] fields, Object obj)
    {

        // Make the fields Accessible
        AccessibleObject.setAccessible(fields, true);

        String fieldName = "";

        for (int i = 0; i < fields.length; i++)
        {

            Field field = fields[i];

            try
            {
                Object[] properties = getFieldProperties(field, obj);

                fieldName = "" + properties[3] + " " + properties[1];

                int fieldLength = fieldName.length();
                if (fieldLength > padLength)
                {
                    padLength = fieldLength;
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        return padLength;

    }// end of getPadLength

    /**
     * NOTE THIS METHOD HAS A FEW GLITCHES IT IS STILL UNDER DEVELOPMENT Helper method to
     * objectToString
     * 
     * @param padLength the initial pad length
     * @param fields an array of Field objects
     * @param obj reference to an object of a any Class
     * @return int containing the pad length
     */
    private static void objectToString(Field[] fields, Object obj, StringBuffer buf, String pad,
        int padLength)
    {

        for (int i = 0; i < fields.length; i++)
        {

            Field field = fields[i];

            try
            {
                Object[] properties = getFieldProperties(field, obj);

                Object fieldVal = properties[0];
                String fieldName = (String) properties[1];
                Class fieldType = (Class) properties[2];
                String modifier = (String) properties[3] + " ";

                // First add the name of the field to the the StringBuffer buf
                buf.append(pad + "\t" + padSpaces(modifier + fieldName, padLength) + " = ");

                if (fieldVal == null)
                {
                    buf.append("null");
                }
                else
                {

                    if (fieldVal instanceof Map)
                    {
                        buf.append("{\n" + mapToString(fieldVal, pad + "\t") + pad + "\t}//end of "
                            + fieldName);

                    }
                    else if (fieldVal instanceof Object[])
                    {
                        buf.append("[\n" + arrayToString(fieldVal, pad + "\t") + pad + "\t]");

                    }
                    else if (fieldVal instanceof ArrayList || fieldVal instanceof Vector)
                    {
                        buf.append("[\n" + listToString(fieldVal, pad) + pad + "\t]");
                    }
                    else if (fieldVal instanceof StringBuffer || fieldVal instanceof String
                        || fieldType.isPrimitive()
                        || Pattern.compile("^java.*|com.*|javax.*").matcher(fieldName).matches())
                    {

                        if (fieldVal instanceof Boolean || fieldVal instanceof Integer
                            || fieldVal instanceof Double || fieldVal instanceof Long)
                        {
                            if (fieldVal instanceof Boolean)
                                buf.append(pad + ((Boolean) fieldVal).booleanValue() + "\n");
                            if (fieldVal instanceof Integer)
                                buf.append(pad + ((Integer) fieldVal).intValue() + "\n");
                            if (fieldVal instanceof Double)
                                buf.append(pad + ((Double) fieldVal).doubleValue() + "\n");
                        }
                        else
                        {
                            getStringValueOfField(obj, fieldVal, buf, pad);
                        }

                    }
                    else
                    {
                        // This must be a user defined class
                        getStringValueOfField(obj, fieldVal, buf, pad);
                    }
                }

                buf.append("\n");

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

    }// end of objectToString

    private static void getStringValueOfField(Object obj, Object objField, StringBuffer buf,
        String pad)
    {

        // Si el tipo de clase de atributo es igual que el de la clase del objeto al que pertenece
        // obtenemos so el hasCode
        if (objField.getClass().getName().equals(obj.getClass().getName()))
            buf
                .append(addPad(objField.getClass().getName() + "@" + objField.hashCode(), pad
                    + "\t"));
        else
            buf.append(addPad(objField, pad + "\t"));
    }

    /**
     * Helper method to objectToString: APROBADO mÃ¡s documentaciÃ³n requerida
     */
    private static String addPad(Object obj, String pad)
    {

        String[] strs = obj.toString().split("\n");

        StringBuilder strBuilder = new StringBuilder(strs[0] + "\n");

        for (int i = 1; i < strs.length; i++)
        {
            strBuilder.append(pad + strs[i] + "\n");
        }

        return strBuilder.toString();
    }

    /**
     * Helper method to setFieldType
     * 
     * @param field a reference to a Field object
     * @param obj a reference to an Object object
     * @return an array of objects
     */
    private static Object[] getFieldProperties(Field field, Object obj)
    {

        Object[] objs = new Object[4];

        try
        {
            // Get a class representing the field type
            Class fieldType = field.getType();

            String fieldTypeName = fieldType.getName();

            // See if the name is an inner class
            fieldTypeName = scanForInnerClass(fieldTypeName);

            // Get the value of the field represnted by the Field field on the Object obj
            objs[0] = field.get(obj);
            objs[1] = fieldTypeName + " " + field.getName();
            objs[2] = fieldType;
            objs[3] = Modifier.toString(field.getModifiers());

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return objs;

    }// end of getFieldProperties

    /**
     * Helper method to setFieldType
     * 
     * @param str a reference to a String object
     * @return String
     */
    private static String scanForInnerClass(String str)
    {

        if (Pattern.compile(".*\\$.*").matcher(str).matches())
        {
            str = str.replaceAll("\\$", " innerclass ");
        }

        return str;
    }

    /**
     * Pads spaces to a String
     * 
     * @param str a String object containing the string to which spaces should be padded
     * @param maxLength maximum length of the string.
     */
    public static String padSpaces(String str, int maxLength)
    {
        return padSpaces(str, maxLength, " ");
    }

    /**
     * Second overloaded padSpaces method Pads spaces to a String
     * 
     * @param str a String object containing the string to which spaces should be padded
     * @param maxLength maximum length of the string.
     * @param padding a String containing the padding this could be any number of characters
     */
    public static String padSpaces(String str, int maxLength, String padding)
    {

        int difference = maxLength - str.length();

        for (int i = 0; i < difference; i++)
        {
            str += padding;
        }

        return str;
    }

    public static String MD5(String str, boolean... args)
    {

        String retVal = null;
        try
        {

            MessageDigest md = MessageDigest.getInstance("MD5");

            byte[] array = md.digest(str.getBytes("CP1252"));

            StringBuffer sb = new StringBuffer();

            for (int i = 0; i < array.length; ++i)
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));

            retVal = sb.toString();

            boolean convertToUpperCase = (args.length > 0) ? args[0] : false;

            if (convertToUpperCase)
                retVal = retVal.toUpperCase();

        }
        catch (Exception e)
        {
            logger.error(e);
        }
        finally
        {
            return retVal;
        }
    }

    /**
     * Método de uso fácil para imprimir un objeto usando logger.debug
     * 
     * @param obj
     */
    public static void debug(Object obj)
    {
        logger.debug(toString(obj));
    }

    /**
     * Método de uso fácil para imprimir un objeto usando logger.debug
     * 
     * @param obj
     */
    public static void log(Object obj)
    {
        logger.info("\n" + toString(obj) + "\n");
    }

    public static void print(Object obj)
    {
        System.out.println(toString(obj));
    }
}
