/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package discrepancyfinder;

import java.util.ArrayList;

/**
 *
 * @author sunny
 */
public class BinarySearch {
    
    public static final int KEY_NOT_FOUND = -1;
    
    public static int bsearch(ArrayList<DataModel> list, String key) {
        
        return binarySearch(list, key, 0, list.size() - 1);
    
    }    
    
    private static int binarySearch(ArrayList<DataModel> list, String key, int min, int max) {
        
        if (min > max)
            return KEY_NOT_FOUND;
        
        int mid = (min + max) / 2;
        
        //System.out.println("min = " + min + ", max = " + max + ", mid = " + mid);
        
        if (KeyPicker.getPrimaryKey(list.get(mid)).equals(key)) {
            int i = mid;
        
            //System.out.println("i = " + i);
            
            do {
                i--;
                //System.out.println("i = " + i);
                
            } while (i >= 0 && KeyPicker.getPrimaryKey(list.get(i)).equals(key));
            
            return i + 1;
        
        }
        else if (KeyPicker.getPrimaryKey(list.get(mid)).compareTo(key) > 0)
            return binarySearch(list, key, min, mid - 1);
        else
            return binarySearch(list, key, mid + 1, max);
    
    
    
    }
    
    
    
    
}
