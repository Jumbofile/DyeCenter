package utilities;

import java.util.ArrayList;

public class Util {
    public Util () { }

    public int getOccurences (String[] stringArr, String find) {
        int count = 0;

        for(String str : stringArr) {
            if(str == find) {
                count++;
            }
        }
        return count ;
    }

    public ArrayList<Integer> getOccurenceSet (String[] stringArr) {
        ArrayList<Integer> countSet = new ArrayList<>();

        for(String str : stringArr) {
            countSet.add(getOccurences(stringArr,str));
        }

        return countSet;
    }
}
