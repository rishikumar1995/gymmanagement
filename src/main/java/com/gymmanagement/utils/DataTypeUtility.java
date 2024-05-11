package com.gymmanagement.utils;

import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;

public class DataTypeUtility {
    public static String stringValue(Object value) {
        if (value == null || value.toString().equalsIgnoreCase("undefined") || value.toString().equalsIgnoreCase("null")) {
            return "";
        }
        return (value.toString()).trim();
    }

    public static Long longValue(Object value) {
        if (value instanceof String) {
            value = stringValue(value);
            String svalue = DataTypeUtility.stringValue(value);
            if (svalue.equalsIgnoreCase("all") || svalue.equalsIgnoreCase("null") || svalue.length() == 0) {
                return null;
            }
            if (svalue.contains("null")) {
                return null;
            }

            float fvalue = floatObjectValue(value);
            Long val = (long) (float) fvalue;
            return val;
        }
        if (value instanceof Double) {
            Double svalue = (Double) value;
            Long val = (long) (float) (double) svalue;
            return val;
        }
        if (value instanceof Float) {
            Float svalue = (Float) value;
            Long val = (long) (float) svalue;
            return val;
        }
        if (value instanceof BigInteger) {
            BigInteger svalue = (BigInteger) value;
            Long val = svalue.longValue();
            return val;
        }
        if (value instanceof Integer) {
            return (long) (int) (Integer) value;
        }
        return (value == null ? null : (Long) value);
    }

    public static Float floatObjectValue(Object value) {
        try {
            if (value instanceof Float) {
                return ((Float) value).floatValue();
            } else if (value instanceof Integer) {
                return (float) ((Integer) value).intValue();
            } else if (value instanceof Long) {
                return (float) ((Long) value).longValue();
            } else if (value instanceof String) {
                if (DataTypeUtility.stringValue(value).length() > 0) {
                    return (float) (double) Double.parseDouble(DataTypeUtility.stringValue(value));
                }
                return null;
            } else if (value instanceof Double) {
                return (float) ((Double) value).doubleValue();
            }

            return (value == null ? null : (Float) value);
        } catch (Exception e) {
            return 0f;
        }
    }

    public static boolean booleanValue(Object value) {
        if (value == null) {
            return false;
        }
        boolean isboolean = false;
        if (value instanceof Integer) {
            int iday = ((Integer) value).intValue();
            if (iday == 1) {
                isboolean = true;
            }
        } else if (value instanceof Long) {
            long iday = ((Long) value).longValue();
            if (iday == 1l) {
                isboolean = true;
            }
        } else if (value instanceof Short) {
            int iday = ((Short) value).shortValue();
            if (iday == 1) {
                isboolean = true;
            }
        } else if (value instanceof Boolean) {
            isboolean = (Boolean) value;
        } else if (value instanceof String) {
            String str = (String) value;
            if (str.equalsIgnoreCase("on") || str.equalsIgnoreCase("true") || str.equalsIgnoreCase("yes")) {
                return true;
            } else if (str.equalsIgnoreCase("1")) {
                return true;
            }
        }

        return isboolean;

    }

    public static Long getForeignKeyValue(Object value) {


        if (value == null) {
            return null;
        }


        Long val = DataTypeUtility.longValue(value);


        if (val == null || val <= 0l) {
            val = null;
        }
        return val;
    }

    public static String getCurrentDateInIndianFormat() {
        Date date = new Date();
        return DataTypeUtility.getDateObjectInIndianFormat(date);
    }

    public static String getDateObjectInIndianFormat(Date date) {
        if (date != null) {
            return new SimpleDateFormat("dd-MM-yyyy").format(date);
        }
        return "";
    }
    public static String getCurrentDateTimeInIndianFormatUI() {
        Date date = getCurrentDateTime();
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        date = cal.getTime();
        return DataTypeUtility.getDateTimeObjectInIndianFormat(date);
    }

    public static String getDateTimeObjectInIndianFormat(Date date) {

        if (date != null) {
            return new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(date);
        }
        return "";
    }

    public static Date getCurrentDateTime() {
        Date date = new Date();
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        date = cal.getTime();
        return date;
    }

    public static  <T> Map<Long, Object> getIdFieldMap(JpaRepository<T, ?> repo, String fieldName) throws IllegalAccessException, NoSuchFieldException {
        Map<Long, Object> map = new HashMap<>();
        List<T> list = repo.findAll();
        if (list==null || list.size()==0) return null;
        Class<?> objClass = list.get(0).getClass();
        Field idField = objClass.getDeclaredField("id");
        idField.setAccessible(true);
        Field field = objClass.getDeclaredField(fieldName);
        field.setAccessible(true);
        for (T obj : list) {
            Long key = (Long) idField.get(obj);
            Object value = field.get(obj);
            map.put(key, value);
        }
        return map;
    }

    public static int integerValue(Object value) {
//        //// System.out.println("inside integerValue " + value instanceof String);
        if (value instanceof String) {
            String svalue = DataTypeUtility.stringValue(value);
            if (svalue.length() == 0) {
                return 0;
            } else if (svalue.equalsIgnoreCase("null")) {
                return 0;
            }
            return Integer.parseInt(svalue);
        }

        if (value instanceof Double) {
            return (int) (double) (Double) value;
        }
        if (value instanceof Float) {
            return (int) (float) (Float) value;
        }
        if (value instanceof Long) {
            return (int) (long) (Long) value;
        }
        return (value == null ? 0 : (Integer) value);
    }

    public static Double doubleZeroValue(Object value) {
        if (value != null && !value.toString().equalsIgnoreCase("undefined")) {
            if (value instanceof Float) {
                Float svalue = (Float) value;
                Double val = Double.valueOf(svalue.toString());
                return val;
            }
            if (value instanceof Long) {
                Long svalue = (Long) value;
                Double val = (double) (long) svalue;
                return val;
            }

            if (value.toString().length() != 0) {
                return Double.valueOf(value.toString());
            }
        }
        return 0d;
    }

}
