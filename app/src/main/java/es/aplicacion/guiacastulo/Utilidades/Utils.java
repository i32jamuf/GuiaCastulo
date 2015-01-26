package es.aplicacion.guiacastulo.Utilidades;

/**
 * Created by Enmanuel on 22/01/2015.
 */
public class Utils {

    /**
     * Convierte un array de IDs en un String de IDs separados por comas
     * @param ids
     * @return
     */
    public static String crearStringComas(long [] ids){


//Pasamos de long a String
        String[] string_list = new String[ids.length];

        for(int i = 0; i < ids.length; i++){
            string_list[i] = String.valueOf(ids[i]);
        }

//concatenamos con comas
        String result = "";
        boolean first = true;
        for(String string : string_list) {
            if(first) {
                result+=string;
                first=false;
            } else {
                result+=","+string;
            }
        }
        return result;
    }

    /**
     * Convierte un array de Strings (URIs) en un String de URIs separadas por comas
     * @param URIs
     * @return
     */
    public static String crearStringComas(String [] URIs){
        String result = "";
        boolean first = true;
        for(String string : URIs) {
            if(first) {
                result+=string;
                first=false;
            } else {
                result+=","+string;
            }
        }
        return result;
    }


    /**
     * Devuelve un array de String, desde un String separado por comas.
     * @param comas
     * @return
     */
   public static String [] separarStringComasAString(String comas){
       return comas.split(",");
       //String[] results = comas.split(",");
       //return results;
   }

    /**
     * Devuelve un array de String, desde un String separado por comas.
     * @param comas
     * @return
     */
    public static long [] separarStringComasALong(String comas) {

        //sacamos los Strings separados por comas
        String[] results =comas.split(",");
        long[] IDs = new long[results.length];
        for (int i = 0; i < results.length; i++) {
            //convertimos de String a long
            IDs[i] = Long.parseLong(results[i]);
        }

        return IDs;
    }
}