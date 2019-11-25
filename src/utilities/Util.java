package utilities;

import java.util.ArrayList;

public class Util {
    public Util () { }

    public int getOccurences (ArrayList<String> stringArr, String find) {
        int count = 0;

        if(stringArr.size() == 0) {
            return 0;
        }

        for(String str : stringArr) {
            if(str == find) {
                count++;
            }
        }
        return count ;
    }

    public ArrayList<Integer> getOccurenceSet (ArrayList<String> stringArr) {
        ArrayList<Integer> countSet = new ArrayList<>();

        if(stringArr.size() == 0) {
            countSet.add(0);
            return countSet;
        }

        for(String str : stringArr) {
            countSet.add(getOccurences(stringArr,str));
        }

        return countSet;
    }
}
