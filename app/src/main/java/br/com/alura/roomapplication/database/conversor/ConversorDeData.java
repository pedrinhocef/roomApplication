package br.com.alura.roomapplication.database.conversor;

import android.arch.persistence.room.TypeConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class ConversorDeData {

    public static final String DATA_PADRAO = "dd/MM/yyyy";
    public static final SimpleDateFormat FORMATADOR = new SimpleDateFormat(DATA_PADRAO);

    @TypeConverter
    public static Long converte(Calendar dataAserConvertida) {
        return dataAserConvertida.getTime().getTime();
    }

    @TypeConverter
    public static Calendar converte(Long tempoEmMilisegundos) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(tempoEmMilisegundos));
        return calendar;
    }

    public static Calendar converte(String data) {
        Calendar calendar = Calendar.getInstance();
        try {
            Date date = FORMATADOR.parse(data);
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar;
    }

    public static String converteDo(Calendar data) {
        String dataFormatada = FORMATADOR.format(data.getTime());
        return dataFormatada;
    }

}
