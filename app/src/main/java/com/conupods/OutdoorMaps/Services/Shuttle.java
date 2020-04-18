package com.conupods.OutdoorMaps.Services;

public class Shuttle {
    private String[] LOYscheduleStandard;
    private String[] LOYscheduleFriday;
    private String[] SGWscheduleStandard;
    private String[] SGWscheduleFriday;

    public Shuttle() {
        LOYscheduleStandard = new String[] {
                "07:30", "07:40", "07:55", "08:20", "08:35", "08:55", "09:10",
                "09:30", "09:45", "10:20", "10:35", "10:55", "11:10", "11:30",

                "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30",
                "16:00", "16:30", "17:00", "17:30", "18:00", "18:30", "19:30", "20:00",

                "20:30", "20:50", "21:05", "21:30", "22:00", "22:30", "23:00"
        };

        SGWscheduleStandard = new String[] {
                "07:45", "08:05", "08:20", "08:35", "08:55", "09:10", "09:30",
                "09:45", "10:05", "10:20", "10:55", "11:10", "11:45",

                "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30",
                "16:00", "16:30", "17:00", "17:30", "18:00", "18:30", "19:30", "20:00",

                "20:15", "20:30", "21:00", "21:25", "21:45", "22:05", "22:30",
                "23:00"
        };

        LOYscheduleFriday = new String[] {
                "07:40", "08:15", "08:55", "09:30", "10:20", "10:35", "11:10",
                "11:45", "12:20", "12:55", "13:30", "14:05", "14:40", "15:15",
                "15:50", "16:25", "16:40", "17:00", "18:05", "18:40", "19:15",
                "19:50"
        };

        SGWscheduleFriday = new String[] {
                "07:45", "08:20", "08:55", "09:30", "10:05", "10:55", "11:10",
                "11:45", "12:20", "12:55", "13:30", "14:05", "14:40", "15:15",
                "15:50", "16:25", "17:15", "17:30", "18:05", "18:40", "19:15",
                "19:50"
        };
    }

    public String getNextShuttle(String campus, boolean standard, String time) {
        String[] schedule = selectSchedule(campus, standard);

        return parseSchedule(schedule, time);
    }

    private String[] selectSchedule(String campus, boolean standardSchedule) {
        if (standardSchedule) {
            return campus.equalsIgnoreCase("SGW") ? SGWscheduleStandard : LOYscheduleStandard;
        }
        else {
            return campus.equalsIgnoreCase("SGW") ? SGWscheduleFriday : LOYscheduleFriday;
        }
    }

    private String parseSchedule(String[] schedule, String time) {
        for (int i = 0; i < schedule.length; i++) {
            if (Integer.parseInt(formatTime(time)) < Integer.parseInt((formatTime(schedule[i])))) {
                return schedule[i];
            }
        }

        return "na";
    }

    private String formatTime(String time) {
        return time.substring(0, time.indexOf(':')).concat(time.substring(time.indexOf(':' + 1)));
    }
}
