package com.fyxridd.lib.potions;

import com.fyxridd.lib.core.api.hashList.ChanceHashList;
import com.fyxridd.lib.core.api.hashList.ChanceHashListImpl;

import java.util.Random;

public class Time {
    private static final Random r = new Random();

    //1,2,3
    int mode;

    //mode 1
    int time;

    //mode 2
    int min,max;

    //mode 3
    ChanceHashList<Integer> chances;

    public Time(int time) {
        mode = 1;
        this.time = time;
    }

    public Time(int min, int max) {
        mode = 2;
        this.min = min;
        this.max = max;
    }

    public Time(ChanceHashList<Integer> chances) {
        mode = 3;
        this.chances = chances;
    }

    /**
     * 获取随机时间
     * @return 随机时间,>=0
     */
    public int getRandomTime() {
        switch (mode) {
            case 1:
                return time;
            case 2:
                return r.nextInt(max-min+1)+min;
            case 3:
                return chances.getRandom();
        }
        //异常
        return 0;
    }

    /**
     * 从字符串中读取时间信息
     * @param s 定义时间信息字符串
     * @return 异常返回null
     */
    public static Time load(String s) {
        try {
            Time time;
            if (s.contains("-")) {
                int min = Integer.parseInt(s.split("\\-")[0]);
                int max = Integer.parseInt(s.split("\\-")[1]);
                if (min < 0 || min > max) return null;
                time = new Time(min, max);
            }else if (s.contains(":")) {
                ChanceHashList<Integer> chances = new ChanceHashListImpl<Integer>();
                for (String temp:s.split(";")) {
                    //num
                    int num = Integer.parseInt(temp.split(":")[0]);
                    //chance
                    int chance = Integer.parseInt(temp.split(":")[1]);
                    if (num < 0 || chance < 0) return null;
                    chances.addChance(num, chance);
                }
                time = new Time(chances);
            }else {
                int num = Integer.parseInt(s);
                if (num < 0) return null;
                time = new Time(num);
            }
            return time;
        } catch (Exception e) {
            return null;
        }
    }
}
