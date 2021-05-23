package org.china2b2t.azurmgr.listener;

import org.bukkit.scheduler.BukkitRunnable;
import org.china2b2t.azurmgr.Main;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

public class Timed {

    public Timed(){
        Instant instNow = Instant.now();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int startDay = calendar.get(Calendar.DAY_OF_YEAR)+1;
        calendar.set(Calendar.DAY_OF_YEAR,startDay);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Instant instStart = calendar.getTime().toInstant();
        long l = Duration.between(instNow, instStart).toMillis()/1000;
        Main.instance.getServer().getScheduler().runTaskLater(Main.instance,() ->{
            Main.instance.getServer().setWhitelist(false);
            Instant instNow1 = Instant.now();
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(new Date());
            calendar1.set(Calendar.HOUR_OF_DAY, 7);
            calendar1.set(Calendar.MINUTE, 0);
            calendar1.set(Calendar.SECOND, 0);
            calendar1.set(Calendar.MILLISECOND, 0);
            Instant instEnd = calendar1.getTime().toInstant();
            long l1 = Duration.between(instNow1, instEnd).toMinutes()/1000;
            Main.instance.getServer().getScheduler().runTaskLater(Main.instance,() ->{
                Main.instance.getServer().setWhitelist(true);
                new Timed();
            },l*20);
        },l*20);
    }

}
