import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Youlim Jung on 2017-04-30.
 */
public class AudioInfo {
    private String audioLevel;
    private Date audioDuration;

    public String getAudioLevel() {
        return audioLevel;
    }

    public void setAudioLevel(String audioLevel) {
        this.audioLevel = audioLevel;
    }

    public Date getAudioDuration() { return audioDuration; }

    public void setAudioDuration(Date audioDuration) {
        this.audioDuration = audioDuration;
    }
}
