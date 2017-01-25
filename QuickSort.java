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
public class QuickSort {
    
    public static void qsort(ArrayList<DataModel> list) {
        quickSort(list, 0, list.size() - 1);
    
    }
    
    private static void quickSort(ArrayList<DataModel> list, int p, int r) {
        if (p < r) {
            int q = partition(list, p, r);
            
            if (q == r)
                q--;
            
            quickSort(list, p, q);
            quickSort(list, q + 1, r);
            
        }
        
        
    }
    
    private static int partition(ArrayList<DataModel> list, int p, int r) {
        String pivot = KeyPicker.getPrimaryKey(list.get(p));
        int lo = p;
        int hi = r;
        
        while (true) {
            while (KeyPicker.getPrimaryKey(list.get(hi)).compareTo(pivot) >= 0 && lo < hi)
                hi--;
            
            while (KeyPicker.getPrimaryKey(list.get(lo)).compareTo(pivot) < 0 && lo < hi)
                lo++;
            
            if (lo < hi) {
                DataModel t = list.get(lo);
                list.set(lo, list.get(hi));
                list.set(hi, t);
            }
            else return hi;
        
        }
        
        
        
        
    }
    
    
}
