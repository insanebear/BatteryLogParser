import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by Youlim Jung on 2017-05-04.
 */
public class Information {
    private final String DATE_FORMAT = "HH'h 'mm'm 'ss's 'SSS'ms'";

//    public Date strToDate(String strTime) throws ParseException {
////        SimpleDateFormat trasStr = new SimpleDateFormat("HH:mm:ss:SSS");
//        SimpleDateFormat transStr = new SimpleDateFormat(DATE_FORMAT);
//        System.out.println(strTime);
//        return transStr.parse(strTime);
//    }
//
//    public String dateToStr(Date duration){
//        // Maybe change return type later
//        SimpleDateFormat transStr = new SimpleDateFormat(DATE_FORMAT);
//        return transStr.format(duration);
//    }

    public String calculateDuration(String end, String start){
        int[] startTime = strToInt(start);
        int[] endTime = strToInt(end);
        int[] res = new int[startTime.length];

        for(int i=0; i<endTime.length; i++){
            int interVal = endTime[i]-startTime[i];
            if(interVal<0){
                res[i-1] -= 1;
                if(i!=3){ // h, m, s
                    res[i] = 60+interVal;
                }else{ // ms
                    res[i] = 1000+interVal;
                }
            }else{
                res[i] = interVal;
            }
        }

        return intToStr(res);
    }

    public String addDuration(String sumDuration, String newDuration){
        int[] totDuration = strToInt(sumDuration);
        int[] element = strToInt(newDuration);

        for(int i=0; i<totDuration.length; i++){
            int interVal = totDuration[i]+element[i];

            if(interVal>=60){
                if(interVal>=1000){ // ms
                    interVal-=1000;
                }else{ // h, m, s
                    interVal-=60;
                }
                totDuration[i-1]+=1;
                totDuration[i] = interVal;
            }else{
                totDuration[i] = interVal;
            }
        }
        return intToStr(totDuration);
    }

    private int[] strToInt(String time){
        String[] s = time.split(" ");
        int[] res = new int[s.length];

        // strip char
        s[0] = s[0].substring(0, s[0].length()-1);
        s[1] = s[1].substring(0, s[1].length()-1);
        s[2] = s[2].substring(0, s[2].length()-1);
        s[3] = s[3].substring(0, s[3].length()-2);

        for (int i=0; i<s.length; i++) {
            res[i] = Integer.parseInt(s[i]);
        }
        return res;
    }

    private String intToStr(int[] time){
        String[] strings = new String[time.length];

        strings[0] = String.valueOf(time[0]);
        strings[1] = String.valueOf(time[1]);
        strings[2] = String.valueOf(time[2]);
        strings[3] = String.valueOf(time[3]);

        return padZero(strings);
    }
    public String padZero(String[] strings){
        for (int i=0; i<strings.length; i++) {
            if(strings[i]==null && i != 3){ // no digit h, m, s
                strings[i] = "00";
            }else if(strings[i].length()==1 && i != 3){ // 1 digit h, m, s
                strings[i] = "0"+strings[i];
            }else if(i == 3){ // ms
                if(strings[i].length() == 1){
                    strings[i] = "00"+strings[i];
                }else if (strings[i].length() == 2){
                    strings[i] = "0"+strings[i];
                }else if(strings[i]==null){
                    strings[i] = "000";
                }
            }
        }
        return addUnits(strings);
    }

    private String addUnits(String[] values){
        return values[0]+"h "+values[1]+"m "+values[2]+"s "+values[3]+"ms";
    }

}
